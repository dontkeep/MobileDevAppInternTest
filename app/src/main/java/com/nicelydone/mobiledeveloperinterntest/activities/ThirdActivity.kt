package com.nicelydone.mobiledeveloperinterntest.activities

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.nicelydone.mobiledeveloperinterntest.activities.adapter.UserAdapter
import com.nicelydone.mobiledeveloperinterntest.databinding.ActivityThirdBinding
import com.nicelydone.mobiledeveloperinterntest.viewmodel.ThirdViewModel

class ThirdActivity : AppCompatActivity() {
   private lateinit var binding: ActivityThirdBinding
   private val viewModel by viewModels<ThirdViewModel>()

   private val userAdapter = UserAdapter { selectedUser ->
      val resultIntent = intent.apply {
         putExtra("selectedUser", "${selectedUser.firstName} ${selectedUser.lastName}")
      }
      setResult(RESULT_OK, resultIntent)
      finish()
   }

   override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)
      binding = ActivityThirdBinding.inflate(layoutInflater)
      setContentView(binding.root)

      setupToolbar()

      setupRecyclerView()

      viewModel.fetchData()
      viewModel.user.observe(this) {
         userAdapter.submitList(it)
      }

      viewModel.isLoading.observe(this) {
         binding.loadingAnimation.visibility = if (it) View.VISIBLE else View.GONE
      }
   }

   private fun setupRecyclerView() {
      binding.userRecyclerView.apply {
         layoutManager = LinearLayoutManager(this@ThirdActivity)
         addItemDecoration(DividerItemDecoration(this@ThirdActivity, LinearLayoutManager.VERTICAL))
         adapter = userAdapter
      }
   }

   private fun setupToolbar(){
      val toolbar: Toolbar = binding.toolbar
      setSupportActionBar(toolbar)

      toolbar.setNavigationOnClickListener {
         onBackPressedDispatcher.onBackPressed()
      }
   }
}