package com.example.countrycodes.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.countrycodes.model.Country
import com.example.countrycodes.network.NetworkUtils
import com.example.countrycodes.repository.CountryRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch


class CountryViewModel(
    private val repository: CountryRepository = CountryRepository(NetworkUtils.api)
) : ViewModel() {

    private val _countries = MutableStateFlow<List<Country>>(emptyList())
    val countries: StateFlow<List<Country>> = _countries

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery

    val filteredCountries: StateFlow<List<Country>> = combine(_countries, _searchQuery) { countries, query ->
        if (query.isBlank()) countries
        else countries.filter { it.name.contains(query, ignoreCase = true) || it.capital.contains(query, ignoreCase = true) || it.code.contains(query, ignoreCase = true)}
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())

    init {
        fetchCountries()
    }

    fun setSearchQuery(query: String) {
        _searchQuery.value = query
    }

    private fun fetchCountries() {
        viewModelScope.launch {
            repository.getCountries()
                .catch { e -> _error.value = e.message }
                .collect { countryList ->
                    _countries.value = countryList
                }
        }
    }
}
