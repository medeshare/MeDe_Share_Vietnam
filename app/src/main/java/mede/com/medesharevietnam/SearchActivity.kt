package mede.com.medesharevietnam

import android.app.Activity
import android.os.Bundle
import android.support.v7.app.ActionBar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.Toolbar
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import kotlinx.android.synthetic.main.activity_search.*
import mede.com.medesharevietnam.custom.MediAutoCompleteAdapter
import mede.com.medesharevietnam.custom.RecyclerAdapter
import mede.com.medesharevietnam.domain.SearchData
import mede.com.medesharevietnam.domain.medical.MediDisease
import mede.com.medesharevietnam.domain.medical.MedicalManager
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
        var adapter = MediAutoCompleteAdapter(this, R.layout.activity_main, R.id.tvDiseaseName, MedicalManager.getAllDiseases())
        tvMediSearch.setAdapter(adapter)


        recyclerAdapter = RecyclerAdapter(this){ data -> tvMediSearch.setText(data.disease)}
        recyclerView.adapter = recyclerAdapter

        val linearLayoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = linearLayoutManager

        tvMediSearch.setOnItemClickListener {
            adapterView, view, i,
            l -> selectedDisease = adapter.getItem(i)
            recyclerAdapter.addDataAndRefresh(SearchData(adapter.getItem(i).name, getDate()))
            checkSize(recyclerAdapter.getItemSize())
        }

        checkSize(recyclerAdapter.getItemSize())

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
