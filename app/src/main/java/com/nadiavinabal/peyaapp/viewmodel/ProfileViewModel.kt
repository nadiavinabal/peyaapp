package com.nadiavinabal.peyaapp.viewmodel

import android.app.Application
import android.net.Uri
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import com.nadiavinabal.peyaapp.model.Profile
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject
import androidx.lifecycle.viewModelScope
import com.cloudinary.Cloudinary
import com.nadiavinabal.peyaapp.data.ProfileDataSource
import com.nadiavinabal.peyaapp.data.api.dto.RegisterRequest
import com.nadiavinabal.peyaapp.domain.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import okhttp3.Dispatcher

@HiltViewModel
class ProfileViewModel @Inject constructor(
    val myApplication: Application,
    private val profileDataSource: ProfileDataSource,
    private val authRepository: AuthRepository
): AndroidViewModel(myApplication) {
    private val _profile = MutableStateFlow(Profile())
    val profile: MutableStateFlow<Profile> = _profile

    private val _registerStatus = MutableStateFlow<Result<String>?>(null)
    val registerStatus: StateFlow<Result<String>?> = _registerStatus

    // saber si la imagen ya se cargo
    private val _isImageUploading = MutableStateFlow(false)
    val isImageUploading: StateFlow<Boolean> = _isImageUploading

    private val _selectedImageUri = MutableStateFlow<Uri?>(null)
    val selectedImageUri: StateFlow<Uri?> = _selectedImageUri

    // grandel.properties y hacerle un encode a las variables.
    private val cloudinaty = Cloudinary(
        mapOf(
            "cloud_name" to "db5jy29c5",
            "api_key" to "318975697528874",
            "api_secret" to "sqmpRCF9lEAx-hu4o2SHTDJfhCY"
        )
    )
    init {
        loadProfile()
    }

    private fun loadProfile () {
        viewModelScope.launch {
            delay(150)
            _profile.value = profileDataSource.getProfileInfo()
        }
    }

    fun registerUser(email: String, fullName: String, password: String) {
        viewModelScope.launch {
            val request = RegisterRequest(
                email = email,
                fullName = fullName,
                encryptedPassword = password
            )
            val result = authRepository.register(request)
            _registerStatus.value = result
        }
    }

    fun setImageUri(uri: Uri) {
        _selectedImageUri.value = uri
        uploadImage(uri)
    }

    fun updateProfile(newProfile: Profile, imageUri: Uri?) {
        _profile.value = newProfile
        if (imageUri != null) {
            uploadImage(imageUri)
        }
    }

    private fun uploadImage(uri: Uri) {
        viewModelScope.launch (Dispatchers.IO ) {
            _isImageUploading.value = true
            try {
                val inputScream = getApplication<Application>().contentResolver.openInputStream(uri)
                val uploadResult = cloudinaty.uploader().upload(inputScream, mapOf("upload_preset" to "pruebapeya" ))
                val imageUrl = uploadResult["secure_url"] as String
                val updateProfile = _profile.value.copy( image = imageUrl)
                _profile.value = updateProfile
            } catch (e: Exception){
                Log.e("Cloudinary", "Error uploading image", e)
            }
            finally {
                _isImageUploading.value = false
            }
        }
    }
}