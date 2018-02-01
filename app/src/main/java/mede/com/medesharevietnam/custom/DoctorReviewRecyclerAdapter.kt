package mede.com.medesharevietnam.custom

import android.content.Context
import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import mede.com.medesharevietnam.R
import mede.com.medesharevietnam.databinding.ItemDoctorReviewBinding
import mede.com.medesharevietnam.domain.SearchData
import mede.com.medesharevietnam.domain.match.DoctorReview

/**
 * Created by daeho on 2018. 1. 26..
 */
class DoctorReviewRecyclerAdapter(context: Context, itemClick: (SearchData) -> Unit) : RecyclerView.Adapter<DoctorReviewRecyclerAdapter.Holder>() {
    private var data:ArrayList<DoctorReview> = ArrayList()

    init {

    }

    fun addData(item:DoctorReview){
        data.add(item)
    }

    fun addData(items:ArrayList<DoctorReview>){
        data.addAll(items)
    }

    fun refresh(){
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<ItemDoctorReviewBinding>(layoutInflater, R.layout.item_doctor_review, parent, false)

        return Holder(binding)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.setItem(data.get(position))
    }

    class Holder(itemBinding: ItemDoctorReviewBinding) : RecyclerView.ViewHolder(itemBinding.root) {
        private val itemBinding:ItemDoctorReviewBinding

        init{
            this.itemBinding = itemBinding
        }

        fun setItem(review: DoctorReview){
            itemBinding.doctor = review
        }
    }

}