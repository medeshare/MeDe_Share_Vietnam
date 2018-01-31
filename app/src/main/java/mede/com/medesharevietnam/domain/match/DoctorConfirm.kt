package mede.com.medesharevietnam.domain.match

/**
 * Created by daeho on 2018. 1. 31..
 */
class DoctorConfirm {
    var key:String = ""
    var symptom:String = "Cold Common"
    var placeType:Int = 1
    var place:String = "At my house"
    var timeLong:Long = 12344
    var time:String = "Today, PM 5:30"
    var price:String = "About \$10/Case"
    var isWalking:Boolean = false
    var tag:Any? = null

    constructor(){}

    constructor(key:String){
        this.key = key
    }
}