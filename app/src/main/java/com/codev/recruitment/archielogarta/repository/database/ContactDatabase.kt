package com.codev.recruitment.archielogarta.repository.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.codev.recruitment.archielogarta.repository.dao.ContactDao
import com.codev.recruitment.archielogarta.repository.entity.Contact

@Database(entities = [Contact::class], version = 2)
abstract class ContactDatabase : RoomDatabase() {
    abstract fun contactDao(): ContactDao
}