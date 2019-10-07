package com.vngrs.githubchallange.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Owner(
    @SerializedName("login") val login: String,
    @SerializedName("avatar_url") val avatarUrl: String? = null
) : Parcelable