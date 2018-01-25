package mede.com.medesharevietnam

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
import android.support.v7.app.ActionBar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import com.google.android.gms.location.*
import com.google.android.gms.location.places.ui.PlaceAutocomplete
import com.google.android.gms.location.places.ui.PlacePicker
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.Polyline
import com.google.android.gms.tasks.OnSuccessListener
import kotlinx.android.synthetic.main.activity_main.*
import mede.com.medesharevietnam.common.Const
import mede.com.medesharevietnam.controller.DoctorMatchActivity
import mede.com.medesharevietnam.databinding.BottomDoctorInfomationBinding
import mede.com.medesharevietnam.domain.match.Doctor
import mede.com.medesharevietnam.domain.medical.MediDisease
import mede.com.medesharevietnam.domain.medical.MediLocation


class MainActivity : AppCompatActivity() {
    private val REQ_PERMISSION: Int = 991
    private var successedPermissions: ArrayList<String> = ArrayList()

    lateinit var mapFragment: GoogleMapFragment
    lateinit var bottomBinding: BottomDoctorInfomationBinding
    lateinit var bottomSheetBehavior: BottomSheetBehavior<View>
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

            var latLng = LatLng(21.083026, 105.780140)
            markerHospital = mapFragment.addMarker(latLng, "하노이 공공의과대학교(하노이대학병원)", R.drawable.ic_48_pin_hospital)

            checkLocationPermission(true)

            initView()
        }
        mapFragment.setOnMarkerClicked { marker ->
            var isDoctor = false

            if (marker != null && marker.tag != null) {
                var mediLocation = marker.tag as MediLocation

                if(mediLocation != null){
                    if(mediLocation.type == "1"){
                        isDoctor = true
                        var doctor = Doctor()
                        doctor.key = mediLocation.key
                        doctor.name = mediLocation.name
                        doctor.rank = "4.2"
                        doctor.subjectName = mediLocation.getMediSubject().name

                        bottomBinding.doctor = doctor
                    }
                }
            }

            if(isDoctor) bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED)
            else bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED)
        }
        mapFragment.setOnMapClicked {
            var bottomSheetBehavior = BottomSheetBehavior.from(bottomBinding.root)
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED)
        }
        mapFragment.getMapAsync(mapFragment)
    }

    private fun initView(){
        tgUseLocation.setOnCheckedChangeListener { buttonView, isChecked -> if(isChecked) onLocationChange() }
        tgUseFavorite.setOnCheckedChangeListener { buttonView, isChecked -> if(isChecked) onLocationChange() }
        tgUseHospital.setOnCheckedChangeListener { buttonView, isChecked -> if(isChecked) onLocationChange() }
    }

    private fun getTempMediDisease():ArrayList<MediDisease>{
        var diseases = ArrayList<MediDisease>();
        diseases.add(MediDisease("s01", "m01", "dislocation", ""))
        diseases.add(MediDisease("s02", "m01", "fracture", ""))
        diseases.add(MediDisease("s03", "m01", "nerve pain (sharp pain)", ""))
        diseases.add(MediDisease("s04", "m01", "musculoskeletal pain", ""))
        diseases.add(MediDisease("s05", "m01", "Back pain", ""))
        diseases.add(MediDisease("s06", "m01", "lie back", ""))
        diseases.add(MediDisease("s07", "m01", "shoulder pain", ""))
        diseases.add(MediDisease("s08", "m01", "neck pain", ""))
        diseases.add(MediDisease("s09", "m01", "Movement ", ""))
        diseases.add(MediDisease("s10", "m02", "Stomach pain", ""))
        diseases.add(MediDisease("s11", "m02", "chest pain", ""))
        diseases.add(MediDisease("s12", "m02", "Breathing difficulty", ""))
        diseases.add(MediDisease("s13", "m02", "fever/temperature", ""))
        diseases.add(MediDisease("s14", "m02", "cold", ""))
        diseases.add(MediDisease("s15", "m02", "vomit", ""))
        diseases.add(MediDisease("s16", "m02", "heart", ""))
        diseases.add(MediDisease("s17", "m02", "skin check", ""))
        diseases.add(MediDisease("s18", "m02", "diabetes", ""))
        diseases.add(MediDisease("s19", "m02", "high blood pressure", ""))
        diseases.add(MediDisease("s20", "m02", "travel medicine", ""))
        diseases.add(MediDisease("s21", "m02", "Immunisation", ""))
        diseases.add(MediDisease("s22", "m02", "sexual health", ""))
        diseases.add(MediDisease("s23", "m02", "Mental health", ""))
        diseases.add(MediDisease("s24", "m02", "flu vaccination", ""))
        diseases.add(MediDisease("s25", "m03", "Sore throat", ""))
        diseases.add(MediDisease("s26", "m03", "ear pain", ""))
        diseases.add(MediDisease("s27", "m03", "hearing loss", ""))
        diseases.add(MediDisease("s28", "m03", "cough", ""))
        diseases.add(MediDisease("s29", "m03", "vocal change", ""))
        diseases.add(MediDisease("s30", "m03", "blocked nose", ""))
        diseases.add(MediDisease("s31", "m03", "nose bleed (epistaxis)", ""))
        diseases.add(MediDisease("s32", "m04", "Vision changes", ""))
        diseases.add(MediDisease("s33", "m04", "eye pain", ""))
        diseases.add(MediDisease("s34", "m04", "eye discharge", ""))
        diseases.add(MediDisease("s35", "m04", "double vision (diplopia)", ""))
        diseases.add(MediDisease("s36", "m04", "eye pressure", ""))
        diseases.add(MediDisease("s37", "m05", "toothache", ""))
        diseases.add(MediDisease("s38", "m05", "gum bleeding", ""))
        diseases.add(MediDisease("s39", "m05", "tooth fracture", ""))
        diseases.add(MediDisease("s40", "m05", "Dental care", ""))
        diseases.add(MediDisease("s41", "m05", "teeth whitening", ""))

        return diseases
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

    private fun clearMarker(){
        for(marker in markers){
            marker.remove()
        }

        markers.clear()
    }

    private fun updateMarkerLocation(lat:Double, lng:Double){
        var latLng = LatLng(lat, lng)
        if(markerLocation == null){
            markerLocation = mapFragment.addMarker(latLng, "My location", R.drawable.ic_75_point)
        }
        else markerLocation!!.position = latLng
    }

    fun onSearch(v: View){
        val intent = Intent(this, SearchActivity::class.java)
        startActivityForResult(intent, Const.REQ_DISEASE_AUTOCOMPLETE)
        overridePendingTransition(0, 0)
    }

    fun search(selectedDisease: MediDisease?){
        clearMarker()

        var locations = selectedDisease!!.getMediSubject().getLocations()

        for(location in locations){
            var latlng = LatLng(location.lat, location.lng)
            var icon = 0
            if(location.type == "1") icon = R.drawable.ic_48_pin_doctor
            else icon = R.drawable.ic_48_pin_pharmacy

            var marker = mapFragment.addMarker(latlng, location.name, icon)
            marker.tag = location
            markers.add(marker)
        }

        mapFragment.zoomToFit(markers, 10)
    }

    fun onMediSearch(v: View){
        if(selectedDisease != null){
            clearMarker()

            var locations = selectedDisease!!.getMediSubject().getLocations()

            for(location in locations){
                var latlng = LatLng(location.lat, location.lng)
                var icon = 0
                if(location.type == "1") icon = R.drawable.ic_48_pin_doctor
                else icon = R.drawable.ic_48_pin_pharmacy

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

    fun onMatching(v: View){
        if(useDoctorMatch()){
            val intent = Intent(this, DoctorMatchActivity::class.java)
            intent.putExtra(Const.EXT_DOCTOR_KEY, bottomBinding.doctor!!.key)
            startActivityForResult(intent, Const.REQ_DOCTOR_MATCH)
        }
    }

    fun onDriving(v: View){
        mapFragment?.getDirection(
                LatLng(21.004060, 105.840235),
                LatLng(21.027544, 105.845984),
                "driving",
                {direction ->
                    Log.d("driving count:",direction.routes.count().toString())

                    if (direction.routes.count() > 0) setDirection(direction.routes)
                    else Log.d("onDriving", "is not found")
                })
    }

    fun onWalking(v: View){
        mapFragment?.getDirection(
                LatLng(21.004060, 105.840235),
                LatLng(21.027544, 105.845984),
                "walking",
                {direction ->
                    Log.d("walking count:",direction.routes.count().toString())

                    if (direction.routes.count() > 0) setDirection(direction.routes)
                    else Log.d("onWalking", "is not found")
                })
    }

    fun onSearching(v: View){
        val intent = PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_OVERLAY).build(this)
        startActivityForResult(intent, Const.REQ_PLACE_AUTOCOMPLATE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(requestCode == Const.REQ_PLACE_AUTOCOMPLATE){
            if (resultCode == RESULT_OK) {
                val place = PlacePicker.getPlace(data, this)
                //tvSearch.text = place.name
                
                mapFragment.addMarker(place.latLng, "")
            }
        }else if(requestCode == Const.REQ_DISEASE_AUTOCOMPLETE){
            if(resultCode == RESULT_OK) {
                selectedDisease = data?.getSerializableExtra("Disease") as MediDisease
                search(selectedDisease)
            }
        }
    }

    var directionLines: ArrayList<Polyline> = ArrayList()
    private fun initDirection(){
        for(direction in directionLines) direction.remove()

        directionLines.clear()
    }
    private fun setDirection(routes: ArrayList<Routes>) {
        initDirection()

        var isFirst = true
        var color = Color.BLUE
        for (route in routes) {
            mapFragment.zoomToFit(route.bounds.getBounds())
            directionLines.add(mapFragment.drawPolyline(route.overview_polyline.getPoints(), color, 15F))

            if(isFirst) {
                color = Color.DKGRAY
                isFirst = false
            }
        }
    }

}
