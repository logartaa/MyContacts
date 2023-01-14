package com.codev.recruitment.archielogarta.repository.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Contact(
    @ColumnInfo(name = "first_name") var firstName: String?,
    @ColumnInfo(name = "last_name") var lastName: String?,
    @ColumnInfo(name = "phone_number") var phoneNumber: String?,
    @ColumnInfo(name = "is_favorite") var isFavorite: Boolean?,
    @PrimaryKey(autoGenerate = true) val cid: Int = 0,
    )