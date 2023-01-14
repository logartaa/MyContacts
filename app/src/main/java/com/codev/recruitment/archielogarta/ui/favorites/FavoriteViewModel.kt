package com.codev.recruitment.archielogarta.ui.favorites

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codev.recruitment.archielogarta.repository.dao.ContactDao
import com.codev.recruitment.archielogarta.repository.entity.Contact
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FavoriteViewModel(private val contactDao: ContactDao) : ViewModel() {

    private val favoriteList: MutableLiveData<List<Contact>> = MutableLiveData()

    fun callFavoriteContactsDao(){
        viewModelScope.launch(Dispatchers.IO) {
            favoriteList.postValue(contactDao.getAllFavoriteContacts())
        }
    }
    fun getFavoriteContactsObserver() : MutableLiveData<List<Contact>>{
        return favoriteList
    }
}