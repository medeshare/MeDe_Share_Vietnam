package mede.com.medesharevietnam.domain.medical

/**
 * Created by daeho on 2018. 1. 29..
 */
object MedicalManager{
    private var diseases = ArrayList<MediDisease>()
    private var subjects = ArrayList<MediSubject>()
    private var locations = ArrayList<MediLocation>()

    init {
        diseases.add(MediDisease("s01", "m01", "dislocation", ""))
        diseases.add(MediDisease("s02", "m01", "fracture", ""))
        diseases.add(MediDisease("s03", "m01", "nerve pain (sharp pain)", ""))
        diseases.add(MediDisease("s04", "m01", "musculoskeletal pain", ""))
        diseases.add(MediDisease("s05", "m01", "Back pain", ""))
        diseases.add(MediDisease("s06", "m01", "lie back", ""))
        diseases.add(MediDisease("s07", "m01", "shoulder pain", ""))
        diseases.add(MediDisease("s08", "m01", "neck pain", ""))
        diseases.add(MediDisease("s09", "m01", "Movement ", ""))
        diseases.add(MediDisease("s10", "m02", "Stomach pain", ""))
        diseases.add(MediDisease("s11", "m02", "chest pain", ""))
        diseases.add(MediDisease("s12", "m02", "Breathing difficulty", ""))
        diseases.add(MediDisease("s13", "m02", "fever/temperature", ""))
        diseases.add(MediDisease("s14", "m02", "cold", ""))
        diseases.add(MediDisease("s15", "m02", "vomit", ""))
        diseases.add(MediDisease("s16", "m02", "heart", ""))
        diseases.add(MediDisease("s17", "m02", "skin check", ""))
        diseases.add(MediDisease("s18", "m02", "diabetes", ""))
        diseases.add(MediDisease("s19", "m02", "high blood pressure", ""))
        diseases.add(MediDisease("s20", "m02", "travel medicine", ""))
        diseases.add(MediDisease("s21", "m02", "Immunisation", ""))
        diseases.add(MediDisease("s22", "m02", "sexual health", ""))
        diseases.add(MediDisease("s23", "m02", "Mental health", ""))
        diseases.add(MediDisease("s24", "m02", "flu vaccination", ""))
        diseases.add(MediDisease("s25", "m03", "Sore throat", ""))
        diseases.add(MediDisease("s26", "m03", "ear pain", ""))
        diseases.add(MediDisease("s27", "m03", "hearing loss", ""))
        diseases.add(MediDisease("s28", "m03", "cough", ""))
        diseases.add(MediDisease("s29", "m03", "vocal change", ""))
        diseases.add(MediDisease("s30", "m03", "blocked nose", ""))
        diseases.add(MediDisease("s31", "m03", "nose bleed (epistaxis)", ""))
        diseases.add(MediDisease("s32", "m04", "Vision changes", ""))
        diseases.add(MediDisease("s33", "m04", "eye pain", ""))
        diseases.add(MediDisease("s34", "m04", "eye discharge", ""))
        diseases.add(MediDisease("s35", "m04", "double vision (diplopia)", ""))
        diseases.add(MediDisease("s36", "m04", "eye pressure", ""))
        diseases.add(MediDisease("s37", "m05", "toothache", ""))
        diseases.add(MediDisease("s38", "m05", "gum bleeding", ""))
        diseases.add(MediDisease("s39", "m05", "tooth fracture", ""))
        diseases.add(MediDisease("s40", "m05", "Dental care", ""))
        diseases.add(MediDisease("s41", "m05", "teeth whitening", ""))

        locations.add(MediLocation("l01", "m01", "1", 21.0295486, 105.8543937, "Euan Harold Kim", ""))
        locations.add(MediLocation("l02", "m01", "1", 21.0291061, 105.8496247, "Crare Lim", ""))
        locations.add(MediLocation("l03", "m01", "1", 21.0303529, 105.8167703, "Dr. Ashima collatin", ""))
        locations.add(MediLocation("l04", "m01", "2", 21.026046, 105.8513564, "Phar1", ""))
        locations.add(MediLocation("l05", "m01", "2", 21.0158115, 105.8375115, "Phar2", ""))
        locations.add(MediLocation("l06", "m02", "1", 21.0289124, 105.8175971, "Dr. Chris johnson", ""))
        locations.add(MediLocation("l07", "m02", "1", 21.026411, 105.8194596, "Dr. Paul nakamura", ""))
        locations.add(MediLocation("l08", "m02", "1", 21.0224633, 105.814306, "Dr. Yojhanes euro", ""))
        locations.add(MediLocation("l09", "m02", "2", 21.0331221, 105.8319315, "Phar3", ""))
        locations.add(MediLocation("l10", "m02", "2", 21.035112, 105.8250077, "Phar4", ""))
        locations.add(MediLocation("l11", "m03", "1", 21.0214781, 105.8143369, "Dr. Mosses karl", ""))
        locations.add(MediLocation("l12", "m03", "1", 21.0257359, 105.8182378, "Dr. Royce norman", ""))
        locations.add(MediLocation("l13", "m03", "1", 21.0254089, 105.8185828, "Dr. Junmo jung", ""))
        locations.add(MediLocation("l14", "m03", "2", 21.0231631, 105.8159955, "Phar5", ""))
        locations.add(MediLocation("l15", "m03", "2", 21.0304834, 105.8176826, "Phar6", ""))
        locations.add(MediLocation("l16", "m04", "1", 21.0275304, 105.8254838, "Dr. Hanashiba ktrace", ""))
        locations.add(MediLocation("l17", "m04", "1", 21.0258355, 105.8349832, "Dr. Brian contraras", ""))
        locations.add(MediLocation("l18", "m04", "1", 21.027311, 105.8254231, "Dr. Song", ""))
        locations.add(MediLocation("l19", "m04", "2", 21.0351051, 105.8250164, "Phar7", ""))
        locations.add(MediLocation("l20", "m04", "2", 21.0196492, 105.8172477, "Phar8", ""))
        locations.add(MediLocation("l21", "m05", "1", 21.0248443, 105.8448819, "Dr. Ben harani", ""))
        locations.add(MediLocation("l22", "m05", "1", 21.0419019, 105.8465096, "Dr. James tomson", ""))
        locations.add(MediLocation("l23", "m05", "1", 21.0432791, 105.8145856, "Dr. Michael chae", ""))
        locations.add(MediLocation("l24", "m05", "2", 21.0271405, 105.8360158, "Phar9", ""))
        locations.add(MediLocation("l25", "m05", "2", 21.0304721, 105.8177077, "Phar10", ""))

        subjects.add(MediSubject("m01", "Orthopaedics"))
        subjects.add(MediSubject("m02", "general practice"))
        subjects.add(MediSubject("m03", "Ear nose and throat"))
        subjects.add(MediSubject("m04", "Opthalmology"))
        subjects.add(MediSubject("m05", "Dental surgery"))
    }

    fun getDisease(diseaseKey:String):MediDisease?{
        for(disease in diseases){
            if(disease.key == diseaseKey) return disease
        }

        return null
    }
    fun getDiseases(mediKey: String):ArrayList<MediDisease>{
        var result:ArrayList<MediDisease> = ArrayList()

        for(disease in diseases){
            if(disease.mediKey == mediKey) result.add(disease)
        }

        return result
    }
    fun getLocation(locationKey:String):MediLocation?{
        for(location in locations){
            if(location.key == locationKey) return location
        }

        return null
    }
    fun getLocations(mediKey: String):ArrayList<MediLocation>{
        var result:ArrayList<MediLocation> = ArrayList()

        for(location in locations){
            if(location.mediKey == mediKey) result.add(location)
        }

        return result
    }

    fun getAllDiseases():ArrayList<MediDisease>{
        return diseases
    }


    fun getSubject(mediKey:String):MediSubject?{
        for(subject in subjects){
            if(subject.key == mediKey) return subject
        }

        return null
    }
}