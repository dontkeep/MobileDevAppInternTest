package com.nicelydone.mobiledeveloperinterntest.activities

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.nicelydone.mobiledeveloperinterntest.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
   private lateinit var binding: ActivityMainBinding
   private var currentImageUri: Uri? = null

   override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)
      binding = ActivityMainBinding.inflate(layoutInflater)
      setContentView(binding.root)

      binding.userIcon.setOnClickListener {
         checkAndRequestPermissions()
      }


      binding.checkButton.setOnClickListener {
         val text = binding.palindromeInput.text.toString()
         if (text.isEmpty()) {
            Toast.makeText(this, "Please enter a text", Toast.LENGTH_SHORT).show()
            return@setOnClickListener
         }
         if (isPalindrome(text)) {
            Toast.makeText(this, "isPalindrome", Toast.LENGTH_SHORT).show()
         } else {
            Toast.makeText(this, "not Palindrome", Toast.LENGTH_SHORT).show()
         }
      }

      binding.nextButton.setOnClickListener {
         if(binding.nameInput.text.toString().isEmpty()){
            Toast.makeText(this, "Please enter a name", Toast.LENGTH_SHORT).show()
            return@setOnClickListener
         }

         val name = binding.nameInput.text.toString()
         val intent = Intent(this, SecondActivity::class.java)
         intent.putExtra("name", name)
         startActivity(intent)
      }
   }

   private fun checkAndRequestPermissions(){
      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
         val permissionsToRequest = mutableListOf<String>()
         if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_MEDIA_IMAGES) != PackageManager.PERMISSION_GRANTED) {
            permissionsToRequest.add(Manifest.permission.READ_MEDIA_IMAGES)
         }
         if (permissionsToRequest.isNotEmpty()) {
            ActivityCompat.requestPermissions(this, permissionsToRequest.toTypedArray(), REQUEST_CODE_PERMISSIONS)
         } else {
            startGallery()
         }
      } else {
         if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), REQUEST_CODE_PERMISSIONS)
         } else {
            startGallery()
         }
      }
   }

   private fun startGallery() {
      launcherGallery.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
   }

   private val launcherGallery = registerForActivityResult(
      ActivityResultContracts.PickVisualMedia()
   ) { uri: Uri? ->
      if (uri != null) {
         currentImageUri = uri
         showImage()
      } else {
         Log.d("Photo Picker", "No media selected")
      }
   }

   private fun showImage() {
      currentImageUri?.let {
         Log.d("Image URI", "showImage: $it")
         binding.userIcon.setImageURI(it)
      }
   }


   fun isPalindrome(text: String): Boolean {
      val cleanText = text.toLowerCase().replace(Regex("[^a-zA-Z0-9]"), "")
      return cleanText == cleanText.reversed()
   }

   companion object {
      private const val REQUEST_CODE_PERMISSIONS = 1001
   }
}