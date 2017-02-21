package com.example.orobe.merch;

import Models.Order;
import Models.Product;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

public class MainMenuActivity extends AppCompatActivity implements ClientOrderFragment.OnListFragmentInteractionListener,NavigationView.OnNavigationItemSelectedListener,OrderDetailsFragment.OnListFragmentInteractionListener	,ShopFragment.OnListFragmentInteractionListener {
    private Fragment currentFrag=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

		ClientOrderFragment orderFragment = new ClientOrderFragment();
		currentFrag=orderFragment;
		FragmentManager fragmentManager = getSupportFragmentManager();
		fragmentManager.beginTransaction().add(R.id.content_main_menu,orderFragment).addToBackStack(null).commit();
    }

    @Override
    public void onBackPressed() {
//        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        if (drawer.isDrawerOpen(GravityCompat.START)) {
//            drawer.closeDrawer(GravityCompat.START);
//        } else {
//            super.onBackPressed();
//        }
    }

    @Override
    public void onListFragmentInteraction(Product item) {}

    public Fragment getCurrentFrag(){
    	return currentFrag;
	}

	public void setCurrentFrag(Fragment currentFrag) {
		this.currentFrag = currentFrag;
	}

	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings)
            return true;
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.nav_shop) {
            removeCurrentFragment(currentFrag);
            ShopFragment shopFragment = new ShopFragment();
            currentFrag=shopFragment;
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().add(R.id.content_main_menu,shopFragment).addToBackStack(null).commit();
        } else if (id == R.id.nav_orders) {
            removeCurrentFragment(currentFrag);
            ClientOrderFragment orderFragment = new ClientOrderFragment();
            currentFrag=orderFragment;
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().add(R.id.content_main_menu,orderFragment).addToBackStack(null).commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

	@Override
	public void onListFragmentInteraction(Order order) {}

	public void removeCurrentFragment(Fragment currentFrag){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (currentFrag!=null)
            transaction.remove(currentFrag).commit();
    }
}
