package a.mnisdh.com.kotlingooglemap.googleMapAPIs.domain.directions

import com.google.android.gms.maps.model.LatLng

/**
 * Created by daeho on 2018. 1. 8..
 */
class Southwest {
    var lng: Double = 0.0
    var lat: Double = 0.0

    open fun getLatLng(): LatLng {
        return LatLng(lat, lng)
    }

    override fun toString(): String {
        return "ClassPojo [lng = $lng, lat = $lat]"
    }
}