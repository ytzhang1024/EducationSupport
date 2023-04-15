package com.android.example.educationsupport.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.android.example.educationsupport.databinding.ActivityUserProfileBinding
import com.android.example.educationsupport.repository.entity.User
import com.android.example.educationsupport.repository.firebase.FirebaseRepository
import com.google.firebase.firestore.DocumentSnapshot
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.io.IOException
import java.util.Objects

class UserProfileViewModel: ViewModel() {
    private val firebaseRepository = FirebaseRepository()
    private lateinit var user: User
    private var res: DocumentSnapshot? = null

    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    fun SaveUserProfile(binding: ActivityUserProfileBinding) {
        val firstName = binding.etFirstName.text.toString()
        val lastName = binding.etLastName.text.toString()
        val bio = binding.etBio.text.toString()

        val usermap = hashMapOf(
            "firstName" to firstName,
            "lastName" to lastName,
            "bio" to bio
        )

        firebaseRepository.saveUserProfile(usermap)
    }

//    fun getUserProfile(){
//        this.user = firebaseRepository.getUserProfileByCurrentUser()!!
//    }

    suspend fun getUserProfile(){

//        val ref = firebaseRepository.getFireStore().collection("user").document(uemail)
//        ref.get().addOnSuccessListener {
//            if (it != null) {
//                val first_name = it.data?.get("firstName").toString()
//                val last_name = it.data?.get("lastName").toString()
//                val bio = it.data?.get("bio").toString()
//                val role  = it.data?.get("role").toString()
//                this.user = User(first_name, last_name, bio, role)
//                println("111")
//                println(this.user)
//            }
//            println("222")
//            println(this.user)
//        }
//        println("333")
//        println(this.user)

        this.res = firebaseRepository.getUserProfileByCurrentUser()!!
        println(this.res)
    }

    fun test() {
        uiScope.launch {
            try {
                getUserProfile()
            } catch (e: IOException) {

            }
        }
    }

    fun getUser(): User? {
        return this.user
    }

    fun getRes(): DocumentSnapshot? {
        return this.res
    }

}