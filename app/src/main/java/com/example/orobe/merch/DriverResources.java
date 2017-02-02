package com.example.orobe.merch;

import Models.Order;
import Models.User;
import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.res.AssetManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.SystemClock;

import java.io.*;
import java.util.List;

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

	public static void startRealUpdaterThread(Activity act){
		(new RealUpdaterThread()).execute(act);
	}

	public static void startFakeUpdaterThread(Activity act, AssetManager manager){
		(new FakeUpdaterThread()).executeOnExecutor(Client.exec,act,manager);
	}

	private static class RealUpdaterThread extends AsyncTask<Activity, Void, Void> {
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

	private static class FakeUpdaterThread extends AsyncTask<Object, Void, Void> {
		@Override
		protected Void doInBackground(Object... params) {
			BufferedReader reader;
			String line;
			String[] tokens;
			try {
				AssetManager manager=(AssetManager)params[1];
				reader = new BufferedReader(new InputStreamReader(manager.open("gps.gps")));
				line=reader.readLine();
			}catch(IOException e){
				e.printStackTrace();
				this.cancel(false);
				return null;
			}
			while (line!=null) {
				tokens=line.split("\t");
				synchronized (lock) {
					if(ordersInTransit.size()>0) {
						Client.updateOrderLocation(ordersInTransit, Float.parseFloat(tokens[2]), Float.parseFloat(tokens[1]));
					}
				}
				SystemClock.sleep(5000);
				try {
					line = reader.readLine();
				}catch (IOException e){
					e.printStackTrace();
				}
			}
			return null;
		}
	}
}
