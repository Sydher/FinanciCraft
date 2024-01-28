package fr.dev.sydher.financicraft.bean.dto

import java.util.*

data class TransactionDTO(

    var id: Long? = null,
    var name: String,
    var amount: Double,
    var date: Date,
    var accountId: Long?,
    var categoriesId: List<Long?>?

)
