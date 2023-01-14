package com.codev.recruitment.archielogarta

import android.app.Application
import androidx.room.Room
import com.codev.recruitment.archielogarta.repository.dao.ContactDao
import com.codev.recruitment.archielogarta.repository.database.ContactDatabase
import com.codev.recruitment.archielogarta.app.ui.contactdetail.ContactDetailViewModel
import com.codev.recruitment.archielogarta.app.ui.contactdetail.ViewContactViewModel
import com.codev.recruitment.archielogarta.app.ui.contactlist.ContactListViewModel
import com.codev.recruitment.archielogarta.app.ui.favorites.FavoriteViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val contactDbModule = module {
    fun provideDataBase(application: Application): ContactDatabase {
        return Room.databaseBuilder(application, ContactDatabase::class.java, "CONTACTDB")
            .fallbackToDestructiveMigration()
            .build()
    }

    fun provideDao(dataBase: ContactDatabase): ContactDao {
        return dataBase.contactDao()
    }
    single { provideDataBase(androidApplication()) }
    single { provideDao(get()) }

}
val  viewModelModule= module{
    viewModel {
        ContactListViewModel(get())
    }
    viewModel {
        ContactDetailViewModel(get())
    }
    viewModel {
        ViewContactViewModel(get())
    }
    viewModel {
        FavoriteViewModel(get())
    }

}
