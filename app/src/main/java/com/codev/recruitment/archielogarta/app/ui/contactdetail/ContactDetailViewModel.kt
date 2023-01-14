package com.codev.recruitment.archielogarta.app.ui.contactdetail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codev.recruitment.archielogarta.repository.dao.ContactDao
import com.codev.recruitment.archielogarta.repository.entity.Contact
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ContactDetailViewModel(private val contactDao: ContactDao) : ViewModel() {

    fun setContact(cid: String, contact: Contact, updateContact: Contact) {
        if (cid.isNotBlank()) {
            updateContact.firstName = contact.firstName
            updateContact.lastName = contact.lastName
            updateContact.phoneNumber = contact.phoneNumber
            updateContact.isFavorite = contact.isFavorite
            updateContact(updateContact)
        } else {
            addContact(contact)
        }
    }

    private fun addContact(contact: Contact) {
        contactDao.addContact(contact)
    }

     private fun updateContact(contact: Contact) {
        contactDao.update(contact)
    }

    val contact: MutableLiveData<Contact> = MutableLiveData()

    fun callContactDao(cid: String){
        viewModelScope.launch(Dispatchers.IO) {
            contact.postValue(contactDao.getContactDetail(cid))
        }
    }
    fun getContactDetailObserver() : MutableLiveData<Contact> {
        return contact
    }

}