package mede.com.medesharevietnam.domain.match

/**
 * Created by daeho on 2018. 1. 25..
 */
class DoctorAbout {
    var key:String = ""
    var hours:Int = 319
    var patients:Int = 180
    var rate:String = "3.1"
    var introduce:String = "Dr. James Riyadi is surrently the Director and H.O.D of the Department of Mediciene, Artemis Hospital, where he’s been working for the past 8 years."
    var lat:Double = 0.0
    var lng:Double = 0.0
    var price:String = "\$10 / Case"
    var tag:Any? = null

    constructor(){}

    constructor(key:String){
        this.key = key
    }
}