package com.minminaya.nidaily;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import com.blankj.utilcode.util.FragmentUtils;
import com.blankj.utilcode.util.SnackbarUtils;
import com.minminaya.library.util.Logger;
import com.minminaya.nidaily.base.BaseActivity;
import com.minminaya.nidaily.column.fragment.ColumnFragment;
import com.minminaya.nidaily.home.fragment.HomeFragment;
import com.minminaya.nidaily.hot.fragment.HotFragment;
import com.minminaya.nidaily.mvp.view.MvpView;
import com.minminaya.nidaily.topic.fragment.TopicFragment;
import com.minminaya.nidaily.util.BottomNavigationViewHelper;

import butterknife.BindView;

public class MainActivity extends BaseActivity
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
                        Logger.e("MainActivity", "home");
                        break;
                    case R.id.hot:
                        FragmentUtils.hideFragment(HomeFragment.getInstance());
                        FragmentUtils.showFragment(HotFragment.getInstance());
                        FragmentUtils.hideFragment(TopicFragment.getInstance());
                        FragmentUtils.hideFragment(ColumnFragment.getInstance());

                        setTitle("热门");

                        Logger.e("MainActivity", "hot");
                        break;
                    case R.id.topic:
                        FragmentUtils.hideFragment(HomeFragment.getInstance());
                        FragmentUtils.hideFragment(HotFragment.getInstance());
                        FragmentUtils.showFragment(TopicFragment.getInstance());
                        FragmentUtils.hideFragment(ColumnFragment.getInstance());

                        setTitle("主题");

                        Logger.e("MainActivity", "topic");
                        break;
                    case R.id.column:
                        FragmentUtils.hideFragment(HomeFragment.getInstance());
                        FragmentUtils.hideFragment(HotFragment.getInstance());
                        FragmentUtils.hideFragment(TopicFragment.getInstance());
                        FragmentUtils.showFragment(ColumnFragment.getInstance());

                        setTitle("栏目");

                        Logger.e("MainActivity", "column");
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

        View view = LayoutInflater.from(App.getINSTANCE()).inflate(R.layout.nav_header_outer, null);
        view.findViewById(R.id.imageView).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Logger.e("MainActivity--navigationView--click", "viewId:" + v.getId());
            }
        });

        navigationView.addHeaderView(view);


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
            SnackbarUtils.with(getWindow().getDecorView())
                    .setMessage("要离开我了吗")
                    .setDuration(SnackbarUtils.LENGTH_LONG)
                    .setBottomMargin(313)
                    .setBgColor(Color.parseColor("#009688"))
                    .setAction("是", Color.parseColor("#FF4081"), new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            MainActivity.super.onBackPressed();
                        }
                    }).show();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_issue) {
            // Handle the camera action
        } else if (id == R.id.nav_suggestion) {

        } else if (id == R.id.nav_about) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onFailed(Throwable throwable) {

    }

}
