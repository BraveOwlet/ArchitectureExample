package ru.braveowlet.oauth.data.model

import com.google.gson.annotations.SerializedName

internal data class MicrosoftAuthUser(
    @SerializedName("id")
    val id: String,

    @SerializedName("businessPhones")
    val businessPhones: Array<String>,

    @SerializedName("displayName")
    val displayName: String?,

    @SerializedName("givenName")
    val givenName: String?,

    @SerializedName("jobTitle")
    val jobTitle: String?,

    @SerializedName("mail")
    val mail: String?,

    @SerializedName("mobilePhone")
    val mobilePhone: String?,

    @SerializedName("officeLocation")
    val officeLocation: String?,

    @SerializedName("preferredLanguage")
    val preferredLanguage: String?,

    @SerializedName("surname")
    val surname: String?,

    @SerializedName("userPrincipalName")
    val userPrincipalName: String?,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as MicrosoftAuthUser

        if (id != other.id) return false
        if (!businessPhones.contentEquals(other.businessPhones)) return false
        if (displayName != other.displayName) return false
        if (givenName != other.givenName) return false
        if (jobTitle != other.jobTitle) return false
        if (mail != other.mail) return false
        if (mobilePhone != other.mobilePhone) return false
        if (officeLocation != other.officeLocation) return false
        if (preferredLanguage != other.preferredLanguage) return false
        if (surname != other.surname) return false
        if (userPrincipalName != other.userPrincipalName) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + businessPhones.contentHashCode()
        result = 31 * result + (displayName?.hashCode() ?: 0)
        result = 31 * result + (givenName?.hashCode() ?: 0)
        result = 31 * result + (jobTitle?.hashCode() ?: 0)
        result = 31 * result + (mail?.hashCode() ?: 0)
        result = 31 * result + (mobilePhone?.hashCode() ?: 0)
        result = 31 * result + (officeLocation?.hashCode() ?: 0)
        result = 31 * result + (preferredLanguage?.hashCode() ?: 0)
        result = 31 * result + (surname?.hashCode() ?: 0)
        result = 31 * result + (userPrincipalName?.hashCode() ?: 0)
        return result
    }
}
