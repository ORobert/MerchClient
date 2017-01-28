package com.example.orobe.merch;

import Models.Order;
import Models.User;
import Protocol.ProtocolException;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

public class DriverOrderActivity extends AppCompatActivity implements DOrderFragment.OnListFragmentInteractionListener, DeliveryFragment.OnListFragmentInteractionListener {
	@Override
	public void onListFragmentInteraction(Order item) {}
	/**We use a {@link FragmentPagerAdapter} derivative, which will keep every
	 * loaded fragment in memory. If this becomes too memory intensive, it
	 * may be best to switch to a
	 * {@link android.support.v4.app.FragmentStatePagerAdapter}.
	 */
	private SectionsPagerAdapter mSectionsPagerAdapter;
	/**The {@link ViewPager} that will host the section contents.
	 */
	private ViewPager mViewPager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Intent intent=this.getIntent();
		try {
			DriverResources.user = (User) intent.getSerializableExtra("user");
			DriverResources.orders = Client.getConfirmedOrders();
			DriverResources.ordersInTransit = Client.getOrdersByDriver(DriverResources.user);
			DriverResources.startUpdaterThread(this);
		}catch(ProtocolException e){}
		setContentView(R.layout.activity_driver_order);

		//Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		//setSupportActionBar(toolbar);
		mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

		mViewPager = (ViewPager) findViewById(R.id.container);
		mViewPager.setAdapter(mSectionsPagerAdapter);

		TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
		tabLayout.setupWithViewPager(mViewPager);

		RecyclerView rvContacts = (RecyclerView) findViewById(R.id.list);
		try {
			OrderRecyclerAdapter adapter = new OrderRecyclerAdapter(DriverResources.orders, null);
			rvContacts.setAdapter(adapter);
		}catch(Exception e){}
		rvContacts = (RecyclerView) findViewById(R.id.list_delivery);
		try {
			DeliveryRecyclerAdapter adapter = new DeliveryRecyclerAdapter(DriverResources.ordersInTransit, null,getApplicationContext(),rvContacts);
			rvContacts.setAdapter(adapter);
		}catch(Exception e){}
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu_driver_order, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.action_settings)
			return true;
		return super.onOptionsItemSelected(item);
	}

	public static class PlaceholderFragment extends Fragment {
		private static final String ARG_SECTION_NUMBER = "section_number";
		public PlaceholderFragment() {}
		/**
		 * Returns a new instance of this fragment for the given section
		 * number.
		 */
		public static PlaceholderFragment newInstance(int sectionNumber) {
			PlaceholderFragment fragment = new PlaceholderFragment();
			Bundle args = new Bundle();
			args.putInt(ARG_SECTION_NUMBER, sectionNumber);
			fragment.setArguments(args);
			return fragment;
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_driver_order, container, false);
			TextView textView = (TextView) rootView.findViewById(R.id.section_label);
			textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
			return rootView;
		}
	}

	/**
	 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
	 * one of the sections/tabs/pages.
	 */
	public class SectionsPagerAdapter extends FragmentPagerAdapter {
		public SectionsPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			// getItem is called to instantiate the fragment for the given page.
			// Return a PlaceholderFragment (defined as a static inner class below).
			if(position==0){
				return new DOrderFragment();
			}
			if(position==1){
				return new DeliveryFragment();
			}
			return PlaceholderFragment.newInstance(position + 1);
		}

		@Override
		public int getCount() {
			return 2;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			switch (position) {
				case 0:
					return "Comenzi nelivrate";
				case 1:
					return "Comenzi spre livrare";
			}
			return null;
		}
	}
}
