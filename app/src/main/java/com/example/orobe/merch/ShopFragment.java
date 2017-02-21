package com.example.orobe.merch;

import Models.Product;
import Protocol.ProtocolException;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ShopFragment extends Fragment {

    private static final String ARG_COLUMN_COUNT = "column-count";
    private int mColumnCount = 1;
    private OnListFragmentInteractionListener mListener;
    private List<Product> curentSelectedItems = new ArrayList<>();

    public ShopFragment() {}

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static ShopFragment newInstance(int columnCount) {
        ShopFragment fragment = new ShopFragment();
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
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.shop_layout, container, false);
        Button button = (Button) view.findViewById(R.id.Order);
        RecyclerView recyclerView= (RecyclerView) view.findViewById(R.id.shopList);
        //if (view instanceof RecyclerView) {
            final Context context = view.getContext();
            if (mColumnCount <= 1)
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            else
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            try {
                recyclerView.setAdapter(new ShopAdapter(Client.getAllProducts(),button,getContext(),new ShopAdapter.OnClickListener(){

                    @Override
                    public void onItemCheck(Product product) {
                        curentSelectedItems.add(product);
                    }

                    @Override
                    public void onItemUncheck(Product product) {
                        curentSelectedItems.remove(product);
                    }

                    @Override
                    public void onOrderClick(){
                            if(curentSelectedItems.size()>0) {
								AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
								alertDialog.setMessage("Introduce-ti adresa:");
								final EditText input = new EditText(context);
								LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
								input.setLayoutParams(lp);
								alertDialog.setView(input);
								alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog, int which) {
										try {
											Client.takeUsersOrders(input.getText().toString(),curentSelectedItems);
											Toast.makeText(context, "Comanda efectuata cu succes!", Toast.LENGTH_SHORT).show();
										} catch (ProtocolException e) {
											e.printStackTrace();
										}
									}
								});
								alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
											public void onClick(DialogInterface dialog, int which) {
												dialog.cancel();
											}
								});
								alertDialog.show();
							}else{
								Toast.makeText(context,"Nu ati selectat produse!",Toast.LENGTH_SHORT).show();
							}

                }}));
            } catch (ProtocolException e) {
                e.printStackTrace();
            }
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

    public interface OnListFragmentInteractionListener {
        void onListFragmentInteraction(Product item);
    }
}
