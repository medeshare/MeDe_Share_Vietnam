package mede.com.medesharevietnam.controller

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v7.app.AppCompatActivity
import android.view.View
import kotlinx.android.synthetic.main.activity_doctor_match.*
import mede.com.medesharevietnam.MatchingActivity
import mede.com.medesharevietnam.R
import mede.com.medesharevietnam.common.Const
import mede.com.medesharevietnam.domain.match.Doctor
import mede.com.medesharevietnam.domain.match.DoctorAbout
import mede.com.medesharevietnam.domain.match.DoctorReviews

class DoctorMatchActivity : AppCompatActivity() {
    var doctorKey:String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_doctor_match)

        doctorKey = intent.getStringExtra(Const.EXT_DOCTOR_KEY)
        if(doctorKey == null && doctorKey == "") finishActivity(Activity.RESULT_CANCELED)

        init()
        initView()

        loadData()
    }

    private fun init(){

    }

    private fun initView(){


        tabLayout.addOnTabSelectedListener(TabLayout.ViewPagerOnTabSelectedListener(viewPager))
        viewPager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabLayout))

    }

    private fun loadData(){
        var doctor = Doctor()
        var doctorAbout = DoctorAbout()
        var doctorReviews = DoctorReviews()
    }

    fun onMatchingSelect(v: View){
        val intentMatching = Intent(this, MatchingActivity::class.java)
        startActivity(intentMatching)
    }
}
