package com.minminaya.nidaily;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.blankj.utilcode.util.FragmentUtils;
import com.minminaya.library.util.Logger;
import com.minminaya.nidaily.base.BaseActivity;
import com.minminaya.nidaily.home.fragment.HomeFragment;
import com.minminaya.nidaily.home.presenter.HomeFragmentPresenter;
import com.minminaya.nidaily.mvp.view.MvpView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OuterActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener, MvpView {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.nav_view)
    NavigationView navigationView;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;
    @BindView(R.id.frame_layout_content)
    FrameLayout frameLayoutContent;
    @BindView(R.id.bottom_navigation)
    BottomNavigationView bottomNavigation;

    FragmentManager mFragmentManager = getSupportFragmentManager();



    @Override
    public int getContentView() {
        return R.layout.activity_outer;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        setSupportActionBar(toolbar);
        //添加Fragment
        FragmentUtils.addFragment(mFragmentManager, HomeFragment.getInstance(), R.id.frame_layout_content, true);

        FragmentUtils.showFragment(HomeFragment.getInstance());
    }

    @Override
    public void setListeners() {
        bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.home:
                        FragmentUtils.showFragment(HomeFragment.getInstance());
                        Logger.e("OuterActivity", "home");
                        break;
                    case R.id.topic:
                        FragmentUtils.hideFragment(HomeFragment.getInstance());
                        Logger.e("OuterActivity", "topic");
                        break;
                    case R.id.column:
                        FragmentUtils.showFragment(HomeFragment.getInstance());
                        Logger.e("OuterActivity", "column");
                        break;
                }
                return true;
            }
        });
    }

    @Override
    public void bind() {


        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void unBind() {
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.outer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onFailed(Throwable throwable) {

    }

}
