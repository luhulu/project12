package come.example.a12_meridian;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.ListFragment;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Fhistore#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fhistore extends Fragment {
    private SQLdata sqLdata;
    private SQLiteDatabase db;
    private SimpleDateFormat time;
    private Cursor cursor;
    private View view;
    private ListView list;
    private ArrayList arrayList;
    private MainActivity mainActivity;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private ArrayList<String> listView;
    private baseadapter_histore baseApapter;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Fhistore() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Fhistore.
     */
    // TODO: Rename and change types and number of parameters
    public static Fhistore newInstance(String param1, String param2) {
        Fhistore fragment = new Fhistore();
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
         view= inflater.inflate(R.layout.fragment_histore, container, false);
        list=view.findViewById(R.id.listview_histore);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        time=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        time.setTimeZone(TimeZone.getTimeZone("GMT 08"));
        cursor= db.rawQuery("SELECT COUNT(rowid) FROM "+MainActivity.username,null);

        cursor.moveToFirst();
        arrayList=new ArrayList();
        int number=cursor.getInt(0);
        Log.d("資料量1="+number, "onStart: ");
        if(cursor.getInt(0)!=0) {
            cursor = db.rawQuery("SELECT rowid FROM "+MainActivity.username, null);
            cursor.moveToFirst();
            for (int a = 0; a < number; a++) {
                Log.d("資料轉移", "onStart: ");
                Log.d(cursor.getInt(0)+"", "onStart: ");
                arrayList.add(cursor.getInt(0));
                Log.d("資料轉移的資料:"+cursor.getInt(0), "onStart: ");
                cursor.moveToNext();
            }
        }
        baseApapter=new baseadapter_histore(this.getContext(),arrayList);
        list.setAdapter(baseApapter);
        list.setOnItemClickListener(onItemClickListener);
        list.setOnItemLongClickListener((AdapterView.OnItemLongClickListener) onItemClickListenerlong);
        cursor.close();
    }
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mainActivity=(MainActivity)context;
        sqLdata=new SQLdata(mainActivity,"alldata",null,1);
        db=sqLdata.getWritableDatabase();
    }
    private AdapterView.OnItemClickListener onItemClickListener=new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Log.d("baseadpater", "onItemClick: ");
            Toast.makeText(getContext(),""+position,Toast.LENGTH_SHORT).show();
            mainActivity.to_histore_data((Integer) arrayList.get(position));
        }
    };
    private AdapterView.OnItemLongClickListener onItemClickListenerlong=new AdapterView.OnItemLongClickListener() {
        @Override
        public  boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
            AlertDialog.Builder builder=new AlertDialog.Builder(mainActivity);
            builder.setTitle("確定要刪除??");
            builder.setPositiveButton("確定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    db.execSQL("DELETE FROM "+ MainActivity.username+" WHERE rowid="+arrayList.get(position));
                    arrayList.remove(position);
                    baseApapter.notifyDataSetChanged();
                    Log.d("問題", "onClick: ");
                    }
            });
            builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                }
            });
            builder.create();
            builder.show();
            cursor.close();
            return true;
        }
    };
}