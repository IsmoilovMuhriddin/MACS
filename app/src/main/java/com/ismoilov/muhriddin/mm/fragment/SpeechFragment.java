package com.ismoilov.muhriddin.mm.fragment;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Button;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.ismoilov.muhriddin.mm.R;
import com.ismoilov.muhriddin.mm.activity.CallBackBluetoth;
import com.ismoilov.muhriddin.mm.other.Config;
import com.ismoilov.muhriddin.mm.other.LanguageConfig;
import com.ismoilov.muhriddin.mm.other.ResponceProcessor;
import com.ismoilov.muhriddin.mm.other.TTS;

import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.lang.Runnable;

import ai.api.AIListener;
import ai.api.RequestExtras;
import ai.api.android.AIConfiguration;
import ai.api.android.AIService;
import ai.api.android.GsonFactory;
import ai.api.model.AIContext;
import ai.api.model.AIError;
import ai.api.model.AIResponse;
import ai.api.model.Metadata;
import ai.api.model.ResponseMessage;
import ai.api.model.Result;
import ai.api.model.Status;
import ai.api.ui.AIButton;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SpeechFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SpeechFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SpeechFragment extends Fragment   implements AIListener, AdapterView.OnItemSelectedListener{

    CallBackBluetoth callBackBluetoth;
    public static final String TAG = SpeechFragment.class.getName();
    private LinearLayout containerMSG;
    private AIService aiService;
    private TextView resultTextView;
    private Button micPhone;
    private Gson gson = GsonFactory.getGson();

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        callBackBluetoth = (CallBackBluetoth) activity;

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //returning our layout file
        //change R.layout.yourlayoutfilename for each of your fragments
        View rootView=inflater.inflate(R.layout.fragment_speech, container, false);
       // resultTextView =(TextView) rootView.findViewById(R.id.resultTextForView);
        containerMSG = (LinearLayout) rootView.findViewById(R.id.containerMessages);



        final AIConfiguration config = new AIConfiguration(Config.ACCESS_TOKEN,
                AIConfiguration.SupportedLanguages.English,
                AIConfiguration.RecognitionEngine.System);

        config.setRecognizerStartSound(getResources().openRawResourceFd(R.raw.test_start));
        config.setRecognizerStopSound(getResources().openRawResourceFd(R.raw.test_stop));
        config.setRecognizerCancelSound(getResources().openRawResourceFd(R.raw.test_cancel));
        aiService = AIService.getService(getContext(),config);
        aiService.setListener(this);
        Button listen_button = (Button) rootView.findViewById(R.id.buttonListen);
        listen_button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                aiService.startListening();
            }
        });
        Button stop_button = (Button) rootView.findViewById(R.id.buttonStopListen);
        stop_button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                aiService.stopListening();
            }
        });
        Button cancel_button = (Button) rootView.findViewById(R.id.buttonCancel);
        cancel_button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                aiService.cancel();
            }
        });

        return rootView ;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle("Speech");
    }

    @Override
    public void onResult(final AIResponse response) {
         getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
//                Log.d(TAG, "onResult");
//
//
//                Log.i(TAG, "Received success response");
//
//                // this is example how to get different parts of result object
//                final Status status = response.getStatus();
//                Log.i(TAG, "Status code: " + status.getCode());
//                Log.i(TAG, "Status type: " + status.getErrorType());
//
//                final Result result = response.getResult();
//                Log.i(TAG, "Resolved query: " + result.getResolvedQuery());
//
//                Log.i(TAG, "Action: " + result.getAction());
//                final String speech = result.getFulfillment().getSpeech();
//                Log.i(TAG, "Speech: " + speech);
//                TTS.speak(speech);
//
//                final List<ResponseMessage> messages = result.getFulfillment().getMessages();
//                if(messages != null){
//                    Log.i(TAG,"Messages: ");
//                    for(ResponseMessage elem : messages){
//                        Log.i(TAG,String.format("%s: %s",elem.getClass(),elem));
//
//
//                    }
//                }
//              //  resultTextView.setText(String.format("%s",messages.get(0)));
//                final Metadata metadata = result.getMetadata();
//                if (metadata != null) {
//                    Log.i(TAG, "Intent id: " + metadata.getIntentId());
//                    Log.i(TAG, "Intent name: " + metadata.getIntentName());
//                }
//
//                final HashMap<String, JsonElement> params = result.getParameters();
//                if (params != null && !params.isEmpty()) {
//                    Log.i(TAG, "Parameters: ");
//                    for (final Map.Entry<String, JsonElement> entry : params.entrySet()) {
//                        Log.i(TAG, String.format("%s: %s", entry.getKey(), entry.getValue().toString()));
//                    }
//                }
                    manageResponce(response);
            }

        });
    }

    public  void manageResponce(AIResponse response){
        Log.d(TAG, "onResult");

        //resultTextView.setText(gson.toJson(response));

        Log.i(TAG, "Received success response");

        // this is example how to get different parts of result object
        final Status status = response.getStatus();
        Log.i(TAG, "Status code: " + status.getCode());
        Log.i(TAG, "Status type: " + status.getErrorType());

        final Result result = response.getResult();
        Log.i(TAG, "Resolved query: " + result.getResolvedQuery());

        Log.i(TAG, "Action: " + result.getAction());
        final String speech = result.getFulfillment().getSpeech();
        Log.i(TAG, "Speech: " + speech);
        String userQuery = result.getResolvedQuery();
        // print result in text Speech fragment
        addUserMsg(null,userQuery);

        //***********************************

//          TTS.speak(speech);

        final Metadata metadata = result.getMetadata();
        if (metadata != null) {
            Log.i(TAG, "Intent id: " + metadata.getIntentId());
            Log.i(TAG, "Intent name: " + metadata.getIntentName());
        }

        final HashMap<String, JsonElement> params = result.getParameters();
        if (params != null && !params.isEmpty()) {
            Log.i(TAG, "Parameters: ");
            for (final Map.Entry<String, JsonElement> entry : params.entrySet()) {
                Log.i(TAG, String.format("%s:     %s", entry.getKey(), entry.getValue()));

            }
            int data_to_be_written = ResponceProcessor.getParams(params);
            if (data_to_be_written>0){
                addMacsMsg(speech);
                Character a=(char)data_to_be_written;
                Log.i(TAG,String.format("%d",data_to_be_written));
                callBackBluetoth.writeFromInterface(a.toString());
            }
            else
                addMacsMsg("Please try again command like" +
                        "Open/close Window\n" +
                        "on/off cooler\n" +
                        "set temperature to 25 degree");
            Log.i(TAG,String.format("hey %d",data_to_be_written));
            if(data_to_be_written!=-1) {
                Character a =(char) data_to_be_written;
                callBackBluetoth.writeFromInterface(a.toString());
                Log.i(TAG,String.format("   %c",a));
            }
        }
    }

    public void addUserMsg(View view, String msg){

        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        final View addUserView = inflater.inflate(R.layout.sample_message_user, null);
        TextView txtMsg = (TextView) addUserView.findViewById(R.id.userText);
        txtMsg.setText(msg);

        containerMSG.addView(addUserView,containerMSG.getChildCount()-1);


    }
    public void addMacsMsg(String s){


        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        final View addUserView = inflater.inflate(R.layout.sample_message_mymacs, null);
        TextView txtMsg = (TextView) addUserView.findViewById(R.id.textViewMacs);
        txtMsg.setText(s);

        containerMSG.addView(addUserView,containerMSG.getChildCount()-1);


    }


    @Override
    public void onPause() {
        super.onPause();
        if (aiService != null) {
            aiService.pause();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (aiService != null) {
            aiService.resume();
        }
    }

    @Override
    public void onError(final AIError error) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Log.d(TAG, "onError");
               // resultTextView.setText(error.toString());
            }
        });
    }

    @Override
    public void onAudioLevel(float level) {

    }

    @Override
    public void onListeningStarted() {

    }

    @Override
    public void onListeningCanceled() {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {

              //  resultTextView.setText("");
            }
        });
    }

    @Override
    public void onListeningFinished() {

    }




    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
