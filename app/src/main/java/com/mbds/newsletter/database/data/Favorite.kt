package com.mbds.newsletter.database.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.mbds.newsletter.models.Source
@Entity(tableName = "favorite")
class Favorite(
    @PrimaryKey(autoGenerate = true)
    val id:Int,
    val url:String,
    val author: String,
    val urlToImage: String,
    val title:String,
    val description:String,
    val source: String
)