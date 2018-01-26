package mede.com.medesharevietnam.domain.match

/**
 * Created by daeho on 2018. 1. 25..
 */
class DoctorAbout {
    var key:String = ""
    var hours:String = ""
    var patients:String = ""
    var rate:String = "3.1"
    var introduce:String = "Dr. James Riyadi is surrently the Director and H.O.D of the Department of Mediciene, Artemis Hospital, where he’s been working for the past 8 years."
    var mapUrl = "https://maps.googleapis.com/maps/api/staticmap?center=40.714728,-73.998672&zoom=14&size=400x400&key=AIzaSyA3VhwLoySupQAQxdS4fwBle7eE_UEf-9U"
    var price:String = "\$10 / Case"
    var tag:Any? = null

    constructor(){}

    constructor(key:String){
        this.key = key
    }
}