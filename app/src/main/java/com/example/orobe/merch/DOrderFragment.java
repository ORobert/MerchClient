package com.example.orobe.merch;

import Models.Order;
import Protocol.ProtocolException;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class DOrderFragment extends Fragment{
	private static final String ARG_COLUMN_COUNT = "column-count";
	private int mColumnCount = 1;
	private OnListFragmentInteractionListener mListener;

	public DOrderFragment() {}

	public static DOrderFragment newInstance(int columnCount) {
		DOrderFragment fragment = new DOrderFragment();
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
		View view = inflater.inflate(R.layout.driver_order_list, container, false);
			Context context = view.getContext();
			final RecyclerView recyclerView=(RecyclerView)view.findViewById(R.id.list);
			if (mColumnCount <= 1)
				recyclerView.setLayoutManager(new LinearLayoutManager(context));
			else
				recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
			final OrderRecyclerAdapter adapter=new OrderRecyclerAdapter
					(DriverResources.orders, mListener);
			Button button=(Button)view.findViewById(R.id.button);
			button.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					for (Order order:DriverResources.selected) {
						order.setState("ToBeDelivered");
						order.setDriverId(DriverResources.user.getId());
						int position=DriverResources.orders.indexOf(order);
						DriverResources.orders.remove(position);
						recyclerView.removeViewAt(position);
						adapter.notifyItemRemoved(position);
					}
					synchronized (DriverResources.lock) {
						DriverResources.ordersInTransit.addAll(DriverResources.selected);
					}
					adapter.notifyDataSetChanged();
					DeliveryFragment.recyclerView.getAdapter().notifyDataSetChanged();
					try {
						Client.takeOrders(DriverResources.selected);
						DriverResources.selected.clear();
					}catch(ProtocolException e){}
				}
			});
			recyclerView.setAdapter(adapter);
		return view;
	}

	@Override
	public void onAttach(Context context) {
		super.onAttach(context);
		if (context instanceof OnListFragmentInteractionListener) {
			mListener = (OnListFragmentInteractionListener) context;
		} else {
			throw new RuntimeException(context.toString() + " must implement OnListFragmentInteractionListener");
		}
	}

	@Override
	public void onDetach() {
		super.onDetach();
		mListener = null;
	}

	public interface OnListFragmentInteractionListener{
		void onListFragmentInteraction(Order item);
	}
}
