package com.example.booster.data.remote.network

import com.example.booster.util.UserManager
import okhttp3.Interceptor
import okhttp3.Response

class CookiesInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request =
            chain.request().newBuilder().header("Content-Type", "application/json")
                .header("token", UserManager.token?:"")
                .build()
        return chain.proceed(request)
    }
}

//맨처음 되던 쿠키: eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX2lkeCI6MSwiaWF0IjoxNTk0MDI1NzE2LCJleHAiOjE1OTc2MjU3MTYsImlzcyI6IkJvb3N0ZXIifQ.FtWfnt4rlyYH9ZV3TyOjLZXOkeR7ya96afmA0zJqTI8
//aaa쿠키 : eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX2lkeCI6MjcsImlhdCI6MTU5NDkzNTg2MSwiZXhwIjoxNTk4NTM1ODYxLCJpc3MiOiJCb29zdGVyIn0.lhn4cdLCDDz4HU36n-f7d-FWTutP9NEF2gx2XIMWgVU