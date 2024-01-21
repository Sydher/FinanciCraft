package fr.dev.sydher.financicraft.ds

import fr.dev.sydher.financicraft.bean.dto.TransactionDTO
import fr.dev.sydher.financicraft.bean.exception.TransactionNotFoundException

interface TransactionDS {

    /**
     * Find a transaction by id.
     * @param id the identifier of the transaction to find
     * @return the transaction
     * @throws TransactionNotFoundException if the transaction with the specified id doesn't exist
     */
    @Throws(TransactionNotFoundException::class)
    fun find(id: Long): TransactionDTO

    /**
     * Get all transactions by accounts.
     * @param accountId id of the account to search from
     * @return all transactions on this account
     */
    fun findAllByAccount(accountId: Long): List<TransactionDTO>?

    /**
     * Get all transactions.
     * @return all transactions
     */
    fun getAll(): List<TransactionDTO>

    /**
     * Creates or updates a transaction.
     * @param transaction the transaction to create/update
     * @return the created/updated transaction
     */
    fun save(transaction: TransactionDTO): TransactionDTO

    /**
     * Deletes a transaction by its identifier
     * @param id the identifier of the transaction to delete
     * @throws TransactionNotFoundException if the transaction with the specified id doesn't exist
     */
    @Throws(TransactionNotFoundException::class)
    fun delete(id: Long)

}
