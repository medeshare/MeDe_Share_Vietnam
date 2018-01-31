package mede.com.medesharevietnam

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import kotlinx.android.synthetic.main.activity_chat.*
import mede.com.medesharevietnam.custom.MsgRecyclerAdapter
import mede.com.medesharevietnam.domain.chat.MsgItem
import java.text.SimpleDateFormat
import java.util.*

class ChatActivity : AppCompatActivity() {
    lateinit private var msgAdapter : MsgRecyclerAdapter
    val url: String = "http://cdn.ajoumc.or.kr/Upload/MedicalCenter/Doctor/Profile/201303/104382.jpg"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)
        initView()
    }

    private fun initView(){
        edit_msg.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(s.isNullOrEmpty()){
                    btn_send.visibility=GONE
                }else{
                    btn_send.visibility=VISIBLE
                }
            }
        })

        msgAdapter = MsgRecyclerAdapter(this)
        chatRecyclerView.adapter = msgAdapter

        val linearLayoutManager = LinearLayoutManager(this)
        chatRecyclerView.layoutManager = linearLayoutManager
        setData()
    }

    fun setData(){
        var msgData = ArrayList<MsgItem>()
        msgData.add(MsgItem("Hi", "PM 1:00",url,false))
        msgData.add(MsgItem("Hi", "PM 1:05",null,true))
        msgData.add(MsgItem("Today Reservation is full booked sorry", "PM 1:07",url,false))
        msgAdapter.addDataAndRefresh(msgData)
        chatRecyclerView.scrollToPosition(msgAdapter.itemCount-1)
    }

    fun getTime() : String{
        var now = System.currentTimeMillis()
        var date = Date(now)
        var sdf = SimpleDateFormat("a HH:mm")
        return sdf.format(date)
    }

    fun send(v:View){
        var msg = MsgItem(edit_msg.text.toString(),getTime(),null,true)
        msgAdapter.addDataAndRefresh(msg)
        chatRecyclerView.scrollToPosition(msgAdapter.itemCount-1)
        edit_msg.text=null
    }

    fun showOption(v: View){
        if(optionLayout.visibility==GONE){
            optionLayout.visibility=VISIBLE
        }else{
            optionLayout.visibility=GONE
        }
    }

    override fun onBackPressed() {
        if(optionLayout.visibility==VISIBLE){
            optionLayout.visibility=GONE
        }else{
            finish()
        }
    }

}
