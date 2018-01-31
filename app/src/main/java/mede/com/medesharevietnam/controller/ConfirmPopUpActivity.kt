package mede.com.medesharevietnam.controller

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import kotlinx.android.synthetic.main.activity_confirm_pop_up.*
import mede.com.medesharevietnam.R
import mede.com.medesharevietnam.common.Const

class ConfirmPopUpActivity : AppCompatActivity() {
    var doctorKey:String = ""
    var isClose = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_confirm_pop_up)

        doctorKey = intent.getStringExtra(Const.EXT_DOCTOR_KEY)
        if(doctorKey == null && doctorKey == "") {
            setResult(Activity.RESULT_CANCELED)
            finish()
        }
    }

    fun onOk(v: View){
        if(isClose) {
            var data = Intent()
            data.putExtra(Const.EXT_DOCTOR_KEY, doctorKey)
            setResult(Activity.RESULT_OK, data)

            finish()
        }
        else {
            tvMessage.text = "You have 5 minutes left."
            isClose = true
        }
    }
}
