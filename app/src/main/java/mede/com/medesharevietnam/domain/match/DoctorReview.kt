package mede.com.medesharevietnam.domain.match

/**
 * Created by daeho on 2018. 1. 25..
 */
class DoctorReview {
    var key:String = ""
    var idx:Int = 0
    var userKey:String = ""
    var userName:String = ""
    var userImageUrl:String = ""
    var rate:String = ""
    var rateTime:String = ""
    var review:String = ""
    var tag:Any? = null

    constructor(){}

    constructor(key:String){
        this.key = key
    }
}