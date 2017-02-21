package com.example.orobe.merch;

import Models.Order;
import Protocol.ProtocolException;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.google.android.gms.maps.*;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.text.SimpleDateFormat;

public class OrderDetailsFragment extends Fragment implements OnMapReadyCallback {
	private static final String ARG_COLUMN_COUNT = "column-count";
	private int mColumnCount = 1;
	private Order order;
	private OnListFragmentInteractionListener mListener;
	private MapView mapView;
	private Marker positionMarker;
	private GoogleMap map;
	private AsyncTask<Object,Double,Void> obj;

	public OrderDetailsFragment() {}

	@SuppressWarnings("unused")
	public static OrderDetailsFragment newInstance(int columnCount) {
		OrderDetailsFragment fragment = new OrderDetailsFragment();
		Bundle args = new Bundle();
		args.putInt(ARG_COLUMN_COUNT, columnCount);
		fragment.setArguments(args);
		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (getArguments() != null) {
			mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
			order= (Order) getArguments().getSerializable("order");
		}
	}

	@Override
	public void onMapReady(GoogleMap googleMap) {
		map=googleMap;
		map.getUiSettings().setMapToolbarEnabled(true);
		map.getUiSettings().setZoomControlsEnabled(true);
		map.setMaxZoomPreference(16);
		map.setMinZoomPreference(14);
		map.animateCamera(CameraUpdateFactory.zoomTo(14));
		MarkerOptions markerOptions = new MarkerOptions();
		markerOptions.title("Curier");
		LatLng coord=order.getLatitude()!=null?new LatLng(order.getLatitude(),order.getLongitude()):new LatLng(45.9432,24.9668);
		markerOptions.position(coord);
		positionMarker = map.addMarker(markerOptions);
		positionMarker.setDraggable(false);
		positionMarker.showInfoWindow();
		map.animateCamera(CameraUpdateFactory.newLatLng(coord));
		obj=Client.startUpdaterThread(this,order);
	}

	public void updateMapPosition(double latitude, double longitude){
		LatLng coord=new LatLng(latitude,longitude);
		positionMarker.setPosition(coord);
		map.animateCamera(CameraUpdateFactory.newLatLng(coord));
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		SimpleDateFormat sdf=new SimpleDateFormat("dd.MM.yyyy");
		final View view = inflater.inflate(R.layout.fragment_order_details, container, false);
		((TextView)view.findViewById(R.id.prodNo)).setText(""+order.getProdCount());
		((TextView)view.findViewById(R.id.orderId)).setText("Detalii comanda nr.: #"+order.getId());
		((TextView)view.findViewById(R.id.address)).setText(order.getAddress());
		((TextView)view.findViewById(R.id.date)).setText(sdf.format(order.getDate()));
		TextView status= (TextView) view.findViewById(R.id.status);
		TextView driver= (TextView) view.findViewById(R.id.driver);
		if(order.getDriver()!=null)
			driver.setText(order.getDriver());
		else
			driver.setText("N/A");
		switch (order.getState()){
			case("Confirmed"):
				status.setText("Confirmata");
				break;
			case("ToBeDelivered"):
				status.setText("In curs de livrare");
				break;
			case("Delivered"):
				status.setText("Livrata");
				break;
		}
		final Button prodButton= (Button) view.findViewById(R.id.productButton);
		prodButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				RelativeLayout lView= (RelativeLayout) view.findViewById(R.id.listContainer);
				if(lView.getVisibility()==View.GONE){
					prodButton.setText("Ascunde produse!");
					lView.setVisibility(View.VISIBLE);
				}else{
					prodButton.setText("Arata produse!");
					lView.setVisibility(View.GONE);
				}
			}
		});
		final Button mapButton= (Button) view.findViewById(R.id.mapButton);
		mapView= (MapView) view.findViewById(R.id.mapView);
		mapView.onCreate(savedInstanceState);
		mapView.getMapAsync(this);
		MapsInitializer.initialize(this.getActivity());
		final OrderDetailsFragment frag=this;
		if(order.getState().equals("ToBeDelivered")) {
			mapButton.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					if(mapView.getVisibility()==View.VISIBLE){
						obj.cancel(false);
						mapButton.setText("Arata Harta!");
						mapView.setVisibility(View.GONE);
					}else{
						if(obj!=null && obj.getStatus()== AsyncTask.Status.FINISHED){
							obj=Client.startUpdaterThread(frag,order);
						}
						mapButton.setText("Ascunde Harta!");
						mapView.setVisibility(View.VISIBLE);
					}
				}
			});
		}else{
			mapButton.setEnabled(false);
		}
		RecyclerView recyclerView= (RecyclerView) view.findViewById(R.id.list);
		Context context = view.getContext();
		if (mColumnCount <= 1)
			recyclerView.setLayoutManager(new LinearLayoutManager(context));
		else
			recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
		try {
			recyclerView.setAdapter(new ProductItemAdapter(Client.getProductsByOrder(order), mListener));
		}catch (ProtocolException e){
			e.printStackTrace();
		}
		return view;
	}

	@Override
	public void onAttach(Context context) {
		super.onAttach(context);
		if (context instanceof OnListFragmentInteractionListener)
			mListener = (OnListFragmentInteractionListener) context;
		else
			throw new RuntimeException(context.toString() + " must implement OnListFragmentInteractionListener");
	}

	@Override
	public void onDetach() {
		super.onDetach();
		mListener = null;
		obj.cancel(true);
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		obj.cancel(true);
	}

	@Override
	public void onResume() {
		super.onResume();
		mapView.onResume();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		mapView.onDestroy();
		obj.cancel(true);
	}

	@Override
	public void onLowMemory() {
		super.onLowMemory();
		mapView.onLowMemory();
	}

	public interface OnListFragmentInteractionListener {
		//void onListFragmentInteraction(DummyItem item);
	}
}
