package iamdhariot.github.retrofitphpapp.activities

import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.view.MenuItem
import android.widget.Toast
import  androidx.appcompat.widget.Toolbar
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import iamdhariot.github.retrofitphpapp.R

class NavDrawerActivity : AppCompatActivity() {

    // for navigation drawer
    private lateinit var drawerLayout:DrawerLayout
    private lateinit var toolbar:Toolbar
    private lateinit var navDrawerView: NavigationView

    //Make sure to be using androidx.appcompat.app.ActionBarDrawerToggle version
    private lateinit var drawerToggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nav_drawer)
        // set a Toolbar and replace the ActionBar
        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        // This will display an Up icon (<-). We will replace it to the hamburger later
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // DrawerLayout
        drawerLayout = findViewById(R.id.drawer_layout)
        // drawer toggle
        drawerToggle = setupDrawerToggle()

        // NavDrawerView
        navDrawerView = findViewById(R.id.nav_drawer_view)
        // setup the nav drawer menu items
        setupDrawerContent(navDrawerView)

        // fragment initialize

        // to start the initial fragment on the activity create
        if(savedInstanceState==null){
            //adding the default fragment on activity create
           // supportFragmentManager.beginTransaction().add().commit()
            title ="Home"
        }
        // setup toggle to display the hamburger icon with animation
        drawerToggle.isDrawerIndicatorEnabled = true
        drawerToggle.syncState()

        //tie the DrawerLayout events to the ActionBarToggle
        drawerLayout.addDrawerListener(drawerToggle)

    }
    private fun setupDrawerToggle(): ActionBarDrawerToggle {
        // Note: make sure you pass the valid toolbar reference. ActionBarToggle() doesn't require it.
        // and will not render the hamburger icon without it
        return ActionBarDrawerToggle(NavDrawerActivity@this, drawerLayout,
            R.string.drawer_open,
            R.string.drawer_close
        )

    }

    override fun onPostCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onPostCreate(savedInstanceState, persistentState)
        //sync the toggle state after onRestoreInstanceState has occurred
        drawerToggle.syncState()
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        // pass any configuration change to the drawer toggles
        drawerToggle.onConfigurationChanged(newConfig)
    }

    private fun setupDrawerContent(navDrawerView: NavigationView?) {
        navDrawerView?.setNavigationItemSelectedListener { menuItem ->
            selectDrawerMenuItem(menuItem)
            true
        }
    }
    private fun selectDrawerMenuItem(menuItem: MenuItem) {
        when(menuItem.itemId){
            R.id.nav_home -> {
                // show home fragment

                highLightMenuItemAndTitle(menuItem)
            }
            R.id.nav_profile -> {
                // show home fragment

                highLightMenuItemAndTitle(menuItem)
            }
            R.id.nav_messages -> {
                // show home fragment

                highLightMenuItemAndTitle(menuItem)
            }
            R.id.nav_logout -> {
                // show home fragment
                Toast.makeText(NavDrawerActivity@this,"logging out",Toast.LENGTH_SHORT).show()
            }
        }
        // close the NavDrawer
        drawerLayout.closeDrawers()
    }

    // highlight the selected item has been done by the NavigationView except logout
    private fun highLightMenuItemAndTitle(menuItem: MenuItem) {
        // highlight the selected item has been done by the NavigationView
        menuItem.isChecked = true

        //set action bar title
        title= menuItem.title
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(drawerToggle.onOptionsItemSelected(item)){
            return true
        }
        return super.onOptionsItemSelected(item)
    }

}