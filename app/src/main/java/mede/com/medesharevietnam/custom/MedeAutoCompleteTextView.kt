package mede.com.medesharevietnam.custom

import android.content.Context
import android.util.AttributeSet
import android.widget.AutoCompleteTextView



/**
 * Created by daeho on 2018. 1. 17..
 */
class MedeAutoCompleteTextView : AutoCompleteTextView {

    constructor(context: Context) : super(context) {
        // TODO Auto-generated constructor stub
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        // TODO Auto-generated constructor stub
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {
        // TODO Auto-generated constructor stub
    }

    init {

    }

    // this is how to disable AutoCompleteTextView filter
    override fun performFiltering(text: CharSequence, keyCode: Int) {
        val filterText = ""
        super.performFiltering(filterText, keyCode)
    }
}