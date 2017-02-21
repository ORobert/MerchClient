package com.example.orobe.merch;

import Models.Order;
import Protocol.ProtocolException;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import android.widget.Toast;
import com.example.orobe.merch.DeliveryFragment.OnListFragmentInteractionListener;

import java.text.SimpleDateFormat;
import java.util.List;

public class DeliveryRecyclerAdapter extends RecyclerView.Adapter<DeliveryRecyclerAdapter.ViewHolder> {
	private final List<Order> mValues;
	private final Context context;
	private final RecyclerView view;
	private final DeliveryRecyclerAdapter adapter=this;
	private final OnListFragmentInteractionListener mListener;
	private final SimpleDateFormat sdt=new SimpleDateFormat("dd.MM.yyyy");

	public DeliveryRecyclerAdapter(List<Order> items, OnListFragmentInteractionListener listener, Context context, RecyclerView view) {
		mValues = items;
		this.view=view;
		mListener = listener;
		this.context=context;
	}

	@Override
	public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_todeliverorder, parent, false);
		return new ViewHolder(view);
	}

	@Override
	public void onBindViewHolder(final ViewHolder holder, int position) {
		holder.mItem = mValues.get(position);
		holder.mId.setText("Id:"+mValues.get(position).getId());
		holder.mContent.setText(mValues.get(position).getUsername());
		holder.mDate.setText(sdt.format(mValues.get(position).getDate()));
		holder.mProdNo.setText("Numar de produse: "+mValues.get(position).getProdCount());
		holder.mCity.setText("Oras: "+mValues.get(position).getAddress());
		holder.mButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						switch (which){
							case DialogInterface.BUTTON_POSITIVE:
								try {
									Client.deliverOrder(holder.mItem);
								}catch(ProtocolException e){}
								int position=DriverResources.ordersInTransit.indexOf(holder.mItem);
								synchronized (DriverResources.lock) {
									DriverResources.ordersInTransit.remove(holder.mItem);
								}
								view.removeViewAt(position);
								adapter.notifyItemRemoved(position);
								adapter.notifyItemRangeChanged(position,DriverResources.ordersInTransit.size());
								Toast toast=Toast.makeText(context,"Comanda livrata cu succes!",Toast.LENGTH_LONG);
								toast.show();
								break;
						}
					}
				};
				AlertDialog.Builder builder = new AlertDialog.Builder(context);
				builder.setMessage("Sigur doriti sa marcati comanda ca livrata?").setPositiveButton("DA", dialogClickListener).setNegativeButton("NU", dialogClickListener).show();
				builder.create();
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
		public final TextView mContent;
		public final TextView mDate;
		public final TextView mProdNo;
		public final TextView mCity;
		public final Button mButton;
		public Order mItem;

		public ViewHolder(View view) {
			super(view);
			mView = view;
			mId = (TextView) view.findViewById(R.id.id);
			mContent = (TextView) view.findViewById(R.id.content);
			mDate = (TextView) view.findViewById(R.id.date);
			mProdNo = (TextView) view.findViewById(R.id.prodNo);
			mCity = (TextView) view.findViewById(R.id.city);
			mButton = (Button) view.findViewById(R.id.button);
		}
	}
}
