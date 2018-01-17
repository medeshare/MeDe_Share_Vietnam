package mede.com.medesharevietnam.custom

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Filter
import android.widget.TextView
import mede.com.medesharevietnam.R
import mede.com.medesharevietnam.domain.Disease


/**
 * Created by daeho on 2018. 1. 17..
 */
class MediAdapter(internal var context: Context, internal var resource: Int, internal var textViewResourceId: Int, internal var items: List<Disease>) : ArrayAdapter<Disease>(context, resource, textViewResourceId, items) {
    internal var tempItems: ArrayList<Disease> = ArrayList()
    internal var suggestions: ArrayList<Disease> = ArrayList()

    /**
     * Custom Filter implementation for custom suggestions we provide.
     */
    internal var nameFilter: Filter = object : Filter() {
        override fun convertResultToString(resultValue: Any): CharSequence {
            return (resultValue as Disease).name
        }

        override fun performFiltering(constraint: CharSequence?): FilterResults {
            suggestions.clear()

            if (constraint != null) {
                for (people in tempItems) {
                    if (people.name.toLowerCase().contains(constraint.toString().toLowerCase())) {
                        suggestions.add(people)
                    }
                }
            }

            val filterResults = FilterResults()
            filterResults.values = suggestions
            filterResults.count = suggestions.size
            return filterResults
//            } else {
//                return FilterResults()
//            }
        }

        override fun publishResults(constraint: CharSequence, results: FilterResults) {
            val filterList = results!!.values as ArrayList<Disease>
            if (results != null && results!!.count > 0) {
                clear()
                if(filterList != null) {
                    for (people in filterList) {
                        add(people)
                        notifyDataSetChanged()
                    }
                }
            }
        }
    }

    init {
        tempItems = ArrayList(items) // this makes the difference.
        suggestions = ArrayList()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View? {
        var view = convertView
        if (convertView == null) {
            val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            view = inflater.inflate(R.layout.item_autocomplete_disease, parent, false)
        }

        val people = items[position]
        if (people != null) {
            val lblName = view!!.findViewById(R.id.tvName) as TextView
            lblName?.setText(people.name)
        }
        return view
    }

    override fun getFilter(): Filter {
        return nameFilter
    }
}