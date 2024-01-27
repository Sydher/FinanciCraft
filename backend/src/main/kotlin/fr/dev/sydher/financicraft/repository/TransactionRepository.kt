package fr.dev.sydher.financicraft.repository

import fr.dev.sydher.financicraft.bean.entity.Account
import fr.dev.sydher.financicraft.bean.entity.Transaction
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface TransactionRepository : JpaRepository<Transaction, Long> {

    fun findAllByAccount(account: Account, pageable: Pageable): Page<Transaction>

    @Query("SELECT SUM(t.amount) FROM Transaction t WHERE t.account = :account")
    fun sumAmountByAccount(account: Account): Double

}
