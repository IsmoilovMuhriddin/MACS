package com.ismoilov.muhriddin.mm.fragment;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.util.Log;
import com.ismoilov.muhriddin.mm.R;
import com.ismoilov.muhriddin.mm.activity.CallBackBluetoth;
import com.ismoilov.muhriddin.mm.other.ResponceProcessor;

import org.jetbrains.annotations.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;

import android.widget.NumberPicker;
/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SettingsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SettingsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SettingsFragment extends Fragment  {
    CallBackBluetoth callBackBluetoth;
    public static final String TAG = SettingsFragment.class.getName();

    ImageButton CoolerButton,WindowButton;
    Spinner menuMode;
    NumberPicker numberPicker;
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
        View rootView=inflater.inflate(R.layout.fragment_settings, container, false);
        menuMode =(Spinner) rootView.findViewById(R.id.spinnermode);
        numberPicker = (NumberPicker) rootView.findViewById(R.id.numberPicker);
        //min max
        numberPicker.setMinValue(16);
        numberPicker.setMaxValue(32);
        numberPicker.setWrapSelectorWheel(true);

        numberPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal){
                //Display the newly selected number from picker
                Character tem = (char)(newVal+50);
                ResponceProcessor.Target_temp=newVal;
                callBackBluetoth.writeFromInterface(String.format("%c",tem));


            }
        });






        CoolerButton = (ImageButton) rootView.findViewById(R.id.imageButtonCooler);
        WindowButton =(ImageButton) rootView.findViewById(R.id.imageButtonwindow);

        menuMode.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position!=3){


                    CoolerButton.setEnabled(false);
                    WindowButton.setEnabled(false);

                }
                else {
                    //defaultmode =37

                    CoolerButton.setEnabled(true);
                    WindowButton.setEnabled(true);
                }
                Character c=(char)(37+position);
                callBackBluetoth.writeFromInterface(String.format("%c",c));
                getteminalInfo();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

                CoolerButton.setEnabled(true);
                WindowButton.setEnabled(true);
            }
        });

        CoolerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ResponceProcessor.COOLER_STAT==ResponceProcessor.COOLER_OFF)
                {
                    ResponceProcessor.COOLER_STAT=ResponceProcessor.COOLER_ON;

                    Character c=(char)(ResponceProcessor.COOLER_ON);
                    callBackBluetoth.writeFromInterface(String.format("%c",c));
                 }
                else{

                    ResponceProcessor.COOLER_STAT=ResponceProcessor.COOLER_OFF;

                    Character c=(char)(ResponceProcessor.COOLER_OFF);
                    callBackBluetoth.writeFromInterface(String.format("%c",c));

                }
            }
        });
        WindowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ResponceProcessor.WINDOW_STAT==ResponceProcessor.WINDOW_CLOSE){
                    ResponceProcessor.WINDOW_STAT=ResponceProcessor.WINDOW_OPEN;

                    Character c=(char)(ResponceProcessor.WINDOW_OPEN);
                    callBackBluetoth.writeFromInterface(String.format("%c",c));

                }
                else{
                    ResponceProcessor.WINDOW_STAT=ResponceProcessor.WINDOW_CLOSE;

                    Character c=(char)(ResponceProcessor.WINDOW_CLOSE);
                    callBackBluetoth.writeFromInterface(String.format("%c",c));



                }
            }
        });

        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {

                if(ResponceProcessor.COOLER_STAT==ResponceProcessor.COOLER_ON)
                    CoolerButton.setImageResource(R.drawable.fan_propeller_on);

                else
                    CoolerButton.setImageResource(R.drawable.fan_propeller_off);

                if(ResponceProcessor.WINDOW_STAT==ResponceProcessor.WINDOW_OPEN)
                    WindowButton.setImageResource(R.drawable.window_open);
                else WindowButton.setImageResource(R.drawable.window_close);



            }
        });


        return rootView;


    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle("Settings");
    }

    public void getteminalInfo(){
        Character infoget =41; //const for avr we set
        callBackBluetoth.writeFromInterface(String.format("%c",infoget));
    }
}
