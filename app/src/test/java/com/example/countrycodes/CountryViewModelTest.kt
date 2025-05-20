package com.example.countrycodes

import app.cash.turbine.test
import com.example.countrycodes.model.Country
import com.example.countrycodes.repository.CountryRepository
import com.example.countrycodes.viewmodel.CountryViewModel
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class CountryViewModelTest {

    private val dispatcher = StandardTestDispatcher()
    private lateinit var viewModel: CountryViewModel
    private val repository = mockk<CountryRepository>()

    @Before
    fun setup() {
        Dispatchers.setMain(dispatcher)

        val countries = listOf(
            Country("India", "Asia", "IN", "New Delhi"),
            Country("France", "Europe", "FR", "Paris")
        )

        coEvery { repository.getCountries() } returns flow { emit(countries) }

        viewModel = CountryViewModel(repository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `filteredCountries should emit filtered result`() = runTest {
        viewModel.setSearchQuery("India")

        viewModel.filteredCountries.test {
            // advance time to trigger fetch
            dispatcher.scheduler.advanceUntilIdle()

            val first = awaitItem()
            val second = awaitItem()

            assert(second.size == 1)
            assert(second[0].name == "India")

            cancelAndIgnoreRemainingEvents()
        }
    }
}
