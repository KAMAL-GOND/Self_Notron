package org.example.project.DATA.MODELS

import kotlinx.serialization.Serializable

@Serializable
data class Note (
    val id: Long?= null,
    val Task: String? = null,
    val Checked: Boolean = false,
    val Priority: Int?=null,
    //val created_at: String,

)