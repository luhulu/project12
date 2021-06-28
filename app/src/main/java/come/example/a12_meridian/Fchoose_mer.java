package come.example.a12_meridian;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Fchoose_mer#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fchoose_mer extends Fragment implements View.OnClickListener {
    private View view;
    private MainActivity mainActivity;
    private Button button0,button1,button2,button3,button4,button5,button6,button7,button8,button9,button10,button11,button12;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Fchoose_mer() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Fchoose_mer.
     */
    // TODO: Rename and change types and number of parameters
    public static Fchoose_mer newInstance(String param1, String param2) {
        Fchoose_mer fragment = new Fchoose_mer();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mainActivity= (MainActivity) context;
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
        view=inflater.inflate(R.layout.fragment_fchoose_mer, container, false);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        button0=view.findViewById(R.id.button0);
        button0.setOnClickListener(this);
        button1=view.findViewById(R.id.button1);
        button1.setOnClickListener(this);
        button2=view.findViewById(R.id.button2);
        button2.setOnClickListener(this);
        button3=view.findViewById(R.id.button3);
        button3.setOnClickListener(this);
        button4=view.findViewById(R.id.button4);
        button4.setOnClickListener(this);
        button5=view.findViewById(R.id.button5);
        button5.setOnClickListener(this);
        button6=view.findViewById(R.id.button6);
        button6.setOnClickListener(this);
        button7=view.findViewById(R.id.button7);
        button7.setOnClickListener(this);
        button8=view.findViewById(R.id.button8);
        button8.setOnClickListener(this);
        button9=view.findViewById(R.id.button9);
        button9.setOnClickListener(this);
        button10=view.findViewById(R.id.button10);
        button10.setOnClickListener(this);
        button11=view.findViewById(R.id.button11);
        button11.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button0:
                Log.d("acupuncture", "onClick: ");
                mainActivity.to_acupuncturepoint_choose(1);
                break;
        }
    }
}