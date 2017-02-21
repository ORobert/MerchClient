package com.example.orobe.merch;

import Models.Order;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class ClientOrderAdapter extends RecyclerView.Adapter<ClientOrderAdapter.ViewHolder> {

	private final List<Order> mValues;
	private final ClientOrderFragment.OnListFragmentInteractionListener mListener;
	private final SimpleDateFormat sdt=new SimpleDateFormat("dd.MM.yyyy");
	private final FragmentManager manager;
	private final MainMenuActivity act;

	public ClientOrderAdapter(List<Order> items, ClientOrderFragment.OnListFragmentInteractionListener listener, FragmentManager mng, MainMenuActivity act) {
		DriverResources.selected=new ArrayList<>();
		mValues = items;
		mListener = listener;
		manager=mng;
		this.act=act;
	}

	@Override
	public ClientOrderAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_client_order, parent, false);
		return new ClientOrderAdapter.ViewHolder(view);
	}

	@Override
	public void onBindViewHolder(final ClientOrderAdapter.ViewHolder holder, final int position) {
		holder.mItem = mValues.get(position);
		holder.mId.setText("Id: "+mValues.get(position).getId());
		holder.mDate.setText(sdt.format(mValues.get(position).getDate()));
		holder.mProdNum.setText("Nr. de produse: "+mValues.get(position).getProdCount());
		switch (mValues.get(position).getState()){
			case("Confirmed"):
				holder.mStatus.setText("Confirmat");
				break;
			case("ToBeDelivered"):
				holder.mStatus.setText("Spre Livrare");
				break;
			case("Delivered"):
				holder.mStatus.setText("Livrat");
				break;
		}
		holder.mCity.setText("Oras: "+mValues.get(position).getAddress());
		holder.mButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				FragmentTransaction transaction = manager.beginTransaction();
				transaction.remove(act.getCurrentFrag()).commit();
				OrderDetailsFragment detailsFragment = new OrderDetailsFragment();
				Bundle bundle = new Bundle();
				bundle.putSerializable("order", mValues.get(position));
				detailsFragment.setArguments(bundle);
				act.setCurrentFrag(detailsFragment);
				manager.beginTransaction().add(R.id.content_main_menu,detailsFragment).addToBackStack(null).commit();
			}
		});
	}

	@Override
	public int getItemCount() {
		return mValues.size();
	}

	public class ViewHolder extends RecyclerView.ViewHolder {
		public final View mView;
		public final TextView mId;
		public final TextView mStatus;
		public final TextView mProdNum;
		public final TextView mDate;
		public final Button mButton;
		public final TextView mCity;
		public Order mItem;

		public ViewHolder(View view) {
			super(view);
			mView = view;
			mId = (TextView) view.findViewById(R.id.id);
			mStatus=(TextView) view.findViewById(R.id.status);
			mProdNum=(TextView) view.findViewById(R.id.prodNo);
			mCity=(TextView) view.findViewById(R.id.city);
			mDate=(TextView) view.findViewById(R.id.date);
			mButton=(Button) view.findViewById(R.id.button);
		}
	}
}
