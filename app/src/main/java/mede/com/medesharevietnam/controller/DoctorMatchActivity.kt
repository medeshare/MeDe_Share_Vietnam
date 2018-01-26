package mede.com.medesharevietnam.controller

import android.app.Activity
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v7.app.AppCompatActivity
import android.view.View
import kotlinx.android.synthetic.main.activity_doctor_match.*
import mede.com.medesharevietnam.R
import mede.com.medesharevietnam.common.Const
import mede.com.medesharevietnam.common.DataBinder
import mede.com.medesharevietnam.custom.DoctorMatchProfilePagerAdapter
import mede.com.medesharevietnam.domain.match.Doctor
import mede.com.medesharevietnam.domain.match.DoctorAbout
import mede.com.medesharevietnam.domain.match.DoctorReview
import mede.com.medesharevietnam.domain.match.DoctorReviews


class DoctorMatchActivity : AppCompatActivity() {
    var doctorKey:String = ""
    lateinit var pagerAdapter:DoctorMatchProfilePagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_doctor_match)

        doctorKey = intent.getStringExtra(Const.EXT_DOCTOR_KEY)
        if(doctorKey == null && doctorKey == "") finishActivity(Activity.RESULT_CANCELED)

        init()
        initView()

        loadData()
    }

    private fun init(){
        pagerAdapter = DoctorMatchProfilePagerAdapter(this)
    }

    private fun initView(){
        viewPager.adapter = pagerAdapter

        tabLayout.addOnTabSelectedListener(TabLayout.ViewPagerOnTabSelectedListener(viewPager))
        viewPager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabLayout))
    }

    private fun loadData(){
        loadDoctor()
        loadAbout()
        loadReviews()
    }
    private fun loadDoctor(){
        var doctor = Doctor()
        doctor.name = "Dr. James Riyadi"
        doctor.subjectName = "Internal medicine"
        doctor.imageUrl = "http://cdn.ajoumc.or.kr/Upload/MedicalCenter/Doctor/Profile/201303/104382.jpg"

        tvName.text = doctor.name
        tvSubjectName.text = doctor.subjectName
        DataBinder.setCircleImageUrl(ivProfile, doctor.imageUrl)
    }
    private fun loadAbout(){
        var doctorAbout = DoctorAbout()
        doctorAbout.hours = "319"
        doctorAbout.patients = "180"
        doctorAbout.rate = "4.3"
        doctorAbout.introduce = "Dr. James Riyadi is surrently the Director and H.O.D of the Department of Mediciene, Artemis Hospital, where he’s been working for the past 8 years."
        doctorAbout.mapUrl = "https://maps.googleapis.com/maps/api/staticmap?center=40.714728,-73.998672&zoom=14&size=800x500&key=AIzaSyA3VhwLoySupQAQxdS4fwBle7eE_UEf-9U"
        doctorAbout.price = "\$10 / Case"

        pagerAdapter.setAbout(doctorAbout)
    }
    private fun loadReviews(){
        var doctorReviews = DoctorReviews()
        doctorReviews.key = "1"

        var review = DoctorReview()
        review.key = "1"
        review.idx = 1
        review.userKey = "1"
        review.userName = "Daniel Thomas"
        review.userImageUrl = "http://www.melbourneresumes.com.au/wp-content/uploads/best-linkedin-profile-examples-300x200.jpg"
        review.rate = "4.3"
        review.rateTime = "2 min ago"
        review.review = "Dr. James made it easy during treatment, even though it was late. After the treatment, my fever went down. I'd like to meet again next time."
        doctorReviews.reviews.add(review)

        review = DoctorReview()
        review.key = "1"
        review.idx = 2
        review.userKey = "2"
        review.userName = "Avery Clayton"
        review.userImageUrl = "http://www.mba.hec.edu/var/hec_mba/storage/images/student-life/student-profiles/reema-arya/332608-1-eng-GB/Reema-Arya_profile_image-HEC-Paris-MBA.jpg"
        review.rate = "4.1"
        review.rateTime = "6 hour ago"
        review.review = "Great Doctor!"
        doctorReviews.reviews.add(review)

        review = DoctorReview()
        review.key = "1"
        review.idx = 3
        review.userKey = "3"
        review.userName = "Mas Hitman"
        review.userImageUrl = "http://www.leisureopportunities.co.uk/images/coprofilepic1_3267.gif"
        review.rate = "4.5"
        review.rateTime = "Yesterday"
        review.review = "He is very kind and answered all of my questions and concerns. "
        doctorReviews.reviews.add(review)

        pagerAdapter.setReviews(doctorReviews)
    }

    fun onCancel(v:View){
        setResult(Activity.RESULT_CANCELED)
        finish()
    }
}
