package fr.dev.sydher.financicraft.ds

import fr.dev.sydher.financicraft.bean.entity.Account
import fr.dev.sydher.financicraft.bean.exception.AccountNotFoundException

interface AccountDS {

    /**
     * Find an account by id.
     * @param id the identifier of the account to find
     * @return the account
     * @throws AccountNotFoundException if the account with the specified id doesn't exist
     */
    @Throws(AccountNotFoundException::class)
    fun find(id: Long): Account

    /**
     * Get all accounts by category.
     * @param category the category to search from
     * @return all accounts on this category
     */
    fun findAll(category: String): List<Account>

    /**
     * Get all accounts.
     * @return all accounts
     */
    fun getAll(): List<Account>

    /**
     * Creates or updates an account.
     * @param account the account to create/update
     * @return the created/updated account
     */
    fun save(account: Account): Account

    /**
     * Deletes an account by its identifier
     * @param id the identifier of the account to delete
     * @throws AccountNotFoundException if the account with the specified id doesn't exist
     */
    @Throws(AccountNotFoundException::class)
    fun delete(id: Long)

}
