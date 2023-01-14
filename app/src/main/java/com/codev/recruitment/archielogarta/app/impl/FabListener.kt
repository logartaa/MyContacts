package com.codev.recruitment.archielogarta.app.impl

import com.codev.recruitment.archielogarta.app.enum.FabActionType
import com.codev.recruitment.archielogarta.repository.entity.Contact

interface FabListener {
    fun onFabAction(fabActionType: FabActionType)
    fun onFabEdit(contact: Contact)
    fun onFabDelete()
    fun onFabError(errorMessage: String)
}