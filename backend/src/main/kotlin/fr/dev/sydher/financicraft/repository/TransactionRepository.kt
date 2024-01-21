package fr.dev.sydher.financicraft.repository

import fr.dev.sydher.financicraft.bean.entity.Account
import fr.dev.sydher.financicraft.bean.entity.Category
import fr.dev.sydher.financicraft.bean.entity.Transaction
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface TransactionRepository : JpaRepository<Transaction, Long> {

    fun findAllByAccount(account: Account): List<Transaction>

}
