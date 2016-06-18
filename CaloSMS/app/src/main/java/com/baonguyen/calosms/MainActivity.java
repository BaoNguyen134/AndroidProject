package com.baonguyen.calosms;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    EditText editTextUsername;
    Button buttonDangKy,buttonChat;
    ListView listViewUssername,listViewChat;

    ArrayList<String> mangUsername, mangChat;

    private Socket mSocket;

    {
        try {
            mSocket = IO.socket("http://tuhoc360.net:4000");
        } catch (URISyntaxException e) {
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSocket.on("ketQuaDangKy", onNewMessage_DangKyUser);
        mSocket.on("server-gui-username", onNewMessage_DanhSachUser);
        mSocket.on("server-gui-tinchat", onNewMessage_DanhSachTinchat);
        mSocket.connect();

        editTextUsername = (EditText) findViewById(R.id.editTextUsername);
        buttonDangKy = (Button) findViewById(R.id.buttonDangKy);
        buttonChat = (Button) findViewById(R.id.buttonChat);
        mangChat = new ArrayList<String>();

        buttonDangKy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSocket.emit("client-gui-username", editTextUsername.getText().toString());
            }
        });

        buttonChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSocket.emit("client-gui-tinchat", editTextUsername.getText().toString());
            }
        });


    }

    private Emitter.Listener onNewMessage_DanhSachTinchat= new Emitter.Listener()
    {
        @Override
        public void call(final Object... args) {
            runOnUiThread(new Runnable()
            {
                @Override
                public void run() {
                    JSONObject data = (JSONObject) args[0];
                    String noidung;
                    try {
                        noidung = data.getString("tinchat");
                        listViewChat = (ListView) findViewById(R.id.listViewChat);
                        mangChat.add(noidung);
                        ArrayAdapter arrayAdapter = new ArrayAdapter(getApplicationContext(),android.R.layout.simple_list_item_1,mangChat);
                        listViewChat.setAdapter(arrayAdapter);

                    } catch (JSONException e) {
                        return;
                    }
                }
            });
        }
    };

    private Emitter.Listener onNewMessage_DanhSachUser = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    JSONObject data = (JSONObject) args[0];
                    JSONArray danhsach;
                    try {
                        danhsach = data.getJSONArray("danhsach");

                        listViewUssername = (ListView) findViewById(R.id.listViewUsername);
                        mangUsername = new ArrayList<String>();
                        for (int i = 0; i< danhsach.length();i++){
                            mangUsername.add(danhsach.get(i).toString());
                        }
                        ArrayAdapter adapter = new ArrayAdapter(getApplicationContext(),android.R.layout.simple_list_item_1,mangUsername);
                        listViewUssername.setAdapter(adapter);
//                      Toast.makeText(getApplicationContext(), danhsach.length() + "", Toast.LENGTH_LONG).show();

                    } catch (JSONException e) {
                        return;
                    }
                }
            });
        }
    };

    private Emitter.Listener onNewMessage_DangKyUser = new Emitter.Listener()
    {
        @Override
        public void call(final Object... args) {
            runOnUiThread(new Runnable()
            {
                @Override
                public void run() {
                    JSONObject data = (JSONObject) args[0];
                    String noidung;
                    try {
                        noidung = data.getString("noidung");
                        if (noidung == "true") {
                            Toast.makeText(getApplicationContext(), "Đăng Ký Thành Công", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(getApplicationContext(), "Đăng Ký Thất Bại", Toast.LENGTH_LONG).show();
                        }
                    } catch (JSONException e) {
                        return;
                    }
                }
            });
        }
    };
}
