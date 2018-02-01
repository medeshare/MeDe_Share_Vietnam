package mede.com.medesharevietnam.controller

import a.mnisdh.com.kotlingooglemap.googleMapAPIs.GoogleMapFragment
import a.mnisdh.com.kotlingooglemap.googleMapAPIs.domain.directions.Routes
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.location.Location
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
import kotlinx.android.synthetic.main.activity_matching.*
import kotlinx.android.synthetic.main.bottom_matching_select.*
import kotlinx.android.synthetic.main.custom_action_bar.view.*
import mede.com.medesharevietnam.R
import mede.com.medesharevietnam.common.Const
import mede.com.medesharevietnam.domain.match.Doctor
import mede.com.medesharevietnam.domain.medical.MediDisease
import mede.com.medesharevietnam.domain.medical.MedicalManager

class MatchingActivity : AppCompatActivity() {
    var doctorKey:String = ""
    lateinit var currentDoctor: Doctor

    var useCurrentLocation = true
    var currentLocation: LatLng = LatLng(0.0, 0.0)
    var customLocation: LatLng = LatLng(0.0, 0.0)
    var doctorLocation: LatLng = LatLng(0.0, 0.0)

    lateinit var mapFragment: GoogleMapFragment
    lateinit var markerHospital: Marker
    var markerLocation: Marker? = null

    var locationClient: FusedLocationProviderClient? = null

    var selectedDisease: MediDisease? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_matching)

        doctorKey = intent.getStringExtra(Const.EXT_DOCTOR_KEY)
        if(doctorKey == "") {
            setResult(Activity.RESULT_CANCELED)
            finish()
        }

        var mediLoc = MedicalManager.getLocation(doctorKey)
        doctorLocation = LatLng(mediLoc!!.lat, mediLoc.lng)
        currentDoctor = Doctor()
        currentDoctor.key = doctorKey
        currentDoctor.name = mediLoc.name

        setCustomActionbar()
        init()
    }

    @SuppressLint("MissingPermission")
    private fun initLocationClient(){
        locationClient = LocationServices.getFusedLocationProviderClient(this)

        locationClient!!.lastLocation.addOnSuccessListener(this, object : OnSuccessListener<Location> {
            override fun onSuccess(location: Location?) {
                if (location != null) {
                    updateMarkerLocation(location.latitude, location.longitude)
                    currentLocation = LatLng(location.latitude, location.longitude)

                    setEstimTime()
                    drawDriving()
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
                    currentLocation = LatLng(location.latitude, location.longitude)
                }
            }
        }, null)
    }

    private fun updateMarkerLocation(lat:Double, lng:Double){
        var latLng = LatLng(lat, lng)
        if(markerLocation == null){
            markerLocation = mapFragment.addCenterMarker(latLng, "My location", R.drawable.ic_75_point)
        }
        else markerLocation!!.position = latLng
    }

    fun init() {
        mapFragment = supportFragmentManager.findFragmentById(R.id.map) as GoogleMapFragment
        mapFragment.setOnMapReadied{
            var hanoi = MedicalManager.getHanoiHospital()
            var latLng = LatLng(hanoi.lat, hanoi.lng)
            markerHospital = mapFragment.addMarker(latLng, hanoi.name, R.drawable.ic_48_pin_hospital)

            mapFragment.addMarker(doctorLocation, "doctor", R.drawable.ic_48_pin_doctor)

            initLocationClient()

            initView()
        }
        mapFragment.getMapAsync(mapFragment)
    }

    private fun initView(){
        tgUseLocation.setOnCheckedChangeListener { _, isChecked -> if(isChecked) onLocationChange() }
        tgUseFavorite.setOnCheckedChangeListener { _, isChecked -> if(isChecked) onLocationChange() }
        tgUseHospital.setOnCheckedChangeListener { _, isChecked -> if(isChecked) onLocationChange() }

        btnMyHouse.setOnCheckedChangeListener { _, isChecked ->
            if(isChecked) {
                btnDoctorHouse.isChecked = false
                btnNext_.isEnabled = true
            }
            else btnNext_.isEnabled = false
        }
        btnDoctorHouse.setOnCheckedChangeListener { _, isChecked ->
            if(isChecked) {
                btnMyHouse.isChecked = false
                btnNext_.isEnabled = true
            }
            else btnNext_.isEnabled = false
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

    private fun setCustomActionbar() {
        val actionBar = supportActionBar

        if(actionBar != null){
            actionBar.setDisplayShowCustomEnabled(true)
            actionBar.setDisplayHomeAsUpEnabled(false)
            actionBar.setDisplayShowTitleEnabled(false)
            actionBar.elevation = 0f

            val mCustomView = LayoutInflater.from(this).inflate(R.layout.custom_action_bar, null)
            mCustomView.btnMenu.visibility = View.GONE
            mCustomView.ivLogo.visibility = View.GONE
            mCustomView.btnBack.visibility = View.VISIBLE
            mCustomView.btnBack.setOnClickListener { _ -> setResult(Activity.RESULT_CANCELED)
                finish() }
            mCustomView.tvTitle.visibility = View.VISIBLE
            mCustomView.tvTitle.text = currentDoctor.name

            val params = ActionBar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT)
            actionBar.setCustomView(mCustomView, params)

            val parent = mCustomView.getParent() as Toolbar
            parent.setContentInsetsAbsolute(0, 0)
        }
    }

    private fun setEstimTime(){
        var start = if(useCurrentLocation) currentLocation else customLocation

        mapFragment.getDirection(
                start,
                doctorLocation,
                "driving",
                {direction ->
                    Log.d("driving count:",direction.routes.count().toString())

                    if (direction.routes.count() > 0) {
                        tvEstimDrive.text = direction.routes[0].legs[0].duration.text
                    }
                    else tvEstimDrive.text = "-"
                })

        mapFragment.getDirection(
                start,
                doctorLocation,
                "walking",
                {direction ->
                    Log.d("driving count:",direction.routes.count().toString())

                    if (direction.routes.count() > 0) {
                        tvEstimWalk.text = direction.routes[0].legs[0].duration.text
                    }
                    else tvEstimWalk.text = "-"
                })
    }

    private fun changeDirectionButton(isDriving:Boolean){
        var enableBackColor = Color.parseColor("#ffffff")
        var disableBackColor = Color.parseColor("#0069cb")
        var enableFontColor = Color.parseColor("#1990ff")
        var disableFontColor = Color.parseColor("#ffffff")

        var dirveBackColor = if(isDriving) enableBackColor else disableBackColor
        var dirveFontColor = if(isDriving) enableFontColor else disableFontColor
        var walkBackColor = if(!isDriving) enableBackColor else disableBackColor
        var walkFontColor = if(!isDriving) enableFontColor else disableFontColor

        cvDrive.setCardBackgroundColor(dirveBackColor)
        tvEstimDrive.setTextColor(dirveFontColor)

        cvWalk.setCardBackgroundColor(walkBackColor)
        tvEstimWalk.setTextColor(walkFontColor)

        if(isDriving){
            ivDrive.setImageResource(R.drawable.ic_20_car_press)
            ivWalk.setImageResource(R.drawable.ic_20_walk_nor)
        }
        else{
            ivDrive.setImageResource(R.drawable.ic_20_car_nor)
            ivWalk.setImageResource(R.drawable.ic_20_walk_press)
        }
    }

    fun onDriving(v: View){
        drawDriving()
    }

    fun drawDriving(){
        changeDirectionButton(true)

        var start = if(useCurrentLocation) currentLocation else customLocation

        mapFragment.getDirection(
                start,
                doctorLocation,
                "driving",
                {direction ->
                    Log.d("driving count:",direction.routes.count().toString())

                    if (direction.routes.count() > 0) setDirection(direction.routes)
                    else setDirection(null)
                })
    }

    fun onWalking(v: View){
        drawWalking()
    }
    fun drawWalking(){
        changeDirectionButton(false)

        var start = if(useCurrentLocation) currentLocation else customLocation

        mapFragment.getDirection(
                start,
                doctorLocation,
                "walking",
                {direction ->
                    Log.d("walking count:",direction.routes.count().toString())

                    if (direction.routes.count() > 0) setDirection(direction.routes)
                    else setDirection(null)
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
                customLocation = place.latLng
                tvCurrentSearch.setText(place.name.toString())
                useCurrentLocation = false

                setEstimTime()
                drawDriving()
            }
        }
        else if(requestCode == Const.REQ_DOCTOR_MATCH){
            setResult(resultCode, data)
            finish()
        }
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

                val scale = resources.displayMetrics.density
                directionLines.add(mapFragment.drawPolyline(route.overview_polyline.getPoints(), color, 4.0f * scale))

                if (isFirst) {
                    color = Color.DKGRAY
                    isFirst = false
                }
            }

            mapFragment.zoomToFit(LatLng(minLat - 0.05, minLng - 0.05), LatLng(maxLat + 0.05, maxLng + 0.05))
        }

        progress.visibility = View.GONE
    }

    fun onSelecting(v: View){
        var bottomSheetBehavior = BottomSheetBehavior.from(bottomSheetMatching)
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
    }

    fun onSelectingLocation(v: View){
        var bottomSheetBehavior = BottomSheetBehavior.from(bottomSheetMatchingTime)
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
    }

    fun onConfirm(v: View){
        val intentMatching = Intent(this, ConfirmActivity::class.java)
        intentMatching.putExtra(Const.EXT_DOCTOR_KEY, doctorKey)
        startActivityForResult(intentMatching, Const.REQ_DOCTOR_MATCH)
    }

//    customized dialog from matching_confirm layout
//    fun onMatchingConfirm(v: View){
//        val alert = ViewDialogAdapter()
//        alert.showDialog(this, "Appointment Confirm!")
//    }

}
