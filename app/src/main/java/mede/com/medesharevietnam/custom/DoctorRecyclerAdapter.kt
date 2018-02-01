package mede.com.medesharevietnam.custom

import android.content.Context
import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import mede.com.medesharevietnam.R
import mede.com.medesharevietnam.databinding.ItemDoctorRecyclerBinding
import mede.com.medesharevietnam.domain.match.Doctor

/**
 * Created by user on 2018-02-01.
 */
class DoctorRecyclerAdapter(var context: Context, var itemClick : (Doctor)->Unit) : RecyclerView.Adapter<DoctorRecyclerAdapter.Holder>() {
    private var data:MutableList<Doctor> = ArrayList()

    init {

    }

    fun addData(item: Doctor){
        data.add(item)
    }

    fun addData(items:MutableList<Doctor>){
        data.addAll(items)
    }

    fun refresh(){
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<ItemDoctorRecyclerBinding>(layoutInflater, R.layout.item_doctor_recycler, parent, false)

        return Holder(binding)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: Holder?, position: Int) {
        holder?.setItem(data.get(position))
    }

    inner class Holder(itemBinding: ItemDoctorRecyclerBinding) : RecyclerView.ViewHolder(itemBinding.root) {
        var itemBinding: ItemDoctorRecyclerBinding

        init{
            this.itemBinding = itemBinding
            this.itemBinding.root.setOnClickListener{itemClick(this.itemBinding.doctor!!)}
            }

        fun setItem(item: Doctor){
            itemBinding.doctor = item
        }
    }

}