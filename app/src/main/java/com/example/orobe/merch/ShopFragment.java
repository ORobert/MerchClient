package com.example.orobe.merch;

import Models.Product;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ShopFragment extends Fragment {
    private List<Product> prods;
    //    IRepository repository;

    public ShopFragment(){}

    public ShopFragment(List<Product> list) {
//      repository = new ShopRepository();
        prods=list;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

//        ((ShopRepository) repository).seed("products.xml");
//        List<Products> products= repository.getAll();
//        ListView listView = (ListView)getActivity().findViewById(R.id.products_list);
//        ArrayAdapter<Products> itemsAdapter = new ArrayAdapter<Products>(getActivity(),android.R.layout.simple_list_item_1,products);
//        listView.setAdapter(itemsAdapter);
        return inflater.inflate(R.layout.fragment_shop, container, false);

    }


}
