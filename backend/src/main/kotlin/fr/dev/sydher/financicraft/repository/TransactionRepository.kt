package fr.dev.sydher.financicraft.repository

import fr.dev.sydher.financicraft.bean.entity.Account
import fr.dev.sydher.financicraft.bean.entity.Transaction
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface TransactionRepository : JpaRepository<Transaction, Long> {

    fun findAllByAccount(account: Account, pageable: Pageable): Page<Transaction>

    @Query("SELECT SUM(t.amount) FROM Transaction t WHERE t.account = :account")
    fun sumAmountByAccount(account: Account): Double

    @Query("SELECT SUM(t.amount) FROM Transaction t JOIN t.categories c WHERE t.account = :account AND c.id = :categoryId AND t.date >= :dateMin AND t.date <= :dateMax")
    fun sumAmountByAccountAndCategoryInDateRange(account: Account, categoryId: Long, dateMin: Date, dateMax: Date): Double

}
