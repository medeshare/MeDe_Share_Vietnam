package mede.com.medesharevietnam.controller

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.graphics.PorterDuff
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.ActionBar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.LayoutInflater
import android.view.View
import com.alamkanak.weekview.MonthLoader
import com.alamkanak.weekview.WeekViewEvent
import kotlinx.android.synthetic.main.custom_action_bar.view.*
import kotlinx.android.synthetic.main.item_hospital_doctor_profile.*
import kotlinx.android.synthetic.main.item_hospital_doctor_schedule.*
import mede.com.medesharevietnam.R
import mede.com.medesharevietnam.common.Const
import mede.com.medesharevietnam.common.DataBinder




class HospitalDoctorProfileActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hospital_doctor_profile)

        setCustomActionbar()
        initView()
    }

    private fun setCustomActionbar() {
        val actionBar = supportActionBar

        if(actionBar != null){
            actionBar.setDisplayShowCustomEnabled(true)
            actionBar.setDisplayHomeAsUpEnabled(false)
            actionBar.setDisplayShowTitleEnabled(false)
            actionBar.elevation = 0f

            val mCustomView = LayoutInflater.from(this).inflate(R.layout.custom_action_bar, null)
            mCustomView.btnMenu.visibility = View.GONE
            mCustomView.ivLogo.visibility = View.GONE
            mCustomView.btnBack.visibility = View.VISIBLE
            mCustomView.btnBack.setOnClickListener { _ -> setResult(Activity.RESULT_CANCELED)
                finish() }
            mCustomView.tvTitle.visibility = View.VISIBLE
            mCustomView.tvTitle.text = "Dr.Michael"
            mCustomView.btnAlarm.visibility = View.INVISIBLE

            val params = ActionBar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT)
            actionBar.setCustomView(mCustomView, params)

            val parent = mCustomView.getParent() as Toolbar
            parent.setContentInsetsAbsolute(0, 0)
        }
    }

    private fun initView(){
        weekView.setOnEventClickListener{event, eventRect ->
                TODO("not implemented")
        }
        weekView.setMonthChangeListener(object:MonthLoader.MonthChangeListener{
            override fun onMonthChange(newYear: Int, newMonth: Int): MutableList<out WeekViewEvent> {
                return ArrayList<WeekViewEvent>()
            }
        })
        weekView.setEventLongPressListener{event, eventRect ->
                TODO("not implemented")
        }

        DataBinder.setImageUrl(ivProfile, "http://image.kmib.co.kr/online_image/2017/0925/201709251041_61120011781723_1.jpg")
        ivProfile.setColorFilter(Color.parseColor("#BDBDBD"), PorterDuff.Mode.MULTIPLY)
    }

    fun onCall(v: View){
        val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:123-1234-1234"))
        startActivity(intent)
    }

    fun onAppointment(v: View){
        val intentMatching = Intent(this, MatchingActivity::class.java)
        intentMatching.putExtra(Const.EXT_DOCTOR_KEY, "l01")
        startActivityForResult(intentMatching, Const.REQ_DOCTOR_MATCH)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(requestCode == Const.REQ_DOCTOR_MATCH){
            if(resultCode == RESULT_OK) {
                val intent = Intent(this, MatchingInfoActivity::class.java)
                intent.putExtra(Const.EXT_DOCTOR_KEY, data?.getStringExtra(Const.EXT_DOCTOR_KEY))
                startActivity(intent)
            }
        }
    }
}
