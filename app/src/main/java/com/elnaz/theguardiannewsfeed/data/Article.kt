package com.elnaz.theguardiannewsfeed.data

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity( tableName = "articles")
data class Article(
    @PrimaryKey (autoGenerate = true)
    val _id: Int = 0,
    val id: String,
    val type: String,
    val sectionId: String,
    val sectionName: String,
    val webPublicationDate: String,
    val webTitle: String,
    val webUrl: String,
    val isHosted: Boolean,
    val pillarId: String,
    val pillarName: String,
    var selected: Boolean = false
): Parcelable
