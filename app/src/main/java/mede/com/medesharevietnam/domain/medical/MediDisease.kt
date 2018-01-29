package mede.com.medesharevietnam.domain.medical

import java.io.Serializable

/**
 * Created by daeho on 2018. 1. 17..
 */
class MediDisease : Serializable{
    var key:String = ""
    var mediKey:String = ""
    var name:String = ""
    var description:String = ""
    var tag:Any? = null

    constructor(){}

    constructor(key:String, mediKey:String, name:String, description:String){
        this.key = key
        this.mediKey = mediKey
        this.name = name
        this.description = description
    }

    fun getMediSubject():MediSubject? {
        return MedicalManager.getSubject(mediKey)
    }
}