package a.mnisdh.com.kotlingooglemap.googleMapAPIs

import a.mnisdh.com.kotlingooglemap.googleMapAPIs.domain.directions.Direction
import a.mnisdh.com.kotlingooglemap.util.PermissionUtil
import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
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
    private val REQ_PERMISSION: Int = 991
    private var successedPermissions: ArrayList<String> = ArrayList()

    private lateinit var gMap: GoogleMap
    private var directionsAPI: DirectionsAPI

    private var onMapReadied: () -> Unit = {}

    init {
        directionsAPI = DirectionsAPI()
    }

    private fun permissionCheck(activity: Activity, permissions: ArrayList<String>, success: (() -> Unit), failed: (() -> Unit)){
        if(permissions.count() > 0) {
            val permissionUtil = PermissionUtil(REQ_PERMISSION, permissions)
            permissionUtil.check(activity, success, failed)
        }
    }

    override fun onMapReady(map: GoogleMap?) {
        gMap = map as GoogleMap

        onMapReadied()
    }

    @SuppressLint("MissingPermission")
    fun setUseLocation(activity: Activity, use: Boolean) {
        var permission: String = Manifest.permission.ACCESS_FINE_LOCATION

        if (use) {
            if (!successedPermissions.contains(permission)) {
                var permissions: ArrayList<String> = ArrayList()
                permissions.add(permission)
                permissionCheck(activity, permissions, {
                    successedPermissions.add(permission)
                    gMap.setMyLocationEnabled(use)
                }, {})
            } else gMap.setMyLocationEnabled(use)
        } else gMap.setMyLocationEnabled(use)
    }

    fun setOnMapReadied(onMapReadied: () -> Unit){
        this.onMapReadied = onMapReadied
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
        zoomToFit(LatLngBounds(latLng1, latLng2))
    }
    fun zoomToFit(latLngBounds: LatLngBounds){
        gMap.moveCamera(CameraUpdateFactory.newLatLngBounds(latLngBounds, 0))
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