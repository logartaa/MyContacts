package com.codev.recruitment.archielogarta.ui.contactdetail

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.codev.recruitment.archielogarta.R
import com.codev.recruitment.archielogarta.databinding.FragmentContactDetailBinding
import com.codev.recruitment.archielogarta.enum.FabActionType
import com.codev.recruitment.archielogarta.impl.FabListener
import com.codev.recruitment.archielogarta.repository.entity.Contact
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class ContactDetailFragment : Fragment() {

    companion object {
        fun newInstance() = ContactDetailFragment()
    }

    private lateinit var binding: FragmentContactDetailBinding
    private val contactDetailViewModel: ContactDetailViewModel by viewModel()
    private lateinit var updateContact: Contact
    private val args: ContactDetailFragmentArgs by navArgs()

    private var fabListener: FabListener? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentContactDetailBinding.bind(inflater.inflate(R.layout.fragment_contact_detail, container, false))
        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        fabListener = context as FabListener
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (args.contactId.isNotBlank()) {
            contactDetailViewModel.getContactDetailObserver()
                .observe(viewLifecycleOwner) {
                    updateContact = it
                    binding.textInputFirstName.setText(it.firstName)
                    binding.textInputLastName.setText(it.lastName)
                    binding.textInputPhoneNumber.setText(it.phoneNumber)
                    binding.cbFavorite.isChecked = it.isFavorite == true
                }
            contactDetailViewModel.callContactDao(args.contactId)
        } else {
            updateContact = Contact("","","", isFavorite = false)
        }

        activity?.let { it ->
            val fab = it.findViewById<FloatingActionButton>(R.id.fab_contact)
            fab.setImageResource(R.drawable.ic_saved_contact_24dp)
            fab.setOnClickListener {
                if (binding.textInputPhoneNumber.text?.isNotBlank() == true) {
                    CoroutineScope(Dispatchers.IO).launch {
                        val contact = Contact(
                            binding.textInputFirstName.text.toString(),
                            binding.textInputLastName.text.toString(),
                            binding.textInputPhoneNumber.text.toString(),
                            binding.cbFavorite.isChecked
                        )
                        contactDetailViewModel.setContact(args.contactId, contact, updateContact)

                    }
                    fabListener?.onFabAction(FabActionType.ADD_CONTACT)
                } else {
                    fabListener?.onFabError("Contact number is required.")
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        fabListener = null
    }

}