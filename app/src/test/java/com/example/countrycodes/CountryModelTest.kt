package com.example.countrycodes

import com.example.countrycodes.model.Country
import com.google.gson.Gson
import org.junit.Assert.assertEquals
import org.junit.Test

class CountryModelTest {

    @Test
    fun `country JSON deserializes correctly`() {
        val json = """
            {
                "name": "India",
                "region": "Asia",
                "code": "IN",
                "capital": "New Delhi"
            }
        """.trimIndent()

        val country = Gson().fromJson(json, Country::class.java)

        assertEquals("India", country.name)
        assertEquals("Asia", country.region)
        assertEquals("IN", country.code)
        assertEquals("New Delhi", country.capital)
    }
}
