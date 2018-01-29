package mede.com.medesharevietnam

import android.app.Activity
import android.os.Bundle
import android.support.v7.app.ActionBar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.Toolbar
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.View.*
import android.view.inputmethod.EditorInfo
import kotlinx.android.synthetic.main.activity_search.*
import mede.com.medesharevietnam.custom.MediAutoCompleteAdapter
import mede.com.medesharevietnam.custom.RecyclerAdapter
import mede.com.medesharevietnam.domain.SearchData
import mede.com.medesharevietnam.domain.medical.MediDisease
import java.text.SimpleDateFormat
import java.util.*

class SearchActivity : AppCompatActivity() {
    private var selectedDisease: MediDisease? = null
    lateinit private var recyclerAdapter : RecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        setCustomActionbar()

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


        recyclerAdapter = RecyclerAdapter(this){ data -> tvMediSearch.setText(data.disease)}
        recyclerView.adapter = recyclerAdapter

        val linearLayoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = linearLayoutManager

        tvMediSearch.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if(s.isNullOrEmpty()){
                    imgClear.visibility=INVISIBLE
                }else{
                    imgClear.visibility = VISIBLE
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                imgVisible(s)
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                imgVisible(s)
            }
        })
        tvMediSearch.setOnItemClickListener {
            adapterView, view, i,
            l -> selectedDisease = adapter.getItem(i)
            recyclerAdapter.addDataAndRefresh(SearchData(adapter.getItem(i).name, getDate()))
            checkSize(recyclerAdapter.getItemSize())
        }
        tvMediSearch.setOnEditorActionListener{ v, actionId, event ->
            if(actionId == EditorInfo.IME_ACTION_SEARCH){
                onMediSearch(v)
                true
            } else {
                false
            }
        }

        checkSize(recyclerAdapter.getItemSize())

    }

    fun imgVisible(s: CharSequence?){
        if(s.isNullOrEmpty()){
            imgClear.visibility = INVISIBLE
        }else{
            imgClear.visibility = VISIBLE
        }
    }

    fun clearText(v:View){
        tvMediSearch.text=null
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

    fun dontSave(v: View){
        if(recyclerAdapter.save==true){
            recyclerAdapter.save=false
            txtDontSave.text = "Save Searches"
        }else{
            recyclerAdapter.save=true
            txtDontSave.text = "Don't Save Searches"
        }
    }

    fun deleteAll(v: View){
        recyclerAdapter.removeAllDataAndRefresh()
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

    fun getDate() : String{
        var now = System.currentTimeMillis()
        var date = Date(now)
        var sdf = SimpleDateFormat("MM-dd")
        return sdf.format(date)
    }

    fun checkSize(size:Int){
        if(size==0){
            txtNoRecent.visibility = VISIBLE
            txtRecentSearch.visibility = GONE
            recyclerView.visibility = GONE
            cardView_Search.visibility = GONE
        }else{
            txtNoRecent.visibility = GONE
            txtRecentSearch.visibility = VISIBLE
            recyclerView.visibility = VISIBLE
            cardView_Search.visibility = VISIBLE
        }
    }


}
