package com.baonguyen.dieukhientb;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class SmartHomeMainActivity extends AppCompatActivity {
	public static final int BUFFER_SIZE = 2048;
	private Socket sck = null;
	private PrintWriter out = null;
	private BufferedReader in = null;
	Button Btconnect, Btdisconnect, BtUpdate;
    Button btBatDC, btTatCD, btLED1B, btLED1T, btLED1N,btLED2B, btLED2T,btBatPin,btTatPin;
	BufferedReader dataInputStream = null;
	TextView Status, Update, tvIP, tvPort;
    EditText edtIP,edtPort;
	String response = "", ip;
	int Received, port ;
	final Handler handler = new Handler();
	final Runnable updateUI = new Runnable() {
		public void run() {
			// Status.setText(response);
			Received = Integer.parseInt(response);
			if (Received == 1) {
				Update.setText("Chế Độ Hiện Tại: Tự Động");
			}
			if (Received == 0) {
				Update.setText("Chế Độ Hiện Tại: Bằng Tay");
			}

			if(Received>-1)
				{
					Status.setText("Trạng Thái: Đã Kết Nối");
					Status.setTextColor(Color.BLUE);
				}
			else {
				Status.setText("Trạng Thái: Không Kết Nối");
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_smart_home_main);
        ScrollView scrollView = (ScrollView) findViewById(R.id.scrollViewp);
        scrollView.setVerticalScrollBarEnabled(false);

		Btconnect = (Button) findViewById(R.id.btconnect);
		Btdisconnect = (Button) findViewById(R.id.btDisconnect);
		BtUpdate = (Button) findViewById(R.id.btUpdate);
		Status = (TextView) findViewById(R.id.Status);
		Update = (TextView) findViewById(R.id.tvUpdate);
        edtIP = (EditText) findViewById(R.id.edtIP);
        edtPort = (EditText) findViewById(R.id.edtPort);
        tvIP = (TextView) findViewById(R.id.tvIP);
        tvPort = (TextView) findViewById(R.id.tvPort);
        ip = edtIP.getText().toString();
        port = Integer.valueOf(edtPort.getText().toString());

        btBatDC = (Button) findViewById(R.id.btBatDC);
        btTatCD = (Button) findViewById(R.id.btTatCD);
        btBatPin = (Button) findViewById(R.id.btBatPin);
        btTatPin = (Button) findViewById(R.id.btTatPin);
        btLED1B = (Button) findViewById(R.id.btLED1B);
        btLED1T = (Button) findViewById(R.id.btLED1T);
        btLED1N = (Button) findViewById(R.id.btLED1N);
        btLED2B = (Button) findViewById(R.id.btLED2B);
        btLED2T = (Button) findViewById(R.id.btLED2T);
	}

	public void ConnectUp(View view) {
		OpenConnection();
	}

	public void OpenConnection() {
		new Thread(new Runnable() {
			public void run() {
				try {
					if (sck == null) {
						/*sck = new Socket("192.168.126.1", 8080);*/
//                        ip = "192.168.0.101";
//                        port = 8080;
                        sck = new Socket(ip,port);
						sck.setKeepAlive(true);
						screenConfig();

						in = new BufferedReader(new InputStreamReader(
								sck.getInputStream()));
						out = new PrintWriter(sck.getOutputStream());
						sendDataWithString("UD");
						int charsRead = 0;
						char[] buffer = new char[BUFFER_SIZE];

						while ((charsRead = in.read(buffer)) != -1) {
							response = new String(buffer).substring(0,
									charsRead);
							handler.post(updateUI);
						}

						screenConfig();
					}
				} catch (Exception e) {
					System.out.print(e + "");
				}
			}
		}).start();

	}

	public void screenConfig() {
		if (!sck.isClosed()) {
            btBatDC.setClickable(true);
            btTatCD.setClickable(true);
            btBatPin.setClickable(true);
            btTatPin.setClickable(true);
            btLED1B.setClickable(true);
            btLED1T.setClickable(true);
            btLED1N.setClickable(true);
            btLED2B.setClickable(true);
            btLED2T.setClickable(true);
			BtUpdate.setClickable(true);
			Btdisconnect.setClickable(true);
			Btconnect.setClickable(false);

		} else {
            btBatDC.setClickable(false);
            btTatCD.setClickable(false);
            btBatPin.setClickable(false);
            btTatPin.setClickable(false);
            btLED1B.setClickable(false);
            btLED1T.setClickable(false);
            btLED1N.setClickable(false);
            btLED2B.setClickable(false);
            btLED2T.setClickable(false);
			BtUpdate.setClickable(true);
			Btconnect.setClickable(true);
			Status.setText("Trạng Thái: Không Kết Nối");
			Status.setTextColor(Color.RED);
		}
	}

	public void Update(View view) {
		sendDataWithString("UD");
	}

	public void DisConnect(View view) {
		CloseConnection();
	}

	public void CloseConnection() {
		if (sck != null) {
			try {
				sck.close();
				screenConfig();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				sck = null;
			}
		}
	}

	public void sendDataWithString(String message) {
		if (message != null && sck != null) {
			out.write(message);
			out.flush();
		}
	}

    public void BatDC(View view) {
        sendDataWithString("BDC");
    }

    public void TatDC(View view) {
        sendDataWithString("TDC");
    }

    public void BatLED1(View view) {
        sendDataWithString("BL1");
    }

    public void TatLED1(View view) {
        sendDataWithString("TL1");
    }

    public void NhayLED1(View view) {
        sendDataWithString("NL1");
    }

    public void BatLED2(View view) {
        sendDataWithString("BL2");
    }

    public void TatLED2(View view) {
        sendDataWithString("TL2");
    }

    public void BatPin(View view) {
        sendDataWithString("NP");
    }

    public void TatPin(View view) {
        sendDataWithString("HP");
    }

    public void CaiDat(View view) {
        ip = edtIP.getText().toString();
        port = Integer.valueOf(edtPort.getText().toString());
        edtIP.setText("OK");
        edtPort.setText("OK");
    }

    public void MacDinh(View view) {
        edtIP.setText("192.168.0.101");
        edtPort.setText("8080");
        ip = edtIP.getText().toString();
        port = Integer.valueOf(edtPort.getText().toString());
    }
}
