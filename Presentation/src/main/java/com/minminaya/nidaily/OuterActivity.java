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
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.blankj.utilcode.util.FragmentUtils;
import com.minminaya.library.util.Logger;
import com.minminaya.nidaily.base.BaseActivity;
import com.minminaya.nidaily.column.fragment.ColumnFragment;
import com.minminaya.nidaily.home.fragment.HomeFragment;
import com.minminaya.nidaily.hot.fragment.HotFragment;
import com.minminaya.nidaily.mvp.view.MvpView;
import com.minminaya.nidaily.topic.fragment.TopicFragment;
import com.minminaya.nidaily.util.BottomNavigationViewHelper;

import butterknife.BindView;

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
        FragmentUtils.addFragment(mFragmentManager, HomeFragment.getInstance(), R.id.frame_layout_content, false);
        FragmentUtils.addFragment(mFragmentManager, HotFragment.getInstance(), R.id.frame_layout_content, true);
        FragmentUtils.addFragment(mFragmentManager, TopicFragment.getInstance(), R.id.frame_layout_content, true);
        FragmentUtils.addFragment(mFragmentManager, ColumnFragment.getInstance(), R.id.frame_layout_content, true);

        setTitle("首页");
    }

    @Override
    public void setListeners() {
        bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home:
                        FragmentUtils.showFragment(HomeFragment.getInstance());
                        FragmentUtils.hideFragment(HotFragment.getInstance());
                        FragmentUtils.hideFragment(TopicFragment.getInstance());
                        FragmentUtils.hideFragment(ColumnFragment.getInstance());

                        setTitle("首页");
                        Logger.e("OuterActivity", "home");
                        break;
                    case R.id.hot:
                        FragmentUtils.hideFragment(HomeFragment.getInstance());
                        FragmentUtils.showFragment(HotFragment.getInstance());
                        FragmentUtils.hideFragment(TopicFragment.getInstance());
                        FragmentUtils.hideFragment(ColumnFragment.getInstance());

                        setTitle("热门");

                        Logger.e("OuterActivity", "hot");
                        break;
                    case R.id.topic:
                        FragmentUtils.hideFragment(HomeFragment.getInstance());
                        FragmentUtils.hideFragment(HotFragment.getInstance());
                        FragmentUtils.showFragment(TopicFragment.getInstance());
                        FragmentUtils.hideFragment(ColumnFragment.getInstance());

                        setTitle("主题");

                        Logger.e("OuterActivity", "topic");
                        break;
                    case R.id.column:
                        FragmentUtils.hideFragment(HomeFragment.getInstance());
                        FragmentUtils.hideFragment(HotFragment.getInstance());
                        FragmentUtils.hideFragment(TopicFragment.getInstance());
                        FragmentUtils.showFragment(ColumnFragment.getInstance());

                        setTitle("栏目");

                        Logger.e("OuterActivity", "column");
                        break;
                }
                return true;
            }
        });
    }

    @Override
    public void bind() {
        BottomNavigationViewHelper.disableShiftMode(bottomNavigation);

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
