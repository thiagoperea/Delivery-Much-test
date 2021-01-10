package com.thiagoperea.deliverymuchtest.presentation.repositorylist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.thiagoperea.deliverymuchtest.R
import com.thiagoperea.deliverymuchtest.data.model.Repository

class RepositoryListAdapter(
    private val onRepositoryClick: (Repository) -> Unit
) : RecyclerView.Adapter<RepositoryListAdapter.ViewHolder>() {

    private var dataset = mutableListOf<Repository>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_repository, parent, false)
        return ViewHolder(view, onRepositoryClick)
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) = holder.bind(dataset[position])

    override fun getItemCount() = dataset.size

    fun setData(repositories: List<Repository>?) {
        clearData()
        repositories?.let {
            dataset.addAll(it)
            notifyDataSetChanged()
        }
    }

    fun clearData() = dataset.clear()

    class ViewHolder(
        itemView: View,
        private val onRepositoryClick: (Repository) -> Unit
    ) : RecyclerView.ViewHolder(itemView) {

        fun bind(repository: Repository) {
            itemView.findViewById<TextView>(R.id.repositoryItemTitle)
                .text = repository.name

            itemView.findViewById<TextView>(R.id.repositoryItemSubtitle)
                .text = repository.author.username

            itemView.setOnClickListener { onRepositoryClick(repository) }
        }
    }
}
