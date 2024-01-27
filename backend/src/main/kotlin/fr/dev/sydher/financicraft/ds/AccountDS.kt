package fr.dev.sydher.financicraft.ds

import fr.dev.sydher.financicraft.bean.dto.AccountDTO
import fr.dev.sydher.financicraft.bean.exception.AccountNotFoundException

interface AccountDS {

    /**
     * Find an account by id.
     * @param id the identifier of the account to find
     * @return the account
     * @throws AccountNotFoundException if the account with the specified id doesn't exist
     */
    @Throws(AccountNotFoundException::class)
    fun find(id: Long): AccountDTO

    /**
     * Get balance account.
     * @param id the identifier of the account to get balance
     * @return the account balance
     * @throws AccountNotFoundException if the account with the specified id doesn't exist
     */
    @Throws(AccountNotFoundException::class)
    fun getBalance(id: Long): Double

    /**
     * Get all accounts by category.
     * @param categoryId id of the category to search from
     * @return all accounts on this category
     */
    fun findAll(categoryId: Long): List<AccountDTO>?

    /**
     * Get all accounts.
     * @return all accounts
     */
    fun getAll(): List<AccountDTO>

    /**
     * Creates or updates an account.
     * @param account the account to create/update
     * @return the created/updated account
     */
    fun save(account: AccountDTO): AccountDTO

    /**
     * Deletes an account by its identifier
     * @param id the identifier of the account to delete
     * @throws AccountNotFoundException if the account with the specified id doesn't exist
     */
    @Throws(AccountNotFoundException::class)
    fun delete(id: Long)

}
