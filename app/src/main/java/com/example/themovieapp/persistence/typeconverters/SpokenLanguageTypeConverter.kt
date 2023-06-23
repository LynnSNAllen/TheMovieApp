package com.example.themovieapp.persistence.typeconverters

import androidx.room.TypeConverter
import com.example.themovieapp.data.vos.SpokenLanguageVO
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class SpokenLanguageTypeConverter {
    @TypeConverter
    fun toString(spokenLanguage : List<SpokenLanguageVO>?) : String {
        return Gson().toJson(spokenLanguage)
    }

    @TypeConverter
    fun toSpokenLanguages(spokenLanguagesJsonString: String) : List<SpokenLanguageVO>? {
      val spokenLanguageListType =  object : TypeToken<List<SpokenLanguageVO>?>() {}.type
        return Gson().fromJson(spokenLanguagesJsonString , spokenLanguageListType)
    }
}