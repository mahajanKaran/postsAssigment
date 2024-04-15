package com.karan.mvvmassignment.ui.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.viewbinding.ViewBinding
import com.google.android.material.snackbar.Snackbar
import com.karan.mvvmassignment.R


abstract class ViewBindingFragment<VB : ViewBinding, VM : ViewModel> : Fragment() {

    private var _binding: VB? = null
    protected val binding get() = _binding!!
    protected abstract val viewModel: VM

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        if (_binding == null) {
            _binding = getViewBinding(inflater, container)
        }
        return binding.root
    }

    fun applicationContext(): Context = requireActivity().applicationContext

    protected abstract fun getViewBinding(inflater: LayoutInflater, container: ViewGroup?): VB

    fun showSnackBar(message: String) {
        val snackBar: Snackbar =
            Snackbar.make(requireActivity().window.decorView, message, Snackbar.LENGTH_LONG)
        snackBar.setBackgroundTint(ContextCompat.getColor(requireContext(), R.color.red))
        snackBar.show()
    }
}
