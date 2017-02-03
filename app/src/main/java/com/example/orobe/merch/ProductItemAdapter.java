package com.example.orobe.merch;

import Models.Product;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class ProductItemAdapter extends RecyclerView.Adapter<ProductItemAdapter.ViewHolder> {

	private final List<Product> mValues;
	private final OrderDetailsFragment.OnListFragmentInteractionListener mListener;

	public ProductItemAdapter(List<Product> items, OrderDetailsFragment.OnListFragmentInteractionListener listener) {
		mValues = items;
		mListener = listener;
	}

	@Override
	public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_order_prod_details, parent, false);
		return new ViewHolder(view);
	}

	@Override
	public void onBindViewHolder(final ViewHolder holder, int position) {
		holder.mItem = mValues.get(position);
		holder.mId.setText("Id: "+mValues.get(position).getId());
		holder.mContent.setText(mValues.get(position).getName());
		holder.mPrice.setText(mValues.get(position).getPrice()+"$");
		holder.mQuantity.setText(""+mValues.get(position).getQuantity());
	}

	@Override
	public int getItemCount() {
		return mValues.size();
	}

	public class ViewHolder extends RecyclerView.ViewHolder {
		public final View mView;
		public final TextView mId;
		public final TextView mContent;
		public final TextView mPrice;
		public final TextView mQuantity;
		public Product mItem;

		public ViewHolder(View view) {
			super(view);
			mView = view;
			mId = (TextView) view.findViewById(R.id.id);
			mContent = (TextView) view.findViewById(R.id.content);
			mQuantity = (TextView) view.findViewById(R.id.quantity);
			mPrice = (TextView) view.findViewById(R.id.price);
		}
	}
}
