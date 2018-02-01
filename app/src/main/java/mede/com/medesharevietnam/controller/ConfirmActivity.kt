package mede.com.medesharevietnam.controller

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import kotlinx.android.synthetic.main.activity_confirm.*
import mede.com.medesharevietnam.R
import mede.com.medesharevietnam.common.Const
import mede.com.medesharevietnam.common.DataBinder
import mede.com.medesharevietnam.domain.match.Doctor
import mede.com.medesharevietnam.domain.medical.MedicalManager

class ConfirmActivity : AppCompatActivity() {
    var doctorKey:String = ""
    var place:String = ""
    var time:String = ""
    var price:String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_confirm)

        doctorKey = intent.getStringExtra(Const.EXT_DOCTOR_KEY)
        if(doctorKey == null && doctorKey == "") {
            setResult(Activity.RESULT_CANCELED)
            finish()
        }

        initView()

        loadData()
    }

    private fun initView(){

    }

    private fun loadData(){
        loadDoctor()
    }
    private fun loadDoctor(){
        var mediLoc = MedicalManager.getLocation(doctorKey)

        var doctor = Doctor()
        doctor.name = mediLoc!!.name
        doctor.subjectName = mediLoc!!.getMediSubject()!!.name

        tvName.text = doctor.name
        tvSymptomValue.text = doctor.subjectName
        DataBinder.setCircleImageUrl(ivProfile, doctor.imageUrl)
    }

    fun onCancel(v: View){
        setResult(Activity.RESULT_CANCELED)
        finish()
    }

    fun onAppointment(v: View){
        val intent = Intent(this, ConfirmPopUpActivity::class.java)
        intent.putExtra(Const.EXT_DOCTOR_KEY, doctorKey)
        startActivityForResult(intent, Const.REQ_DOCTOR_MATCH)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(requestCode == Const.REQ_DOCTOR_MATCH){
            setResult(resultCode, data)
            finish()
        }
    }
}
