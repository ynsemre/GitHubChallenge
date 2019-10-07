package com.vngrs.githubchallange.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
class UserResponse(
    @SerializedName("login") var login: String? = null,
    @SerializedName("avatar_url") var avatarUrl: String? = null,
    @SerializedName("type") var type: String? = null,
    @SerializedName("name") var name: String? = null,
    @SerializedName("company") var company: String? = null,
    @SerializedName("blog") var blog: String? = null,
    @SerializedName("location") var location: String? = null,
    @SerializedName("email") var email: String? = null,
    @SerializedName("public_repos") var publicRepos: Int = 0
) : Parcelable