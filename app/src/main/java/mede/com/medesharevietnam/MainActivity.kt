package mede.com.medesharevietnam

import a.mnisdh.com.kotlingooglemap.googleMapAPIs.GoogleMapFragment
import a.mnisdh.com.kotlingooglemap.googleMapAPIs.domain.directions.Routes
import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Polyline

class MainActivity : AppCompatActivity() {
    lateinit var mapFragment: GoogleMapFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        init()
    }

    fun init() {
        mapFragment = supportFragmentManager.findFragmentById(R.id.map) as GoogleMapFragment
        mapFragment.setOnMapReadied ({
            var latLng = LatLng(100.0, 100.0)
            mapFragment.addMarker(latLng, "init location")
            mapFragment.moveToCamera(latLng, 10F)
            mapFragment.setUseLocation(this, true)
        })
        mapFragment.getMapAsync(mapFragment)
    }

    fun onTest(v: View) {
        mapFragment?.getDirection(
                LatLng(35.1301882, 136.8772562),
                LatLng(35.1903641, 136.9098043),
                {direction ->
                    if (direction.routes.count() > 0) setDirection(direction.routes.get(0))
                    else Log.d("setDirection", "is not found")
                })
    }

    var lineDirection: Polyline? = null

    private fun setDirection(route: Routes){
        lineDirection?.remove()

        mapFragment.zoomToFit(route.bounds.getBounds())
        lineDirection = mapFragment.drawPolyline(route.overview_polyline.getPoints(), Color.BLUE, 15F)
    }
}
