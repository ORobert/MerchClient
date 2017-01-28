package com.example.orobe.merch;

import Models.Order;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class OrderRecyclerAdapter extends RecyclerView.Adapter<OrderRecyclerAdapter.ViewHolder> {

	private final List<Order> mValues;
	private final DOrderFragment.OnListFragmentInteractionListener mListener;
	private final SimpleDateFormat sdt=new SimpleDateFormat("dd.MM.yyyy");

	public OrderRecyclerAdapter(List<Order> items, DOrderFragment.OnListFragmentInteractionListener listener) {
		DriverResources.selected=new ArrayList<>();
		mValues = items;
		mListener = listener;
	}

	@Override
	public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_driverorder, parent, false);
		return new ViewHolder(view);
	}

	@Override
	public void onBindViewHolder(final ViewHolder holder, final int position) {
		holder.mItem = mValues.get(position);
		holder.mId.setText("Id: "+mValues.get(position).getId());
		holder.mDate.setText(sdt.format(mValues.get(position).getDate()));
		holder.mProdNum.setText("Numar de produse: "+mValues.get(position).getProdCount());
		holder.mUsername.setText(mValues.get(position).getUsername());
		holder.mCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton button, boolean isChecked) {
				if (isChecked){
					DriverResources.selected.add(mValues.get(position));
				}else{
					DriverResources.selected.remove(mValues.get(position));
				}
			}

		});
		/*holder.mView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (null != mListener)
					mListener.onListFragmentInteraction(holder.mItem);
			}
		});*/
	}

	@Override
	public int getItemCount() {
		return mValues.size();
	}

	public class ViewHolder extends RecyclerView.ViewHolder {
		public final View mView;
		public final TextView mId;
		public final TextView mUsername;
		public final TextView mProdNum;
		public final TextView mDate;
		public final CheckBox mCheck;
		public Order mItem;

		public ViewHolder(View view) {
			super(view);
			mView = view;
			mId = (TextView) view.findViewById(R.id.id);
			mUsername=(TextView) view.findViewById(R.id.content);
			mProdNum=(TextView) view.findViewById(R.id.prodNo);
			mDate=(TextView) view.findViewById(R.id.date);
			mCheck=(CheckBox) view.findViewById(R.id.check);
		}
	}
}
