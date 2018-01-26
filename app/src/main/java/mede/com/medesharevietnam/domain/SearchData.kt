package mede.com.medesharevietnam.domain

/**
 * Created by user on 2018-01-25.
 */
class SearchData(val disease:String, val date:String) {
    override fun toString():String{
        return disease + SPLIT_REGEX + date
    }

    companion object {
        val SPLIT_REGEX = "%SD%"

        fun load(string: String):SearchData?{
            var strs = string.split(SPLIT_REGEX)

            if(strs.size == 2) return SearchData(strs[0], strs[1])
            else return null
        }
    }
}