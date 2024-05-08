package com.vk.usersapp.feature.feed.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.RecyclerView
import com.vk.usersapp.R
import com.vk.usersapp.core.ViewModelProvider
import com.vk.usersapp.feature.feed.presentation.UserListAction
import com.vk.usersapp.feature.feed.presentation.UserListFeature
import com.vk.usersapp.feature.feed.presentation.UserListViewState
import kotlinx.coroutines.launch

class UserListFragment : Fragment() {

    private val adapter: UserListAdapter by lazy { UserListAdapter() }
    private var recycler: RecyclerView? = null
    private var queryView: EditText? = null
    private var errorView: TextView? = null
    private var loaderView: ProgressBar? = null

    val feature: UserListFeature by lazy { ViewModelProvider.obtainFeature { UserListFeature() } }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fr_user_list, container, false).apply {
            recycler = findViewById(R.id.recycler)
            recycler?.adapter = adapter
            queryView = findViewById(R.id.search_input)
            errorView = findViewById(R.id.error)
            loaderView = findViewById(R.id.loader)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        queryView?.addTextChangedListener {
            it?.let {
                feature.submitAction(UserListAction.QueryChanged(it.toString()))
            }
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                feature.viewStateFlow.collect {
                    renderState(it)
                }
            }
        }
    }

    override fun onDestroy() {
        feature.let {
            if (activity?.isFinishing == true) {
                ViewModelProvider.destroyFeature(it.javaClass)
            }
        }
        super.onDestroy()
    }

    private fun renderState(viewState: UserListViewState) {
        when (viewState) {
            is UserListViewState.Error -> {
                errorView?.isVisible = true
                errorView?.text = viewState.errorText
                loaderView?.isVisible = false
                recycler?.isVisible = false
            }

            is UserListViewState.List -> {
                loaderView?.isVisible = false
                if (viewState.itemsList.isEmpty()) {
                    errorView?.isVisible = true
                    recycler?.isVisible = false
                    errorView?.text = requireContext().getString(R.string.nothing_found)
                } else {
                    errorView?.isVisible = false
                    recycler?.isVisible = true
                    adapter.setUsers(viewState.itemsList)
                }
            }

            UserListViewState.Loading -> {
                errorView?.isVisible = false
                loaderView?.isVisible = true
                recycler?.isVisible = false
            }
        }
    }
}