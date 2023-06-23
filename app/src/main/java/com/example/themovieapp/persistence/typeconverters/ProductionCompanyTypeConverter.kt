package com.example.themovieapp.persistence.typeconverters

import androidx.room.TypeConverter
import com.example.themovieapp.data.vos.ProductionCompanyVO
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class ProductionCompanyTypeConverter {
    @TypeConverter
    fun toString(productionCompanies : List<ProductionCompanyVO>?): String {
        return Gson().toJson(productionCompanies)
    }

    @TypeConverter
    fun toProductionCompanies(productionCompaniesJsonString: String): List<ProductionCompanyVO>? {
        val productionCompaniesListType = object : TypeToken<List<ProductionCompanyVO>?>() {}.type
        return Gson().fromJson(productionCompaniesJsonString, productionCompaniesListType)
    }
}