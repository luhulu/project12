package come.example.a12_meridian;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link acupuncturepoint_data#newInstance} factory method to
 * create an instance of this fragment.
 */
public class acupuncturepoint_data extends Fragment {
    private View view;
    private TextView acupuncturepoint,meridian,inter;
    private int anInt,mnInt;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public acupuncturepoint_data() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment acupuncturepoint_data.
     */
    // TODO: Rename and change types and number of parameters
    public static acupuncturepoint_data newInstance(String param1, String param2) {
        acupuncturepoint_data fragment = new acupuncturepoint_data();
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_acupuncturepoint_data, container, false);
        acupuncturepoint=view.findViewById(R.id.acupuncturepoint);
        meridian=view.findViewById(R.id.meridian);
        inter=view.findViewById(R.id.inter);
        return  view;
    }

    @Override
    public void onStart() {
        super.onStart();
        Bundle bundle=getArguments();
        anInt=bundle.getInt("num_btn_acu");
        mnInt=bundle.getInt("num_btn_mer");
        if (mnInt==1){
            switch (anInt){
                case 1:
                    meridian.setText("手太陰肺經");
                    acupuncturepoint.setText("雲門");
                    inter.setText("這裡打算放網址");
                    break;
                case 2:
                    break;
                case 3:
                    break;
            }
        }
        else if (mnInt==2){
            switch (anInt){
                case 1:
                    break;
                case 2:
                    break;
                case 3:
                    break;
            }
        }
        else if (mnInt==3){
            switch (anInt){
                case 1:
                    break;
                case 2:
                    break;
                case 3:
                    break;
            }
        }
    }
}