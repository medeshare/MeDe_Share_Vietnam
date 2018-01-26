package mede.com.medesharevietnam.common

import android.content.Context
import android.content.SharedPreferences
import mede.com.medesharevietnam.ApplicationInitializer
import mede.com.medesharevietnam.domain.SearchData

/**
 * Created by user on 2018-01-26.
 */
object SharedPreferenceManager{
    private val searchDataSpf : SharedPreferences
    private val searchEditor : SharedPreferences.Editor

    var SEARCH = "search"
    var SEARCH_DATA = "searchData"

    init {
        val context = ApplicationInitializer.getAppContext()
        searchDataSpf = context.getSharedPreferences(SEARCH, Context.MODE_PRIVATE)
        searchEditor = searchDataSpf.edit()
    }

    var searchDataList : ArrayList<SearchData>
        get(){
            var result: ArrayList<SearchData> = ArrayList()

            var tempData : List<String> = searchDataSpf.getString(SEARCH_DATA,"").split("::")
            for(temp in tempData){
                var tempSearchData = SearchData.load(temp)
                if(tempSearchData != null){
                    result.add(tempSearchData)
                }
            }

            return result
        }
        //SearchData(disease,,date) ::
        set(value) {
            val sb = StringBuilder("")
            for (temp in value) {
                sb.append(temp.toString())
                sb.append("::")
            }
            searchEditor.putString(SEARCH_DATA, sb.toString())
            searchEditor.commit()
        }
}