package com.nicelydone.mobiledeveloperinterntest.activities

import android.content.Intent
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.nicelydone.mobiledeveloperinterntest.databinding.ActivitySecondBinding

class SecondActivity : AppCompatActivity() {
   private lateinit var binding: ActivitySecondBinding


   override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)
      binding = ActivitySecondBinding.inflate(layoutInflater)
      setContentView(binding.root)

      setupToolbar()

      val name = intent.getStringExtra("name")
      binding.nameText.text = name

      val selectUserLauncher = registerForActivityResult(
         ActivityResultContracts.StartActivityForResult()
      ) { result ->
         if (result.resultCode == RESULT_OK) {
            val selectedUser = result.data?.getStringExtra("selectedUser")
            binding.selectedUserName.text = selectedUser
          }
      }

      binding.chooseButton.setOnClickListener {
         val intent = Intent(this, ThirdActivity::class.java)
         selectUserLauncher.launch(intent)
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