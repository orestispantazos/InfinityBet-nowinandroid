package com.infinitybet.premierleague.core.network.firebase

import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.Tasks
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GetTokenResult
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

class FirebaseUserIdTokenInterceptor : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request: Request = chain.request()
        return try {
            val user = FirebaseAuth.getInstance().currentUser
            if (user == null) {
                throw Exception("User is not logged in.")
            } else {
                val task: Task<GetTokenResult> = user.getIdToken(true);
                val tokenResult = Tasks.await<GetTokenResult>(task)
                val idToken = tokenResult.token
                if (idToken == null) {
                    throw Exception("idToken is null")
                } else {
                    val modifiedRequest: Request = request.newBuilder()
                        .addHeader(X_FIREBASE_ID_TOKEN, idToken)
                        .build()
                    chain.proceed(modifiedRequest)
                }
            }
        } catch (e: Exception) {
            throw IOException(e.message)
        }
    }

    companion object {
        // Custom header for passing ID token in request.
        private const val X_FIREBASE_ID_TOKEN = "YOUR-CUSTOM-HEADER"
    }
}
