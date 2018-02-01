package mede.com.medesharevietnam.domain.medical

/**
 * Created by daeho on 2018. 1. 31..
 */
class MediHospital{
    var key:String = "hanoi"
    var lat:Double = 21.083026
    var lng:Double = 105.780140
    var name:String = "Hanoi University \n" +
            "of Public Health"
    //var imageUrl:String = ""
    var description:String = ""
    var tag:Any? = null

    constructor(){}

    constructor(key:String){
        this.key = key
    }
}