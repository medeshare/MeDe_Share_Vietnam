package mede.com.medesharevietnam

import a.mnisdh.com.kotlingooglemap.googleMapAPIs.GoogleMapFragment
import a.mnisdh.com.kotlingooglemap.googleMapAPIs.domain.directions.Routes
import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.ActionBar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.Polyline

class MainActivity : AppCompatActivity() {
    lateinit var mapFragment: GoogleMapFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setCustomActionbar()
        init()
    }

    fun init() {
        mapFragment = supportFragmentManager.findFragmentById(R.id.map) as GoogleMapFragment
        mapFragment.setOnMapReadied ({
            var latLng = LatLng(21.004060, 105.840235)
            mapFragment.moveToCamera(latLng, 13F)
            mapFragment.setUseLocation(this, true)

            setSample()
        })
        mapFragment.getMapAsync(mapFragment)
    }

    private fun setCustomActionbar() {
        val actionBar = supportActionBar

        actionBar!!.setDisplayShowCustomEnabled(true)
        actionBar.setDisplayHomeAsUpEnabled(false)
        actionBar.setDisplayShowTitleEnabled(false)


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
                    if (direction.routes.count() > 0) setDirection(direction.routes)
                    else Log.d("onWalking", "is not found")
                })
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

    fun setSample(){
        setSample1Marker()
        setSample2Marker()
    }

    var samples1: ArrayList<Marker> = ArrayList()
    var samples2: ArrayList<Marker> = ArrayList()
    var samples3: ArrayList<Marker> = ArrayList()

    fun setSample1Marker(){
        var imgHospital = R.drawable.ic_48_pin_hospital
        samples1.add(mapFragment.addMarker(LatLng(21.027544, 105.845984), "베트남암(癌)병원", imgHospital))
        samples1.add(mapFragment.addMarker(LatLng(21.025688, 105.851031), "베트남쿠바우정병원", imgHospital))
        samples1.add(mapFragment.addMarker(LatLng(21.028793, 105.838528), "하노이피부비뇨기과병원", imgHospital))
        samples1.add(mapFragment.addMarker(LatLng(21.024582, 105.843881), "하노이심장병원", imgHospital))
        samples1.add(mapFragment.addMarker(LatLng(21.016731, 105.848393), "중앙전통의학병원", imgHospital))
        samples1.add(mapFragment.addMarker(LatLng(21.018801, 105.860589), "병원타임시티", imgHospital))
        samples1.add(mapFragment.addMarker(LatLng(21.015517, 105.861927), "베트남우정병원", imgHospital))
        samples1.add(mapFragment.addMarker(LatLng(21.004060, 105.840235), "하노이프랑스병원", imgHospital))
        samples1.add(mapFragment.addMarker(LatLng(21.000608, 105.839666), "베트남심장혈관병원", imgHospital))
        samples1.add(mapFragment.addMarker(LatLng(21.010053, 105.801130), "행림 한의원 하노이", imgHospital))
    }
    fun setSample2Marker(){

    }
}
