package com.codev.recruitment.archielogarta.impl

import com.codev.recruitment.archielogarta.enum.FabActionType
import com.codev.recruitment.archielogarta.repository.entity.Contact

interface FabListener {
    fun onFabAction(fabActionType: FabActionType)
    fun onFabEdit(contact: Contact)
    fun onFabDelete()
    fun onFabError(errorMessage: String)
}