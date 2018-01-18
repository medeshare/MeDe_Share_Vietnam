package mede.com.medesharevietnam

import a.mnisdh.com.kotlingooglemap.googleMapAPIs.GoogleMapFragment
import a.mnisdh.com.kotlingooglemap.googleMapAPIs.domain.directions.Routes
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.ActionBar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import com.google.android.gms.location.places.ui.PlaceAutocomplete
import com.google.android.gms.location.places.ui.PlacePicker
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.Polyline
import kotlinx.android.synthetic.main.activity_main.*
import mede.com.medesharevietnam.common.Const
import mede.com.medesharevietnam.custom.MediAutoCompleteAdapter
import mede.com.medesharevietnam.domain.medical.MediDisease

class MainActivity : AppCompatActivity() {
    lateinit var mapFragment: GoogleMapFragment
    var selectedDisease: MediDisease? = null
    var markers: ArrayList<Marker> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setCustomActionbar()
        init()
    }

    fun init() {
        mapFragment = supportFragmentManager.findFragmentById(R.id.map) as GoogleMapFragment
        mapFragment.setOnMapReadied({
            initView()

            var latLng = LatLng(21.004060, 105.840235)
            mapFragment.moveToCamera(latLng, 13F)
            mapFragment.setUseLocation(this, true)

            //setSample()
        })
        mapFragment.getMapAsync(mapFragment)
    }

    private fun getTempMediDisease():ArrayList<MediDisease>{
        var diseases = ArrayList<MediDisease>();
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

        return diseases
    }
    private fun initView(){
        tvMediSearch.setThreshold(1);
        var adapter = MediAutoCompleteAdapter(this, R.layout.activity_main, R.id.tvDiseaseName, getTempMediDisease())
        tvMediSearch.setAdapter(adapter)
        tvMediSearch.setOnItemClickListener { adapterView, view, i, l -> selectedDisease = adapter.getItem(i) }
    }

    private fun setCustomActionbar() {
        val actionBar = supportActionBar

        actionBar!!.setDisplayShowCustomEnabled(true)
        actionBar.setDisplayHomeAsUpEnabled(false)
        actionBar.setDisplayShowTitleEnabled(false)


        val mCustomView = LayoutInflater.from(this).inflate(R.layout.custom_action_bar, null)
        actionBar.setCustomView(mCustomView)

        val parent = mCustomView.getParent() as Toolbar
        parent.setContentInsetsAbsolute(0, 0)

        val params = ActionBar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT)
        actionBar.setCustomView(mCustomView, params)
    }

    private fun clearMarker(){
        for(marker in markers){
            marker.remove()
        }

        markers.clear()
    }

    fun onMediSearch(v: View){
        if(selectedDisease != null){
            clearMarker()

            var locations = selectedDisease!!.getMediSubject().getLocations()

            for(location in locations){
                var latlng = LatLng(location.lat, location.lng)
                var icon = 0
                if(location.type == "1") icon = R.drawable.ic_48_pin_doctor
                else icon = R.drawable.ic_48_pin_pharmacy

                markers.add(mapFragment.addMarker(latlng, location.name, icon))
            }

            mapFragment.zoomToFit(markers, 10)
        }
    }

    fun onDriving(v: View){
        mapFragment?.getDirection(
                LatLng(21.004060, 105.840235),
                LatLng(21.027544, 105.845984),
                "driving",
                {direction ->
                    Log.d("driving count:",direction.routes.count().toString())

                    if (direction.routes.count() > 0) setDirection(direction.routes)
                    else Log.d("onDriving", "is not found")
                })
    }

    fun onWalking(v: View){
        mapFragment?.getDirection(
                LatLng(21.004060, 105.840235),
                LatLng(21.027544, 105.845984),
                "walking",
                {direction ->
                    Log.d("walking count:",direction.routes.count().toString())

                    if (direction.routes.count() > 0) setDirection(direction.routes)
                    else Log.d("onWalking", "is not found")
                })
    }

    fun onSearching(v: View){
        val intent = PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_OVERLAY).build(this)
        startActivityForResult(intent, Const.REQ_PLACE_AUTOCOMPLATE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(requestCode == Const.REQ_PLACE_AUTOCOMPLATE){
            if (resultCode == RESULT_OK) {
                val place = PlacePicker.getPlace(data, this)
                //tvSearch.text = place.name
                
                mapFragment.addMarker(place.latLng, "")
            }
        }
    }

    var directionLines: ArrayList<Polyline> = ArrayList()
    private fun initDirection(){
        for(direction in directionLines) direction.remove()

        directionLines.clear()
    }
    private fun setDirection(routes: ArrayList<Routes>) {
        initDirection()

        var isFirst = true
        var color = Color.BLUE
        for (route in routes) {
            mapFragment.zoomToFit(route.bounds.getBounds())
            directionLines.add(mapFragment.drawPolyline(route.overview_polyline.getPoints(), color, 15F))

            if(isFirst) {
                color = Color.DKGRAY
                isFirst = false
            }
        }
    }

    fun setSample(){
        setSample1Marker()
        setSample2Marker()
    }

    var samples1: ArrayList<Marker> = ArrayList()
    var samples2: ArrayList<Marker> = ArrayList()
    var samples3: ArrayList<Marker> = ArrayList()

    fun setSample1Marker(){
        var imgHospital = R.drawable.ic_48_pin_hospital
        samples1.add(mapFragment.addMarker(LatLng(21.027544, 105.845984), "베트남암(癌)병원", imgHospital))
        samples1.add(mapFragment.addMarker(LatLng(21.025688, 105.851031), "베트남쿠바우정병원", imgHospital))
        samples1.add(mapFragment.addMarker(LatLng(21.028793, 105.838528), "하노이피부비뇨기과병원", imgHospital))
        samples1.add(mapFragment.addMarker(LatLng(21.024582, 105.843881), "하노이심장병원", imgHospital))
        samples1.add(mapFragment.addMarker(LatLng(21.016731, 105.848393), "중앙전통의학병원", imgHospital))
        samples1.add(mapFragment.addMarker(LatLng(21.018801, 105.860589), "병원타임시티", imgHospital))
        samples1.add(mapFragment.addMarker(LatLng(21.015517, 105.861927), "베트남우정병원", imgHospital))
        samples1.add(mapFragment.addMarker(LatLng(21.004060, 105.840235), "하노이프랑스병원", imgHospital))
        samples1.add(mapFragment.addMarker(LatLng(21.000608, 105.839666), "베트남심장혈관병원", imgHospital))
        samples1.add(mapFragment.addMarker(LatLng(21.010053, 105.801130), "행림 한의원 하노이", imgHospital))
    }
    fun setSample2Marker(){

    }
}
