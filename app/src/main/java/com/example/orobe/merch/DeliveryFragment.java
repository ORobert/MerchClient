package com.example.orobe.merch;

import Models.Order;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class DeliveryFragment extends Fragment {
	private static final String ARG_COLUMN_COUNT = "column-count";
	private int mColumnCount = 1;
	public static RecyclerView recyclerView;
	private OnListFragmentInteractionListener mListener;

	public DeliveryFragment() {}

	@SuppressWarnings("unused")
	public static DeliveryFragment newInstance(int columnCount) {
		DeliveryFragment fragment = new DeliveryFragment();
		Bundle args = new Bundle();
		args.putInt(ARG_COLUMN_COUNT, columnCount);
		fragment.setArguments(args);
		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (getArguments() != null)
			mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_delivery_list, container, false);
		if (view instanceof RecyclerView) {
			Context context = view.getContext();
			recyclerView = (RecyclerView) view;
			if (mColumnCount <= 1) {
				recyclerView.setLayoutManager(new LinearLayoutManager(context));
			} else {
				recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
			}
			recyclerView.setAdapter(new DeliveryRecyclerAdapter(DriverResources.ordersInTransit, mListener,getContext(),recyclerView));
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
	}

	public interface OnListFragmentInteractionListener {
		void onListFragmentInteraction(Order item);
	}
}
