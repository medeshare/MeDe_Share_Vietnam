package mede.com.medesharevietnam.controller

import a.mnisdh.com.kotlingooglemap.googleMapAPIs.GoogleMapFragment
import a.mnisdh.com.kotlingooglemap.googleMapAPIs.domain.directions.Routes
import a.mnisdh.com.kotlingooglemap.util.PermissionUtil
import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.databinding.DataBindingUtil
import android.graphics.Color
import android.location.Location
import android.net.Uri
import android.os.Bundle
import android.support.design.widget.BottomSheetBehavior
import android.support.design.widget.BottomSheetBehavior.STATE_EXPANDED
import android.support.v7.app.ActionBar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import com.google.android.gms.location.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.Polyline
import com.google.android.gms.tasks.OnSuccessListener
import kotlinx.android.synthetic.main.activity_matching_info.*
import kotlinx.android.synthetic.main.bottom_doctor_infomation.*
import mede.com.medesharevietnam.R
import mede.com.medesharevietnam.common.Const
import mede.com.medesharevietnam.databinding.BottomDoctorInfomationBinding
import mede.com.medesharevietnam.domain.match.Doctor
import mede.com.medesharevietnam.domain.medical.MediDisease
import mede.com.medesharevietnam.domain.medical.MediLocation
import mede.com.medesharevietnam.domain.medical.MedicalManager

class MatchingInfoActivity : AppCompatActivity() {
    private val REQ_PERMISSION: Int = 991
    private var successedPermissions: ArrayList<String> = ArrayList()

    lateinit var mapFragment: GoogleMapFragment
    lateinit var bottomBinding: BottomDoctorInfomationBinding
    lateinit var bottomSheetBehavior: BottomSheetBehavior<View>
    var locationClient: FusedLocationProviderClient? = null
    var selectedDisease: MediDisease? = null

    lateinit var currentDoctor: Doctor
    lateinit var currentDoctorLocation: MediLocation

    var markerLocation: Marker? = null
    lateinit var markerHospital: Marker
    var markerDoctor: Marker? = null

    var isWalking = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_matching_info)

        var doctorKey = intent.getStringExtra(Const.EXT_DOCTOR_KEY)
        if(doctorKey == null && doctorKey == "") {
            setResult(Activity.RESULT_CANCELED)
            finish()
        }

        currentDoctorLocation = MedicalManager.getLocation(doctorKey)!!
        currentDoctor = Doctor()
        currentDoctor.key = currentDoctorLocation.key
        currentDoctor.name = currentDoctorLocation.name
        currentDoctor.subjectName = currentDoctorLocation.getMediSubject()!!.name
        currentDoctor.imageUrl = "http://cdn.ajoumc.or.kr/Upload/MedicalCenter/Doctor/Profile/201303/104382.jpg"

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
            bottomSheetBehavior.setBottomSheetCallback(object: BottomSheetBehavior.BottomSheetCallback() {
                override fun onStateChanged(bottomSheet: View, newState: Int) {
                    if (newState == STATE_EXPANDED) {
                        tvSubjectName.visibility = VISIBLE
                    } else {
                        tvSubjectName.visibility = INVISIBLE
                    }
                }
                override fun onSlide(bottomSheet: View, slideOffset: Float) {

                }
            })
            bottomSheetBehavior.state = STATE_EXPANDED

            var latLng = LatLng(21.083026, 105.780140)
            markerHospital = mapFragment.addMarker(latLng, "하노이 공공의과대학교(하노이대학병원)", R.drawable.ic_48_pin_hospital)

            bottomBinding.doctor = currentDoctor
            updateMarkerDoctor(currentDoctorLocation.lat, currentDoctorLocation.lng)

            checkLocationPermission(true)

            initView()
        }
        mapFragment.setOnMapClicked {
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
        }
        mapFragment.getMapAsync(mapFragment)
    }

    @SuppressLint("ResourceAsColor")
    private fun initView(){
        tgUseLocation.setOnCheckedChangeListener { _, isChecked -> if(isChecked) onLocationChange() }
        tgUseFavorite.setOnCheckedChangeListener { _, isChecked -> if(isChecked) onLocationChange() }
        tgUseHospital.setOnCheckedChangeListener { _, isChecked -> if(isChecked) onLocationChange() }

        btnMatching.text = "Cancel"
        btnMatching.setBackgroundColor(R.color.btn_next_disable)

        val scale = resources.displayMetrics.density
        bottomSheetBehavior.peekHeight = (135.0f * scale).toInt()
    }

    private fun setCustomActionbar() {
        val actionBar = supportActionBar

        actionBar!!.setDisplayShowCustomEnabled(true)
        actionBar.setDisplayHomeAsUpEnabled(false)
        actionBar.setDisplayShowTitleEnabled(false)
        actionBar.elevation=0f


        val mCustomView = LayoutInflater.from(this).inflate(R.layout.custom_action_bar, null)
        actionBar.setCustomView(mCustomView)

        val parent = mCustomView.getParent() as Toolbar
        parent.setContentInsetsAbsolute(0, 0)

        val params = ActionBar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT)
        actionBar.setCustomView(mCustomView, params)
    }

    private fun onLocationChange(){
        var markers = ArrayList<Marker>()

        if(tgUseLocation.isChecked && markerLocation != null) markers.add(markerLocation!!)
        //if(btnUseFavorite.isChecked)
        if(tgUseHospital.isChecked) markers.add(markerHospital)

        if(markers.size == 1) mapFragment.moveToCamera(markers.get(0), 13F)
        else if(markers.size > 1) mapFragment.zoomToFit(markers, 10)
    }

    private fun updateMarkerLocation(lat:Double, lng:Double){
        var latLng = LatLng(lat, lng)
        if(markerLocation == null){
            markerLocation = mapFragment.addCenterMarker(latLng, "My location", R.drawable.ic_75_point)
        }
        else markerLocation!!.position = latLng

        if(isWalking) drawWalking()
        else drawDriving()
    }

    private fun updateMarkerDoctor(lat:Double, lng:Double){
        var latLng = LatLng(lat, lng)
        if(markerDoctor == null){
            markerDoctor = mapFragment.addMarker(latLng, currentDoctorLocation.name, R.drawable.ic_48_pin_doctor)
        }
        else markerDoctor!!.position = latLng
    }

    fun onCall(v: View) {
        val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + bottomBinding.doctor!!.phoneNumber))
        startActivity(intent)
    }

    fun onMatching(v: View){
        setResult(Activity.RESULT_CANCELED)
        finish()
    }

    fun drawDriving(){
        var start = markerLocation!!.position

        mapFragment.getDirection(
                start,
                markerDoctor!!.position,
                "driving",
                {direction ->
                    Log.d("driving count:",direction.routes.count().toString())

                    if (direction.routes.count() > 0) setDirection(direction.routes)
                    else setDirection(null)
                })
    }

    fun drawWalking(){
        var start = markerLocation!!.position

        mapFragment.getDirection(
                start,
                markerDoctor!!.position,
                "walking",
                {direction ->
                    Log.d("walking count:",direction.routes.count().toString())

                    if (direction.routes.count() > 0) setDirection(direction.routes)
                    else setDirection(null)
                })
    }

    var directionLines: ArrayList<Polyline> = ArrayList()
    private fun initDirection(){
        for(direction in directionLines) direction.remove()

        directionLines.clear()
    }
    private fun setDirection(routes: ArrayList<Routes>?) {
        initDirection()

        if(routes != null) {
            var isFirst = true
            var color = Color.parseColor("#21c9d9")

            var minLat = 99999999.9
            var maxLat = -99999999.9
            var minLng = 99999999.9
            var maxLng = -99999999.9

            for (route in routes) {
                var southwest = route.bounds.getBounds().southwest
                var northeast = route.bounds.getBounds().northeast

                minLat = if (minLat > southwest.latitude) southwest.latitude else minLat
                minLng = if (minLng > southwest.longitude) southwest.longitude else minLng
                maxLat = if (maxLat < northeast.latitude) northeast.latitude else maxLat
                maxLng = if (maxLng < northeast.longitude) northeast.longitude else maxLng

                directionLines.add(mapFragment.drawPolyline(route.overview_polyline.getPoints(), color, 15F))

                if (isFirst) {
                    color = Color.DKGRAY
                    isFirst = false
                }
            }

            mapFragment.zoomToFit(LatLng(minLat - 0.05, minLng - 0.05), LatLng(maxLat + 0.05, maxLng + 0.05))
        }
    }
}
