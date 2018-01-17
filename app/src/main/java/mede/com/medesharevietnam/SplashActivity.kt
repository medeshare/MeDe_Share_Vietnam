package mede.com.medesharevietnam

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity



/**
 * Created by nell on 17/01/2018.
 */
class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)

        finish()
    }
}