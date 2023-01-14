package com.codev.recruitment.archielogarta.app.ui.contactdetail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codev.recruitment.archielogarta.repository.dao.ContactDao
import com.codev.recruitment.archielogarta.repository.entity.Contact
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ViewContactViewModel(private val contactDao: ContactDao) : ViewModel() {

    val contact: MutableLiveData<Contact> = MutableLiveData()

    fun callContactDao(cid: String){
        viewModelScope.launch(Dispatchers.IO) {
            contact.postValue(contactDao.getContactDetail(cid))
        }
    }
    fun getContactDetailObserver() : MutableLiveData<Contact> {
        return contact
    }
    fun deleteContactDao(contact: Contact){
        viewModelScope.launch(Dispatchers.IO) {
            contactDao.delete(contact)
        }
    }

}