package com.devid.feedarticlescompose.network

import com.devid.feedarticlescompose.network.dtos.ArticlesResponseItem
import com.devid.feedarticlescompose.network.dtos.AuthDto
import com.devid.feedarticlescompose.network.dtos.AuthResponse
import com.devid.feedarticlescompose.network.dtos.NewArticleDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT

interface ApiInterface {

//    *************************************** USERS ***************************************

    @PUT(ApiRoutes.USER_AUTH)
    suspend fun registerUser(@Body authDto: AuthDto): Response<AuthResponse>?

    @FormUrlEncoded
    @POST(ApiRoutes.USER_AUTH)
    suspend fun loginUser(
        @Field("login") login: String,
        @Field("mdp") password: String
    ): Response<AuthResponse>?

//    *************************************** ARTICLES ***************************************

    @GET(ApiRoutes.ARTICLES)
    suspend fun getAllArticles(@Header("token") token: String): Response<List<ArticlesResponseItem>>?

    @PUT(ApiRoutes.ARTICLES)
    suspend fun createArticle(@Header("token") token: String, @Body newArticle: NewArticleDto): Response<Unit>?
}