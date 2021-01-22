package com.example.currencyconverterapplication.main

import com.example.currencyconverterapplication.data.models.CurrencyResponse
import com.example.currencyconverterapplication.util.Resource

interface MainRepository {

    suspend fun getRates(base : String) : Resource<CurrencyResponse>
}