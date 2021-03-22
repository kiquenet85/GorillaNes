package com.nesgorilla.feature.feed.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nesgorilla.R
import com.nesgorilla.model.database.entity.Feed


class FeedListAdapter (private val localDataSet: MutableList<Feed>) : RecyclerView.Adapter<FeedListAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        //val textView: TextView

        init {
            //textView = view.findViewById(R.id.textView)
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.feed_list_item, viewGroup, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

       // viewHolder.textView.text = localDataSet[position]
    }

    override fun getItemCount(): Int {
        return localDataSet.size
    }

    fun addNewItems(allItems: List<Feed>) {
        localDataSet.retainAll(allItems)
        notifyDataSetChanged()
    }
}
