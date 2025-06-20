package com.example.myapplication.classes.services.firebase.authUserService

import com.google.firebase.firestore.FirebaseFirestore

interface AuthService {
    suspend fun session(): String?
    suspend fun register(name: String,email: String, pswd: String): Boolean
    suspend fun login(email: String, pswd: String): Boolean
    suspend fun checkUser(email: String, pswd: String, isCreating: Boolean): String?
    suspend fun logout()
    val db: FirebaseFirestore
}