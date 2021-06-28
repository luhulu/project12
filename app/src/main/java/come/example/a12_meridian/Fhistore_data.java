package come.example.a12_meridian;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Fhistore_data#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fhistore_data extends Fragment {
    private View view;
    private MainActivity mainActivity;
    private TextView mname,aname,data,staute;
    private String st_mname,st_aname,st_data,st_staute;
    public  SQLiteDatabase db;
    private SQLdata sqLdata;
    private Bundle bundle;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Fhistore_data() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Fhistore_data.
     */
    // TODO: Rename and change types and number of parameters
    public static Fhistore_data newInstance(String param1, String param2) {
        Fhistore_data fragment = new Fhistore_data();
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
        view= inflater.inflate(R.layout.fragment_fhistore_data, container, false);
        mname=view.findViewById(R.id.mname);
        aname=view.findViewById(R.id.aname);
        data=view.findViewById(R.id.data);
        staute=view.findViewById(R.id.staute);
        bundle=getArguments();
        sqLdata=new SQLdata(mainActivity,"alldata",null,1);
        db=sqLdata.getWritableDatabase();
        Log.d("Fhistore_data"+bundle.getInt("position"), "onCreateView: ");
        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mainActivity= (MainActivity) context;
    }

    @Override
    public void onStart() {
        super.onStart();

        Cursor cursor= db.rawQuery("SELECT COUNT(rowid) FROM "+MainActivity.username,null);
        cursor.moveToFirst();
        if(cursor.getInt(0)!=0) {
            cursor = db.rawQuery("SELECT MNAME,ANAME,RESISTANCE,SITUATION FROM "+MainActivity.username+" WHERE rowid=" + bundle.getInt("position")+";", null);
            cursor.moveToFirst();
            if(cursor!=null&&cursor.moveToFirst()!=false) {
                st_mname = cursor.getString(0);
                Log.d(""+cursor.getString(0), "onStart: ");
                st_aname = cursor.getString(1);
                st_data = cursor.getInt(2) + "";
                st_staute = cursor.getString(3);
                mname.setText(st_mname);
                aname.setText(st_aname);
                data.setText(st_data);
                staute.setText(st_staute);
            }
            else {
                Log.d("cursor出錯誤:"+bundle.getInt("position"), "onStart: ");
            }
            cursor.close();
        }
    }
}