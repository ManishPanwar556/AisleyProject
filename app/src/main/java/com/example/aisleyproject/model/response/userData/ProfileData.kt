package com.example.aisleyproject.model.response.userData

data class ProfileData(
    val invitation_type: String,
    val preferences: List<PreferenceX>,
    val question: String
)