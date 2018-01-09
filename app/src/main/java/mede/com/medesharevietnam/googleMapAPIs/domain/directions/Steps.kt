package a.mnisdh.com.kotlingooglemap.googleMapAPIs.domain.directions

/**
 * Created by daeho on 2018. 1. 8..
 */
class Steps {
    var html_instructions: String = ""
    var duration: Duration = Duration()
    var distance: Distance = Distance()
    var end_location: EndLocation = EndLocation()
    var polyline: Polyline = Polyline()
    var start_location: StartLocation = StartLocation()
    var travel_mode: String = ""

    override fun toString(): String {
        return "ClassPojo [html_instructions = $html_instructions, duration = $duration, distance = $distance, end_location = $end_location, polyline = $polyline, start_location = $start_location, travel_mode = $travel_mode]"
    }
}