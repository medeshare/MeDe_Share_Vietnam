package mede.com.medesharevietnam.common

import android.databinding.BindingAdapter
import android.widget.ImageButton
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

/**
 * Created by daeho on 2018. 1. 23..
 */
object DataBinder {
    @JvmStatic
    @BindingAdapter("image_url")
    fun setImageUrl(v: ImageView, imageUrl: String?) {
        if (imageUrl == null || imageUrl == "") return

        var requestOptions = RequestOptions().override(v.width, v.height).circleCrop()

        Glide.with(v.context)
                .load(imageUrl)
                .apply(requestOptions)
                .into(v)
    }

    @JvmStatic
    @BindingAdapter("image_url")
    fun setImageUrl(v: ImageView, imageResource: Int) {
        var requestOptions = RequestOptions().override(v.width, v.height).circleCrop()

        Glide.with(v.context)
                .load(imageResource)
                .apply(RequestOptions()
                        .override(v.width, v.height))
                .apply(requestOptions)
                .into(v)
    }

    @JvmStatic
    @BindingAdapter("image_url")
    fun setImageUrl(v: ImageButton, imageUrl: String?) {
        if (imageUrl == null || imageUrl == "") return

        var requestOptions = RequestOptions().override(v.width, v.height).circleCrop()

        Glide.with(v.context)
                .load(imageUrl)
                .apply(RequestOptions()
                        .override(v.width, v.height))
                .apply(requestOptions)
                .into(v)
    }

    @JvmStatic
    @BindingAdapter("image_url")
    fun setImageUrl(v: ImageButton, imageResource: Int) {
        var requestOptions = RequestOptions().override(v.width, v.height).circleCrop()

        Glide.with(v.context)
                .load(imageResource)
                .apply(RequestOptions()
                        .override(v.width, v.height))
                .apply(requestOptions)
                .into(v)
    }
}