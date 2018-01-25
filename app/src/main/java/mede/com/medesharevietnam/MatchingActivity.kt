package mede.com.medesharevietnam

import a.mnisdh.com.kotlingooglemap.googleMapAPIs.GoogleMapFragment
import a.mnisdh.com.kotlingooglemap.googleMapAPIs.domain.directions.Routes
import android.graphics.Color
import android.os.Bundle
import android.support.design.widget.BottomSheetBehavior
import android.support.v7.app.ActionBar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.Polyline
import kotlinx.android.synthetic.main.activity_matching.*

class MatchingActivity : AppCompatActivity() {

    lateinit var mapFragment: GoogleMapFragment
    lateinit var markerHospital: Marker

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_matching)

        setCustomActionbar()
        init()
    }

    fun onSelecting(v: View){
        var bottomSheetBehavior = BottomSheetBehavior.from(bottomSheetMatching)
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED)
    }

    fun onSelectingLocation(v: View){
        var bottomSheetBehavior = BottomSheetBehavior.from(bottomSheetMatchingTime)
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED)
    }

    fun init() {
        mapFragment = supportFragmentManager.findFragmentById(R.id.map_) as GoogleMapFragment
        mapFragment.setOnMapReadied{

            var latLng = LatLng(21.083026, 105.780140)
            markerHospital = mapFragment.addMarker(latLng, "하노이 공공의과대학교(하노이대학병원)", R.drawable.ic_48_pin_hospital)
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
        mapFragment?.getDirection(
                LatLng(21.004060, 105.840235),
                LatLng(21.027544, 105.845984),
                "driving",
                {direction ->
                    Log.d("driving count:",direction.routes.count().toString())

                    if (direction.routes.count() > 0) setDirection(direction.routes)
                    else Log.d("onDriving", "is not found")
                })
        btnNext.setBackgroundColor(Color.parseColor("#1990ff"))
    }

    fun onWalking(v: View){
        btnNext.setBackgroundColor(Color.parseColor("#1990ff"))
        mapFragment?.getDirection(
                LatLng(21.004060, 105.840235),
                LatLng(21.027544, 105.845984),
                "walking",
                {direction ->
                    Log.d("walking count:",direction.routes.count().toString())

                    if (direction.routes.count() > 0) setDirection(direction.routes)
                    else Log.d("onWalking", "is not found")
                })
        btnNext.setBackgroundColor(Color.parseColor("#1990ff"))
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
