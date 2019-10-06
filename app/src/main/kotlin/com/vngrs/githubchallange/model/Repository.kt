package com.vngrs.githubchallange.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Repository(
    @SerializedName("id") val id: Int,
    @SerializedName("node_id") val nodeId: String? = null,
    @SerializedName("name") val name: String,
    @SerializedName("full_name") val fullName: String,
    @SerializedName("private") val private: Boolean,
    @SerializedName("owner") val owner: Owner,
    @SerializedName("html_url") val htmlUrl: String? = null,
    @SerializedName("description") val description: String? = null,
    @SerializedName("fork") val fork: Boolean? = null,
    @SerializedName("url") val url: String,
    @SerializedName("default_branch") val defaultBranch: String,
    @SerializedName("language") val language: String? = null,
    @SerializedName("collaborators_url") val collaboratorsUrl: String? = null,
    @SerializedName("teams_url") val teamsUrl: String? = null,
    @SerializedName("hooks_url") val hooksUrl: String? = null,
    @SerializedName("issue_events_url") val issueEventsUrl: String? = null,
    @SerializedName("forks") val forks: Int
) : Parcelable