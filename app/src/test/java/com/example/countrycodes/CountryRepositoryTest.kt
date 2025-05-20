package com.example.countrycodes

import com.example.countrycodes.model.Country
import com.example.countrycodes.network.CountryApiService
import com.example.countrycodes.repository.CountryRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class CountryRepositoryTest {

    private lateinit var repository: CountryRepository
    private val apiService = mockk<CountryApiService>()

    @Before
    fun setup() {
        repository = CountryRepository(apiService)
    }

    @Test
    fun `test getCountries returns list of countries`() = runBlocking {
        // Arrange: prepare a mock list that matches the Country model
        val mockList = listOf(
            Country("India", "Asia", "IN", "New Delhi"),
            Country("France", "Europe", "FR", "Paris")
        )
        coEvery { apiService.getAllCountries() } returns mockList

        // Act: call the repository
        val result = repository.getCountries().first()

        // Assert: check that the contents match
        assertEquals(2, result.size)

        val india = result.find { it.name == "India" }
        val france = result.find { it.name == "France" }

        assertNotNull(india)
        assertNotNull(france)

        assertEquals("Asia", india?.region)
        assertEquals("FR", france?.code)
    }
}
