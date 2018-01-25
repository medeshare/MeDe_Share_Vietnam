package mede.com.medesharevietnam.domain.medical

/**
 * Created by daeho on 2018. 1. 17..
 */
class MediLocation{
    var key:String = ""
    var mediKey:String = ""
    var type:String = ""
    var lat:Double = 0.0
    var lng:Double = 0.0
    var name:String = ""
    var description:String = ""
    var tag:Any? = null

    constructor(){}

    constructor(key:String, mediKey:String, type:String, lat:Double, lng:Double, name:String, description:String){
        this.key = key
        this.mediKey = mediKey
        this.type = type
        this.lat = lat
        this.lng = lng
        this.name = name
        this.description = description
    }

    fun getMediSubject():MediSubject {
        if(mediKey=="m01"){
            return MediSubject(mediKey, "Orthopaedics")
        }
        else if(mediKey=="m02"){
            return MediSubject(mediKey, "general practice")
        }
        else if(mediKey=="m03"){
            return MediSubject(mediKey, "Ear nose and throat")
        }
        else if(mediKey=="m04"){
            return MediSubject(mediKey, "Opthalmology")
        }
        else if(mediKey=="m05"){
            return MediSubject(mediKey, "Dental surgery")
        }

        return MediSubject()
    }
}