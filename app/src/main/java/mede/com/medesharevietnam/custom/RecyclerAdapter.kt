package mede.com.medesharevietnam.custom

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import mede.com.medesharevietnam.R
import mede.com.medesharevietnam.domain.SearchData

/**
 * Created by user on 2018-01-25.
 */
class RecyclerAdapter(val context: Context, val itemClick : (SearchData)->Unit) : RecyclerView.Adapter<RecyclerAdapter.Holder>() {

    var save = true
    val searchData = ArrayList<SearchData>()

    fun addDataAndRefresh(data : SearchData){
        if(save==true){
            searchData.add(data)
            notifyDataSetChanged()
        }
    }

    fun removeDataAndRefresh(data : SearchData){
        if(searchData.contains(data)) searchData.remove(data)
        notifyDataSetChanged()
    }

    fun removeAllDataAndRefresh(){
        searchData.clear()
        notifyDataSetChanged()
    }

    fun dontSave(){
        save=false
    }

    fun save(){
        save=true
    }

    fun getItemSize() : Int{
        return searchData.size
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): Holder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_list_search, parent, false)
        return Holder(view, itemClick)
    }


    override fun getItemCount(): Int {
        return searchData.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(searchData[position], context)
        holder.imgDelete.setOnClickListener{
            removeDataAndRefresh(searchData[position])
        }
    }


    inner class Holder(itemView: View, val itemClick: (SearchData) -> Unit) : RecyclerView.ViewHolder(itemView) {
        val txtDisease = itemView.findViewById<TextView>(R.id.txtDisease)
        val txtDate = itemView.findViewById<TextView>(R.id.txtDate)
        val imgDelete = itemView.findViewById<ImageView>(R.id.imgDelete)

        fun bind(data : SearchData, context : Context){
            txtDisease?.text = data.disease
            txtDate?.text = data.date
            txtDisease.setOnClickListener{itemClick(data)}

        }

    }

}