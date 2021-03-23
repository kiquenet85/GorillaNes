package com.nesgorilla.feature.feed.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.nesgorilla.R
import com.nesgorilla.di.AppComponent
import com.nesgorilla.manager.ResourceManager
import com.nesgorilla.model.database.entity.Feed
import com.nesgorilla.util.WHITE_SPACE


class FeedListAdapter(private val localDataSet: MutableList<Feed>) :
    RecyclerView.Adapter<FeedListAdapter.ViewHolder>() {

    private val resourceManager: ResourceManager = AppComponent.getInstance().provideResourceManager()

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView = view.findViewById(R.id.name)
        val date: TextView = view.findViewById(R.id.date)
        val description: TextView = view.findViewById(R.id.description)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.feed_list_item, viewGroup, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        with(localDataSet[position]) {
            viewHolder.name.text = first_name.plus(WHITE_SPACE).plus(last_name)
            viewHolder.date.text = resourceManager.formatDate(unix_timestamp)
            viewHolder.description.text = post_body
        }
    }

    override fun getItemCount(): Int {
        return localDataSet.size
    }

    fun addNewItems(allItems: List<Feed>) {
        localDataSet.clear()
        localDataSet.addAll(allItems)
        notifyDataSetChanged()
    }
}
