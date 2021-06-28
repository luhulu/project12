package come.example.a12_meridian;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

import javax.xml.transform.sax.TemplatesHandler;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Fbattery_power#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fbattery_power extends Fragment {
    private Timer Ftimer_battery;
    private TimerTask FtimerTask_battery;
    public  TextView TV_battery_data;
    public ImageView imageView_wifi;
    public int wifi_data=5;
    private MainActivity mainActivity;
    private  int battery_power_data=0;
    public Handler handler;
    private Runnable runnable;
    private Button sign_out;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Fbattery_power() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Fbattery_power.
     */
    // TODO: Rename and change types and number of parameters
    public static Fbattery_power newInstance(String param1, String param2) {
        Fbattery_power fragment = new Fbattery_power();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
          handler=new Handler();
          runnable=new Runnable() {
            @Override
            public void run() {
                TV_battery_data.setText(battery_power_data+"%");
                wifi_data=MainService.wifi_data;
                if(wifi_data>-41) {
                    imageView_wifi.setImageResource(R.drawable.wifi_picture4);
                } else if (wifi_data < -40 && wifi_data > -61) {
                    imageView_wifi.setImageResource(R.drawable.wifi_picture3);
                }else if (wifi_data < -60 && wifi_data > -71) {
                    imageView_wifi.setImageResource(R.drawable.wifi_picture2);
                }else if (wifi_data < -70 && wifi_data > -110) {
                    imageView_wifi.setImageResource(R.drawable.wifi_picture1);
                }else {
                    imageView_wifi.setImageResource(R.drawable.nowifi_picture0);

                }

            }
        };
        Ftimer_battery=new Timer();
        FtimerTask_battery=new TimerTask() {
            @Override
            public void run() {
                battery_power_data=MainService.battery_data;
                wifi_data=MainService.wifi_data;
                handler.post(runnable);
            }
        };
        Ftimer_battery.schedule(FtimerTask_battery,0,1000);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_battery_power, container, false);
        TV_battery_data=view.findViewById(R.id.TV_battery);
        imageView_wifi=view.findViewById(R.id.imageview_wifi);
        return  view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mainActivity=(MainActivity)context;
    }
}