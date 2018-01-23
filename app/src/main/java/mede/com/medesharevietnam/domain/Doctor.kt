package mede.com.medesharevietnam.domain

/**
 * Created by daeho on 2018. 1. 23..
 */
class Doctor {
    var key:String = ""
    var name:String = ""
    var subjectName:String = ""
    var imageUrl:String = ""
    var rank:String = ""
    var phoneNumber:String = ""
    var callImageResource:Int = 0
    var chatImageResource:Int = 1
    var useCircleImage:Boolean = true
    var tag:Any? = null

    constructor(){}

    constructor(key:String, name:String, subjectName:String, imageUrl:String){
        this.key = key
        this.name = name
        this.subjectName = subjectName
        this.imageUrl = imageUrl
    }
}