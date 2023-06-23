package com.example.themovieapp.persistence.typeconverters

import androidx.room.TypeConverter
import com.example.themovieapp.data.vos.CollectionVO
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class CollectionTypeConverter  {
    @TypeConverter
    fun toString(collection: CollectionVO?): String {
        return Gson().toJson(collection)
    }

    @TypeConverter
    fun toCollectionVO(commentListJsonStr:String) : CollectionVO? {
      val collectionVOType =   object : TypeToken<CollectionVO?>() {}.type
        return Gson().fromJson(commentListJsonStr , collectionVOType)
    }
}