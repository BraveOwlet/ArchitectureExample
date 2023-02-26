package ru.braveowlet.oauth.data.model

import com.google.gson.annotations.SerializedName

internal data class YandexAuthUser (
    @SerializedName("id")
    val id: String,

    @SerializedName("login")
    val login: String?,

    @SerializedName("client_id")
    val clientId: String?,

    @SerializedName("display_name")
    val displayName: String?,

    @SerializedName("real_name")
    val realName: String?,

    @SerializedName("first_name")
    val firstName: String?,

    @SerializedName("last_name")
    val lastName: String?,

    @SerializedName("sex")
    val sex: String?,

    @SerializedName("default_avatar_id")
    val defaultAvatarId: String?,

    @SerializedName("is_avatar_empty")
    val isAvatarEmpty: Boolean,

    @SerializedName("psuid")
    val psuId: String?,
)