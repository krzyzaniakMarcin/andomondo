package com.example.marcin.andomondo.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.marcin.andomondo.Main;
import com.example.marcin.andomondo.R;
import com.facebook.share.widget.ShareButton;


/**
 * Created by Marcin on 6/26/2017.
 */

public class MyFacebookFragment extends Fragment {

    int mNum;

    public static Fragment newInstance() {
        Fragment f = new MyFacebookFragment();
        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mNum = getArguments() != null ? getArguments().getInt("num") : 1;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.facebook_fragment_layout, container, false);
        Main.facebookButton = (ShareButton) v.findViewById(R.id.shareButton);
        Main.allCalories = (TextView) v.findViewById(R.id.allCalories);
        Main.allCalories.setText((Main.allTimeCalories+Main.calories)+ " kalc");

        Main.allCalories.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Main.allTimeCalories=0;
                Main.allCalories.setText(Main.calories+" kalc");
                return false;
            }
        });

        return v;
    }


}
