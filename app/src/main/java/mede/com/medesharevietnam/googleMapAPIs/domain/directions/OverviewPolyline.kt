package a.mnisdh.com.kotlingooglemap.googleMapAPIs.domain.directions

import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.PolyUtil

/**
 * Created by daeho on 2018. 1. 8..
 */
class OverviewPolyline {
    var points: String = ""

    open fun getPoints(): List<LatLng>{
        return PolyUtil.decode(points)
    }

    override fun toString(): String {
        return "ClassPojo [points = $points]"
    }
}