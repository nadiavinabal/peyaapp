package com.nadiavinabal.peyaapp.data

import com.nadiavinabal.peyaapp.model.Profile
import javax.inject.Inject

class ProfileDataSourceImpl @Inject constructor(): ProfileDataSource {

    private val profile = Profile(
        firstName = "Nadia",
        lastName = "Viñabal",
        email = "nadiavinabal@gmail.com",
        nationality = "argentina",
        password = "pass123456"
    )
    override fun getProfileInfo(): Profile = profile


}