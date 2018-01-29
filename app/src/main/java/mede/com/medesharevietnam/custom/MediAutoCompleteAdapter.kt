package mede.com.medesharevietnam.custom

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Filter
import android.widget.TextView
import mede.com.medesharevietnam.R
import mede.com.medesharevietnam.domain.medical.MediDisease


/**
 * Created by daeho on 2018. 1. 17..
 */
class MediAutoCompleteAdapter(internal var context: Context?, internal var resource: Int, internal var textViewResourceId: Int, internal var items: List<MediDisease>) : ArrayAdapter<MediDisease>(context, resource, textViewResourceId, items) {
    private var baseItems: ArrayList<MediDisease> = ArrayList()
    private var suggestions: LinkedHashMap<String, ArrayList<MediDisease>> = LinkedHashMap()
    private var addedSubjects: ArrayList<String> = ArrayList()

    private var diseaseFilter: Filter = object : Filter() {
        override fun convertResultToString(resultValue: Any): CharSequence {
            return (resultValue as MediDisease).name
        }

        override fun performFiltering(constraint: CharSequence?): FilterResults {
            suggestions.clear()

            if (constraint != null) {
                if (constraint.toString() == "") addSuggestions(baseItems)
                else {
                    for (item in baseItems) {
                        if (item.name.toLowerCase().contains(constraint.toString().toLowerCase())) {
                            addSuggestions(item)
                        }
                    }
                }
            }

            val filterResults = FilterResults()
            filterResults.values = suggestions
            filterResults.count = suggestions.size

            return filterResults
        }

        private fun addSuggestions(disease: MediDisease) {
            if (!suggestions.containsKey(disease.mediKey)) {
                suggestions.put(disease.mediKey, ArrayList())
            }
            disease.tag = null
            suggestions[disease.mediKey]?.add(disease)
        }

        private fun addSuggestions(diseases: ArrayList<MediDisease>) {
            for (disease in diseases) addSuggestions(disease)
        }

        override fun publishResults(constraint: CharSequence?, results: FilterResults) {
            val subjects = results.values as LinkedHashMap<String, ArrayList<MediDisease>>

            clear()

            if (results.count > 0) {
                for (subject in subjects) {
                    for(disease in subject.value) {
                        add(disease)
                    }
                }
            }

            addedSubjects.clear()
            notifyDataSetChanged()
        }
    }

    init {
        baseItems = ArrayList(items)
        suggestions = LinkedHashMap()
    }


    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View? {
        var view = convertView

        if (convertView == null) {
            val inflater = context!!.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            view = inflater.inflate(R.layout.item_autocomplete_disease, parent, false)
        }

        val disease = items[position]

        if(view != null){
            val tvSubject = view.findViewById(R.id.tvSubjectName) as TextView
            val tvDisease = view.findViewById(R.id.tvDiseaseName) as TextView

            tvSubject.text = disease.getMediSubject()!!.name
            if(!addedSubjects.contains(disease.mediKey)){
                addedSubjects.add(disease.mediKey)
                disease.tag = true
            }

            if(disease.tag != null) tvSubject.visibility = View.VISIBLE
            else tvSubject.visibility = View.GONE

            tvDisease.text = disease.name
        }

        return view
    }

    override fun getFilter(): Filter {
        return diseaseFilter
    }
}