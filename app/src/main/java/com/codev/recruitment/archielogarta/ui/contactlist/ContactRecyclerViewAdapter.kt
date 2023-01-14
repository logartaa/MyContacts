package com.codev.recruitment.archielogarta.ui.contactlist

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.codev.recruitment.archielogarta.databinding.FragmentContactItemBinding
import com.codev.recruitment.archielogarta.repository.entity.Contact

/**
 * [RecyclerView.Adapter] that can display a [PlaceholderItem].
 * TODO: Replace the implementation with code for your data type.
 */
class ContactRecyclerViewAdapter(
    private val contactListener: ContactListener,
) : RecyclerView.Adapter<ContactRecyclerViewAdapter.ViewHolder>(){

    private var contacts = emptyList<Contact>()

    @SuppressLint("NotifyDataSetChanged")
    fun setContactList(contactList: List<Contact>) {
        this.contacts = contactList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(
            FragmentContactItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = contacts[position]
        holder.contactNumber.text = item.firstName
        holder.fName.text = item.phoneNumber
        holder.container.setOnClickListener {
            contactListener.onViewContact(item)
        }
        holder.bindContact(item)
    }

    override fun getItemCount(): Int = contacts.size

    inner class ViewHolder(binding: FragmentContactItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        lateinit var contact: Contact

        val contactNumber: TextView = binding.tvContactNumber
        val fName: TextView = binding.tvName
        val container: LinearLayout = binding.contactContainer

        fun bindContact(contact: Contact){
            this@ViewHolder.contact = contact
        }
    }

    interface ContactListener {
        fun onViewContact(contact: Contact)
        fun onEditContact(contact: Contact)
        fun onDeleteContact(contact: Contact)
    }

}