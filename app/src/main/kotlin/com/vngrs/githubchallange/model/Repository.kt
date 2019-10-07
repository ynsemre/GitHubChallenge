package com.vngrs.githubchallange.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Repository(
    @SerializedName("name") val name: String,
    @SerializedName("full_name") val fullName: String,
    @SerializedName("owner") val owner: Owner,
    @SerializedName("description") val description: String? = null,
    @SerializedName("default_branch") val defaultBranch: String,
    @SerializedName("language") val language: String? = null,
    @SerializedName("forks") val forks: Int
) : Parcelable