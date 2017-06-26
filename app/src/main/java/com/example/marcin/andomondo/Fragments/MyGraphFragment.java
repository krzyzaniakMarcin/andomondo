package com.example.marcin.andomondo.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.marcin.andomondo.Main;
import com.example.marcin.andomondo.R;
import com.jjoe64.graphview.GraphView;

/**
 * Created by Marcin on 6/15/2017.
 */

public class MyGraphFragment extends Fragment {

    int mNum;

    public static Fragment newInstance() {
        Fragment f = new MyGraphFragment();

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
        View v = inflater.inflate(R.layout.graph_fragment_layout, container, false);

        Main.height_graph = (GraphView) v.findViewById(R.id.height_graph);
        Main.speed_graph = (GraphView) v.findViewById(R.id.speed_graph);
        Main.height_graph.getGridLabelRenderer().setVerticalAxisTitle("Height");
        Main.speed_graph.getGridLabelRenderer().setVerticalAxisTitle("Speed");

        Main.height_graph.getGridLabelRenderer().setGridColor(getResources().getColor(R.color.text));
        Main.height_graph.getGridLabelRenderer().setHorizontalLabelsColor(getResources().getColor(R.color.text));
        Main.height_graph.getGridLabelRenderer().setVerticalLabelsColor(getResources().getColor(R.color.text));
        Main.height_graph.getGridLabelRenderer().setVerticalAxisTitleColor(getResources().getColor(R.color.text));

        Main.speed_graph.getGridLabelRenderer().setGridColor(getResources().getColor(R.color.text));
        Main.speed_graph.getGridLabelRenderer().setHorizontalLabelsColor(getResources().getColor(R.color.text));
        Main.speed_graph.getGridLabelRenderer().setVerticalLabelsColor(getResources().getColor(R.color.text));
        Main.speed_graph.getGridLabelRenderer().setVerticalAxisTitleColor(getResources().getColor(R.color.text));


        return v;
    }

}

