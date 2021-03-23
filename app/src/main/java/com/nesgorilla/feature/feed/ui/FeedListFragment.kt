package com.nesgorilla.feature.feed.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
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
import java.util.*

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FeedListFragment : BaseFragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var feedHeader: LinearLayout
    private lateinit var postBox: EditText
    private lateinit var feedAdapter: FeedListAdapter
    private val viewModel by viewModels<FeedListViewModel>(factoryProducer = {
        AppComponent.getInstance().provideFeedModule()
            .provideFeedListViewFactory((requireActivity() as AppCompatActivity))
    }, ownerProducer = { this })

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.feed_list_fragment, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<TextView>(R.id.date).text =
            resourceManager.formatDate(Calendar.getInstance().time)
        recyclerView = view.findViewById(R.id.feed)
        feedHeader = view.findViewById(R.id.feedHeader)
        postBox = view.findViewById(R.id.post)

        postBox.apply {
            setOnFocusChangeListener { it, hasFocus ->
                if (hasFocus) {
                    navigateToCreatePost()
                    it.clearFocus()
                }
            }
        }

        feedAdapter = FeedListAdapter(mutableListOf())
        val itemDecorator = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
        itemDecorator.setDrawable(
            ContextCompat.getDrawable(
                requireContext(),
                R.drawable.shape_item_divider
            )!!
        )
        recyclerView.apply {
            layoutManager = LinearLayoutManager(context)

            addItemDecoration(itemDecorator)
            addItemDecoration(
                MarginItemDecoration(
                    resources.getDimension(R.dimen.cell_top_margin).toInt(),
                    resources.getDimension(R.dimen.cell_top_margin).toInt(),
                    resources.getDimension(R.dimen.cell_label_margin).toInt()
                )
            )
            recyclerView.adapter = feedAdapter
        }

        viewModel.getErrorState().observe(viewLifecycleOwner, { errorState ->
            getView()?.let {
                Snackbar.make(it, errorState.message, Snackbar.LENGTH_SHORT).show()
            }
        })

        viewModel.getScreenState().observe(viewLifecycleOwner, { feedListState ->
            feedListState as FeedListLoaded
            feedAdapter.addNewItems(feedListState.allItems)
        })
    }

    private fun navigateToCreatePost() {
        findNavController().navigate(FeedListFragmentDirections.actionToCreatePost())
    }
}