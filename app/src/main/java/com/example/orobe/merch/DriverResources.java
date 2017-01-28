package com.example.orobe.merch;

import Models.Order;
import Models.User;
import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.SystemClock;
import android.support.v4.app.ActivityCompat;

import java.util.List;
import java.util.concurrent.Executor;

/**
 * Created by Sergiu on 27-Jan-17.
 */
public class DriverResources{
	public static List<Order> selected;
	public static List<Order> ordersInTransit;
	public static List<Order> orders;
	public static User user;
	public final static Object lock = new Object();

	private DriverResources() {
	}

	public static void startUpdaterThread(Activity act){
		(new UpdaterThread()).execute(act);
	}

	private static class UpdaterThread extends AsyncTask<Activity, Void, Void> {
		@Override
		protected Void doInBackground(Activity... params) {
			LocationManager locationManager = (LocationManager) params[0].getSystemService(Context.LOCATION_SERVICE);
			params[0].requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION},1337);
			//LocationListener locationListener = new MyLocationListener();
			//locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 10, locationListener);
			while (true) {
				Location loc = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
				if (loc != null) {
					synchronized (lock) {
						Client.updateOrderLocation(ordersInTransit, loc.getLongitude(), loc.getLatitude());
					}
				}
				SystemClock.sleep(5000);
			}
		}
	}
}
