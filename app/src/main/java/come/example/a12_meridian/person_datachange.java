package come.example.a12_meridian;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link person_datachange#newInstance} factory method to
 * create an instance of this fragment.
 */
public class person_datachange extends Fragment {
    private SQLdata sqLdata;
    private SQLiteDatabase db;
    private View view;
    private Button bth_sure;
    private MainActivity mainActivity;
    private EditText edwight,edheight;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public person_datachange() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment person_datachange.
     */
    // TODO: Rename and change types and number of parameters
    public static person_datachange newInstance(String param1, String param2) {
        person_datachange fragment = new person_datachange();
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
        view= inflater.inflate(R.layout.fragment_person_datachange, container, false);
        bth_sure=view.findViewById(R.id.bth_person_datasure);
        bth_sure.setOnClickListener(con_bth_sure);
        edwight=view.findViewById(R.id.wight_data);
        edheight=view.findViewById(R.id.height_data);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        sqLdata=new SQLdata(mainActivity,"alldata",null,1);
        db=sqLdata.getWritableDatabase();
        Bundle bundle=MainActivity.bundle;
        Log.d("TAG"+bundle.getString("wight"), "onStart: ");
        String wight=bundle.getString("wight"),height=bundle.getString("height");
        edwight.setText(wight);
        edheight.setText(height);
    }

    private View.OnClickListener con_bth_sure=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (edwight.getText().toString().trim().isEmpty()){
                edwight.setText("0");
            }
            if(edheight.getText().toString().trim().isEmpty()){
                edheight.setText("0");
                Log.d("456", "onClick: ");
            }
            db.execSQL("UPDATE "+MainActivity.username+"_persondata"+" SET wight="+edwight.getText().toString()+",height="+edheight.getText().toString());
            mainActivity.to_person_data();
        }
    };
}