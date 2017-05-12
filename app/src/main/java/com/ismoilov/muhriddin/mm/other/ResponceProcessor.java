package com.ismoilov.muhriddin.mm.other;

import android.util.Log;

import com.google.gson.JsonElement;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import  org.json.JSONObject;
/**
 * Created by Muhriddin on 10-May-17.
 */
public abstract class ResponceProcessor {

    public static final String TAG = ResponceProcessor.class.getName();
    public static  int offset =50;
    public static int Temp_Min = 16;
    public static int Temp_Max = 32;
    public static int COOLER_OFF = 33;
    public static int COOLER_ON = 34;
    public static int WINDOW_CLOSE =35;
    public static int WINDOW_OPEN = 36;

    public static int MODE_DEFAULT = 37;
    public static int MODE_WINDOW = 38;
    public static int MODE_COOLER =39;
    public static int MODE_MANUAL = 40;
    public static HashMap<String,Integer> modeMenu =new HashMap<String, Integer>();



    public static int WINDOW_STAT;
    public static int COOLER_STAT; //
    public static int MODE_STAT;
    public static int Target_temp=20;
    public static int Current_outSide_temp=22;
    public static int Current_Inside_temp=21;



    public static int getParams( HashMap<String, JsonElement> params){

        if (params != null && !params.isEmpty()) {
            for (final Map.Entry<String, JsonElement> entry : params.entrySet()) {
                Log.i(TAG,String.format("%s -----%s",entry.getKey().toString(),entry.getValue().toString()));

                if(entry.getKey().toString().equals("openfan"))
                     return getCooler(entry.getValue());
                 else if(entry.getKey().toString().equals("openWindow")){

                     Log.i(TAG,String.format("getWindow %s","get it"));
                     return getWindow(entry.getValue());
                 }
                 else if(entry.getKey().toString().equals("temperature"))

                     return getTemprature(entry.getValue());


            }
        }
        return -5;
    }

    public static void getTerminal(String x){
        /*
        * receiving string about terminal info
        * MCWTTIIOO
        * M Mode
        * C Cooler stat
        * W Win stat
        * TT Target temp
        * II in temp
        * OO out temp
        *
        * */

        switch (x.charAt(0)-'0') {
            case 0:   MODE_STAT = MODE_DEFAULT;
                break;
            case 1: MODE_STAT=MODE_COOLER;
                break;
            case 2:MODE_STAT=MODE_WINDOW;
                break;
            case 3:
                MODE_STAT=MODE_MANUAL;
                break;
            default:break;

        }

        if(x.charAt(1)-'0'==0)
            COOLER_STAT=COOLER_OFF;
        else if(x.charAt(1)-'0'==1)
            COOLER_STAT=COOLER_ON;

        if(x.charAt(2)-'0'==0)
            WINDOW_STAT=WINDOW_CLOSE;
        else if(x.charAt(2)-'0'==1)
            WINDOW_STAT=WINDOW_OPEN;

        int tr_temp = (x.charAt(3)-'0')*10+(x.charAt(4)-'0');
        if(tr_temp>=Temp_Min&&tr_temp<=Temp_Max)
            Target_temp=tr_temp;

        int in_temp = (x.charAt(5)-'0')*10+(x.charAt(6)-'0');
        Current_Inside_temp=in_temp;

        int out_temp =(x.charAt(7)-'0')*10+(x.charAt(8)-'0');
        Current_outSide_temp=out_temp;
        Log.i("responce Process",x);

    }

    public static int getCooler(JsonElement x){
        Log.i(TAG,String.format("getCooler %s",x));
        if(x.getAsString().equals("on"))
            return COOLER_ON;
        else if (x.getAsString().equals("off"))
            return COOLER_OFF;
        else return -4;

    }

    public static int getWindow(JsonElement x){

        Log.i(TAG,String.format("getWindow %s",x));

        if(x.getAsString().equals("open"))
            return  WINDOW_OPEN;

        else if (x.getAsString().equals("close"))return WINDOW_CLOSE;

        else return -3;

    }


    public static int getTemprature(JsonElement temp)  {


        int Temp =20;



       if (Temp>15&&Temp<33)
        return Temp;
        else return -2;
    }

}
