package fr.dev.sydher.financicraft.mapper

import fr.dev.sydher.financicraft.bean.dto.TransactionDTO
import fr.dev.sydher.financicraft.bean.entity.Transaction

object TransactionMapper {
    fun Transaction.toDTO() = TransactionDTO(id, name, amount, date, account?.id, categories?.map { it.id })
    fun TransactionDTO.toEntity() = Transaction(id, name, amount, date)
}
