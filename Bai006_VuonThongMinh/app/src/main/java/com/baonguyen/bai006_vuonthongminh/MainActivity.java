package com.baonguyen.bai006_vuonthongminh;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Calendar;

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

    //About the socket
    Handler bluetoothIn;
    ClientThread clientThread;
    int dem = 0;

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

        batMayBom = (EditText) findViewById(R.id.batMayBom);
        tatMayBom = (EditText) findViewById(R.id.tatMayBom);
        batPhunSuong = (EditText) findViewById(R.id.batPhunSuong);
        tatPhunSuong = (EditText) findViewById(R.id.tatPhunSuong);

        ReceivedTime = (TextView) findViewById(R.id.ReceivedTime);
        ReceivedMode = (TextView) findViewById(R.id.ReceivedMode);
        ReceivedNhietDo = (TextView) findViewById(R.id.ReceivedNhietDo);
        ReceivedDoAmDat = (TextView) findViewById(R.id.ReceivedDoAmDat);
        ReceivedDoAmKhongKhi = (TextView) findViewById(R.id.ReceivedDoAmKhongKhi);
        ReceivedMayBom = (TextView) findViewById(R.id.ReceivedMayBom);
        ReceivedPhunSuong = (TextView) findViewById(R.id.ReceivedPhunSuong);

        init();

        //Click here to connect
        btn_connect.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                String ip = edit_ip.getText().toString();
                String port = edit_port.getText().toString();

                clientThread = new ClientThread(bluetoothIn, ip, port);
                new Thread(clientThread).start();
            }
        });
    }

    private void init() {
        //Load the datas from share preferences
        SharedPreferences sharedata = getSharedPreferences("data", 0);
        String ip = sharedata.getString("ip", "192.168.0.100");
        String port = sharedata.getString("port", "8080");
        edit_ip.setText(ip);
        edit_port.setText(port);

        bluetoothIn = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == 0x123) {
                    readMessage = (String) msg.obj;
                    //Toast.makeText(MainActivity.this, readMessage, Toast.LENGTH_LONG).show();
                    //Toast.makeText(MainActivity.this, "1", Toast.LENGTH_LONG).show();
                    recDataString.append(readMessage);
                    int endOfLineIndex = recDataString.indexOf("~");
                    if (endOfLineIndex > 0) {
                        // cat chuoi dau tien
                        int dauPhayDauTien = recDataString.indexOf(","); // tim vi tri dau "," dau tien
                        sensor0 = recDataString.substring(0, dauPhayDauTien);

                        // cat chuoi thu 2
                        String s1 = recDataString.substring(dauPhayDauTien + 1);
                        int dauPhayThu2 = s1.indexOf(",");
                        sensor1 = s1.substring(0, dauPhayThu2);

                        // cat chuoi thu 3
                        String s2 = recDataString.substring(dauPhayThu2 + dauPhayDauTien + 2);
                        int dauPhayThu3 = s2.indexOf(",");
                        sensor2 = s2.substring(0, dauPhayThu3);

                        // cat chuoi thu 4
                        String s3 = recDataString.substring(dauPhayThu3 + dauPhayThu2 +
                                dauPhayDauTien + 3);
                        int dauPhayThu4 = s3.indexOf(",");
                        sensor3 = s3.substring(0, dauPhayThu4);

                        // cat chuoi thu 5
                        String s4 = recDataString.substring(dauPhayThu4 + dauPhayThu3 + dauPhayThu2
                                + dauPhayDauTien + 4);
                        int dauPhayThu5 = s4.indexOf(",");
                        sensor4 = s4.substring(0, dauPhayThu5);

                        // cat chuoi thu 6
                        String s5 = recDataString.substring(dauPhayThu5 + dauPhayThu4 + dauPhayThu3
                                + dauPhayThu2 + dauPhayDauTien + 5);
                        int dauPhayThu6 = s5.indexOf(",");
                        sensor5 = s5.substring(0, dauPhayThu6);

                        // cat chuoi thu 7
                        String s6 = recDataString.substring(dauPhayThu6 + dauPhayThu5 + dauPhayThu4
                                + dauPhayThu3 + dauPhayThu2 + dauPhayDauTien + 6);
                        int dauPhayThu7 = s6.indexOf(",");
                        sensor6 = s6.substring(0, dauPhayThu7);

                        // cat chuoi thu 8
                        String s7 = recDataString.substring(dauPhayThu7 + dauPhayThu6 +
                                dauPhayThu5 + dauPhayThu4
                                + dauPhayThu3 + dauPhayThu2 + dauPhayDauTien + 7);
                        int dauPhayThu8 = s7.indexOf(",");
                        sensor7 = s7.substring(0, dauPhayThu8);

                        // cat chuoi thu 9
                        String s8 = recDataString.substring(dauPhayThu7 + dauPhayThu6 +
                                dauPhayThu5 + dauPhayThu4 + dauPhayThu8
                                + dauPhayThu3 + dauPhayThu2 + dauPhayDauTien + 8);
                        int dauPhayThu9 = s8.indexOf(",");
                        sensor8 = s8.substring(0, dauPhayThu9);

                        // cat chuoi thu 10
                        String s9 = recDataString.substring(dauPhayThu7 + dauPhayThu6 +
                                dauPhayThu5 + dauPhayThu4 + dauPhayThu8 + dauPhayThu9
                                + dauPhayThu3 + dauPhayThu2 + dauPhayDauTien + 9);
                        int dauPhayThu10 = s9.indexOf(",");
                        sensor9 = s9.substring(0, dauPhayThu10);

                        // cat chuoi thu 11
                        String s10 = recDataString.substring(dauPhayThu7 + dauPhayThu6 +
                                dauPhayThu5 + dauPhayThu4 + dauPhayThu8 + dauPhayThu9
                                + dauPhayThu3 + dauPhayThu2 + dauPhayDauTien
                                + dauPhayThu10 + 10);
                        int dauPhayThu11 = s10.indexOf(",");
                        sensor10 = s10.substring(0, dauPhayThu11);

                        // cat chuoi thu 12
                        String s11 = recDataString.substring(dauPhayThu7 + dauPhayThu6 +
                                dauPhayThu5 + dauPhayThu4 + dauPhayThu8 + dauPhayThu9
                                + dauPhayThu3 + dauPhayThu2 + dauPhayDauTien
                                + dauPhayThu10 + dauPhayThu11 + 11);
                        int dauPhayThu12 = s11.indexOf(",");
                        sensor11 = s11.substring(0, dauPhayThu12);

                        // cat chuoi thu 13
                        String s12 = recDataString.substring(dauPhayThu7 + dauPhayThu6 +
                                dauPhayThu5 + dauPhayThu4 + dauPhayThu8 + dauPhayThu9
                                + dauPhayThu3 + dauPhayThu2 + dauPhayDauTien
                                + dauPhayThu10 + dauPhayThu11 + dauPhayThu12 + 12);
                        int dauPhayThu13 = s12.indexOf(",");
                        sensor12 = s12.substring(0, dauPhayThu13);

                        // cat chuoi thu 14
                        String s13 = recDataString.substring(dauPhayThu7 + dauPhayThu6 +
                                dauPhayThu5 + dauPhayThu4 + dauPhayThu8 + dauPhayThu9
                                + dauPhayThu3 + dauPhayThu2 + dauPhayDauTien
                                + dauPhayThu10 + dauPhayThu11 + dauPhayThu12
                                + dauPhayThu13 + 13);
                        int dauPhayThu14 = s13.indexOf(",");
                        sensor13 = s13.substring(0, dauPhayThu14);

                        // cat chuoi thu 15
                        String s14 = recDataString.substring(dauPhayThu7 + dauPhayThu6 +
                                dauPhayThu5 + dauPhayThu4 + dauPhayThu8 + dauPhayThu9
                                + dauPhayThu3 + dauPhayThu2 + dauPhayDauTien
                                + dauPhayThu10 + dauPhayThu11 + dauPhayThu12
                                + dauPhayThu13 + dauPhayThu14 + 14);
                        int dauPhayThu15 = s14.indexOf(",");
                        sensor14 = s14.substring(0, dauPhayThu15);

                        // cat chuoi thu 16
                        String s15 = recDataString.substring(dauPhayThu7 + dauPhayThu6 +
                                dauPhayThu5 + dauPhayThu4 + dauPhayThu8 + dauPhayThu9
                                + dauPhayThu3 + dauPhayThu2 + dauPhayDauTien
                                + dauPhayThu10 + dauPhayThu11 + dauPhayThu12
                                + dauPhayThu13 + dauPhayThu14 + dauPhayThu15 + 15);
                        int dauPhayThu16 = s15.indexOf(",");
                        sensor15 = s15.substring(0, dauPhayThu16);

                        // cat chuoi thu 17
                        String s16 = recDataString.substring(dauPhayThu7 + dauPhayThu6 +
                                dauPhayThu5 + dauPhayThu4 + dauPhayThu8 + dauPhayThu9
                                + dauPhayThu3 + dauPhayThu2 + dauPhayDauTien
                                + dauPhayThu10 + dauPhayThu11 + dauPhayThu12
                                + dauPhayThu13 + dauPhayThu14 + dauPhayThu15
                                + dauPhayThu16 + 16);
                        int dauPhayThu17 = s16.indexOf(",");
                        sensor16 = s16.substring(0, dauPhayThu17);

                        // cat chuoi thu 18
                        String s17 = recDataString.substring(dauPhayThu7 + dauPhayThu6 +
                                dauPhayThu5 + dauPhayThu4 + dauPhayThu8 + dauPhayThu9
                                + dauPhayThu3 + dauPhayThu2 + dauPhayDauTien
                                + dauPhayThu10 + dauPhayThu11 + dauPhayThu12
                                + dauPhayThu13 + dauPhayThu14 + dauPhayThu15
                                + dauPhayThu16 + dauPhayThu17 + 17);
                        int dauPhayThu18 = s17.indexOf(",");
                        sensor17 = s17.substring(0, dauPhayThu18);

                        // cat chuoi thu 19
                        sensor18 = recDataString.substring(dauPhayThu7 + dauPhayThu6 +
                                dauPhayThu5 + dauPhayThu4 + dauPhayThu8 + dauPhayThu9
                                + dauPhayThu3 + dauPhayThu2 + dauPhayDauTien
                                + dauPhayThu10 + dauPhayThu11 + dauPhayThu12
                                + dauPhayThu13 + dauPhayThu14 + dauPhayThu15
                                + dauPhayThu16 + dauPhayThu17 + dauPhayThu18 + 18, endOfLineIndex);

                        ReceivedTime.setText(sensor1 + ":" + sensor2 + ":" + sensor3);
                        if (sensor10.equals("TD") && sensor4.equals("1") && sensor6.equals("1")) {
                            ReceivedMode.setText("Tự Động");
                            ReceivedMayBom.setText("Tắt");
                            ReceivedPhunSuong.setText("Tắt");
                        } else if (sensor10.equals("TD") && sensor4.equals("1") && sensor6.equals("0")) {
                            ReceivedMode.setText("Tự Động");
                            ReceivedMayBom.setText("Tắt");
                            ReceivedPhunSuong.setText("Bật");
                        } else if (sensor10.equals("TD") && sensor4.equals("0") && sensor6.equals("1")) {
                            ReceivedMode.setText("Tự Động");
                            ReceivedMayBom.setText("Bật");
                            ReceivedPhunSuong.setText("Tắt");
                        } else if (sensor10.equals("TD") && sensor4.equals("0") && sensor6.equals("0")) {
                            ReceivedMode.setText("Tự Động");
                            ReceivedMayBom.setText("Bật");
                            ReceivedPhunSuong.setText("Bật");
                        } else if (sensor10.equals("BT") && sensor4.equals("1") && sensor6.equals("1")) {
                            ReceivedMode.setText("Bằng Tay");
                            ReceivedMayBom.setText("Tắt");
                            ReceivedPhunSuong.setText("Tắt");
                        } else if (sensor10.equals("BT") && sensor4.equals("1") && sensor6.equals("0")) {
                            ReceivedMode.setText("Bằng Tay");
                            ReceivedMayBom.setText("Tắt");
                            ReceivedPhunSuong.setText("Bật");
                        } else if (sensor10.equals("BT") && sensor4.equals("0") && sensor6.equals("1")) {
                            ReceivedMode.setText("Bằng Tay");
                            ReceivedMayBom.setText("Bật");
                            ReceivedPhunSuong.setText("Tắt");
                        } else if (sensor10.equals("BT") && sensor4.equals("0") && sensor6.equals("0")) {
                            ReceivedMode.setText("Bằng Tay");
                            ReceivedMayBom.setText("Bật");
                            ReceivedPhunSuong.setText("Bật");
                        }
                        ReceivedDoAmKhongKhi.setText(sensor7 + "%");
                        ReceivedNhietDo.setText(sensor8 + "°C");
                        ReceivedDoAmDat.setText(sensor9 + "%");

                        recDataString.delete(0, recDataString.length());
                        //Toast.makeText(MainActivity.this, recDataString.toString(), Toast.LENGTH_LONG).show();
                    }
                }
            }
        };
    }

    public void GuiData(View view) {
        if (clientThread.isConnect) {
            Message msg = new Message();
            msg.what = 0x852;
            msg.obj = "1";
            clientThread.sendHandler.sendMessage(msg);
        }

        edit_port.setVisibility(View.GONE);
        edit_ip.setVisibility(View.GONE);
        txt_port.setVisibility(View.GONE);
        txt_ip.setVisibility(View.GONE);
        btn_connect.setVisibility(View.GONE);
    }

    public void TuDong(View view) {
        try {
            Message msg = new Message();
            msg.what = 0x852;
            msg.obj = "TD";
            clientThread.sendHandler.sendMessage(msg);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void BangTay(View view) {
        try {
            Message msg = new Message();
            msg.what = 0x852;
            msg.obj = "BT";
            clientThread.sendHandler.sendMessage(msg);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setTime(View view) {
        try {
            Message msg = new Message();
            msg.what = 0x852;
            Calendar cal = Calendar.getInstance();

            int second = cal.get(Calendar.SECOND);
            int minute = cal.get(Calendar.MINUTE);
            //24 hour format
            int hourofday = cal.get(Calendar.HOUR_OF_DAY);
            String ss = String.valueOf(hourofday) + ":" + String.valueOf(minute) + ":"
                    + String.valueOf(second);
            msg.obj = "CD" + ss;
            clientThread.sendHandler.sendMessage(msg);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void tatPhunSuong(View view) {
        try {
            Message msg = new Message();
            msg.what = 0x852;
            msg.obj = "TS" + tatPhunSuong.getText().toString();
            clientThread.sendHandler.sendMessage(msg);
            tatPhunSuong.setText("");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void batPhunSuong(View view) {
        try {
            Message msg = new Message();
            msg.what = 0x852;
            msg.obj = "BS" + batPhunSuong.getText().toString();
            clientThread.sendHandler.sendMessage(msg);
            batPhunSuong.setText("");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void tatMayBom(View view) {
        try {
            Message msg = new Message();
            msg.what = 0x852;
            msg.obj = "HT" + tatMayBom.getText().toString();
            clientThread.sendHandler.sendMessage(msg);
            tatMayBom.setText("");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void batMayBom(View view) {
        try {
            Message msg = new Message();
            msg.what = 0x852;
            msg.obj = "HG" + batMayBom.getText().toString();
            clientThread.sendHandler.sendMessage(msg);
            batMayBom.setText("");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void BatMayBom(View view) {
        try {
            Message msg = new Message();
            msg.what = 0x852;
            msg.obj = "BT1";
            clientThread.sendHandler.sendMessage(msg);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void TatMayBom(View view) {
        try {
            Message msg = new Message();
            msg.what = 0x852;
            msg.obj = "BT0";
            clientThread.sendHandler.sendMessage(msg);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void BatPhunSuong(View view) {
        try {
            Message msg = new Message();
            msg.what = 0x852;
            msg.obj = "BT3";
            clientThread.sendHandler.sendMessage(msg);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void TatPhunSuong(View view) {
        try {
            Message msg = new Message();
            msg.what = 0x852;
            msg.obj = "BT2";
            clientThread.sendHandler.sendMessage(msg);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}