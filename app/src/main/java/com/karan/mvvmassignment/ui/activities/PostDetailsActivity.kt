package com.karan.mvvmassignment.ui.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.karan.mvvmassignment.data.Post
import com.karan.mvvmassignment.databinding.ActivityPostDetailsBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class PostDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPostDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPostDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val post = intent.getParcelableExtra<Post>("post")
        if (post != null) {
            binding.post = post
        }
    }
}