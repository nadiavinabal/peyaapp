package com.nadiavinabal.peyaapp

import com.nadiavinabal.peyaapp.data.datasource.ProductDataSource
import com.nadiavinabal.peyaapp.database.dao.ProductDao
import com.nadiavinabal.peyaapp.domain.repository.ProductRepositoryImpl
import com.nadiavinabal.peyaapp.model.Product
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.mockito.Mockito.mock
import kotlin.test.Test
import org.mockito.kotlin.whenever
import kotlin.test.assertEquals
import org.junit.Assert.assertTrue
import kotlinx.coroutines.flow.first

@ExperimentalCoroutinesApi
class ProductRepositoryImplGetAllProductsTest {

    private lateinit var repository: ProductRepositoryImpl
    private lateinit var dao: ProductDao
    private lateinit var dataSource: ProductDataSource

    @Before
    fun setup() {
        dao = mock()
        dataSource = mock()
        repository = ProductRepositoryImpl(dao, dataSource)
    }

    @Test
    fun `getAllProducts emits products from dataSource`() = runTest {
        // Arrange
        val expectedProducts = listOf(Product("1", "Prod", 10.0, "", "", false, ""))
        whenever(dataSource.getAllProducts()).thenReturn(expectedProducts)

        // Act
        val result = repository.getAllProducts().first()

        // Assert
        assertEquals(expectedProducts, result)
    }

    @Test
    fun `getAllProducts emits empty list on exception`() = runTest {
        // Arrange
        whenever(dataSource.getAllProducts()).thenThrow(RuntimeException("API error"))

        // Act
        val result = repository.getAllProducts().first()

        // Assert
        assertTrue(result.isEmpty())
    }
}