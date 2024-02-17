package fr.dev.sydher.financicraft.bean.dto

data class CategoryDTO(

    var id: Long? = null,
    var name: String,
    var icon: String,
    var color: String,
    var main: Boolean? = false

)
