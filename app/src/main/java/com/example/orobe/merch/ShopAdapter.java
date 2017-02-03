package com.example.orobe.merch;

import Models.Product;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import java.util.ArrayList;
import java.util.List;


public class ShopAdapter extends RecyclerView.Adapter<ShopAdapter.ViewHolder> {

    interface OnClickListener{
        void onItemCheck(Product product);
        void onItemUncheck(Product product);
        void onOrderClick();
    }

    @NonNull
    private final OnClickListener onClickListener;
    private final List<Product> mValues;
    private final Context context;
    private Button mButton;

    public ShopAdapter(List<Product> items, Button button, Context context, @NonNull OnClickListener onClickListener) {
        mButton=button;
        mValues = items;
        this.onClickListener=onClickListener;
        this.context=context;
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

        holder.mCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                holder.mCheck.setChecked(holder.mCheck.isChecked());
                if (holder.mCheck.isChecked()){
                    int new_quantity=Integer.valueOf(holder.mEditText.getText().toString());
                    int current_quantity=holder.mItem.getQuantity();
                    String name = holder.mItem.getName();
                    if (new_quantity>current_quantity){
                        Toast toast=Toast.makeText(context,"Prea multe " + name + " comandate",Toast.LENGTH_LONG);
                        toast.show();
                    }else{
                        holder.mItem.setQuantity(new_quantity);
                        onClickListener.onItemCheck(holder.mItem);

                    }

                }else{
                    onClickListener.onItemUncheck(holder.mItem);
                }
            }
        });

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickListener.onOrderClick();
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
        public final CheckBox mCheck;
        public final EditText mEditText;
        public Product mItem;


        public ViewHolder(View view) {
            super(view);
            mView = view;
            mContent = (TextView) view.findViewById(R.id.content);
            mCheck = (CheckBox) view.findViewById(R.id.buy_this);
            mEditText = (EditText) view.findViewById(R.id.quantity);
        }
//
//        public void setOnClickListener(View.OnClickListener onClickListener) {
//            itemView.setOnClickListener(onClickListener);
//        }
 //       @Override
//        public String toString() {
//            return super.toString() + " '" + mContentView.getText() + "'";
//        }
    }
}
