package come.example.a12_meridian;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Facupuncturepoint_choose#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Facupuncturepoint_choose extends Fragment implements View.OnClickListener {
    private View view;
    private int num_btn_mer;
    private Button button01,button2,button3;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private MainActivity mainActivity;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Facupuncturepoint_choose() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Facupuncturepoint_choose.
     */
    // TODO: Rename and change types and number of parameters
    public static Facupuncturepoint_choose newInstance(String param1, String param2) {
        Facupuncturepoint_choose fragment = new Facupuncturepoint_choose();
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
        Bundle bundle=getArguments();
        num_btn_mer=bundle.getInt("num_btn_mer");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_facupuncturepoint_choose, container, false);
        button01=view.findViewById(R.id.btnhandyin01);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        button01.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.btnhandyin01:
                mainActivity.to_acupuncturepoint_data(1,num_btn_mer);
                break;
        }
    }
}