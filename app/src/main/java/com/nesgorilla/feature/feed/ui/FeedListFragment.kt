package com.nesgorilla.feature.feed.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.nesgorilla.R
import com.nesgorilla.base.BaseFragment
import com.nesgorilla.di.AppComponent
import com.nesgorilla.feature.feed.adapter.FeedListAdapter
import com.nesgorilla.feature.feed.use_case.FeedListLoaded
import com.nesgorilla.util.MarginItemDecoration

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FeedListFragment : BaseFragment() {

    private lateinit var adapter: FeedListAdapter
    private val viewModel by viewModels<FeedListViewModel>(factoryProducer = {
        AppComponent.getInstance().provideFeedModule()
            .provideWeatherListViewFactory((requireActivity() as AppCompatActivity))
    })

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.feed_list_fragment, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView = view.findViewById<RecyclerView>(R.id.favoriteLocations)
        adapter = FeedListAdapter(mutableListOf())
        recyclerView.layoutManager = LinearLayoutManager(context)
        val itemDecorator = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
        itemDecorator.setDrawable(
            ContextCompat.getDrawable(
                requireContext(),
                R.drawable.shape_item_divider
            )!!
        )
        recyclerView.addItemDecoration(itemDecorator)
        recyclerView.addItemDecoration(
            MarginItemDecoration(
                marginSides = resources.getDimension(R.dimen.cell_label_margin).toInt()
            )
        )
        recyclerView.adapter = adapter

        viewModel.getErrorState().observe(viewLifecycleOwner, { errorState ->
            getView()?.let {
                Snackbar.make(it, errorState.message, Snackbar.LENGTH_SHORT).show()
            }
        })

        viewModel.getScreenState().observe(viewLifecycleOwner, { feedListState ->
            feedListState as FeedListLoaded
            //adapter.addNewItems(feedListState.allItems)
            getView()?.let {
                Snackbar.make(it, "${feedListState.allItems.size}", Snackbar.LENGTH_SHORT).show()
            }
        })
    }
}