package com.android.example.educationsupport.di

import com.android.example.educationsupport.data.repository.CourseRepository
import com.android.example.educationsupport.data.repository.CourseRepositoryImpl
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object RepositoryModule {

    @Provides
    @Singleton
    fun provideCourseRepository(
        auth: FirebaseAuth,
        database: FirebaseFirestore
    ): CourseRepository {
        return CourseRepositoryImpl(auth, database)
    }


}