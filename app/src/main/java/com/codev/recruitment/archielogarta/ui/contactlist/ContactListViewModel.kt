package com.codev.recruitment.archielogarta.ui.contactlist

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codev.recruitment.archielogarta.repository.dao.ContactDao
import com.codev.recruitment.archielogarta.repository.entity.Contact
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ContactListViewModel(private val contactDao: ContactDao) : ViewModel() {

    private val listConctact: MutableLiveData<List<Contact>> = MutableLiveData()

    fun callContactListDao(){
        viewModelScope.launch(Dispatchers.IO) {
            listConctact.postValue(contactDao.getAll())
        }
    }
    fun getAllContactsObserver() : MutableLiveData<List<Contact>>{
        return listConctact
    }
}