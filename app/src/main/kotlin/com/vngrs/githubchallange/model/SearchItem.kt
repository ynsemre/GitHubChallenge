package com.vngrs.githubchallange.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class SearchItem {

    @SerializedName("id")
    @Expose
    var id: Int? = null
    @SerializedName("node_id")
    @Expose
    var nodeId: String? = null
    @SerializedName("name")
    @Expose
    var name: String? = null
    @SerializedName("full_name")
    @Expose
    var fullName: String? = null
    @SerializedName("private")
    @Expose
    var private: Boolean? = null
    @SerializedName("owner")
    @Expose
    var owner: Owner? = null
    @SerializedName("html_url")
    @Expose
    var htmlUrl: String? = null
    @SerializedName("description")
    @Expose
    var description: String? = null
    @SerializedName("fork")
    @Expose
    var fork: Boolean? = null
    @SerializedName("url")
    @Expose
    var url: String? = null
    @SerializedName("forks_url")
    @Expose
    var forksUrl: String? = null
    @SerializedName("keys_url")
    @Expose
    var keysUrl: String? = null
    @SerializedName("collaborators_url")
    @Expose
    var collaboratorsUrl: String? = null
    @SerializedName("teams_url")
    @Expose
    var teamsUrl: String? = null
    @SerializedName("hooks_url")
    @Expose
    var hooksUrl: String? = null
    @SerializedName("issue_events_url")
    @Expose
    var issueEventsUrl: String? = null

}