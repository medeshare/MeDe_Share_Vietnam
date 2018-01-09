package a.mnisdh.com.kotlingooglemap.googleMapAPIs.domain.directions

/**
 * Created by daeho on 2018. 1. 8..
 */
class Direction {
    var geocoded_waypoints: List<GeocodedWaypoints> = ArrayList()
    var status: String = ""
    var routes: ArrayList<Routes> = ArrayList()

    override fun toString(): String {
        return "ClassPojo [geocoded_waypoints = $geocoded_waypoints, status = $status, routes = $routes]"
    }
}