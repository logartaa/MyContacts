package com.codev.recruitment.archielogarta.repository.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.codev.recruitment.archielogarta.repository.entity.Contact

@Dao
interface ContactDao {

    @Query("SELECT * FROM contact")
    fun getAll(): List<Contact>

    @Query("SELECT * From contact where cid=:cid")
    fun getContactDetail(cid: String): Contact

    @Query("SELECT * From contact where is_favorite='true'")
    fun getAllFavoriteContacts(): List<Contact>

    @Insert
    fun addContact(vararg contact: Contact)

    @Delete
    fun delete(contact: Contact)

    @Update
    fun update(contact: Contact)
}