package mede.com.medesharevietnam.custom

import android.app.Activity
import android.app.Dialog
import android.view.View
import android.view.Window
import android.widget.TextView
import mede.com.medesharevietnam.R


/**
 * Created by nell on 29/01/2018.
 */

class ViewDialogAdapter {

    fun showDialog(activity: Activity, msg: String) {
        val dialog = Dialog(activity)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.custom_dialog)

        val text = dialog.findViewById(R.id.text_dialog) as TextView
        text.text = msg

        fun onOkay(v: View){
            dialog.dismiss()
        }
        dialog.show()
    }
}