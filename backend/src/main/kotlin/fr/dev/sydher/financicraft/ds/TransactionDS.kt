package fr.dev.sydher.financicraft.ds

import fr.dev.sydher.financicraft.bean.dto.TransactionDTO
import fr.dev.sydher.financicraft.bean.exception.TransactionNotFoundException
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable

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
     * @param pageable page informations
     * @return all transactions on this account
     */
    fun findAllByAccount(accountId: Long, pageable: Pageable): PageImpl<TransactionDTO>?

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
