package a.mnisdh.com.kotlingooglemap.googleMapAPIs.domain.directions

/**
 * Created by daeho on 2018. 1. 8..
 */
class GeocodedWaypoints {
    var place_id: String = ""
    var geocoder_status: String = ""
    var types: ArrayList<String> = ArrayList()

    override fun toString(): String {
        return "ClassPojo [place_id = $place_id, geocoder_status = $geocoder_status, types = $types]"
    }
}