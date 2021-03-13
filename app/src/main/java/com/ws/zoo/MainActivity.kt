package com.ws.zoo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.ncapdevi.fragnav.FragNavController
import com.ws.zoo.main.base.BaseFragment
import com.ws.zoo.main.zoo.categorylist.ZooCategoryListFragment
import io.reactivex.disposables.CompositeDisposable

class MainActivity : AppCompatActivity(), BaseFragment.FragmentNavigation {
    private lateinit var drawer:DrawerLayout
    private var compositeDisposable: CompositeDisposable? = null
    private val fragNavController: FragNavController =
        FragNavController(
            supportFragmentManager,
            R.id.fragment_container_view
        )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        drawer = findViewById(R.id.drawer_layout)
        val toggle = ActionBarDrawerToggle(
            this, drawer, null, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer.addDrawerListener(toggle)
        toggle.syncState()

        val fragments = listOf(
            ZooCategoryListFragment.newInstance()
        )

        fragNavController.apply {
            rootFragments = fragments
        }

        fragNavController.initialize(0, savedInstanceState)
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable?.clear()
    }

    override fun pushFragment(fragment: Fragment, sharedElementList: List<Pair<View, String>>?) {
        fragNavController.pushFragment(fragment)
    }

    override fun popFragment() {
        when {
            drawer.isDrawerOpen(GravityCompat.START) -> drawer.closeDrawer(GravityCompat.START)
            fragNavController.isRootFragment.not() -> fragNavController.popFragment()
        }
    }

    override fun openDrawer() {
        drawer.openDrawer(GravityCompat.START)
    }

    override fun onBackPressed() {
        when {
            drawer.isDrawerOpen(GravityCompat.START) -> drawer.closeDrawer(GravityCompat.START)
            fragNavController.isRootFragment.not() -> fragNavController.popFragment()
            else -> super.onBackPressed()
        }
    }

    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        super.onSaveInstanceState(outState, outPersistentState)
        fragNavController.onSaveInstanceState(outState)
    }
}