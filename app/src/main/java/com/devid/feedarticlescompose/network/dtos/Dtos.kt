package com.devid.feedarticlescompose.network.dtos

import com.squareup.moshi.Json

//**************************************** USERS ****************************************

data class AuthDto(
    @Json(name = "login")
    val login: String,
    @Json(name = "mdp")
    val password: String
)

data class AuthResponse(
    @Json(name = "id")
    val id: Long,
    @Json(name = "token")
    val token: String
)

//**************************************** ARTICLES ****************************************

data class ArticlesResponseItem(
    @Json(name = "categorie")
    val categorie: Int,
    @Json(name = "created_at")
    val createdAt: String,
    @Json(name = "descriptif")
    val descriptif: String,
    @Json(name = "id")
    val idArticle: Long,
    @Json(name = "id_u")
    val idUser: Long,
    @Json(name = "titre")
    val titre: String,
    @Json(name = "url_image")
    val urlImage: String
)

data class NewArticleDto(
    @Json(name = "id_u") val userId: Long,
    @Json(name = "title") val title: String,
    @Json(name = "desc") val description: String,
    @Json(name = "image") val imageUrl: String,
    @Json(name = "cat") val category: Int
)


