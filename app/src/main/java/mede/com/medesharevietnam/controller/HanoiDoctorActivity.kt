package mede.com.medesharevietnam.controller

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import kotlinx.android.synthetic.main.activity_hanoi_doctor.*
import mede.com.medesharevietnam.R
import mede.com.medesharevietnam.common.Const
import mede.com.medesharevietnam.custom.DoctorRecyclerAdapter
import mede.com.medesharevietnam.domain.match.Doctor
import mede.com.medesharevietnam.domain.medical.MediDisease
import mede.com.medesharevietnam.domain.medical.MedicalManager

class HanoiDoctorActivity : AppCompatActivity() {
    private var selectedDisease: MediDisease? = null
    private var mediKey:String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hanoi_doctor)
        mediKey = intent.getStringExtra(Const.EXT_MEDI_KEY)
        initView()
    }

    private fun initView(){
        txt_name.text=MedicalManager.getSubject(mediKey)!!.name

        var doctors: MutableList<Doctor> = ArrayList()
        for(item in MedicalManager.getLocations(mediKey)){
            if(item.type=="1"){
                var doctor = Doctor()
                doctor.key = item.key
                doctor.name = item.name
                doctor.subjectName = item.getMediSubject()!!.name
                doctors.add(doctor)
            }
        }

        var adapter = DoctorRecyclerAdapter(this, {
            doctor ->  val intent = Intent(this, HospitalDoctorProfileActivity::class.java)
            intent.putExtra(Const.EXT_DOCTOR_KEY, doctor.key)
            startActivity(intent)})

        adapter.addData(doctors)
        doctor_recycler.adapter = adapter

        val linearLayoutManager = LinearLayoutManager(this)
        doctor_recycler.layoutManager = linearLayoutManager

    }

    fun back(v: View){
        finish()
    }
}
