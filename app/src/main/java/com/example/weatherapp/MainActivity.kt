package com.example.weatherapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.GravityCompat
import androidx.core.view.WindowCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.ViewPager
import com.bumptech.glide.Glide
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.google.android.material.tabs.TabLayout

class MainActivity : AppCompatActivity() {

    lateinit var ivImage: ImageView
    lateinit var tvWeatherStatus: TextView
    lateinit var toolbar: Toolbar
    lateinit var collapsingToolbar: CollapsingToolbarLayout
    lateinit var drawerLayer: DrawerLayout
    lateinit var tabLayout: TabLayout
    lateinit var viewPager: ViewPager
    lateinit var viewPagerAdapter: ViewPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Set the status bar to be transparent
        WindowCompat.setDecorFitsSystemWindows(window,false)
        setContentView(R.layout.activity_main)

        initUI()
        loadCollapsingToolbarImage()
        setupSupportActionBar()

        viewPagerAdapter =
            ViewPagerAdapter(supportFragmentManager, FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT)
        viewPager.adapter = viewPagerAdapter
        tabLayout.setupWithViewPager(viewPager)



    }

    private fun loadCollapsingToolbarImage(){
        Glide
            .with(this)
            .load(ResourcesCompat.getDrawable(resources,R.drawable.flat_mountain_amthyst,theme))
            .into(ivImage)
    }

    private fun initUI(){
        ivImage = findViewById(R.id.iv_collapsing_image)
        toolbar = findViewById(R.id.toolbar)
        drawerLayer = findViewById(R.id.drawer_layout)
        collapsingToolbar = findViewById(R.id.collapsing_toolbar)
        tvWeatherStatus = findViewById(R.id.tv_weather_status)
        tabLayout = findViewById(R.id.tab_layout)
        viewPager = findViewById(R.id.view_pager)
    }

    private fun setupSupportActionBar(){
        setSupportActionBar(toolbar)
        val supportActionBar = supportActionBar
        supportActionBar?.setHomeAsUpIndicator(R.drawable.round_menu_24)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if(item.itemId == android.R.id.home){
            if(drawerLayer.isDrawerOpen(GravityCompat.START)){
                drawerLayer.closeDrawer(GravityCompat.START)
            }
            else{
                drawerLayer.openDrawer(GravityCompat.START)
            }
        }

        return super.onOptionsItemSelected(item)
    }
}