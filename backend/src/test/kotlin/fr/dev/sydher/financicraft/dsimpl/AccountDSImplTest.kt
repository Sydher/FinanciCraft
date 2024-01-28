package fr.dev.sydher.financicraft.dsimpl

import fr.dev.sydher.financicraft.bean.entity.Account
import fr.dev.sydher.financicraft.bean.entity.Category
import fr.dev.sydher.financicraft.bean.exception.AccountNotFoundException
import fr.dev.sydher.financicraft.repository.AccountRepository
import fr.dev.sydher.financicraft.repository.CategoryRepository
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.jupiter.MockitoExtension
import java.util.*

@ExtendWith(MockitoExtension::class)
class AccountDSImplTest {

    @Mock
    private lateinit var accountRepository: AccountRepository

    @Mock
    private lateinit var categoryRepository: CategoryRepository

    @InjectMocks
    private lateinit var accountDSImpl: AccountDSImpl

    @Test
    fun `find account by id`() {
        // given
        val account = Account(1L, "test", 12.3, Category(7L, "cattest", "food", "red"))
        Mockito.`when`(accountRepository.findById(1L)).thenReturn(Optional.of(account))

        // when
        val response = accountDSImpl.find(1L)

        // then
        assertEquals(1L, response.id)
        assertEquals("test", response.name)
        assertEquals(12.3, response.balance)
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
