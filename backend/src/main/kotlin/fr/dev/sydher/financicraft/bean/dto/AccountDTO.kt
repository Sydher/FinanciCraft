package fr.dev.sydher.financicraft.bean.dto

data class AccountDTO(

    var id: Long? = null,
    var name: String,
    var balance: Double,
    var categoryId: Long?

)
