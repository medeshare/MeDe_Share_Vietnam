package mede.com.medesharevietnam.domain

/**
 * Created by daeho on 2018. 1. 17..
 */
class Disease{
    var key:String = ""
    var mediKey:String = ""
    var name:String = ""
    var description:String = ""
    var mediSubject:MediSubject? = null
        private set(value) {field = value}

    constructor(){}

    constructor(key:String, mediKey:String, name:String, description:String){
        this.key = key
        this.mediKey = mediKey
        this.name = name
        this.description = description
    }

    init {
        if(mediKey.equals("m01")){
            mediSubject = MediSubject(mediKey, "Orthopaedics")
        }
        else if(mediKey.equals("m02")){
            mediSubject = MediSubject(mediKey, "general practice")
        }
        else if(mediKey.equals("m03")){
            mediSubject = MediSubject(mediKey, "Ear nose and throat")
        }
        else if(mediKey.equals("m04")){
            mediSubject = MediSubject(mediKey, "Opthalmology")
        }
        else if(mediKey.equals("m05")){
            mediSubject = MediSubject(mediKey, "Dental surgery")
        }
    }
}