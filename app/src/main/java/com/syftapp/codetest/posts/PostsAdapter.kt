package com.syftapp.codetest.posts

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.syftapp.codetest.R
import com.syftapp.codetest.data.model.domain.Post
import kotlinx.android.synthetic.main.view_post_list_item.view.*

class PostsAdapter(
    private val data: List<Post>,
    private val presenter: PostsPresenter
) : RecyclerView.Adapter<PostViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.view_post_list_item, parent, false)

        return PostViewHolder(view, presenter)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        holder.bind(data.get(position))
    }
}

class PostViewHolder(private val view: View, private val presenter: PostsPresenter) : RecyclerView.ViewHolder(view) {

    fun bind(item: Post) {
        view.postTitle.text = item.title
        view.bodyPreview.text = item.body
        view.setOnClickListener { presenter.showDetails(item) }
    }

}