package mede.com.medesharevietnam.domain

/**
 * Created by daeho on 2018. 1. 17..
 */
class MediSubject{
    var key:String = ""
    var name:String = ""
    var description:String = ""
    var diseases:ArrayList<Disease> = ArrayList()
        private set(value) {field = value}
    var locations:ArrayList<MediLocation> = ArrayList()
        private set(value) {field = value}

    constructor(){}

    constructor(key:String, name:String){
        this.key = key
        this.name = name
    }

    init {
        if (key.equals("m01")){
            diseases.add(Disease("s01", "m01", "dislocation", ""))
            diseases.add(Disease("s02", "m01", "fracture", ""))
            diseases.add(Disease("s03", "m01", "nerve pain (sharp pain)", ""))
            diseases.add(Disease("s04", "m01", "musculoskeletal pain", ""))
            diseases.add(Disease("s05", "m01", "Back pain", ""))
            diseases.add(Disease("s06", "m01", "lie back", ""))
            diseases.add(Disease("s07", "m01", "shoulder pain", ""))
            diseases.add(Disease("s08", "m01", "neck pain", ""))
            diseases.add(Disease("s09", "m01", "Movement ", ""))

            locations.add(MediLocation("l01", "m01", "1", 21.0295486, 105.8543937, "Euan Harold Kim", ""))
            locations.add(MediLocation("l02", "m01", "1", 21.0291061, 105.8496247, "Crare Lim", ""))
            locations.add(MediLocation("l03", "m01", "1", 21.0303529, 105.8167703, "Dr. Ashima collatin", ""))
            locations.add(MediLocation("l04", "m01", "2", 21.026046, 105.8513564, "Phar1", ""))
            locations.add(MediLocation("l05", "m01", "2", 21.0158115, 105.8375115, "Phar2", ""))
        }
        else if (key.equals("m02")){
            diseases.add(Disease("s10", "m02", "Stomach pain", ""))
            diseases.add(Disease("s11", "m02", "chest pain", ""))
            diseases.add(Disease("s12", "m02", "Breathing difficulty", ""))
            diseases.add(Disease("s13", "m02", "fever/temperature", ""))
            diseases.add(Disease("s14", "m02", "cold", ""))
            diseases.add(Disease("s15", "m02", "vomit", ""))
            diseases.add(Disease("s16", "m02", "heart", ""))
            diseases.add(Disease("s17", "m02", "skin check", ""))
            diseases.add(Disease("s18", "m02", "diabetes", ""))
            diseases.add(Disease("s19", "m02", "high blood pressure", ""))
            diseases.add(Disease("s20", "m02", "travel medicine", ""))
            diseases.add(Disease("s21", "m02", "Immunisation", ""))
            diseases.add(Disease("s22", "m02", "sexual health", ""))
            diseases.add(Disease("s23", "m02", "Mental health", ""))
            diseases.add(Disease("s24", "m02", "flu vaccination", ""))

            locations.add(MediLocation("l06", "m02", "1", 21.0289124, 105.8175971, "Dr. Chris johnson", ""))
            locations.add(MediLocation("l07", "m02", "1", 21.026411, 105.8194596, "Dr. Paul nakamura", ""))
            locations.add(MediLocation("l08", "m02", "1", 21.0224633, 105.814306, "Dr. Yojhanes euro", ""))
            locations.add(MediLocation("l09", "m02", "2", 21.0331221, 105.8319315, "Phar3", ""))
            locations.add(MediLocation("l10", "m02", "2", 21.035112, 105.8250077, "Phar4", ""))
        }
        else if (key.equals("m03")){
            diseases.add(Disease("s25", "m03", "Sore throat", ""))
            diseases.add(Disease("s26", "m03", "ear pain", ""))
            diseases.add(Disease("s27", "m03", "hearing loss", ""))
            diseases.add(Disease("s28", "m03", "cough", ""))
            diseases.add(Disease("s29", "m03", "vocal change", ""))
            diseases.add(Disease("s30", "m03", "blocked nose", ""))
            diseases.add(Disease("s31", "m03", "nose bleed (epistaxis)", ""))

            locations.add(MediLocation("l11", "m03", "1", 21.0214781, 105.8143369, "Dr. Mosses karl", ""))
            locations.add(MediLocation("l12", "m03", "1", 21.0257359, 105.8182378, "Dr. Royce norman", ""))
            locations.add(MediLocation("l13", "m03", "1", 21.0254089, 105.8185828, "Dr. Junmo jung", ""))
            locations.add(MediLocation("l14", "m03", "2", 21.0231631, 105.8159955, "Phar5", ""))
            locations.add(MediLocation("l15", "m03", "2", 21.0304834, 105.8176826, "Phar6", ""))
        }
        else if (key.equals("m04")){
            diseases.add(Disease("s32", "m04", "Vision changes", ""))
            diseases.add(Disease("s33", "m04", "eye pain", ""))
            diseases.add(Disease("s34", "m04", "eye discharge", ""))
            diseases.add(Disease("s35", "m04", "double vision (diplopia)", ""))
            diseases.add(Disease("s36", "m04", "eye pressure", ""))

            locations.add(MediLocation("l16", "m04", "1", 21.0275304, 105.8254838, "Dr. Hanashiba ktrace", ""))
            locations.add(MediLocation("l17", "m04", "1", 21.0258355, 105.8349832, "Dr. Brian contraras", ""))
            locations.add(MediLocation("l18", "m04", "1", 21.027311, 105.8254231, "Dr. Song", ""))
            locations.add(MediLocation("l19", "m04", "2", 21.0351051, 105.8250164, "Phar7", ""))
            locations.add(MediLocation("l20", "m04", "2", 21.0196492, 105.8172477, "Phar8", ""))
        }
        else if (key.equals("m05")){
            diseases.add(Disease("s37", "m05", "toothache", ""))
            diseases.add(Disease("s38", "m05", "gum bleeding", ""))
            diseases.add(Disease("s39", "m05", "tooth fracture", ""))
            diseases.add(Disease("s40", "m05", "Dental care", ""))
            diseases.add(Disease("s41", "m05", "teeth whitening", ""))

            locations.add(MediLocation("l21", "m05", "1", 21.0248443, 105.8448819, "Dr. Ben harani", ""))
            locations.add(MediLocation("l22", "m05", "1", 21.0419019, 105.8465096, "Dr. James tomson", ""))
            locations.add(MediLocation("l23", "m05", "1", 21.0432791, 105.8145856, "Dr. Michael chae", ""))
            locations.add(MediLocation("l24", "m05", "2", 21.0271405, 105.8360158, "Phar9", ""))
            locations.add(MediLocation("l25", "m05", "2", 21.0304721, 105.8177077, "Phar10", ""))
        }
    }
}