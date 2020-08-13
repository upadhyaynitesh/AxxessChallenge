package com.example.axxesschallenge.model

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.axxesschallenge.R
import java.io.Serializable

data class Image(
    val account_id: Any,
    val account_url: Any,
    val ad_type: Int,
    val ad_url: String,
    val animated: Boolean,
    val bandwidth: Double,
    val comment_count: Any,
    val datetime: Int,
    val description: String,
    val downs: Any,
    val edited: String,
    val favorite: Boolean,
    val favorite_count: Any,
    val has_sound: Boolean,
    val height: Int,
    val id: String,
    val in_gallery: Boolean,
    val in_most_viral: Boolean,
    val is_ad: Boolean,
    val link: String,
    val nsfw: Any,
    val points: Any,
    val score: Any,
    val section: Any,
    val size: Int,
    val tags: List<Any>,
    val title: Any,
    val type: String,
    val ups: Any,
    val views: Int,
    val vote: Any,
    val width: Int
) : Serializable {
    companion object {
        /*This will load the image downloaded using Glide in imageView of recycler items*/
        @BindingAdapter("iconImage")
        @JvmStatic
        fun loadThumbnail(view: ImageView, image: List<Image>?) {
            if (image != null) {
                Glide.with(view.context)  //2
                    .load(image[0].link) //3
                    .centerCrop() //4
                    .thumbnail(/*sizeMultiplier=*/ 0.40f)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .placeholder(R.drawable.place_holder) //5
                    .into(view)
            } else {
                view.setImageResource(R.drawable.place_holder)
            }
        }

        /*This will load the image downloaded using Glide in imageView of recycler items*/
        @BindingAdapter("itemImage")
        @JvmStatic
        fun loadImage(view: ImageView, image: List<Image>?) {
            if (image != null) {
                Glide.with(view.context)  //2
                    .load(image[0].link) //3
                    .centerCrop() //4
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .placeholder(R.drawable.place_holder) //5
                    .into(view)
            } else {
                view.setImageResource(R.drawable.place_holder)
            }
        }
    }
}