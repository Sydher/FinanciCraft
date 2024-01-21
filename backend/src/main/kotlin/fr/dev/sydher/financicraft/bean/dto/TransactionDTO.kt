package fr.dev.sydher.financicraft.bean.dto

import java.time.LocalDate

data class TransactionDTO(

    var id: Long? = null,
    var name: String,
    var amount: Double,
    var date: LocalDate,
    var accountId: Long?,
    var categoriesId: List<Long?>?

)
