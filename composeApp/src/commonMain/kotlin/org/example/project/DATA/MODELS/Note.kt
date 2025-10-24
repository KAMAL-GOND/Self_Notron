package org.example.project.DATA.MODELS

import kotlinx.serialization.Serializable

@Serializable
data class Note (
    var id: Long?= null,
    var Task: String? = null,
    var Checked: Boolean? = null,
    var Priority: Int?=null,
    //val created_at: String,

)