package mede.com.medesharevietnam.domain.medical

/**
 * Created by daeho on 2018. 1. 17..
 */
class MediSubject{
    var key:String = ""
    var name:String = ""
    var description:String = ""

    constructor(){}

    constructor(key:String, name:String){
        this.key = key
        this.name = name
    }

    fun getDiseases():ArrayList<MediDisease>{
        return MedicalManager.getDiseases(key)
    }

    fun getLocations():ArrayList<MediLocation>{
        return MedicalManager.getLocations(key)
    }
}