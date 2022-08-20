package com.example.aisleyproject.model.response.userData

data class Preference(
    val answer_id: Int,
    val id: Int,
    val preference_question: PreferenceQuestion,
    val value: Int
)