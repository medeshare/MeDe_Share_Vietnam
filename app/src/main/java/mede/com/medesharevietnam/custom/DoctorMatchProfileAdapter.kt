package mede.com.medesharevietnam.custom

import android.support.v4.view.PagerAdapter
import android.view.View

/**
 * Created by daeho on 2018. 1. 25..
 */
class DoctorMatchProfileAdapter : PagerAdapter() {
    private var data : ArrayList<View> = ArrayList()

    fun setData(data:ArrayList<View>){
        this.data = data
    }

    override fun isViewFromObject(view: View?, `object`: Any?): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getCount(): Int {
        return data.size
    }

}