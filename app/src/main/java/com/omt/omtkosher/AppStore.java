package com.omt.omtkosher;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.core.view.MenuItemCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.app.Dialog;
import android.app.SearchManager;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import java.util.Objects;


public class AppStore extends AppCompatActivity {

    DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_store);


        getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);

        ImageView draw_btn = findViewById(R.id.draw_btn);

        NavHostFragment navHostFragment =
                (NavHostFragment) getSupportFragmentManager()
                        .findFragmentById(R.id.nav_host);
        assert navHostFragment != null;
        NavController navController = navHostFragment.getNavController();



         drawerLayout=findViewById(R.id.drawer_layout);

        NavigationView navigationView=findViewById(R.id.nav_view);

        View headerView = navigationView.getHeaderView(0);
        LinearLayout supportl = (LinearLayout) headerView.findViewById(R.id.support_ll);

        Dialog dialog = new Dialog(AppStore.this);



        CardView main = findViewById(R.id.main_cv);
        LinearLayout newl = findViewById(R.id.newll);
        LinearLayout mainl = findViewById(R.id.mainll);
        LinearLayout catl = findViewById(R.id.catll);

        main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newl.setBackground(ContextCompat.getDrawable(AppStore.this, R.drawable.white_border));
                catl.setBackground(ContextCompat.getDrawable(AppStore.this, R.drawable.white_border));
                mainl.setBackground(ContextCompat.getDrawable(AppStore.this, R.drawable.appstore_border));
                navController.navigate(R.id.displayFragment);
            }
        });

        catl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newl.setBackground(ContextCompat.getDrawable(AppStore.this, R.drawable.white_border));
                mainl.setBackground(ContextCompat.getDrawable(AppStore.this, R.drawable.white_border));
                catl.setBackground(ContextCompat.getDrawable(AppStore.this, R.drawable.appstore_border));
                navController.navigate(R.id.categoriesFragment);
            }
        });

        newl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newl.setBackground(ContextCompat.getDrawable(AppStore.this, R.drawable.appstore_border));
                catl.setBackground(ContextCompat.getDrawable(AppStore.this, R.drawable.white_border));
                mainl.setBackground(ContextCompat.getDrawable(AppStore.this, R.drawable.white_border));
                navController.navigate(R.id.displayFragment);
            }
        });





        navigationView.bringToFront();


        navigationView.setNavigationItemSelectedListener(this::OnNavigationItemSelected);

        draw_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });

        supportl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.closeDrawer(GravityCompat.START);
                dialog.setContentView(R.layout.support_dialog);
                dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                dialog.setCancelable(false);
                dialog.getWindow().getAttributes().windowAnimations = R.style.animation;

                LinearLayout close = dialog.findViewById(R.id.close_ll);


                close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();

                    }
                });

                dialog.show();
            }
        });



    }

    public boolean OnNavigationItemSelected(@NonNull MenuItem menuItem) {
        String title = getString(R.string.app_name);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(title);
        }

        drawerLayout.closeDrawer(GravityCompat.START);

        return true;
    }

    @Override
    public void onBackPressed(){
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else
        {
            super.onBackPressed();
            //finish();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.custom_menu,menu);


        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.nav_search));
        SearchManager searchManager = (SearchManager) getSystemService(SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.nav_search){
            Toast.makeText(this, "Click Search Icon.", Toast.LENGTH_SHORT).show();
        }

        return super.onOptionsItemSelected(item);
    }
}