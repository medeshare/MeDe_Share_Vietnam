package mede.com.medesharevietnam.domain.match

/**
 * Created by daeho on 2018. 1. 25..
 */
class DoctorReviews {
    var key:String = ""
    var reviews:ArrayList<DoctorReview> = ArrayList()
    var tag:Any? = null

    constructor(){}

    constructor(key:String){
        this.key = key
    }
}