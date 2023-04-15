package com.android.example.educationsupport.viewModel

import androidx.lifecycle.ViewModel
import com.android.example.educationsupport.databinding.ActivityUserProfileBinding
import com.android.example.educationsupport.repository.entity.User
import com.android.example.educationsupport.repository.firebase.FirebaseRepository
import kotlinx.coroutines.tasks.await

class UserProfileViewModel: ViewModel() {
    private val firebaseRepository = FirebaseRepository()
    private val uemail = firebaseRepository.getFirebaseAuth()?.currentUser?.email.toString()
    private lateinit var user: User

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

    fun getUserProfile(){
        this.user = firebaseRepository.getUserProfileByCurrentUser()!!
        val ref = firebaseRepository.getFireStore().collection("user").document(uemail)

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


    }

    fun getUser(): User? {
//        println(this.user)
        return this.user
    }

}