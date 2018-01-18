package a.mnisdh.com.kotlingooglemap.googleMapAPIs

import a.mnisdh.com.kotlingooglemap.googleMapAPIs.domain.directions.Direction
import android.util.Log
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers




/**
 * Created by daeho on 2018. 1. 5..
 */
class GoogleMapFragment : SupportMapFragment(), OnMapReadyCallback {
    private lateinit var gMap: GoogleMap
    private var directionsAPI: DirectionsAPI

    private var onMapReadied: () -> Unit = {}
    fun setOnMapReadied(onMapReadied: () -> Unit){ this.onMapReadied = onMapReadied }

    private var onMarkerClicked: (Marker?) -> Unit = {}
    fun setOnMarkerClicked(onMarkerClicked: (Marker?) -> Unit){ this.onMarkerClicked = onMarkerClicked }

    init {
        directionsAPI = DirectionsAPI()
    }

    override fun onMapReady(map: GoogleMap?) {
        gMap = map as GoogleMap
        initEvents()

        onMapReadied()
    }

    private fun initEvents(){
        gMap.setOnMarkerClickListener(object: GoogleMap.OnMarkerClickListener{
            override fun onMarkerClick(p0: Marker?): Boolean {
                onMarkerClicked(p0)
                return true
            }
        })
    }

    fun addMarker(latLng: LatLng, title: String):Marker{
        return gMap.addMarker(MarkerOptions().position(latLng).title(title))
    }
    fun addMarker(latLng: LatLng, title: String, iconResourceId: Int):Marker{
        return gMap.addMarker(MarkerOptions()
                .position(latLng)
                .title(title)
                .snippet("Population: 4,137,400")
                .icon(BitmapDescriptorFactory.fromResource(iconResourceId)))
    }

    fun moveToCamera(latLng: LatLng, scale: Float){
        gMap.moveCamera(CameraUpdateFactory.newCameraPosition(CameraPosition.fromLatLngZoom(latLng, scale)))
    }
    fun moveToCamera(marker: Marker, scale: Float){
        moveToCamera(marker.position, scale)
    }

    fun zoomToFit(latLng1: LatLng, latLng2: LatLng){
        zoomToFit(LatLngBounds(latLng1, latLng2), 0)
    }
    fun zoomToFit(latLngBounds: LatLngBounds){
        zoomToFit(latLngBounds, 0)
    }
    fun zoomToFit(latLngBounds: LatLngBounds, zoomLevel: Int){
        gMap.moveCamera(CameraUpdateFactory.newLatLngBounds(latLngBounds, zoomLevel))
    }
    fun zoomToFit(markers: ArrayList<Marker>, zoomLevel:Int){
        var minLat = 99999999.9
        var maxLat = -99999999.9
        var minLng = 99999999.9
        var maxLng = -99999999.9

        for(marker in markers){
            var lat = marker.position.latitude
            var lng = marker.position.longitude
            if(lat >= maxLat) maxLat = lat
            if(lat <= minLat) minLat = lat
            if(lng >= maxLng) maxLng = lng
            if(lng <= minLng) minLng = lng
        }

        //zoomToFit(LatLngBounds(LatLng(minLat - 0.0005, minLng - 0.0005), LatLng(maxLat + 0.0005, maxLng + 0.0005)))
        zoomToFit(LatLngBounds(LatLng(minLat, minLng), LatLng(maxLat, maxLng)), zoomLevel)
    }

    fun drawPolyline(latLngs: List<LatLng>, color: Int, width: Float): Polyline{
        val rectOptions = PolylineOptions()
        rectOptions.addAll(latLngs)

        rectOptions.color(color)
        rectOptions.width(width)

        return gMap.addPolyline(rectOptions)
    }

    fun getDirection(startLatLng: LatLng, endLatLng: LatLng, mode: String, callback: ((Direction) -> Unit)){
        directionsAPI.getDirection(startLatLng, endLatLng, mode)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({
                    result ->
                    if(result.status.equals("OK")) callback(result)
                    else Log.d("setDirection", "is failed")
                }, { error ->
                    error.printStackTrace()
                })
    }
}