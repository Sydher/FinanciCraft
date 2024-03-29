package fr.dev.sydher.financicraft.mapper

import fr.dev.sydher.financicraft.bean.dto.AccountDTO
import fr.dev.sydher.financicraft.bean.entity.Account

object AccountMapper {
    fun Account.toDTO() = AccountDTO(id, name, 0.0, category?.id)
    fun AccountDTO.toEntity() = Account(id, name)
}
