package mede.com.medesharevietnam

import android.app.Application
import android.content.Context
import mede.com.medesharevietnam.common.SharedPreferenceManager

/**
 * Created by user on 2018-01-26.
 */
class ApplicationInitializer : Application() {
    companion object {
        private lateinit var context : Context
        fun getAppContext() : Context{
            return ApplicationInitializer.context
        }
    }

    override fun onCreate() {
        super.onCreate()

        ApplicationInitializer.context = applicationContext
        SharedPreferenceManager

    }
}
