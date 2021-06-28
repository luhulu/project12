package come.example.a12_meridian;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.DhcpInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;
import android.util.Xml;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;
import java.nio.charset.Charset;
import java.util.Timer;
import java.util.TimerTask;

public class MainService extends Service {
    private WifiManager wifiManager;
    private WifiInfo wifiInfo;
    private DhcpInfo dhcpInfo;
    private SQLdata sqLdata;
    public static SQLiteDatabase db;
    private String st_mname="測試經絡",st_aname="測試穴位",st_staute="測試狀態",str,IP;
    private String[] strr;
    private int st_data=107,standard_st_data=0;
    private int len=-1;
    private byte[] indata;
    private BufferedInputStream bufferedInputStream;
    private ByteArrayOutputStream byteArrayOutputStream;
    public static int battery_data=0,wifi_data=-200;
    private Timer timer_catch_battery_data;
    private TimerTask timerTask_battery;
    private Socket socket_client=null;
    public static boolean socket_client_status = false,datacon=false;
    private IBinder mBinder=new LocalBinder();
    public class LocalBinder extends Binder{
        MainService getService(){
            return MainService.this;
        }
    }
    @Override
    public IBinder onBind(Intent intent) {
        updata_battery_and_wifi();
        return mBinder;
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }
    @Override
    public void onCreate() {
        super.onCreate();
        sqLdata=new SQLdata(this,"alldata",null,1);
        db=sqLdata.getWritableDatabase();
        Cursor cursor=db.rawQuery("SELECT data FROM standard",null);
        cursor.moveToFirst();
        standard_st_data=cursor.getInt(0);
        sqLdata=new SQLdata(this,"alldata",null,1);
        db=sqLdata.getWritableDatabase();
    }


   public  void updata_battery_and_wifi(){
        indata=new byte[1024];
        timer_catch_battery_data=new Timer();
        timerTask_battery=new TimerTask() {
            @Override
            public void run() {

                wifiManager= (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
                wifiInfo=wifiManager.getConnectionInfo();
                if (socket_client!=null) {
                    try {
                        Log.d("還在跑", "run: ");
                        socket_client.setSoTimeout(2000);
                    } catch (SocketException e) {
                        e.printStackTrace();
                        socket_client_status = false;
                        wifi_data=-200;
                        Log.d("WIFI已經斷了", "run: ");
                    }
                }
                if(socket_client!=null&&socket_client.isConnected()) {
                    wifi_data = wifiInfo.getRssi();
                    try {
                        bufferedInputStream = new BufferedInputStream(socket_client.getInputStream());
                        bufferedInputStream.read(indata);
                        if (indata != null) {
                            if (((char) indata[0])=='A'&&((char) indata[8])=='A'){
                                if (((char) indata[1])=='Z'){
                                    char[] name=new char[2];
                                    name[0]=(char)indata[6];
                                    name[1]=(char)indata[7];
                                    byte[] data=new byte[4];
                                    System.arraycopy(indata,2,data,0,4);
                                    historeSQL(name,data);
                                }
                                else if (((char) indata[1])=='B'){
                                    battery_data=(int)indata[2];
                                }
                            }
                        }
                    } catch (IOException e) {

                    }
                    datacon=false;
                }
            }
        };
        timer_catch_battery_data.schedule(timerTask_battery,0,1000);
    }
    public static int battery_data_return(){
        return battery_data;
    }

    public void socket_connect(){
         IP=new String();
        if(socket_client==null) {
            socket_client_status = false;
        }
        if(socket_client_status==false) {
            Thread thread_connect = new Thread(new Runnable() {
                //啟動線程來放連接的socket
                @Override
                public void run() {
                    try {
                        socket_client = new Socket(IP, 12345);
                        Log.d("連線成功", "run: ");
                        //socket連接
                        if (socket_client!=null&&socket_client.isConnected()) {
                            socket_client_status = true;
                        }
                    } catch (IOException e) {
                        Log.d("連線錯誤", e.getMessage());
                        socket_client_status=true;
                        try {
                            Thread.sleep(200);
                        } catch (InterruptedException interruptedException) {
                            interruptedException.printStackTrace();
                        }

                        socket_client_status=false;
                    }
                }
            });
            wifiManager= (WifiManager) this.getSystemService(Context.WIFI_SERVICE);
                dhcpInfo = wifiManager.getDhcpInfo();
                int inip= dhcpInfo.serverAddress;
                IP=(inip & 0xFF) +  "."  + (0xFF & inip >> 8) +  "." +  (0xFF & inip >> 16)  + "."+
            (0xFF & inip >> 24);
                thread_connect.start();

        }
    }
    public void historeSQL(char[] name,byte[] data) {
        int resistance=byteToInt(data);
        if (name[0] == 'a') {
            switch (name[1]) {
                case 'a':
                    if (resistance<standard_st_data){
                        st_staute="正常";
                    }
                    else {
                        st_staute="不正常";
                    }
                    db.execSQL("INSERT INTO " + MainActivity.username + " VALUES('" + "手太陰經" + "','" + "中府" + "'," + resistance + ",'" + st_staute + "');");
                    Log.d("我先", "historeSQL: ");
                    break;
                case 'b':
                    break;
                case 'c':
                    break;

            }
        } else if (name[0] == 'b') {
            switch (name[1]) {
                case 'a':
                    break;
                case 'b':
                    break;
                case 'c':
                    break;

            }
        } else if (name[0] == 'c') {
            switch (name[1]) {
                case 'a':
                    break;
                case 'b':
                    break;
                case 'c':
                    break;

            }
        }
    }
    public int byteToInt(byte[] bytedata){
        return (0xff000000 & bytedata[0]<<24)|(0x00ff0000 & bytedata[1]<<16)|(0x0000ff00 & bytedata[2]<<8)|(0x000000ff & bytedata[3]);
    }
}