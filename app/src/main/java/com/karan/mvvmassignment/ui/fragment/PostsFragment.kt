package com.karan.mvvmassignment.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.karan.mvvmassignment.databinding.FragmentPostsBinding
import com.karan.mvvmassignment.ui.activities.PostDetailsActivity
import com.karan.mvvmassignment.ui.adapter.PostsAdapter
import com.karan.mvvmassignment.ui.base.ViewBindingFragment
import com.karan.mvvmassignment.ui.uistate.UiState
import com.karan.mvvmassignment.ui.viewmodels.PostsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PostsFragment : ViewBindingFragment<FragmentPostsBinding, PostsViewModel>() {

    override val viewModel by activityViewModels<PostsViewModel>()
    private var isLoadingMore = false
    private val adapter = PostsAdapter { post ->
        val intent = Intent(requireContext(), PostDetailsActivity::class.java)
        intent.putExtra("post", post)
        startActivity(intent)
    }

    override fun getViewBinding(
        inflater: LayoutInflater, container: ViewGroup?
    ): FragmentPostsBinding = FragmentPostsBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.fetchPosts()
        observePosts()
        binding.rvPosts.adapter = adapter

        binding.rvPosts.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val totalItemCount = layoutManager.itemCount
                val lastVisibleItem = layoutManager.findLastCompletelyVisibleItemPosition()

                if (!isLoadingMore && lastVisibleItem >= totalItemCount - 5) {
                    isLoadingMore = true
                    binding.progressBarLoadMore.isVisible = true
                    viewModel.fetchPosts()
                }
            }
        })
    }

    private fun observePosts() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.uiState.collectLatest { state ->
                when (state) {
                    is UiState.Loading -> {
                        binding.progressBar.isVisible = false
                    }

                    is UiState.Success -> {
                        binding.progressBar.isVisible = false
                        adapter.setPosts(state.posts)
                        isLoadingMore = false
                        binding.progressBarLoadMore.isVisible = false
                    }

                    is UiState.Error -> {
                        binding.progressBar.isVisible = false
                        showSnackBar(state.message)
                    }
                }
            }
        }
    }
}