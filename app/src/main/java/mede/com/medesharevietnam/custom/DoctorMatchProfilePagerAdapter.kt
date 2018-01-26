package mede.com.medesharevietnam.custom

import android.content.Context
import android.databinding.DataBindingUtil
import android.support.v4.view.PagerAdapter
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.layout_doctor_about.view.*
import kotlinx.android.synthetic.main.layout_doctor_reviews.view.*
import mede.com.medesharevietnam.R
import mede.com.medesharevietnam.databinding.ItemDoctorAboutIntroduceBinding
import mede.com.medesharevietnam.databinding.ItemDoctorAboutLocationBinding
import mede.com.medesharevietnam.databinding.ItemDoctorAboutPriceBinding
import mede.com.medesharevietnam.databinding.ItemDoctorAboutTopBinding
import mede.com.medesharevietnam.domain.match.DoctorAbout
import mede.com.medesharevietnam.domain.match.DoctorReviews

/**
 * Created by daeho on 2018. 1. 25..
 */
class DoctorMatchProfilePagerAdapter(context:Context) : PagerAdapter() {
    private val ABOUT_KEY = 1
    private val REVIEWS_KEY = 2
    private val titles:ArrayList<Int> = ArrayList()

    private val aboutView:View
    private val aboutTopBinding:ItemDoctorAboutTopBinding
    private val aboutIntroduceBinding:ItemDoctorAboutIntroduceBinding
    private val aboutLocationBinding:ItemDoctorAboutLocationBinding
    private val aboutPriceBinding:ItemDoctorAboutPriceBinding

    private val reviewsView:View
    private val reviewsAdapter:DoctorReviewRecyclerAdapter

    private var about:DoctorAbout? = null
    private var reviews:DoctorReviews? = null

    init {
        titles.add(ABOUT_KEY)
        titles.add(REVIEWS_KEY)

        aboutView = LayoutInflater.from(context).inflate(R.layout.layout_doctor_about, null, false)
        aboutTopBinding = DataBindingUtil.bind(aboutView.layTop)
        aboutIntroduceBinding = DataBindingUtil.bind(aboutView.layIntroduce)
        aboutLocationBinding = DataBindingUtil.bind(aboutView.layLocation)
        aboutPriceBinding = DataBindingUtil.bind(aboutView.layPrice)

        reviewsView = LayoutInflater.from(context).inflate(R.layout.layout_doctor_reviews, null, false)
        reviewsAdapter = DoctorReviewRecyclerAdapter(context, {

        })
        reviewsView.rvReviews.adapter = reviewsAdapter
        reviewsView.rvReviews.layoutManager = LinearLayoutManager(context)
    }

    fun setAbout(about: DoctorAbout){
        this.about = about

        aboutTopBinding.doctor = about
        aboutIntroduceBinding.doctor = about
        aboutLocationBinding.doctor = about
        aboutPriceBinding.doctor = about
    }

    fun setReviews(reviews: DoctorReviews){
        this.reviews = reviews

        reviewsAdapter.addData(reviews.reviews)
        reviewsAdapter.refresh()
    }

    override fun instantiateItem(container: ViewGroup?, position: Int): Any {
        var v:View = aboutView

        if(position == 1) v = reviewsView

        if(container != null){
            if(container.findViewById<View>(v.id) == null) container.addView(v)
        }

        return v
    }

    override fun isViewFromObject(view: View?, `object`: Any?): Boolean {
        return view === `object`
    }

    override fun getCount(): Int {
        return titles.size
    }

    override fun destroyItem(container: ViewGroup?, position: Int, `object`: Any?) {
        //super.destroyItem(container, position, `object`)
    }
}