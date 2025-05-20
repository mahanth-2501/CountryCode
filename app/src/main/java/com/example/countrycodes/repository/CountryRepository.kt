package com.example.countrycodes.repository

import com.example.countrycodes.model.Country
import com.example.countrycodes.network.CountryApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class CountryRepository(
    private val apiService: CountryApiService
) {
    fun getCountries(): Flow<List<Country>> = flow {
        val response = apiService.getAllCountries()
        emit(response)
    }
}
