package com.vngrs.githubchallange.profile

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.facebook.drawee.view.SimpleDraweeView
import com.vngrs.githubchallange.R
import com.vngrs.githubchallange.model.Repository
import com.vngrs.githubchallange.model.UserResponse

private const val VIEW_TYPE_USER = 0
private const val VIEW_TYPE_REPOSITORY = 1

class ProfileAdapter(
    private val user: UserResponse,
    private val repositoryList: List<Repository>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val itemView: View

        return when (viewType) {
            VIEW_TYPE_USER -> {
                itemView = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_profile_user, parent, false)
                UserViewHolder(itemView)
            }
            VIEW_TYPE_REPOSITORY -> {
                itemView = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_profile_repository, parent, false)
                RepositoryViewHolder(itemView)
            }
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        var repositoryPosition = position
        if (position > 0) {
            repositoryPosition -=1
        }
        val repository = repositoryList[repositoryPosition]
        when(holder) {
            is UserViewHolder -> holder.bind(user)
            is RepositoryViewHolder -> holder.bind(repository)
            else -> throw IllegalArgumentException()
        }
    }

    override fun getItemViewType(position: Int): Int = if (position == 0) {
        VIEW_TYPE_USER
    } else {
        VIEW_TYPE_REPOSITORY
    }

    override fun getItemCount() = repositoryList.size + 1

    inner class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var avatarDraweeView: SimpleDraweeView =
            itemView.findViewById(R.id.item_profile_user_avatar_draweeview)

        var ownerNameTextView: TextView =
            itemView.findViewById(R.id.item_profile_user_owner_name_textview)

        var nameTextView: TextView =
            itemView.findViewById(R.id.item_profile_user_name_textview)

        var emailTextView: TextView =
            itemView.findViewById(R.id.item_profile_user_email_textview)

        var locationTextView: TextView =
            itemView.findViewById(R.id.item_profile_user_location_textview)

        fun bind(user: UserResponse) {
            avatarDraweeView.setImageURI(user.avatarUrl)
            ownerNameTextView.text = user.login
            nameTextView.text = user.name
            emailTextView.text = user.email
            locationTextView.text = user.location
        }
    }

    inner class RepositoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var nameTextView: TextView =
            itemView.findViewById(R.id.item_profile_repository_name_textview)

        var fullNameTextView: TextView =
            itemView.findViewById(R.id.item_profile_repository_fullname_textview)

        var languageTextView: TextView =
            itemView.findViewById(R.id.item_profile_repository_language_textview)

        var branchNameTextView: TextView =
            itemView.findViewById(R.id.item_profile_repository_branch_textview)

        fun bind(repository: Repository) {
            nameTextView.text = repository.name
            fullNameTextView.text = repository.fullName
            languageTextView.text = repository.language
            branchNameTextView.text = repository.defaultBranch
        }
    }

}