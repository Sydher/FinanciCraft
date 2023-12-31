package fr.dev.sydher.financicraft.repository

import fr.dev.sydher.financicraft.bean.entity.Account
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface AccountRepository : CrudRepository<Account, Long> {

    override fun findAll(): List<Account>

    fun findAllByCategory(category: String): List<Account>

}
