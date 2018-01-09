package a.mnisdh.com.kotlingooglemap.googleMapAPIs.domain.directions

/**
 * Created by daeho on 2018. 1. 8..
 */
class Legs {
    var duration: Duration = Duration()
    var distance: Distance = Distance()
    var end_location: EndLocation = EndLocation()
    var start_address: String = ""
    var end_address: String = ""
    var start_location: StartLocation = StartLocation()
    var traffic_speed_entry: List<String> = ArrayList()
    var via_waypoint: List<String> = ArrayList()
    var steps: List<Steps> = ArrayList()

    override fun toString(): String {
        return "ClassPojo [duration = $duration, distance = $distance, end_location = $end_location, start_address = $start_address, end_address = $end_address, start_location = $start_location, traffic_speed_entry = $traffic_speed_entry, via_waypoint = $via_waypoint, steps = $steps]"
    }
}