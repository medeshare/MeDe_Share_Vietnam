package mede.com.medesharevietnam

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.support.v7.app.ActionBar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.InputMethodManager
import kotlinx.android.synthetic.main.activity_search.*
import mede.com.medesharevietnam.custom.MediAutoCompleteAdapter
import mede.com.medesharevietnam.domain.medical.MediDisease

class SearchActivity : AppCompatActivity() {
    var selectedDisease: MediDisease? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        setCustomActionbar()
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
        initView()
    }

    private fun setCustomActionbar() {
        val actionBar = supportActionBar

        actionBar!!.setDisplayShowCustomEnabled(true)
        actionBar.setDisplayHomeAsUpEnabled(false)
        actionBar.setDisplayShowTitleEnabled(false)
        actionBar.elevation=0f


        val mCustomView = LayoutInflater.from(this).inflate(R.layout.custom_action_bar, null)
        actionBar.setCustomView(mCustomView)

        val parent = mCustomView.getParent() as Toolbar
        parent.setContentInsetsAbsolute(0, 0)

        val params = ActionBar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT)
        actionBar.setCustomView(mCustomView, params)
    }

    private fun initView(){
        tvMediSearch.setThreshold(1);
        var adapter = MediAutoCompleteAdapter(this, R.layout.activity_main, R.id.tvDiseaseName, getTempMediDisease())
        tvMediSearch.setAdapter(adapter)
        tvMediSearch.setOnItemClickListener { adapterView, view, i, l -> selectedDisease = adapter.getItem(i)}
    }

    fun onMediSearch(v: View){
        if(selectedDisease!=null){
            var intent = getIntent()
            intent.putExtra("Disease",selectedDisease)
            setResult(RESULT_OK,intent)
            finish()
            overridePendingTransition(0, 0)
        }
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

    override fun onBackPressed() {
        super.onBackPressed()
        setResult(Activity.RESULT_CANCELED,intent)
        finish()
        overridePendingTransition(0, 0)
    }
}
