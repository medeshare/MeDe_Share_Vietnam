package mede.com.medesharevietnam.controller

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import kotlinx.android.synthetic.main.activity_confirm_pop_up.*
import mede.com.medesharevietnam.R

class ConfirmPopUpActivity : AppCompatActivity() {
    var isClose = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_confirm_pop_up)
    }

    fun onOk(v: View){
        if(isClose) {
            val intent = Intent(this, MatchingInfoActivity::class.java)
            startActivity(intent)
        }
        else {
            tvMessage.text = "You have 5 minutes left."
            isClose = true
        }
    }
}
