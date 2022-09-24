package es.jolusan.appdemo.presentation

import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import dagger.hilt.android.AndroidEntryPoint
import es.jolusan.appdemo.databinding.MainActivityBinding
import android.view.MenuItem
import androidx.navigation.NavController
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.findNavController
import es.jolusan.appdemo.R
import es.jolusan.appdemo.domain.model.RecipeDetail
import es.jolusan.appdemo.presentation.main.MainFragmentDirections


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: MainActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MainActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        val appBarConfiguration = AppBarConfiguration(navController.graph)
        binding.toolbar.setupWithNavController(navController, appBarConfiguration)
        setToolbarMenu()
        setNavigationListener(navController)
    }

    private fun setToolbarMenu() {
        binding.toolbar.inflateMenu(R.menu.menu_main)
        binding.toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.bookmarks -> {
                    //TODO go to bookmarks fragment
                    val action = MainFragmentDirections.actionMainFragmentToDetailFragment(
                        RecipeDetail("", "", "", "", "", listOf(), 0F, listOf(), listOf())
                    )
                    val navHostFragment =
                        supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
                    val navController = navHostFragment.navController
                    navController.navigate(action)
                    true
                }
                else -> super.onOptionsItemSelected(it)
            }
        }
    }

    private fun setNavigationListener(navController: NavController) {
        navController.addOnDestinationChangedListener { _, destination, arguments ->
            val menuItem = binding.toolbar.menu.findItem(R.id.bookmarks)
            when (destination.id) {
                R.id.mainFragment -> menuItem.isVisible = true
                R.id.detailFragment -> menuItem.isVisible = false
            }
        }
    }

}