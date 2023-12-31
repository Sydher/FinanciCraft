package fr.dev.sydher.financicraft.dsimpl

import fr.dev.sydher.financicraft.bean.entity.Account
import fr.dev.sydher.financicraft.bean.exception.AccountNotFoundException
import fr.dev.sydher.financicraft.ds.AccountDS
import fr.dev.sydher.financicraft.repository.AccountRepository
import fr.dev.sydher.financicraft.utils.AppConst
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class AccountDSImpl @Autowired constructor(private val accountRepository: AccountRepository) : AccountDS {

    companion object {
        private val log = LoggerFactory.getLogger(AccountDSImpl::class.java)
    }

    override fun find(id: Long): Account {
        val account = this.accountRepository.findById(id)
        if (account.isPresent) {
            return account.get()
        }
        log.error(AppConst.MSG_ERR_ACCOUNT_NOT_FOUND, id)
        throw AccountNotFoundException(AppConst.ERR_ACCOUNT_NOT_FOUND)
    }

    override fun findAll(category: String): List<Account> {
        return this.accountRepository.findAllByCategory(category)
    }

    override fun getAll(): List<Account> {
        return this.accountRepository.findAll()
    }

    override fun save(account: Account): Account {
        return this.accountRepository.save(account)
    }

    override fun delete(id: Long) {
        val account = this.accountRepository.findById(id)
        account.ifPresentOrElse(
            { acc -> this.accountRepository.delete(acc) },
            {
                log.error(
                    AppConst.MSG_ERR_ACCOUNT_NOT_FOUND,
                    id
                ); throw AccountNotFoundException(AppConst.ERR_ACCOUNT_NOT_FOUND)
            }
        )
    }

}
