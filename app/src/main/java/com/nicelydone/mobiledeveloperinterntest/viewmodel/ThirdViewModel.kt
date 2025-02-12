package com.nicelydone.mobiledeveloperinterntest.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nicelydone.mobiledeveloperinterntest.connection.ApiConfig
import com.nicelydone.mobiledeveloperinterntest.connection.response.DataItem
import com.nicelydone.mobiledeveloperinterntest.connection.response.UserResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ThirdViewModel: ViewModel() {
   private val _user = MutableLiveData<List<DataItem?>>()
   val user: LiveData<List<DataItem?>> = _user

   private val _isLoading = MutableLiveData<Boolean>()
   val isLoading: LiveData<Boolean> = _isLoading

   fun fetchData(){
      _isLoading.value = true
      val client = ApiConfig.getApiService().getUsers(1, 10)
      client.enqueue(object: Callback<UserResponse>{
         override fun onResponse(p0: Call<UserResponse>, p1: Response<UserResponse>) {
            _isLoading.value = false
            if(p1.isSuccessful){
               val res = p1.body()
               if (res != null) {
                  _user.value = res.data
               }
            } else {
               Log.e("Third ViewModel", "Error : ${p1.message()}")
            }
         }

         override fun onFailure(p0: Call<UserResponse>, p1: Throwable) {
            _isLoading.value = false
            Log.e("Third ViewModel", "Error: ${p1.message}")
         }

      })
   }
}