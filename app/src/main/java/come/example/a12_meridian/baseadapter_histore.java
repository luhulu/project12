package come.example.a12_meridian;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class baseadapter_histore extends BaseAdapter {
    private SQLdata sqLdata;
    private SQLiteDatabase db;
    private Cursor cursor;
    private Context context;
    private ArrayList arrayList;
    private LayoutInflater layoutInflater;
    public baseadapter_histore(Context context,ArrayList arrayList){
        this.context=context;
        this.arrayList=arrayList;
        layoutInflater=LayoutInflater.from(this.context);

    }
    @Override
    public int getCount() {
        Log.d("baseadapter", "getCount: ");
        return arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        Log.d("baseadapter", "getItem: ");
        return null;
    }

    @Override
    public long getItemId(int position) {
        Log.d("baseadapter", "getItemId: ");
        return 0;
    }
    private static class ViewHolder{
        TextView textView;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        sqLdata=new SQLdata(context,"alldata",null,1);
        db=sqLdata.getWritableDatabase();
        if(convertView==null){
            convertView=layoutInflater.inflate(R.layout.histore_baseadapter,parent,false);
            holder=new ViewHolder();
            holder.textView=convertView.findViewById(R.id.textView);
            convertView.setTag(holder);
        }
        else {
            holder= (ViewHolder) convertView.getTag();
        }
        Cursor cursor=db.rawQuery("SELECT RESISTANCE FROM "+ MainActivity.username+"  WHERE rowid="+arrayList.get(position),null);
        cursor.moveToFirst();
        holder.textView.setText(position+1+"\t"+cursor.getInt(0));
        Log.d("baseadapter,total="+arrayList.get(position), "getView: "+position);
        return convertView;
    }
}
