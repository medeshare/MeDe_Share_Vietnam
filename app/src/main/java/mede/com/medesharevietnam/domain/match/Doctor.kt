package mede.com.medesharevietnam.domain.match

/**
 * Created by daeho on 2018. 1. 23..
 */
class Doctor {
    var key:String = ""
    var name:String = ""
    var subjectName:String = ""
    var imageUrl:String = "http://cdn.ajoumc.or.kr/Upload/MedicalCenter/Doctor/Profile/201303/104382.jpg"
    var rank:String = "3.5"
    var phoneNumber:String = "010-1234-1234"
    var tag:Any? = null

    constructor(){}

    constructor(key:String, name:String, subjectName:String, imageUrl:String){
        this.key = key
        this.name = name
        this.subjectName = subjectName
        this.imageUrl = imageUrl
    }
}