package ru.braveowlet.oauth.data.mapper

import ru.braveowlet.oauth.data.model.GoogleAuthUser
import ru.braveowlet.oauth.data.model.MicrosoftAuthUser
import ru.braveowlet.oauth.data.model.YandexAuthUser
import ru.braveowlet.oauth.domain.model.AuthUser

internal fun YandexAuthUser.toAuthUser() = AuthUser(
    name = this.firstName?:"",
    email = this.login?:""
)

internal fun GoogleAuthUser.toAuthUser() = AuthUser(
    name = this.displayName?:"",
    email = this.email?:""
)

internal fun MicrosoftAuthUser.toAuthUser() = AuthUser(
    name = this.displayName?:"",
    email = this.mail?:""
)