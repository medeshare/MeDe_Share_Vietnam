package a.mnisdh.com.kotlingooglemap.googleMapAPIs.domain.directions

/**
 * Created by daeho on 2018. 1. 8..
 */
class Routes {
    val summary: String = ""
    val bounds: Bounds = Bounds()
    val copyrights: String = ""
    val waypoint_order: List<String> = ArrayList()
    val legs: List<Legs> = ArrayList()
    val warnings: List<String> = ArrayList()
    val overview_polyline: OverviewPolyline = OverviewPolyline()

    override fun toString(): String {
        return "ClassPojo [summary = $summary, bounds = $bounds, copyrights = $copyrights, waypoint_order = $waypoint_order, legs = $legs, warnings = $warnings, overview_polyline = $overview_polyline]"
    }
}