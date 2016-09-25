package com.yangpeiwen.dhtclient;

import android.content.Context;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public String getWiFiName() {
        WifiManager manager = (WifiManager) this.getSystemService(Context.WIFI_SERVICE);
        if (manager.isWifiEnabled()) {
            WifiInfo wifiInfo = manager.getConnectionInfo();
            if (wifiInfo != null) {
                NetworkInfo.DetailedState state = WifiInfo.getDetailedStateOf(wifiInfo.getSupplicantState());
                if (state == NetworkInfo.DetailedState.CONNECTED || state == NetworkInfo.DetailedState.OBTAINING_IPADDR) {
                    return wifiInfo.getSSID();
                }
            }
        }
        return null;
    }

    public static void print(String p) {
        System.out.println("OUT:" + p);
    }

    private Toast toast;
    private void show(final String str) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(toast == null){
                    toast = Toast.makeText(getApplicationContext(),
                            str, Toast.LENGTH_SHORT);
                }else{
                    toast.setText(str);
                }
                toast.show();
            }
        });
    }

    long index = 1;
    private void showtext(final String str){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                TextView tv = (TextView)findViewById(R.id.textView);
                tv.setText(str);
                EditText et = (EditText)findViewById(R.id.editText);
                et.append(index + ":" + str + "\n");
                index++;
                try {
                    double t = Double.parseDouble(str.substring(str.indexOf(":") + 1, str.indexOf("℃")));
                    double h = Double.parseDouble(str.substring(str.lastIndexOf(":") + 1, str.lastIndexOf("%")));
                    ProgressBar bar1 = (ProgressBar) findViewById(R.id.progressBar1);
                    ProgressBar bar2 = (ProgressBar) findViewById(R.id.progressBar2);
                    bar1.setProgress((int) (t * 10));
                    bar2.setProgress((int) (h * 10));
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }

    Socket client;
    OutputStream outputStream;
    InputStream inputStream;
    public void connect(View v){
        String wifi = getWiFiName();
        wifi = wifi.replace("\"", "");
        if(wifi == null){
            show("请连接" + getString(R.string.wifi));
        }else if(!wifi.equals(getString(R.string.wifi))){
            show("当前WiFi:" + wifi + ",请连接" + getString(R.string.wifi));
            return;
        }

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    client = new Socket("10.10.100.254", 8080);
                    outputStream = client.getOutputStream();
                    inputStream = client.getInputStream();
                    show("连接成功");
                    showtext("连接成功");
                    Thread t = new Thread(read);
                    t.setDaemon(true);
                    t.start();
                } catch (Exception e) {
                    show("连接失败");
                    showtext("连接失败");
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void get(View v){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    byte buf[] = new byte[1];
                    buf[0] = 'a';
                    outputStream.write(buf);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }

    Runnable read = new Runnable() {
        @Override
        public void run() {
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while(true) {
                try {
                    line = reader.readLine();
                    if (line == null) break;
                    showtext(line);
                    print(line);
                }catch (Exception e){
                    e.printStackTrace();
                    break;
                }
            }
            show("连接中断");
            showtext("连接中断");
        }
    };

}
