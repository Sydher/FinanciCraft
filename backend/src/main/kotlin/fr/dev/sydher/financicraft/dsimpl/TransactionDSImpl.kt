package fr.dev.sydher.financicraft.dsimpl

import fr.dev.sydher.financicraft.bean.dto.TransactionDTO
import fr.dev.sydher.financicraft.bean.exception.TransactionNotFoundException
import fr.dev.sydher.financicraft.ds.TransactionDS
import fr.dev.sydher.financicraft.mapper.TransactionMapper.toDTO
import fr.dev.sydher.financicraft.mapper.TransactionMapper.toEntity
import fr.dev.sydher.financicraft.repository.AccountRepository
import fr.dev.sydher.financicraft.repository.CategoryRepository
import fr.dev.sydher.financicraft.repository.TransactionRepository
import fr.dev.sydher.financicraft.utils.AppConst
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import kotlin.jvm.optionals.getOrNull

@Service
class TransactionDSImpl @Autowired constructor(
    private val transactionRepository: TransactionRepository,
    private val accountRepository: AccountRepository,
    private val categoryRepository: CategoryRepository
) : TransactionDS {

    companion object {
        private val log = LoggerFactory.getLogger(TransactionDSImpl::class.java)
    }

    override fun find(id: Long): TransactionDTO {
        val transactionOptional = transactionRepository.findById(id)
        if (transactionOptional.isPresent) {
            return transactionOptional.get().toDTO()
        }
        log.error(AppConst.MSG_ERR_ITEM_NOT_FOUND, "Transaction", id)
        throw TransactionNotFoundException(AppConst.ERR_ITEM_NOT_FOUND)
    }

    override fun findAllByAccount(accountId: Long, pageable: Pageable): PageImpl<TransactionDTO>? {
        var transac: PageImpl<TransactionDTO>? = null
        accountRepository.findById(accountId).ifPresentOrElse(
            {
                val transactions = transactionRepository.findAllByAccount(it, pageable)
                transac = PageImpl(transactions.content.map { transaction -> transaction.toDTO() }, pageable, transactions.totalElements)
            },
            {
                log.warn(
                    AppConst.MSG_WARN_ITEM_NOT_FOUND_BY_ACC,
                    "Transaction",
                    accountId
                );
            }
        )
        return transac
    }

    override fun save(transaction: TransactionDTO): TransactionDTO {
        val toSave = transaction.toEntity()
        toSave.account = transaction.accountId?.let { accountRepository.findById(it).getOrNull() }
        toSave.categories = transaction.categoriesId?.let { categoryRepository.findAllById(it) }
        return transactionRepository.save(toSave).toDTO()
    }

    override fun delete(id: Long) {
        val transaction = transactionRepository.findById(id)
        transaction.ifPresentOrElse(
            { transactionRepository.delete(it) },
            {
                log.error(
                    AppConst.MSG_ERR_ITEM_NOT_FOUND,
                    "Transaction",
                    id
                ); throw TransactionNotFoundException(AppConst.ERR_ITEM_NOT_FOUND)
            }
        )
    }

}
