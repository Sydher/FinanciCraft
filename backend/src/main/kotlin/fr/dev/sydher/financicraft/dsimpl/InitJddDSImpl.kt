package fr.dev.sydher.financicraft.dsimpl

import fr.dev.sydher.financicraft.bean.entity.Account
import fr.dev.sydher.financicraft.bean.entity.Category
import fr.dev.sydher.financicraft.bean.entity.Transaction
import fr.dev.sydher.financicraft.repository.AccountRepository
import fr.dev.sydher.financicraft.repository.CategoryRepository
import fr.dev.sydher.financicraft.repository.TransactionRepository
import jakarta.annotation.PostConstruct
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.util.*
import java.util.stream.IntStream
import kotlin.math.round
import kotlin.random.Random

@Service
@Profile("local")
class InitJddDSImpl @Autowired constructor(
    private val accountRepository: AccountRepository,
    private val categoryRepository: CategoryRepository,
    private val transactionRepository: TransactionRepository
) {

    companion object {
        private val log = LoggerFactory.getLogger(AccountDSImpl::class.java)
    }

    @PostConstruct()
    fun initJdd() {
        log.info("Init JDD")
        // Categories
        var c1 = Category(null, "Center", "pi pi-align-center", "#db3959", true)
        c1 = categoryRepository.save(c1)
        var c2 = Category(null, "Up", "pi pi-angle-up", "green")
        c2 = categoryRepository.save(c2)
        var c3 = Category(null, "Apple", "pi pi-apple", "#3030e6", true)
        c3 = categoryRepository.save(c3)
        var c4 = Category(null, "Arobase", "pi pi-at", "lightgrey")
        c4 = categoryRepository.save(c4)

        // Accounts
        var a1 = Account(null, "Compte Dépôt", c1)
        a1 = accountRepository.save(a1)
        var a2 = Account(null, "Livret A", c3)
        a2 = accountRepository.save(a2)

        // Transactions
        val t1 = Transaction(null, generateRandomString(10), 99999.99, Date(), a1, listOf(c2, c4))
        transactionRepository.save(t1)
        IntStream.range(0, 100).forEach { i: Int ->
            val cats = if (i%2 == 0) listOf(c2) else if (i%3 == 0) listOf(c3) else listOf(c1, c4)
            val tn = Transaction(null, generateRandomString(Random.nextInt(3, 10)), generateRandomDouble(-200.50, -3.00), generateRandomDate(), a1, cats)
            transactionRepository.save(tn)
        }

        IntStream.range(0, 10).forEach { i: Int ->
            val cats = if (i%2 == 0) listOf(c4) else if (i%3 == 0) listOf(c1) else listOf(c3, c2)
            val tn = Transaction(null, generateRandomString(Random.nextInt(3, 10)), generateRandomDouble(50.00, 500.50), generateRandomDate(), a2, cats)
            transactionRepository.save(tn)
        }
    }

    private fun generateRandomString(length: Int): String {
        val allowedChars = ('A'..'Z') + ('a'..'z') + ('0'..'9')
        return (1..length)
            .map { allowedChars.random() }
            .joinToString("")
    }

    private fun generateRandomDouble(min: Double, max: Double): Double {
        val randomNumber = Random.nextDouble(min, max)
        return round(randomNumber * 100) / 100
    }

    private fun generateRandomDate(): Date {
        val now = LocalDate.now()
        val oneMonthAgo = now.minusMonths(1)
        val oneMonthLater = now.plusMonths(1)

        val startEpochDay = oneMonthAgo.toEpochDay()
        val endEpochDay = oneMonthLater.toEpochDay()

        val randomEpochDay = Random.nextLong(startEpochDay, endEpochDay)
        val randomDate = LocalDate.ofEpochDay(randomEpochDay)
        return java.sql.Date.valueOf(randomDate)
    }

}