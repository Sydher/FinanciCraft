package fr.dev.sydher.financicraft.dsimpl

import fr.dev.sydher.financicraft.bean.entity.Account
import fr.dev.sydher.financicraft.bean.entity.Category
import fr.dev.sydher.financicraft.bean.exception.AccountNotFoundException
import fr.dev.sydher.financicraft.repository.AccountRepository
import fr.dev.sydher.financicraft.repository.CategoryRepository
import fr.dev.sydher.financicraft.repository.TransactionRepository
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.dao.EmptyResultDataAccessException
import java.util.*

@ExtendWith(MockitoExtension::class)
class AccountDSImplTest {

    @Mock
    private lateinit var accountRepository: AccountRepository

    @Mock
    private lateinit var categoryRepository: CategoryRepository

    @Mock
    private lateinit var transactionRepository: TransactionRepository

    @InjectMocks
    private lateinit var accountDSImpl: AccountDSImpl

    @Test
    fun `find account by id`() {
        // given
        val account = Account(1L, "test", Category(7L, "cattest", "food", "red"))
        Mockito.`when`(accountRepository.findById(1L)).thenReturn(Optional.of(account))
        Mockito.`when`(transactionRepository.sumAmountByAccount(account)).thenReturn(12.0)

        // when
        val response = accountDSImpl.find(1L)

        // then
        assertEquals(1L, response.id)
        assertEquals("test", response.name)
        assertEquals(12.0, response.balance)
        assertEquals(7L, response.categoryId)
    }

    @Test
    fun `find account by id, no balance`() {
        // given
        val account = Account(1L, "test", Category(7L, "cattest", "food", "red"))
        Mockito.`when`(accountRepository.findById(1L)).thenReturn(Optional.of(account))
        Mockito.`when`(transactionRepository.sumAmountByAccount(account)).thenThrow(EmptyResultDataAccessException(0))

        // when
        val response = accountDSImpl.find(1L)

        // then
        assertEquals(1L, response.id)
        assertEquals("test", response.name)
        assertEquals(0.0, response.balance)
        assertEquals(7L, response.categoryId)
    }

    @Test
    fun `find account by id not found`() {
        // given
        Mockito.`when`(accountRepository.findById(1L)).thenReturn(Optional.empty())

        // when-then
        assertThrows<AccountNotFoundException> {
            accountDSImpl.find(1L)
        }
    }

}
