package com.nadiavinabal.peyaapp.data

import com.nadiavinabal.peyaapp.model.Profile

interface ProfileDataSource {
    fun getProfileInfo(): Profile
}