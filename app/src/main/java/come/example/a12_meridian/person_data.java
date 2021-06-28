package come.example.a12_meridian;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link person_data#newInstance} factory method to
 * create an instance of this fragment.
 */
public class person_data extends Fragment {
    private MainActivity mainActivity;
    private View view;
    private SQLdata sqLdata;
    private SQLiteDatabase db;
    private String height,wight;
    private Button bth_person_datachange;
    private TextView tvwight,tvheight;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    public person_data() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment person_data.
     */
    // TODO: Rename and change types and number of parameters
    public static person_data newInstance(String param1, String param2) {
        person_data fragment = new person_data();
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
        view= inflater.inflate(R.layout.fragment_person_data, container, false);
        bth_person_datachange=view.findViewById(R.id.bth_person_datasure);
        bth_person_datachange.setOnClickListener(con_bth);
        tvheight=view.findViewById(R.id.height_data);
        tvwight=view.findViewById(R.id.wight_data);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        sqLdata=new SQLdata(mainActivity,"alldata",null,1);
        db=sqLdata.getWritableDatabase();
        Cursor cursor=db.rawQuery("SELECT height FROM "+MainActivity.username+"_persondata",null);
        if ((cursor!=null)) {
            cursor.moveToFirst();
            height = cursor.getString(0);
            tvheight.setText(height);
        }
        cursor = db.rawQuery("SELECT wight FROM "+MainActivity.username+"_persondata", null);
        if (cursor!=null) {
            cursor.moveToFirst();
            wight = cursor.getString(0);
            tvwight.setText(wight);
        }
        cursor.close();
    }
    private View.OnClickListener con_bth=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Log.d(""+wight+"///"+height, "onClick: ");
            mainActivity.to_person_datachange(wight,height);
        }
    };
}