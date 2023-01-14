package com.codev.recruitment.archielogarta.app.ui.favorites

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.codev.recruitment.archielogarta.R
import com.codev.recruitment.archielogarta.databinding.FragmentContactListBinding
import com.codev.recruitment.archielogarta.app.enum.FabActionType
import com.codev.recruitment.archielogarta.app.impl.FabListener
import com.codev.recruitment.archielogarta.repository.entity.Contact
import com.codev.recruitment.archielogarta.app.ui.contactlist.ContactRecyclerViewAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton
import org.koin.androidx.viewmodel.ext.android.viewModel

class FavoriteFragment : Fragment(), ContactRecyclerViewAdapter.ContactListener{

    private var fabListener: FabListener? = null
    private lateinit var binding: FragmentContactListBinding
    private val favoriteListViewModel: FavoriteViewModel by viewModel()
    private lateinit var adapter: ContactRecyclerViewAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentContactListBinding.bind(
            inflater.inflate(
                R.layout.fragment_contact_list,
                container,
                false
            )
        )

        // Set the adapter
        with(binding.list) {
            layoutManager = LinearLayoutManager(context)
        }
        adapter = ContactRecyclerViewAdapter(
            this@FavoriteFragment
        )
        binding.list.adapter = adapter

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        activity?.findViewById<FloatingActionButton>(R.id.fab_contact)?.setOnClickListener {
            fabListener?.onFabAction(FabActionType.CREATE_CONTACT_CONTACTLIST)
        }

        favoriteListViewModel.getFavoriteContactsObserver()
            .observe(viewLifecycleOwner) {
                if(it.isEmpty()){
                    binding.list.visibility = View.GONE
                    binding.emptylist.visibility = View.VISIBLE
                } else {
                    binding.list.visibility = View.VISIBLE
                    binding.emptylist.visibility = View.GONE
                }
                adapter.setContactList(it)
            }
        favoriteListViewModel.callFavoriteContactsDao()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        fabListener = context as FabListener
    }

    override fun onViewContact(contact: Contact) {
        val direction = FavoriteFragmentDirections.actionNavigationFavoritesToViewContactFragment(contact.cid.toString())
        findNavController().navigate(direction)
    }

    override fun onEditContact(contact: Contact) {
        TODO("Not yet implemented")
    }

    override fun onDeleteContact(contact: Contact) {
        TODO("Not yet implemented")
    }
}