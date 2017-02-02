package com.example.orobe.merch;

import Models.Product;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class ShopAdapter extends RecyclerView.Adapter<ShopAdapter.ViewHolder> {

    private final List<Product> mValues;
    private final ShopFragment.OnListFragmentInteractionListener mListener;
    private final ArrayList<Integer> itemsCheckd;

    public ShopAdapter(List<Product> items, ShopFragment.OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
        itemsCheckd = new ArrayList<>();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.shop_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mContent.setText(mValues.get(position).toString());

        //holder.mIdView.setText(mValues.get(position).id);
        //holder.mContentView.setText(mValues.get(position).content);

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                   // mListener.onListFragmentInteraction(holder.mItem);
                    holder.mCheck.setChecked(holder.mCheck.isChecked());
                    if (holder.mCheck.isChecked()){
//                        itemsCheckd.add();
                    }else{

                    }

                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mContent;
        public final Button mButton;
        public final CheckBox mCheck;
        public Product mItem;


        public ViewHolder(View view) {
            super(view);
            mView = view;
            mContent = (TextView) view.findViewById(R.id.content);
            mButton= (Button) view.findViewById(R.id.Order);
            mCheck = (CheckBox) view.findViewById(R.id.buy_this);
        }

 //       @Override
//        public String toString() {
//            return super.toString() + " '" + mContentView.getText() + "'";
//        }
    }
}
