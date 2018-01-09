package a.mnisdh.com.kotlingooglemap.util

import android.app.Activity
import android.content.pm.PackageManager
import android.os.Build
import android.support.annotation.RequiresApi

/**
 * Created by daeho on 2018. 1. 8..
 */
class PermissionUtil(private val REQ_CODE: Int, private val permissions: ArrayList<String>) {

    fun check(activity: Activity, success: (()->Unit), failed: (()->Unit)) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) requestPermission(activity, success, failed)
        else success()
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private fun requestPermission(activity: Activity, success: (()->Unit), failed: (()->Unit)) {
        // 3. 권한에 대한 승인여부
        val requires = ArrayList<String>()
        for (perm in permissions) {
            if (activity.checkSelfPermission(perm) != PackageManager.PERMISSION_GRANTED) requires.add(perm)
        }

        // 4. 승인이 안된 권한이 있을경우 승인 요청
        if (requires.size > 0) activity.requestPermissions(requires.toTypedArray(), REQ_CODE)
        else success()
    }

    private fun afterPermissionResult(requestCode: Int, grantResults: IntArray, success: (()->Unit), failed: (()->Unit)): Boolean {
        if (requestCode == REQ_CODE) {
            var granted = true
            for (result in grantResults) {
                if (result != PackageManager.PERMISSION_GRANTED) granted = false
            }

            if (granted) {
                success()
                return true
            } else {
                failed()
                // 승인이 안된경우 finish() 처리한다
            }
        }

        return false
    }
}