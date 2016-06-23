package com.baonguyen.haidang;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class MainActivity extends AppCompatActivity {

    //For debug
    private final String TAG = "MainActivity";

    //About the ui controls
    private EditText edit_ip = null;
    private TextView txt_ip;
    private TextView txt_port;
    private EditText edit_port = null;
    private Button btn_connect = null;
    private EditText batMayBom;
    private EditText tatMayBom;
    private EditText batPhunSuong;
    private EditText tatPhunSuong;
    private TextView ReceivedTime;
    private TextView ReceivedMode;
    private TextView ReceivedNhietDo;
    private TextView ReceivedDoAmDat;
    private TextView ReceivedDoAmKhongKhi;
    private TextView ReceivedMayBom;
    private TextView ReceivedPhunSuong;
    private StringBuilder recDataString = new StringBuilder();
    static String sensor0, sensor1, sensor2, sensor3, sensor4, sensor5, sensor6,
            sensor7, sensor8, sensor9, sensor10, sensor11, sensor12, sensor13,
            sensor14, sensor15, sensor16, sensor17, sensor18;
    private String readMessage;
    private ProgressBar loading;

    //About the socket
    Handler bluetoothIn;
    ClientThread clientThread;

    /**
     * Called when the activity is first created.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edit_ip = (EditText) this.findViewById(R.id.edit_ip);
        edit_port = (EditText) this.findViewById(R.id.edit_port);
        txt_ip = (TextView) findViewById(R.id.txt_ip);
        txt_port = (TextView) findViewById(R.id.txt_port);
        btn_connect = (Button) this.findViewById(R.id.btn_connect);
        loading = (ProgressBar) this.findViewById(R.id.loading);

//        batMayBom = (EditText) findViewById(R.id.batMayBom);
//        tatMayBom = (EditText) findViewById(R.id.tatMayBom);
//        batPhunSuong = (EditText) findViewById(R.id.batPhunSuong);
//        tatPhunSuong = (EditText) findViewById(R.id.tatPhunSuong);

        /*ReceivedTime = (TextView) findViewById(R.id.ReceivedTime);*/
        ReceivedMode = (TextView) findViewById(R.id.ReceivedMode);
        /*ReceivedNhietDo = (TextView) findViewById(R.id.ReceivedNhietDo);
        ReceivedDoAmDat = (TextView) findViewById(R.id.ReceivedDoAmDat);
        ReceivedDoAmKhongKhi = (TextView) findViewById(R.id.ReceivedDoAmKhongKhi);
        ReceivedMayBom = (TextView) findViewById(R.id.ReceivedMayBom);
        ReceivedPhunSuong = (TextView) findViewById(R.id.ReceivedPhunSuong);*/

        init();

        //Click here to connect
        btn_connect.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                KetNoi();
            }

            private void KetNoi() {
                String ip = edit_ip.getText().toString();
                String port = edit_port.getText().toString();
                clientThread = new ClientThread(bluetoothIn, ip, port);
                new Thread(clientThread).start();
                edit_port.setVisibility(View.GONE);
                edit_ip.setVisibility(View.GONE);
                txt_port.setVisibility(View.GONE);
                txt_ip.setVisibility(View.GONE);
                btn_connect.setVisibility(View.GONE);
                loading.setVisibility(View.VISIBLE);
                delay(1);
            }

            private void delay(int seconds){
                final int milliseconds = seconds * 1000;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        final Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                PhanHoi();
                            }
                        }, milliseconds);
                    }
                });
            }

            private void PhanHoi(){
                try {
                    Message msg = new Message();
                    msg.what = 0x852;
                    msg.obj = "UD";
                    clientThread.sendHandler.sendMessage(msg);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        });
    }

    private void init() {
        //Load the datas from share preferences
        SharedPreferences sharedata = getSharedPreferences("data", 0);
        String ip = sharedata.getString("ip", "192.168.0.101");
        String port = sharedata.getString("port", "8080");
        edit_ip.setText(ip);
        edit_port.setText(port);

        bluetoothIn = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == 0x123) {
                    readMessage = (String) msg.obj;
                    recDataString.append(readMessage);
                    int endOfLineIndex = recDataString.indexOf("~");
                    if (endOfLineIndex > 0) {
                        // cat chuoi dau tien
                        int dauPhayDauTien = recDataString.indexOf(","); // tim vi tri dau "," dau tien
                        sensor0 = recDataString.substring(0, dauPhayDauTien);

                        // cat chuoi thu 2
                        sensor1 = recDataString.substring(dauPhayDauTien + 1,endOfLineIndex);

                        if(sensor1.equals("1")){
                            ReceivedMode.setText("Tự Động");
                        } else {
                            ReceivedMode.setText("Bằng Tay");
                        }
                        int so = Integer.parseInt(sensor1);

                        if (so >= 0 && so <= 1) {
                            loading.setVisibility(View.GONE);
                        }

                        recDataString.delete(0, recDataString.length());
                    }
                }
            }
        };
    }

    public void GuiData(View view) {
        if (clientThread.isConnect) {
            Message msg = new Message();
            msg.what = 0x852;
            msg.obj = "UD";
            clientThread.sendHandler.sendMessage(msg);
        }
    }

    public void TuDong(View view) {
        try {
            Message msg = new Message();
            msg.what = 0x852;
            msg.obj = "TUDONG";
            clientThread.sendHandler.sendMessage(msg);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void BangTay(View view) {
        try {
            Message msg = new Message();
            msg.what = 0x852;
            msg.obj = "BANGTAY";
            clientThread.sendHandler.sendMessage(msg);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void BatMayBom(View view) {
        try {
            Message msg = new Message();
            msg.what = 0x852;
            msg.obj = "BDC";
            clientThread.sendHandler.sendMessage(msg);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void TatMayBom(View view) {
        try {
            Message msg = new Message();
            msg.what = 0x852;
            msg.obj = "TDC";
            clientThread.sendHandler.sendMessage(msg);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void BatDen(View view) {
        try {
            Message msg = new Message();
            msg.what = 0x852;
            msg.obj = "BD";
            clientThread.sendHandler.sendMessage(msg);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void TatDen(View view) {
        try {
            Message msg = new Message();
            msg.what = 0x852;
            msg.obj = "TD";
            clientThread.sendHandler.sendMessage(msg);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void NangPin(View view) {
        try {
            Message msg = new Message();
            msg.what = 0x852;
            msg.obj = "NP";
            clientThread.sendHandler.sendMessage(msg);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void HaPin(View view) {
        try {
            Message msg = new Message();
            msg.what = 0x852;
            msg.obj = "HP";
            clientThread.sendHandler.sendMessage(msg);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.action_favorite:
                Intent i = new Intent(MainActivity.this, MainActivity.class);
                startActivity(i);
                finish();
                return true;

            case R.id.exit:
                finish();
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

    public void Nhay(View view) {
        try {
            Message msg = new Message();
            msg.what = 0x852;
            msg.obj = "NHAY";
            clientThread.sendHandler.sendMessage(msg);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void OffHD(View view) {
        try {
            Message msg = new Message();
            msg.what = 0x852;
            msg.obj = "THD";
            clientThread.sendHandler.sendMessage(msg);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void OnHD(View view) {
        try {
            Message msg = new Message();
            msg.what = 0x852;
            msg.obj = "BHD";
            clientThread.sendHandler.sendMessage(msg);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


// Kết nối

class ClientThread implements Runnable {
    //For debug
    private final String TAG = "ClientThread";

    private Socket socket;
    private String ip;
    private int port;
    private Handler receiveHandler;
    public Handler sendHandler;
    BufferedReader bufferedReader;
    private InputStream inputStream;
    private OutputStream outputStream;
    public boolean isConnect = false;

    public ClientThread(Handler handler, String ip, String port) {
        // TODO Auto-generated constructor stub
        this.receiveHandler = handler;
        this.ip = ip;
        this.port = Integer.valueOf(port);
    }

    public ClientThread() {
    }

    public void run() {
        try {
            socket = new Socket(ip, port);
            isConnect = socket.isConnected();
            inputStream = socket.getInputStream();
            outputStream = socket.getOutputStream();

            //To monitor if receive Msg from Server
            new Thread() {
                @Override
                public void run() {
                    byte[] buffer = new byte[1024];

                    final StringBuilder stringBuilder = new StringBuilder();
                    try {
                        while (socket.isConnected()) {
                            int readSize = inputStream.read(buffer);

                            //If Server is stopping
                            if (readSize == -1) {
                                inputStream.close();
                                outputStream.close();
                            }
                            if (readSize == 0) continue;

                            //Update the receive editText
                            stringBuilder.append(new String(buffer, 0, readSize));
                            Message msg = new Message();
                            msg.what = 0x123;
                            msg.obj = stringBuilder.toString();
                            receiveHandler.sendMessage(msg);
                            // xóa bộ đệm cho lần sau
                            stringBuilder.delete(0, stringBuilder.length());
                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }.start();

            //To Send Msg to Server
            Looper.prepare();
            sendHandler = new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    if (msg.what == 0x852) {
                        try {
                            outputStream.write((msg.obj.toString() + "\r\n").getBytes());
                            outputStream.flush();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            };
            Looper.loop();

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}