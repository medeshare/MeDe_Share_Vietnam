package mede.com.medesharevietnam.controller

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_hanoi_dept.*
import mede.com.medesharevietnam.R
import mede.com.medesharevietnam.common.Const
import mede.com.medesharevietnam.custom.MediAutoCompleteAdapter
import mede.com.medesharevietnam.domain.medical.MediDisease
import mede.com.medesharevietnam.domain.medical.MedicalManager

class HanoiDeptActivity : AppCompatActivity(){
    private var selectedDisease: MediDisease? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hanoi_dept)

        initView()
    }

    private fun initView(){
        txt_deptSearch.setThreshold(1);
        var adapter = MediAutoCompleteAdapter(this, R.layout.activity_main, R.id.tvDiseaseName, MedicalManager.getAllDiseases())
        txt_deptSearch.setAdapter(adapter)

        txt_deptSearch.setOnItemClickListener {
            adapterView, view, i,
            l -> selectedDisease = adapter.getItem(i)
        }
    }

    fun search(v: View){
        var mediKey = selectedDisease?.mediKey

        if(mediKey != null){
            val intent = Intent(this, HanoiDoctorActivity::class.java)
            intent.putExtra(Const.EXT_MEDI_KEY, mediKey)
            startActivity(intent)
        }
        else{
            Toast.makeText(this,"please input complete disease",Toast.LENGTH_SHORT)
        }
    }

    fun clickDept(v: View){
        when{
            v==card_ortho -> goToDoctorActivity("m01")
            v==card_ear -> goToDoctorActivity("m03")
            v==card_optha -> goToDoctorActivity("m04")
            v==card_dentisty -> goToDoctorActivity("m05")
            v==card_general -> goToDoctorActivity("m02")
        }
    }

    fun goToDoctorActivity(mediKey: String){
        val intent = Intent(this, HanoiDoctorActivity::class.java)
        intent.putExtra(Const.EXT_MEDI_KEY, mediKey)
        startActivity(intent)
    }
}
