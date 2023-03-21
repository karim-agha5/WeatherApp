package com.example.weatherapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem

import androidx.core.content.res.ResourcesCompat
import androidx.core.view.GravityCompat
import androidx.core.view.WindowCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentStatePagerAdapter
import com.bumptech.glide.Glide
import com.example.weatherapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var mainActivityBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Set the status bar to be transparent
        WindowCompat.setDecorFitsSystemWindows(window,false)

        initMainActivityBinding()
        loadCollapsingToolbarImage()
        setupSupportActionBar()
        setUpTabLayoutFunctionality()

    }

    private fun loadCollapsingToolbarImage(){
        Glide
            .with(this)
            .load(ResourcesCompat.getDrawable(resources,R.drawable.pxfuel,theme))
            .into(mainActivityBinding.ivCollapsingImage)
    }

    private fun initMainActivityBinding(){
        mainActivityBinding = DataBindingUtil.setContentView(this,R.layout.activity_main)
        mainActivityBinding.lifecycleOwner = this
    }

    /*
    * Sets the custom toolbar as the Appbar along with its components
    * */
    private fun setupSupportActionBar(){
        setSupportActionBar(mainActivityBinding.toolbar)
        val supportActionBar = supportActionBar
        supportActionBar?.setHomeAsUpIndicator(R.drawable.round_menu_24)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)
    }

    private fun setUpTabLayoutFunctionality(){
        mainActivityBinding.viewPager.adapter =
            ViewPagerAdapter(supportFragmentManager, FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT)
        mainActivityBinding.tabLayout.setupWithViewPager(mainActivityBinding.viewPager)

    }

    /*
    * Handles the hamburger icon clicks
    * */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if(item.itemId == android.R.id.home){
            if(mainActivityBinding.drawerLayout.isDrawerOpen(GravityCompat.START)){
                mainActivityBinding.drawerLayout.closeDrawer(GravityCompat.START)
            }
            else{
                mainActivityBinding.drawerLayout.openDrawer(GravityCompat.START)
            }
        }


        return super.onOptionsItemSelected(item)
    }
}