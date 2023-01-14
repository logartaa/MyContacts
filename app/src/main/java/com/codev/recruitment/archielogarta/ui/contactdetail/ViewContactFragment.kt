package com.codev.recruitment.archielogarta.ui.contactdetail

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.core.view.MenuProvider
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.navArgs
import com.codev.recruitment.archielogarta.R
import com.codev.recruitment.archielogarta.databinding.FragmentViewContactBinding
import com.codev.recruitment.archielogarta.enum.FabActionType
import com.codev.recruitment.archielogarta.impl.FabListener
import com.codev.recruitment.archielogarta.repository.entity.Contact
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class ViewContactFragment : Fragment() {

    companion object {
        fun newInstance() = ViewContactFragment()
    }

    private lateinit var binding: FragmentViewContactBinding
    private val viewContactViewModel: ViewContactViewModel by viewModel()
    private var fabListener: FabListener? = null
    private val args: ViewContactFragmentArgs by navArgs()
    private lateinit var contact: Contact

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentViewContactBinding.bind(inflater.inflate(R.layout.fragment_view_contact, container, false))
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewContactViewModel.getContactDetailObserver()
            .observe(viewLifecycleOwner) {
                contact = it
                binding.textViewFirstName.text = it.firstName
                binding.textViewLastName.text = it.lastName
                binding.textViewPhoneNumber.text = it.phoneNumber
                binding.cbFavorite.isChecked = it.isFavorite == true
            }

        viewContactViewModel.callContactDao(args.contactId)

        activity?.let { it ->
            setMenuOptions(it)
            setFab(it)
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        fabListener = context as FabListener
    }

    private fun setFab(it: FragmentActivity) {
        val fab = it.findViewById<FloatingActionButton>(R.id.fab_contact)
        fab.setImageResource(R.drawable.ic_edit_24dp)
        fab.setOnClickListener {
            fabListener?.onFabAction(FabActionType.EDIT_CONTACT)
            fabListener?.onFabEdit(contact)
        }
    }

    private fun setMenuOptions(it: FragmentActivity) {
        it.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.contact_list_menu, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.menu_delete_contact -> {
                        viewContactViewModel.deleteContactDao(contact)
                        fabListener?.onFabDelete()
                        true
                    }
                    else -> false
                }
            }

        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

}