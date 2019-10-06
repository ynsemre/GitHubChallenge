package com.vngrs.githubchallange.core

import androidx.databinding.BindingAdapter
import com.facebook.drawee.view.SimpleDraweeView

@BindingAdapter("imageUri")
fun setImageUri(view: SimpleDraweeView, imageUrl: String) {
    view.setImageURI(imageUrl)
}