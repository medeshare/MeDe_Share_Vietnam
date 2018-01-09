package a.mnisdh.com.kotlingooglemap.googleMapAPIs.domain.directions

import com.google.android.gms.maps.model.LatLngBounds

/**
 * Created by daeho on 2018. 1. 8..
 */
class Bounds {
    var southwest: Southwest = Southwest()
    var northeast: Northeast = Northeast()

    open fun getBounds():LatLngBounds{
        return LatLngBounds(southwest.getLatLng(), northeast.getLatLng())
    }

    override fun toString(): String {
        return "ClassPojo [southwest = $southwest, northeast = $northeast]"
    }
}