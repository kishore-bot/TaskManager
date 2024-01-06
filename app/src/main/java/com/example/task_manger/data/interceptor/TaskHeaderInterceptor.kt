package com.example.task_manger.data.interceptor


import com.example.task_manger.domian.manager.LocalUserManager
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject


class TaskHeaderInterceptor @Inject constructor(
    private val localUserManager: LocalUserManager,
) : Interceptor {
    private var token: String = "";
    override fun intercept(chain: Interceptor.Chain): Response {
        if (token == "") {
            token = runBlocking {
                localUserManager.readAppEntry().first() // Fetch the app token from DataStore
            }
        }
        val request =
            chain.request().newBuilder().addHeader("Content-Type", "application/json").addHeader(
                "Authorization", "Bearer $token"
            )
                .build()

        return chain.proceed(request)
    }
}