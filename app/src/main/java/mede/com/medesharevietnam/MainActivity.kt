package mede.com.medesharevietnam

import a.mnisdh.com.kotlingooglemap.googleMapAPIs.GoogleMapFragment
import a.mnisdh.com.kotlingooglemap.util.PermissionUtil
import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.databinding.DataBindingUtil
import android.location.Location
import android.net.Uri
import android.os.Bundle
import android.support.design.widget.BottomSheetBehavior
import android.support.v7.app.ActionBar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.LayoutInflater
import android.view.View
import com.google.android.gms.location.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.tasks.OnSuccessListener
import kotlinx.android.synthetic.main.activity_main.*
import mede.com.medesharevietnam.common.Const
import mede.com.medesharevietnam.controller.*
import mede.com.medesharevietnam.databinding.BottomDoctorInfomationBinding
import mede.com.medesharevietnam.databinding.BottomHospitalInfomationBinding
import mede.com.medesharevietnam.domain.match.Doctor
import mede.com.medesharevietnam.domain.medical.MediDisease
import mede.com.medesharevietnam.domain.medical.MediLocation
import mede.com.medesharevietnam.domain.medical.MedicalManager


class MainActivity : AppCompatActivity() {
    private val REQ_PERMISSION: Int = 991
    private var successedPermissions: ArrayList<String> = ArrayList()

    lateinit var mapFragment: GoogleMapFragment
    lateinit var bottomBinding: BottomDoctorInfomationBinding
    lateinit var bottomSheetBehavior: BottomSheetBehavior<View>
    lateinit var bottomHospitalBinding: BottomHospitalInfomationBinding
    lateinit var bottomSheetHospitalBehavior: BottomSheetBehavior<View>
    var locationClient: FusedLocationProviderClient? = null
    var selectedDisease: MediDisease? = null

    var markerLocation: Marker? = null
    lateinit var markerHospital: Marker
    var markers: ArrayList<Marker> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setCustomActionbar()
        init()
    }

    private fun permissionCheck(activity: Activity, permissions: ArrayList<String>, success: (() -> Unit), failed: (() -> Unit)){
        if(permissions.count() > 0) {
            val permissionUtil = PermissionUtil(REQ_PERMISSION, permissions)
            permissionUtil.check(activity, success, failed)
        }
    }

    @SuppressLint("MissingPermission")
    private fun checkLocationPermission(use: Boolean) {
        var permission: String = Manifest.permission.ACCESS_FINE_LOCATION

        if (use) {
            if (!successedPermissions.contains(permission)) {
                var permissions: ArrayList<String> = ArrayList()
                permissions.add(permission)
                permissionCheck(this, permissions, {
                    successedPermissions.add(permission)
                    initLocationClient()
                }, {
                    initFirstLocation(markerHospital.position.latitude, markerHospital.position.longitude)
                })
            } else if(use) initLocationClient()
        } else if(use) initLocationClient()
    }

    @SuppressLint("MissingPermission")
    private fun initLocationClient(){
        locationClient = LocationServices.getFusedLocationProviderClient(this)

        locationClient!!.lastLocation.addOnSuccessListener(this, object : OnSuccessListener<Location> {
            override fun onSuccess(location:Location?) {
                if (location != null) {
                    updateMarkerLocation(location.latitude, location.longitude)
                    initFirstLocation(location.latitude, location.longitude)
                }
            }
        })

        var request = LocationRequest()
        request.interval = 1000
        request.fastestInterval = 5000
        request.priority = LocationRequest.PRIORITY_HIGH_ACCURACY

        locationClient!!.requestLocationUpdates(request, object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult?) {
                for (location in locationResult!!.locations) {
                    updateMarkerLocation(location.latitude, location.longitude)
                }
            }
        }, null)
    }

    fun initFirstLocation(lat: Double, lng: Double){
        mapFragment.moveToCamera(LatLng(lat, lng), 13F)
    }

    fun init() {
        mapFragment = supportFragmentManager.findFragmentById(R.id.map) as GoogleMapFragment
        mapFragment.setOnMapReadied{
            bottomBinding = DataBindingUtil.bind(bottomSheet)
            bottomSheetBehavior = BottomSheetBehavior.from(bottomBinding.root)

            var hanoi = MedicalManager.getHanoiHospital()
            var latLng = LatLng(hanoi.lat, hanoi.lng)
            markerHospital = mapFragment.addMarker(latLng, hanoi.name, R.drawable.ic_48_pin_hospital)

            bottomHospitalBinding = DataBindingUtil.bind(bottomSheetHospital)
            bottomSheetHospitalBehavior = BottomSheetBehavior.from(bottomHospitalBinding.root)
            bottomHospitalBinding.hospital = hanoi

            checkLocationPermission(true)

            initView()
        }
        mapFragment.setOnMarkerClicked { marker ->
            if(marker == markerHospital){
                bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
                bottomSheetHospitalBehavior.state = BottomSheetBehavior.STATE_EXPANDED
            }
            else
            {
                bottomSheetHospitalBehavior.state = BottomSheetBehavior.STATE_COLLAPSED

                var isDoctor = false

                if (marker != null && marker.tag != null) {
                    var mediLocation = marker.tag as MediLocation

                    if (mediLocation.type == "1") {
                        isDoctor = true
                        var doctor = Doctor()
                        doctor.key = mediLocation.key
                        doctor.name = mediLocation.name
                        doctor.rank = "4.2"
                        doctor.subjectName = mediLocation.getMediSubject()!!.name

                        bottomBinding.doctor = doctor
                    }
                }

                if(isDoctor) bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED)
                else bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED)
            }


        }
        mapFragment.setOnMapClicked {
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
            bottomSheetHospitalBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
        }
        mapFragment.getMapAsync(mapFragment)
    }

    private fun initView(){
        tgUseLocation.setOnCheckedChangeListener { _, isChecked -> if(isChecked) onLocationChange() }
        tgUseFavorite.setOnCheckedChangeListener { _, isChecked -> if(isChecked) onLocationChange() }
        tgUseHospital.setOnCheckedChangeListener { _, isChecked -> if(isChecked) onLocationChange() }
    }

    private fun setCustomActionbar() {
        val actionBar = supportActionBar

        if(actionBar != null){
            actionBar.setDisplayShowCustomEnabled(true)
            actionBar.setDisplayHomeAsUpEnabled(false)
            actionBar.setDisplayShowTitleEnabled(false)
            actionBar.elevation = 0f

            val mCustomView = LayoutInflater.from(this).inflate(R.layout.custom_action_bar, null)
            val params = ActionBar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT)
            actionBar.setCustomView(mCustomView, params)

            val parent = mCustomView.getParent() as Toolbar
            parent.setContentInsetsAbsolute(0, 0)
        }
    }

    private fun onLocationChange(){
        var markers = ArrayList<Marker>()

        if(tgUseLocation.isChecked && markerLocation != null) markers.add(markerLocation!!)
        //if(btnUseFavorite.isChecked)
        if(tgUseHospital.isChecked) markers.add(markerHospital)

        if(markers.size == 1) mapFragment.moveToCamera(markers.get(0), 13F)
        else if(markers.size > 1) mapFragment.zoomToFit(markers, 10)
    }

    private fun clearMarker(){
        for(marker in markers){
            marker.remove()
        }

        markers.clear()
    }

    private fun updateMarkerLocation(lat:Double, lng:Double){
        var latLng = LatLng(lat, lng)
        if(markerLocation == null){
            markerLocation = mapFragment.addCenterMarker(latLng, "My location", R.drawable.ic_75_point)
        }
        else markerLocation!!.position = latLng
    }

    fun onSearch(v: View) {
        val intent = Intent(this, SearchActivity::class.java)
        startActivityForResult(intent, Const.REQ_DISEASE_AUTOCOMPLETE)
        overridePendingTransition(0, 0)
    }

    fun search(selectedDisease: MediDisease?){
        clearMarker()

        var locations = selectedDisease!!.getMediSubject()!!.getLocations()

        for(location in locations){
            var latlng = LatLng(location.lat, location.lng)
            var icon = if(location.type == "1") R.drawable.ic_48_pin_doctor else R.drawable.ic_48_pin_pharmacy

            var marker = mapFragment.addMarker(latlng, location.name, icon)
            marker.tag = location
            markers.add(marker)
        }

        mapFragment.zoomToFit(markers, 10)
    }

    fun onCheckup(v: View){
        val intent = Intent(this, HanoiDeptActivity::class.java)
        startActivity(intent)
    }

    fun onMediSearch(v: View){
        if(selectedDisease != null){
            clearMarker()

            var locations = selectedDisease!!.getMediSubject()!!.getLocations()

            for(location in locations){
                var latlng = LatLng(location.lat, location.lng)
                var icon = if(location.type == "1") R.drawable.ic_48_pin_doctor else R.drawable.ic_48_pin_pharmacy

                var marker = mapFragment.addMarker(latlng, location.name, icon)
                marker.tag = location
                markers.add(marker)
            }

            mapFragment.zoomToFit(markers, 10)
        }
    }

    private fun useDoctorMatch():Boolean{
        return (bottomSheetBehavior.state == BottomSheetBehavior.STATE_EXPANDED && bottomBinding.doctor != null)
    }

    fun onCall(v: View) {
        if (useDoctorMatch()) {
            val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + bottomBinding.doctor!!.phoneNumber))
            startActivity(intent)
        }
    }

    fun onChat(v: View){
        if (useDoctorMatch()) {
            val intent = Intent(this, ChatActivity::class.java)
            intent.putExtra(Const.EXT_DOCTOR_NAME, bottomBinding.doctor!!.name)
            startActivity(intent)
        }
    }

    fun onMatching(v: View){
        if(useDoctorMatch()){
            val intent = Intent(this, DoctorMatchActivity::class.java)
            intent.putExtra(Const.EXT_DOCTOR_KEY, bottomBinding.doctor!!.key)
            startActivityForResult(intent, Const.REQ_DOCTOR_MATCH)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(requestCode == Const.REQ_DISEASE_AUTOCOMPLETE){
            if(resultCode == RESULT_OK) {
                selectedDisease = data?.getSerializableExtra("Disease") as MediDisease
                search(selectedDisease)
            }
        }
        else if(requestCode == Const.REQ_DOCTOR_MATCH){
            if(resultCode == RESULT_OK) {
                val intent = Intent(this, MatchingInfoActivity::class.java)
                intent.putExtra(Const.EXT_DOCTOR_KEY, data?.getStringExtra(Const.EXT_DOCTOR_KEY))
                startActivity(intent)
            }
        }
    }
}
