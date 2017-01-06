package com.example.orobe.merch;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ShopFragment extends Fragment {
//    IRepository repository;

    public ShopFragment() {
//      repository = new ShopRepository();
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
