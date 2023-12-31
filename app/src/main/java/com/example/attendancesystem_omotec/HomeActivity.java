package com.example.attendancesystem_omotec;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.example.attendancesystem_omotec.Fragments.ContactUsFragment;
import com.example.attendancesystem_omotec.Fragments.HomeFragment;
import com.example.attendancesystem_omotec.Fragments.AttendanceFragment;
import com.example.attendancesystem_omotec.Fragments.SchoolsFragment;
import com.google.android.material.navigation.NavigationView;

import java.util.Objects;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private ImageView school;
    private FrameLayout frameLayout;
    private static final int HOME_FRAGMENT = 0;
    private static final int SCHOOLS_FRAGMENT = 1;
    private static final int ATTENDANCE_FRAGMENT = 2;
    private static final int CONTACT_US = 8;
    private int currentFragment = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.getMenu().getItem(0).setChecked(true);

//        userNameprofile = navigationView.getHeaderView(0).findViewById(R.id.userNameprofile);
//        userProfileImage = navigationView.getHeaderView(0).findViewById(R.id.userProfileImage);
//        userEmailprofile = navigationView.getHeaderView(0).findViewById(R.id.userEmailprofile);

        frameLayout = findViewById(R.id.main_framelayout);
        gotoFragment("OMOTEC", new HomeFragment(), HOME_FRAGMENT);
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
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_notification) {
            return true;
        } else if (id == R.id.action_logout) {
           // FirebaseAuth.getInstance().signOut();
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_Home) {
            gotoFragment("OMOTEC", new HomeFragment(), HOME_FRAGMENT);
        } else if (id == R.id.nav_Schools) {
            gotoFragment("Schools", new SchoolsFragment(), SCHOOLS_FRAGMENT);
        } else if (id == R.id.nav_Attendance) {
            gotoFragment("Attendance", new AttendanceFragment(), ATTENDANCE_FRAGMENT);
        } else if (id == R.id.nav_Summery) {
//3
        } else if (id == R.id.nav_DailyLogs) {
//4
        }
        else if (id == R.id.nav_Planner) {
//5

        } else if (id == R.id.nav_School_Curriculum) {
//6
        }
        else if (id == R.id.nav_AboutUs) {
//7
        }
        else if (id == R.id.nav_ContactUs) {
            gotoFragment("Contact Us", new ContactUsFragment(),CONTACT_US );
        }else if (id == R.id.nav_logOut) {
            // FirebaseAuth.getInstance().signOut();
            finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void gotoFragment(String Title, Fragment fragment, int FragmentNo) {
        invalidateOptionsMenu();
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle(Title);
        setFragment(fragment, FragmentNo);
    }

    private void setFragment(Fragment fragment, int fragmentNo) {
        if (fragmentNo != currentFragment) {
            currentFragment = fragmentNo;
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(frameLayout.getId(), fragment);
            fragmentTransaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
            fragmentTransaction.commit();
        }
    }
}
