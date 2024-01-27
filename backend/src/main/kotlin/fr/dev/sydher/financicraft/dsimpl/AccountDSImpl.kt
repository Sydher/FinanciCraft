package fr.dev.sydher.financicraft.dsimpl

import fr.dev.sydher.financicraft.bean.dto.AccountDTO
import fr.dev.sydher.financicraft.bean.exception.AccountNotFoundException
import fr.dev.sydher.financicraft.ds.AccountDS
import fr.dev.sydher.financicraft.mapper.AccountMapper.toDTO
import fr.dev.sydher.financicraft.mapper.AccountMapper.toEntity
import fr.dev.sydher.financicraft.repository.AccountRepository
import fr.dev.sydher.financicraft.repository.CategoryRepository
import fr.dev.sydher.financicraft.repository.TransactionRepository
import fr.dev.sydher.financicraft.utils.AppConst
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.stereotype.Service
import kotlin.jvm.optionals.getOrNull

@Service
class AccountDSImpl @Autowired constructor(
    private val accountRepository: AccountRepository,
    private val categoryRepository: CategoryRepository,
    private val transactionRepository: TransactionRepository
) : AccountDS {

    companion object {
        private val log = LoggerFactory.getLogger(AccountDSImpl::class.java)
    }

    override fun find(id: Long): AccountDTO {
        val optionalAccount = accountRepository.findById(id)
        if (optionalAccount.isPresent) {
            val account = optionalAccount.get()
            val dto = account.toDTO()
            try {
                dto.balance = transactionRepository.sumAmountByAccount(account)
            } catch (e: EmptyResultDataAccessException) {
                dto.balance = 0.0
            }
            return dto
        }
        log.error(AppConst.MSG_ERR_ITEM_NOT_FOUND, "Account", id)
        throw AccountNotFoundException(AppConst.ERR_ITEM_NOT_FOUND)
    }

    override fun getBalance(id: Long): Double {
        return find(id).balance
    }

    override fun findAll(categoryId: Long): List<AccountDTO>? {
        var acc: List<AccountDTO>? = null
        categoryRepository.findById(categoryId).ifPresentOrElse(
            {
                val accounts = accountRepository.findAllByCategory(it)
                acc = accounts.map { account -> account.toDTO() }
            },
            {
                log.warn(
                    AppConst.MSG_WARN_ITEM_NOT_FOUND_BY_CAT,
                    "Account",
                    categoryId
                );
            }
        )
        return acc
    }

    override fun getAll(): List<AccountDTO> {
        val accounts = accountRepository.findAll()
        return accounts.map { it.toDTO() }
    }

    override fun save(account: AccountDTO): AccountDTO {
        val toSave = account.toEntity()
        toSave.category = account.categoryId?.let { categoryRepository.findById(it).getOrNull() }
        return accountRepository.save(toSave).toDTO()
    }

    override fun delete(id: Long) {
        val account = accountRepository.findById(id)
        account.ifPresentOrElse(
            { accountRepository.delete(it) },
            {
                log.error(
                    AppConst.MSG_ERR_ITEM_NOT_FOUND,
                    "Account",
                    id
                ); throw AccountNotFoundException(AppConst.ERR_ITEM_NOT_FOUND)
            }
        )
    }

}
