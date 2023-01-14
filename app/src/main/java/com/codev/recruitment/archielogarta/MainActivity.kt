package com.codev.recruitment.archielogarta

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.codev.recruitment.archielogarta.databinding.ActivityMainBinding
import com.codev.recruitment.archielogarta.enum.FabActionType
import com.codev.recruitment.archielogarta.impl.FabListener
import com.codev.recruitment.archielogarta.repository.entity.Contact
import com.codev.recruitment.archielogarta.ui.contactdetail.ViewContactFragmentDirections
import com.codev.recruitment.archielogarta.ui.contactlist.ContactListFragmentDirections
import com.codev.recruitment.archielogarta.ui.favorites.FavoriteFragmentDirections
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity(),
    FabListener {

    private lateinit var binding: ActivityMainBinding
    private var fabListener: FabListener? = null
    private lateinit var navController: NavController
    private lateinit var fabActionType: FabActionType

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        navController = findNavController(R.id.nav_host_fragment_activity_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_contact_list, R.id.navigation_favorites
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        fabListener = this
        fabActionType = FabActionType.CREATE_CONTACT_CONTACTLIST

        binding.fabContact.setOnClickListener {
            when (fabActionType) {
                FabActionType.CREATE_CONTACT_CONTACTLIST -> {
                    navController.navigate(R.id.action_navigation_contact_list_to_contactDetailFragment)
                }
                FabActionType.CREATE_CONTACT_FAVORITE_LIST -> {
                    navController.navigate(R.id.action_navigation_favorites_to_contactDetailFragment)
                }
                else -> {
                    // Do nothing
                }
            }
        }

    }

    override fun onFabAction(fabActionType: FabActionType) {
        this.fabActionType = fabActionType
        if (::binding.isInitialized) {
            when (fabActionType) {
                FabActionType.CREATE_CONTACT_CONTACTLIST -> {
                    val direction =
                        ContactListFragmentDirections.actionNavigationContactListToContactDetailFragment(
                            ""
                        )
                    navController.navigate(direction)
                }
                FabActionType.CREATE_CONTACT_FAVORITE_LIST -> {
                    val direction =
                        FavoriteFragmentDirections.actionNavigationFavoritesToContactDetailFragment(
                            ""
                        )
                    navController.navigate(direction)
                }
                FabActionType.ADD_CONTACT -> {
                    binding.fabContact.setImageResource(R.drawable.ic_add_contact_24dp)
                    navController.navigateUp()
                    this.fabActionType = FabActionType.CREATE_CONTACT_CONTACTLIST
                }
                else -> {
                    // Do nothing
                }
            }
        }
    }

    override fun onFabEdit(contact: Contact) {
        this.fabActionType = FabActionType.VIEW_CONTACT
        val direction =
            ViewContactFragmentDirections.actionViewContactFragmentToContactDetailFragment(contact.cid.toString())
        navController.navigate(direction)
    }

    override fun onFabDelete() {
        navController.navigate(R.id.action_viewContactFragment_to_navigation_contact_list)
    }

    override fun onFabError(errorMessage: String) {
        Snackbar.make(binding.root, errorMessage, Snackbar.LENGTH_SHORT).show()
    }

    override fun onSupportNavigateUp(): Boolean {
        binding.fabContact.setImageResource(R.drawable.ic_add_contact_24dp)
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

}