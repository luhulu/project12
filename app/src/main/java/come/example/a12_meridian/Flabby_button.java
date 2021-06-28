package come.example.a12_meridian;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class Flabby_button extends Fragment {
    private View labbyview;
    private MainActivity mainActivity;
    private Button but_histore,but_acupoint_position,but_auto_connect,bth_person_data;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        labbyview= inflater.inflate(R.layout.fragment_flabby_button, container, false);
        Log.d("Flabby_button", "onCreateView:");
        return labbyview;
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d("Flabby_button", "onActivityCreated: ");
        but_auto_connect=labbyview.findViewById(R.id.but_auto_connect);
        but_auto_connect.setOnClickListener(con_but_auto_connect);
        but_histore=labbyview.findViewById(R.id.but_histore);
        but_histore.setOnClickListener(con_but_histore);
        but_acupoint_position=labbyview.findViewById(R.id.but_acupoint_position);
        but_acupoint_position.setOnClickListener(con_but_acupoint_position);
        bth_person_data=labbyview.findViewById(R.id.bth_person_data);
        bth_person_data.setOnClickListener(con_bth_person_data);
    }
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mainActivity=(MainActivity)context;
    }
    View.OnClickListener con_but_auto_connect=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mainActivity.start_connect();
            Log.d("觸發連接按鈕", "onClick: ");
        }
    };
    View.OnClickListener con_but_histore=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mainActivity.to_histore();
        }
    };
    View.OnClickListener con_but_acupoint_position=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mainActivity.to_choose_mer();
        }
    };
    View.OnClickListener con_bth_person_data=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mainActivity.to_person_data();
        }
    };
    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("Flabby_button", "onDestroy: ");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d("tag", "onStart: ");
    }
}