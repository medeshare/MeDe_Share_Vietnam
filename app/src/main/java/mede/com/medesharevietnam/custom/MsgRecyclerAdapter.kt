package mede.com.medesharevietnam.custom

import android.content.Context
import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.custom_message.view.*
import mede.com.medesharevietnam.R
import mede.com.medesharevietnam.databinding.CustomMessageBinding
import mede.com.medesharevietnam.domain.chat.MsgItem

/**
 * Created by user on 2018-01-30.
 */
class MsgRecyclerAdapter(val context: Context): RecyclerView.Adapter<MsgRecyclerAdapter.Holder>(){

    var msgData: MutableList<MsgItem> = ArrayList()

    fun addDataAndRefresh(data : MsgItem){
        msgData.add(data)
        notifyDataSetChanged()
    }

    fun addDataAndRefresh(data : List<MsgItem>){
        msgData.addAll(data)
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): Holder {
        val view = LayoutInflater.from(context).inflate(R.layout.custom_message, parent, false)

        return Holder(DataBindingUtil.bind(view))
    }

    override fun getItemCount(): Int {
        return msgData.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        if(position-1>=0 && msgData[position-1].who==false){
            holder.itemView.imageView.visibility= View.INVISIBLE
        }else{
            holder.itemView.imageView.visibility= View.VISIBLE
        }
        holder.setItem(msgData[position])
    }

    inner class Holder(itemBinding: CustomMessageBinding) : RecyclerView.ViewHolder(itemBinding.root) {
        private val itemBinding:CustomMessageBinding

        init {
            this.itemBinding = itemBinding
        }

        fun setItem(item: MsgItem){
            itemBinding.item = item
        }
    }
}
