package fr.dev.sydher.financicraft.dsimpl

import fr.dev.sydher.financicraft.bean.dto.StatByCategoryDTO
import fr.dev.sydher.financicraft.bean.dto.StatsByCategoryDTO
import fr.dev.sydher.financicraft.ds.StatsDS
import fr.dev.sydher.financicraft.mapper.CategoryMapper.toDTO
import fr.dev.sydher.financicraft.repository.AccountRepository
import fr.dev.sydher.financicraft.repository.CategoryRepository
import fr.dev.sydher.financicraft.repository.TransactionRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.text.SimpleDateFormat
import java.util.*

@Service
class StatsDSImpl @Autowired constructor(
    private val categoryRepository: CategoryRepository,
    private val transactionRepository: TransactionRepository,
    private val accountRepository: AccountRepository
) : StatsDS {

    override fun getStatsByMainCategory(accountId: Long, dateMin: String, dateMax: String): StatsByCategoryDTO {
        val stats = ArrayList<StatByCategoryDTO>()

        val account = accountRepository.findById(accountId)
        val mainCategories = categoryRepository.findAllByMainTrue()

        mainCategories.forEach { category ->
            val amount = transactionRepository.sumAmountByAccountAndCategoryInDateRange(
                account.get(),
                category.id!!,
                toDate(dateMin),
                toDate(dateMax)
            )
            stats.add(StatByCategoryDTO(category.toDTO(), amount))
        }

        return StatsByCategoryDTO(stats)
    }

    private fun toDate(dateString: String): Date {
        val format = SimpleDateFormat("yyyy-MM-dd")
        return format.parse(dateString)
    }

}
