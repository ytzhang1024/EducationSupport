package com.android.example.educationsupport.ui.dashboard

import androidx.lifecycle.ViewModel
import com.android.example.educationsupport.databinding.ActivityUserProfileBinding
import com.android.example.educationsupport.data.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.io.IOException

class UserProfileViewModel: ViewModel() {
    private var firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
    private val firestore = Firebase.firestore
    private val uemail = firebaseAuth?.currentUser?.email.toString()

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

        saveUserProfile(usermap)
    }

//    fun getUserProfile(){
//        this.user = firebaseRepository.getUserProfileByCurrentUser()!!
//    }

    fun saveUserProfile(usermap: HashMap<String, String>) {
        if (uemail != null) {
            firestore.collection("user").document(uemail).update(usermap as Map<String, Any>)
        }
    }

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

        this.res = getUserProfileByCurrentUser()!!
        println(this.res)
    }


    suspend fun getUserProfileByCurrentUser(): DocumentSnapshot? {
        val ref = firestore.collection("user").document(uemail)
        val res = ref.get().await()
        return res
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