package com.ismoilov.muhriddin.mm.fragment;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ismoilov.muhriddin.mm.R;
import com.ismoilov.muhriddin.mm.activity.CallBackBluetoth;
import com.ismoilov.muhriddin.mm.other.ResponceProcessor;

import org.jetbrains.annotations.Nullable;

import android.widget.TextView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link HomeFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {
    TextView infoBluetooth;
    CallBackBluetoth callBackBluetoth;
    TextView innerTemp,outerTemp,targetTemp ,CurrentMode;
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        callBackBluetoth = (CallBackBluetoth) activity;

    }

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //returning our layout file
        //change R.layout.yourlayoutfilename for each of your fragments
         View rootview =inflater.inflate(R.layout.fragment_home, container, false);


        innerTemp =(TextView) rootview.findViewById(R.id.innerTemp);
        outerTemp =(TextView) rootview.findViewById(R.id.outerTemp);
        targetTemp=(TextView) rootview.findViewById(R.id.targetTemp);
        CurrentMode=(TextView) rootview.findViewById(R.id.CurrentMode);





        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                int intemp = ResponceProcessor.Current_Inside_temp;
                innerTemp.setText(String.format("%d C",intemp));
                int outtemp=ResponceProcessor.Current_outSide_temp;
                outerTemp.setText(String.format("%d C",outtemp));
                int targtemp = ResponceProcessor.Target_temp;
                targetTemp.setText(String.format("%d C",targtemp));


            }
        });


        return rootview;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle("Home");
    }
   }
