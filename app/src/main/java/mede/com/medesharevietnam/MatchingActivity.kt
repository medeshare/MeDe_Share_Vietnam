package mede.com.medesharevietnam

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
import mede.com.medesharevietnam.common.Const
import mede.com.medesharevietnam.domain.medical.MediDisease
import mede.com.medesharevietnam.domain.medical.MedicalManager

class MatchingActivity : AppCompatActivity() {
    var doctorKey:String = ""
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
        if(doctorKey == null && doctorKey == "") finishActivity(Activity.RESULT_CANCELED)

        var mediLoc = MedicalManager.getLocation(doctorKey)
        doctorLocation = LatLng(mediLoc!!.lat, mediLoc!!.lng)

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
            markerLocation = mapFragment.addMarker(latLng, "My location", R.drawable.ic_75_point)
        }
        else markerLocation!!.position = latLng
    }

    fun onSelecting(v: View){
        var bottomSheetBehavior = BottomSheetBehavior.from(bottomSheetMatching)
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
    }

    fun onSelectingLocation(v: View){
        var bottomSheetBehavior = BottomSheetBehavior.from(bottomSheetMatchingTime)
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
    }

    fun init() {
        mapFragment = supportFragmentManager.findFragmentById(R.id.map_) as GoogleMapFragment
        mapFragment.setOnMapReadied{
            var latLng = LatLng(21.083026, 105.780140)
            markerHospital = mapFragment.addMarker(latLng, "하노이 공공의과대학교(하노이대학병원)", R.drawable.ic_48_pin_hospital)

            mapFragment.addMarker(doctorLocation, "doctor",  R.drawable.ic_48_pin_doctor)

            initLocationClient()
        }
        mapFragment.getMapAsync(mapFragment)
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

    fun onDriving(v: View){
        drawDriving()
    }
    fun drawDriving(){
        var start = if(useCurrentLocation) currentLocation else customLocation

        mapFragment?.getDirection(
                start,
                doctorLocation,
                "driving",
                {direction ->
                    Log.d("driving count:",direction.routes.count().toString())

                    if (direction.routes.count() > 0) setDirection(direction.routes)
                    else Log.d("onDriving", "is not found")
                })

        btnNext.setBackgroundColor(Color.parseColor("#1990ff"))
    }

    fun onWalking(v: View){
        drawWalking()
    }
    fun drawWalking(){
        btnNext.setBackgroundColor(Color.parseColor("#1990ff"))

        var start = if(useCurrentLocation) currentLocation else customLocation

        mapFragment?.getDirection(
                start,
                doctorLocation,
                "walking",
                {direction ->
                    Log.d("walking count:",direction.routes.count().toString())

                    if (direction.routes.count() > 0) setDirection(direction.routes)
                    else Log.d("onWalking", "is not found")
                })

        btnNext.setBackgroundColor(Color.parseColor("#1990ff"))
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

                drawDriving()
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

        var minLat = 99999999.9
        var maxLat = -99999999.9
        var minLng = 99999999.9
        var maxLng = -99999999.9

        for (route in routes) {
            var southwest = route.bounds.getBounds().southwest
            var northeast = route.bounds.getBounds().northeast

            minLat = if(minLat > southwest.latitude) southwest.latitude else minLat
            minLng = if(minLng > southwest.longitude) southwest.longitude else minLng
            maxLat = if(maxLat < northeast.latitude) northeast.latitude else maxLat
            maxLng = if(maxLng < northeast.longitude) northeast.longitude else maxLng

            directionLines.add(mapFragment.drawPolyline(route.overview_polyline.getPoints(), color, 15F))

            if(isFirst) {
                color = Color.DKGRAY
                isFirst = false
            }
        }

        mapFragment.zoomToFit(LatLng(minLat - 0.05, minLng - 0.05), LatLng(maxLat + 0.05, maxLng + 0.05))
    }

}
