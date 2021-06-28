package come.example.a12_meridian;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.app.Dialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    private ArrayAdapter arrayAdapter;
    private RadioButton radioButton;
    private AutoCompleteTextView editText;
    public EditText editText1;
    private Button sure_button,registered_button;
    private ListView listView;
    private boolean socket_client_stute=false,radiobutton_status=false;
    public static Bundle bundle;
    public static String username=null;
    private SQLdata sqLdata;
    private SQLiteDatabase db;
    private acupuncturepoint_data acupuncturepoint_data;
    private Facupuncturepoint_choose facupuncturepoint_choose;
    private LayoutInflater inflater;
    private View view,edview;
    private Fbattery_power batteryPower;
    //電池的fragment
    private first_interface first_interface;
    private Flabby_button Flabby;
    //labby的fragment
    private Fhistore fhistore;
    private Fchoose_mer fchoose_mer;
    private Fhistore_data fhistore_data;
    private person_data person_data;
    private person_datachange person_datachange;
    private FragmentTransaction fragmentTransaction_status;
    //fragment的控制物件
    private FragmentManager fragmentManager_status;
    private MainService mainService;
    private AlertDialog.Builder alertDialog_warm,alerDialog_password;
    //彈出來顯示的dialog
    private Dialog dialog,getDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(ContextCompat.checkSelfPermission(MainActivity.this,Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(MainActivity.this,Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED ){
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.WRITE_APN_SETTINGS,Manifest.permission.READ_EXTERNAL_STORAGE},1);
        }

       /* alerDialog_password.setPositiveButton("登入", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                EditText editText=view.findViewById(R.id.account);
                EditText editText1=view.findViewById(R.id.password);
                Cursor cursor=db.rawQuery("SELECT accountdata FROM account WHERE "+editText.getText().toString(),null);
                if(cursor!=null&&editText1.getText().toString()==cursor.getString(0)) {

                }
            }
        });
        alerDialog_password.setNegativeButton("註冊", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                EditText editText=view.findViewById(R.id.account);
                EditText editText1=view.findViewById(R.id.password);
                db.execSQL("CREATE TABLE IF NOT EXISTS histore(MNAME CHAR(20),ANAME CHAR(20),RESISTANCE INT,SITUATION CHAR(10));");
                db.execSQL("CREATE TABLE IF NOT EXISTS persondata(height CHAR(10),wight CHAR(10));");
                db.execSQL("INSERT INTO histore VALUES(" + "'"+ "測試" + "','" + "測試" + "','" +"10" + "','" + "測試" + "');");
                db.execSQL("INSERT INTO persondata VALUES('0','0');");
            }
        });*/

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(grantResults[1]==PackageManager.PERMISSION_GRANTED){
            Toast.makeText(this,"取得權限",Toast.LENGTH_LONG).show();
        }
        else {
            Log.d("去決給權限", "onRequestPermissionsResult: ");
            finish();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        acupuncturepoint_data=new acupuncturepoint_data();
        facupuncturepoint_choose=new Facupuncturepoint_choose();
        Flabby=new Flabby_button();
        batteryPower=new Fbattery_power();
        first_interface=new first_interface();
        fhistore=new Fhistore();
        fchoose_mer=new Fchoose_mer();
        fhistore_data=new Fhistore_data();
        person_data=new person_data();
        person_datachange=new person_datachange();
        //這裡是電池的fragment創建記憶體空間
        fragmentManager_status =getSupportFragmentManager();
        //關於電池和訊號強度區塊的fragmentManage
        fragmentTransaction_status = fragmentManager_status.beginTransaction();
        //這邊開啟電池和訊號強度區塊的fragmentManage
        fragmentTransaction_status.add(R.id.F_labby_button,Flabby);
        fragmentTransaction_status.addToBackStack("");
        fragmentTransaction_status.commit();
        fragmentTransaction_status = fragmentManager_status.beginTransaction();
        fragmentTransaction_status.add(R.id.Fbattery,batteryPower);
        fragmentTransaction_status.add(R.id.F_meridian_show,first_interface);
        //添加fragment進入fragmentManage
        fragmentTransaction_status.commit();
        //執行動作
        View view=findViewById(R.id.backgrouond_picture);
        //設定背景的view的物件
        view.getBackground().setAlpha(100);
        //設定背景的透明度0~255
        Intent intent=new Intent(MainActivity.this,MainService.class);
        bindService(intent,mConnection, Context.BIND_AUTO_CREATE);
        Log.d("66666", "onStat: ");
        if (username==null) {
            sign_out();
        }
    }
View.OnClickListener sign_out_con =new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        sign_out();
    }
};
    private ServiceConnection mConnection=new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            MainService.LocalBinder binder = (MainService.LocalBinder)service;
            mainService=binder.getService();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
        }
    };
    public void sign_out(){
        inflater=LayoutInflater.from(MainActivity.this);
        view=inflater.inflate(R.layout.password_layout,null);
        AutoCompleteTextView editText = view.findViewById(R.id.account);
        radioButton =view.findViewById(R.id.radioButton2);
        radioButton.setOnClickListener(raidobutton_onclick);
        editText1 = view.findViewById(R.id.password);
        editText.setOnItemClickListener(accountonclick);
        alerDialog_password=new AlertDialog.Builder(this);
        alerDialog_password.setView(view);
        alerDialog_password.setCancelable(false);
        sqLdata=new SQLdata(this,"alldata",null,1);
        db=sqLdata.getWritableDatabase();
        Cursor cursor=db.rawQuery("SELECT account,password FROM rememberpassword",null);
        cursor.moveToFirst();
        String[] strings=new String[cursor.getCount()];
        Log.d(cursor.getCount()+"", "sign_out: ");
        for (int i=0;i<cursor.getCount();i++){
            strings[i]=cursor.getString(0);
            Log.d(strings[i], "sign_out: ");
            if (i!=cursor.getCount()) {
                cursor.moveToNext();
            }
        }
        arrayAdapter=new ArrayAdapter(this, android.R.layout.simple_list_item_1,strings);
        editText.setAdapter(arrayAdapter);
        sure_button=view.findViewById(R.id.btn_sign);
        sure_button.setOnClickListener(sign);
        registered_button=view.findViewById(R.id.btn_registered);
        registered_button.setOnClickListener(registered);

        alerDialog_password.create();
        getDialog = alerDialog_password.show();
        cursor.close();
        Log.d("結束", "sign_out: ");
    }
    View.OnClickListener raidobutton_onclick=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (radiobutton_status){
                radioButton.setChecked(false);
                radiobutton_status=false;
            }
            else if (radioButton.isChecked()){
                radiobutton_status=true;
            }
        }
    };
    public void start_connect(){
        if (mainService.socket_client_status==false){
            mainService.socket_connect();
            Log.d("TAG", "dialog來過");
            alertDialog_warm = new AlertDialog.Builder(this);
            //給出alertDialog_warm的記憶體空間
            alertDialog_warm.setMessage("連線中....");
            //alertDialog_warm顯示的文字
            alertDialog_warm.setCancelable(false);
            //可以點選返回按鈕
            alertDialog_warm.setPositiveButton("取消連線", new DialogInterface.OnClickListener() {
                //alertDialog_warm按鈕顯示的文字
                @Override
                public void onClick(DialogInterface dialog, int which) {//按鈕的設定
                }
            });

            dialog= alertDialog_warm.show();

            new Thread(new Runnable() {
                @Override
                public void run() {
                    Log.d("測試我來過", "start_connect: "+socket_client_stute);
                            while (socket_client_stute == false) {
                                socket_client_stute = mainService.socket_client_status;
                                try {
                                    Thread.sleep(100);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                Log.d(""+socket_client_stute, "run: ");
                            }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Log.d("我來關掉了", "run: ");
                            dialog.dismiss();
                            Toast.makeText(getApplicationContext(),"連線失敗請檢查設備",Toast.LENGTH_SHORT).show();
                        }
                    });

                }
            }).start();

        }
    }
    public void connect_error(){

            Toast.makeText(getApplicationContext(), "連線錯誤，請檢查網路", Toast.LENGTH_SHORT);

    }
    @Override
    protected void onStop() {

        super.onStop();
    }
    private void error_message(){
        Toast.makeText(this,"錯誤",Toast.LENGTH_SHORT).show();
    }
    public int battery_data(){
        int a= 0;
        a=mainService.battery_data_return();
        return a;
    }
    public void to_histore(){
        if (fragmentManager_status.findFragmentById(R.id.F_meridian_show )==fhistore) {
        }
        else if (fragmentManager_status.findFragmentById(R.id.F_meridian_show)==fhistore_data){
            fragmentManager_status.popBackStack();
        }
        else if(fragmentManager_status.findFragmentById(R.id.F_meridian_show)==fchoose_mer
                |fragmentManager_status.findFragmentById(R.id.F_meridian_show)==person_data){
            fragmentManager_status.popBackStack();
            fragmentTransaction_status = fragmentManager_status.beginTransaction();
            fragmentTransaction_status.replace(R.id.F_meridian_show, fhistore);
            fragmentTransaction_status.addToBackStack("");
            fragmentTransaction_status.commit();
        }
        else if(fragmentManager_status.findFragmentById(R.id.F_meridian_show)==facupuncturepoint_choose
                |fragmentManager_status.findFragmentById(R.id.F_meridian_show)==person_datachange){
            fragmentManager_status.popBackStack();
            fragmentManager_status.popBackStack();
            fragmentTransaction_status = fragmentManager_status.beginTransaction();
            fragmentTransaction_status.replace(R.id.F_meridian_show, fhistore);
            fragmentTransaction_status.addToBackStack("");
            fragmentTransaction_status.commit();
            Log.d("測試切換大聽到裡歷史紀錄的fragment", "to_histore: ");
        }
        else if(fragmentManager_status.findFragmentById(R.id.F_meridian_show)==acupuncturepoint_data){
            fragmentManager_status.popBackStack();
            fragmentManager_status.popBackStack();
            fragmentManager_status.popBackStack();
            fragmentTransaction_status = fragmentManager_status.beginTransaction();
            fragmentTransaction_status.replace(R.id.F_meridian_show, fhistore);
            fragmentTransaction_status.addToBackStack("");
            fragmentTransaction_status.commit();
            Log.d("測試切換大聽到裡歷史紀錄的fragment", "to_histore: ");
        }
        else {
            fragmentTransaction_status = fragmentManager_status.beginTransaction();
            fragmentTransaction_status.replace(R.id.F_meridian_show, fhistore);
            fragmentTransaction_status.addToBackStack("");
            fragmentTransaction_status.commit();
            Log.d("測試切換大聽到裡歷史紀錄的fragment", "to_histore: ");
        }
    }
    public void to_choose_mer(){
        if (fragmentManager_status.findFragmentById(R.id.F_meridian_show)==fchoose_mer){
        }
        else if (fragmentManager_status.findFragmentById(R.id.F_meridian_show)==facupuncturepoint_choose){
            fragmentManager_status.popBackStack();
        }
        else if(fragmentManager_status.findFragmentById(R.id.F_meridian_show)==fhistore
                |fragmentManager_status.findFragmentById(R.id.F_meridian_show)==person_data){
            fragmentManager_status.popBackStack();
            fragmentTransaction_status = fragmentManager_status.beginTransaction();
            fragmentTransaction_status.replace(R.id.F_meridian_show, fchoose_mer);
            fragmentTransaction_status.addToBackStack("");
            fragmentTransaction_status.commit();
        }
        else if(fragmentManager_status.findFragmentById(R.id.F_meridian_show)==fhistore_data
                |fragmentManager_status.findFragmentById(R.id.F_meridian_show)==person_datachange){
            fragmentManager_status.popBackStack();
            fragmentManager_status.popBackStack();
            fragmentTransaction_status = fragmentManager_status.beginTransaction();
            fragmentTransaction_status.replace(R.id.F_meridian_show, fchoose_mer);
            fragmentTransaction_status.addToBackStack("");
            fragmentTransaction_status.commit();
        }
        else if(fragmentManager_status.findFragmentById(R.id.F_meridian_show)==acupuncturepoint_data){
            fragmentManager_status.popBackStack();
            fragmentManager_status.popBackStack();
            fragmentManager_status.popBackStack();
            fragmentTransaction_status = fragmentManager_status.beginTransaction();
            fragmentTransaction_status.replace(R.id.F_meridian_show, fchoose_mer);
            fragmentTransaction_status.addToBackStack("");
            fragmentTransaction_status.commit();
        }
        else {
            fragmentTransaction_status = fragmentManager_status.beginTransaction();
            fragmentTransaction_status.replace(R.id.F_meridian_show, fchoose_mer);
            fragmentTransaction_status.addToBackStack("");
            fragmentTransaction_status.commit();
        }
    }
    public void to_histore_data(int position){
        fragmentTransaction_status = fragmentManager_status.beginTransaction();
        fragmentTransaction_status.replace(R.id.F_meridian_show,fhistore_data);
        fragmentTransaction_status.addToBackStack("");
        Bundle bundle=new Bundle();
        bundle.putInt("position",position);
        fhistore_data.setArguments(bundle);
        fragmentTransaction_status.commit();
    }
    public void to_acupuncturepoint_choose(int num_btn_mer){
        Bundle bundle=new Bundle();
        bundle.putInt("num_btn_mer",num_btn_mer);
        facupuncturepoint_choose.setArguments(bundle);
        Log.d(""+num_btn_mer, "to_acupuncturepoint_choose: ");
        fragmentTransaction_status = fragmentManager_status.beginTransaction();
        fragmentTransaction_status.replace(R.id.F_meridian_show,facupuncturepoint_choose);
        fragmentTransaction_status.addToBackStack("");
        fragmentTransaction_status.commit();
    }
    public void to_acupuncturepoint_data(int num_btn_acu,int num_btn_mer){
        Bundle bundle=new Bundle();
        bundle.putInt("num_btn_mer",num_btn_mer);
        bundle.putInt("num_btn_acu",num_btn_acu);
        acupuncturepoint_data.setArguments(bundle);
        fragmentTransaction_status = fragmentManager_status.beginTransaction();
        fragmentTransaction_status.replace(R.id.F_meridian_show,acupuncturepoint_data);
        fragmentTransaction_status.addToBackStack("");
        fragmentTransaction_status.commit();
    }
    public void to_person_data(){
        if (fragmentManager_status.findFragmentById(R.id.F_meridian_show)==person_data){
        }
        else if (fragmentManager_status.findFragmentById(R.id.F_meridian_show)==fchoose_mer
                |fragmentManager_status.findFragmentById(R.id.F_meridian_show)==fhistore){
            fragmentManager_status.popBackStack();
            fragmentTransaction_status = fragmentManager_status.beginTransaction();
            fragmentTransaction_status.replace(R.id.F_meridian_show, person_data);
            fragmentTransaction_status.addToBackStack("");
            fragmentTransaction_status.commit();
        }
        else if(fragmentManager_status.findFragmentById(R.id.F_meridian_show)==fhistore_data
                |fragmentManager_status.findFragmentById(R.id.F_meridian_show)==facupuncturepoint_choose){
            fragmentManager_status.popBackStack();
            fragmentManager_status.popBackStack();
            fragmentTransaction_status = fragmentManager_status.beginTransaction();
            fragmentTransaction_status.replace(R.id.F_meridian_show, person_data);
            fragmentTransaction_status.addToBackStack("");
            fragmentTransaction_status.commit();
        }
        else if(fragmentManager_status.findFragmentById(R.id.F_meridian_show)==acupuncturepoint_data){
            fragmentManager_status.popBackStack();
            fragmentManager_status.popBackStack();
            fragmentManager_status.popBackStack();
            fragmentTransaction_status = fragmentManager_status.beginTransaction();
            fragmentTransaction_status.replace(R.id.F_meridian_show, person_data);
            fragmentTransaction_status.addToBackStack("");
            fragmentTransaction_status.commit();
        }
        else if(fragmentManager_status.findFragmentById(R.id.F_meridian_show)==person_datachange){
            fragmentManager_status.popBackStack();
        }
        else {
            fragmentTransaction_status = fragmentManager_status.beginTransaction();
            fragmentTransaction_status.replace(R.id.F_meridian_show, person_data);
            fragmentTransaction_status.addToBackStack("");
            fragmentTransaction_status.commit();
        }
    }
    public void to_person_datachange(String wight,String height){
        bundle=new Bundle();
        bundle.putString("wight",wight);
        bundle.putString("height",height);
        fragmentTransaction_status = fragmentManager_status.beginTransaction();
        person_data.setArguments(bundle);
        fragmentTransaction_status.replace(R.id.F_meridian_show, person_datachange);
        fragmentTransaction_status.addToBackStack("");
        fragmentTransaction_status.commit();
    }

    @Override
    protected void onPause() {
        String s;
        File file= new File(getExternalCacheDir().getAbsolutePath()+File.separator+"persondata.csv");
        Log.d(file+"", "onPause() ");
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        SQLdata sqLdata=new SQLdata(this,"alldata",null,1);
        SQLiteDatabase db=sqLdata.getWritableDatabase();
        if(MainActivity.username!=null) {
            Cursor cursor = db.rawQuery("SELECT COUNT(rowid) FROM " + MainActivity.username, null);
            cursor.moveToFirst();
            if ((cursor != null && cursor.getInt(0) != 0)) {
                int data_number = cursor.getInt(0);
                cursor = db.rawQuery("SELECT MNAME,ANAME,RESISTANCE,SITUATION FROM "+MainActivity.username, null);
                cursor.moveToFirst();
                try {
                    FileOutputStream fileOutputStream = new FileOutputStream(file);
                    for (int a = 0; a < data_number; a++) {
                        for (int b = 0; b < 4; b++) {
                            s = cursor.getString(b) + ",";
                            fileOutputStream.write(s.getBytes("Big5"));
                        }
                        fileOutputStream.write("\n".getBytes());
                        if (a < data_number) {
                            cursor.moveToNext();
                        }
                    }
                } catch (IOException e) {
                    Log.d("錯誤", "onPause: ");
                    e.printStackTrace();
                }
            }
            cursor.close();
        }
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        mainService.onDestroy();
        super.onDestroy();
    }
    @Override
    public void onBackPressed() {
        if((fragmentManager_status.findFragmentById(R.id.F_labby_button)==Flabby)&&(fragmentManager_status.findFragmentById(R.id.F_meridian_show)==first_interface)){
            Log.d("tag", "onBackPressed: ");
            finish();
        }
        super.onBackPressed();
    }
    View.OnClickListener sign=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            AutoCompleteTextView editText = view.findViewById(R.id.account);
            EditText editText1 = view.findViewById(R.id.password);
            if (!editText.getText().toString().matches("")&&!editText1.getText().toString().matches("")&&editText.getText().toString().length()==10&&editText1.getText().toString().length()==10) {
                Cursor cursor = db.rawQuery("SELECT accountdata,password FROM account WHERE accountdata=" + "'" + editText.getText().toString() + "'", null);
                cursor.moveToFirst();
                if (cursor != null && cursor.getCount() > 0 && editText1.getText().toString().equals(cursor.getString(1))) {
                    Log.d("進來了", "onClick: " + cursor.getCount());
                    username = editText.getText().toString();
                    if (radioButton.isChecked()){
                        cursor=db.rawQuery("SELECT password FROM rememberpassword WHERE account ='"+editText.getText().toString()+"';",null);
                        if (cursor.getCount()==0) {
                            db.execSQL("INSERT INTO rememberpassword VALUES(" + "'" + editText.getText().toString() + "','" + editText1.getText().toString() + "');");
                            Log.d("密碼記住了", "onClick: ");
                        }
                    }
                    else {
                         cursor=db.rawQuery("SELECT password FROM rememberpassword where account ='"+editText.getText().toString()+"';",null);
                        if (cursor.getCount()!=0) {
                            db.execSQL("DELETE FROM rememberpassword"+" WHERE account='"+editText.getText().toString()+"';");
                            Log.d("密碼記住了", "onClick: ");
                        }
                    }
                    radiobutton_status=false;
                    getDialog.dismiss();
                } else {
                    Toast.makeText(getApplicationContext(), "錯誤", Toast.LENGTH_SHORT).show();
                }
                cursor.close();
            }
            else {
                Toast.makeText(getApplicationContext(),"輸入格式錯誤",Toast.LENGTH_SHORT).show();
            }
        }
    };
    View.OnClickListener registered=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            AutoCompleteTextView editText = view.findViewById(R.id.account);
            EditText editText1 = view.findViewById(R.id.password);
            if (!editText.getText().toString().matches("") && !editText1.getText().toString().matches("")&&editText.getText().toString().length()==10&&editText1.getText().toString().length()==10) {
                Cursor cursor = db.rawQuery("SELECT accountdata FROM account WHERE accountdata=" + "'" + editText.getText().toString() + "'", null);
                cursor.moveToFirst();
                Log.d("" + cursor.getCount(), "onClick: ");
                if (cursor.getCount() < 1) {
                    db.execSQL("CREATE TABLE IF NOT EXISTS " + editText.getText().toString() + "(MNAME CHAR(20),ANAME CHAR(20),RESISTANCE INT,SITUATION CHAR(10));");
                    db.execSQL("CREATE TABLE IF NOT EXISTS " + editText.getText().toString() + "_persondata" + "(height CHAR(10),wight CHAR(10));");
                    db.execSQL("INSERT INTO account VALUES(" + "'" + editText.getText().toString() + "','" + editText1.getText().toString() + "');");
                    db.execSQL("INSERT INTO " + editText.getText().toString() + " VALUES(" + "'" + "測試" + "','" + "測試" + "','" + "10" + "','" + "測試" + "');");
                    db.execSQL("INSERT INTO " + editText.getText().toString() + "_persondata" + " VALUES('0','0');");
                    cursor = db.rawQuery("SELECT password FROM account WHERE accountdata=" + "'" + editText.getText().toString() + "'", null);
                    Toast.makeText(getApplicationContext(), "註冊成功", Toast.LENGTH_SHORT).show();
                } else if (cursor != null && cursor.getCount() > 0) {
                    cursor.moveToFirst();
                    Toast.makeText(getApplicationContext(), "帳號已存在", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "錯誤", Toast.LENGTH_SHORT).show();
                }
                cursor.close();
            }
        }
    };
    AdapterView.OnItemClickListener accountonclick =new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            edview=LayoutInflater.from(MainActivity.this).inflate(R.layout.password_layout,null);
            Cursor cursor=db.rawQuery("SELECT password FROM rememberpassword WHERE account='"+arrayAdapter.getItem(position)+"';",null);
            cursor.moveToFirst();
            Log.d(""+arrayAdapter.getItem(position), "onItemClick: ");
            editText1.setText(cursor.getString(0)+"");
            radioButton.setChecked(true);
            radiobutton_status=true;
            Log.d(cursor.getString(0), "onItemClick: ");
        }
    };
    /*public class remember_password extends BaseAdapter{
        private ArrayList arrayList;
        public remember_password(Context context){
            arrayList=new ArrayList();
            Cursor cursor=db.rawQuery("SELECT account FROM rememberpassword",null);
            cursor.moveToFirst();
            if (cursor!=null) {
                for (int i = 0; i < cursor.getCount(); i++) {
                    arrayList.add(cursor.getString(0));
                }
            }
        }

        @Override
        public int getCount() {
            Log.d("password", ""+arrayList.size());
            return arrayList.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }
public class ViewHold{
            TextView textView;
}
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHold viewHold;
            if (convertView==null) {
                viewHold=new ViewHold();
                convertView=inflater.inflate(R.layout.histore_baseadapter,null);
                viewHold.textView=convertView.findViewById(R.id.textView);
                convertView.setTag(viewHold);
            }
            else {
                viewHold= (ViewHold) convertView.getTag();
            }
            viewHold.textView.setText(""+arrayList.get(0));
            return null;
        }
    }*/
}


