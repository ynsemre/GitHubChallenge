package com.vngrs.githubchallange.search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.facebook.drawee.view.SimpleDraweeView
import com.vngrs.githubchallange.R
import com.vngrs.githubchallange.model.SearchItem

class SearchAdapter(
    private val searchItemList: List<SearchItem>,
    private val avatarClickAction: (String) -> Unit,
    private val repositoryInfoClickAction: (String) -> Unit
) : RecyclerView.Adapter<SearchAdapter.SearchViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        SearchViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_search_repositories, parent, false)
        )

    override fun getItemCount() = searchItemList.size

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        val searchItem = searchItemList.get(position)
        val owner = searchItem.owner

        owner?.let {
            holder.avatarDraweeView.setImageURI(it.avatarUrl)
            holder.ownerNameTextView.text =
                holder.ownerNameTextView.context.resources.getString(
                    R.string.search_repositories_owner,
                    it.login
                )
        }

        with(holder.repositoryNameTextView) {
            text = context.resources.getString(
                R.string.search_repositories_repository,
                searchItem.name
            )
        }
    }

    inner class SearchViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {

        var avatarDraweeView: SimpleDraweeView =
            itemView.findViewById(R.id.item_search_repositories_avatar_draweeview)

        var infoLinearLayout: LinearLayout =
            itemView.findViewById(R.id.item_search_repositories_info_container)

        var ownerNameTextView: TextView =
            itemView.findViewById(R.id.item_search_owner_name_textview)

        var repositoryNameTextView: TextView =
            itemView.findViewById(R.id.item_search_repository_name_textview)

        init {
            val userName = searchItemList[adapterPosition].owner!!.login!!
            avatarDraweeView.setOnClickListener { avatarClickAction(userName) }

            infoLinearLayout.setOnClickListener { repositoryInfoClickAction(userName) }
        }

    }

}