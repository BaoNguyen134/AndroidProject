package com.baonguyen.smartgarden;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
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

	Button Btcl1Relay1, Btcl1Relay2, Btcl2Relay1, Btcl2Relay2, Btcl3Relay1,
			Btcl3Relay2, Btconnect, Btdisconnect, BtUpdate;
	BufferedReader dataInputStream = null;
	TextView Status;
	String response = "";
	int Received;
	final Handler handler = new Handler();
	final Runnable updateUI = new Runnable() {
		public void run() {
			// Status.setText(response);
			Received = Integer.parseInt(response);
			if (Received == 100) {
			
				Btcl1Relay1.setText("Relay1 - Tắt");
				Btcl1Relay1.setTextColor(Color.RED);
				Btcl1Relay2.setText("Relay2 - Tắt");
				Btcl1Relay2.setTextColor(Color.RED);
			}
			if (Received == 101) {
			
				Btcl1Relay1.setText("Relay1 - Tắt");
				Btcl1Relay2.setTextColor(Color.RED);
				Btcl1Relay2.setText("Relay2 - Bật");
				Btcl1Relay2.setTextColor(Color.GREEN);
				
			}
			if (Received == 110) {
				
				Btcl1Relay1.setText("Relay1 - Bật");
				Btcl1Relay1.setTextColor(Color.GREEN);
				Btcl1Relay2.setText("Relay2 - Tắt");
				Btcl1Relay2.setTextColor(Color.RED);
			}
			if (Received == 111) {
				
				Btcl1Relay1.setText("Relay1 - Bật");
				Btcl1Relay1.setTextColor(Color.GREEN);
				Btcl1Relay2.setText("Relay2 - Bật");
				Btcl1Relay2.setTextColor(Color.GREEN);
			}
			if (Received == 200) {
				
				Btcl2Relay1.setText("Relay1 - Tắt");
				Btcl2Relay1.setTextColor(Color.RED);
				Btcl2Relay2.setText("Relay2 - Tắt");
				Btcl2Relay1.setTextColor(Color.RED);
			}
			if (Received == 201) {
				
				Btcl2Relay1.setText("Relay1 - Tắt");
				Btcl2Relay1.setTextColor(Color.RED);
				Btcl2Relay2.setText("Relay2 - Bật");
				Btcl2Relay1.setTextColor(Color.GREEN);
			}
			if (Received == 210) {
				
				Btcl2Relay1.setText("Relay1 - Bật");
				Btcl2Relay1.setTextColor(Color.GREEN);
				Btcl2Relay2.setText("Relay2 - Tắt");
				Btcl2Relay2.setTextColor(Color.RED);
			}
			if (Received == 211) {
				
				Btcl2Relay1.setText("Relay1 - Bật");
				Btcl2Relay1.setTextColor(Color.GREEN);
				Btcl2Relay2.setText("Relay2 - Bật");
				Btcl2Relay1.setTextColor(Color.GREEN);
					
			} 
			
			if (Received == 300) {
				
				Btcl3Relay1.setText("Relay1 - Tắt");
				Btcl3Relay1.setTextColor(Color.RED);
				Btcl3Relay2.setText("Relay2 - Tắt");
				Btcl3Relay2.setTextColor(Color.RED);
			}
			if (Received == 301) {
				
				Btcl3Relay1.setText("Relay1 - Tắt");
				Btcl3Relay1.setTextColor(Color.RED);
				Btcl3Relay2.setText("Relay2 - Bật");
				Btcl3Relay2.setTextColor(Color.GREEN);
			}
			if (Received == 310) {
				
				Btcl3Relay1.setText("Relay1 - Bật");
				Btcl3Relay1.setTextColor(Color.GREEN);
				Btcl3Relay2.setText("Relay2 - Tắt");
				Btcl3Relay2.setTextColor(Color.RED);
			}
			if (Received == 311) {
				
				Btcl3Relay1.setText("Relay1 - Bật");
				Btcl3Relay1.setTextColor(Color.GREEN);
				Btcl3Relay2.setText("Relay2 - Bật");
				Btcl3Relay2.setTextColor(Color.GREEN);
					
			} 
			if(Received>0)
				{
					Status.setText("Connected");
					Status.setTextColor(Color.GREEN);
				}
			
			else
				Status.setText("Disconnect");
				
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_smart_home_main);
		Btcl1Relay1 = (Button) findViewById(R.id.btC1R1);
		Btcl1Relay2 = (Button) findViewById(R.id.btC1R2);
		Btcl2Relay1 = (Button) findViewById(R.id.btC2R1);
		Btcl2Relay2 = (Button) findViewById(R.id.btC2R2);
		Btcl3Relay1 = (Button) findViewById(R.id.btC3R1);
		Btcl3Relay2 = (Button) findViewById(R.id.btC3R2);
		Btconnect = (Button) findViewById(R.id.btconnect);
		Btdisconnect = (Button) findViewById(R.id.btDisconnect);
		BtUpdate = (Button) findViewById(R.id.btUpdate);
		Status = (TextView) findViewById(R.id.Status);
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
						sck = new Socket("192.168.0.100", 8080);
						sck.setKeepAlive(true);
						screenConfig();

						in = new BufferedReader(new InputStreamReader(
								sck.getInputStream()));
						out = new PrintWriter(sck.getOutputStream());
						sendDataWithString("3");
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

			Btcl1Relay1.setClickable(true);
			Btcl1Relay2.setClickable(true);
			Btcl2Relay1.setClickable(true);
			Btcl2Relay2.setClickable(true);
			Btcl3Relay1.setClickable(true);
			Btcl3Relay2.setClickable(true);
			BtUpdate.setClickable(true);
			Btdisconnect.setClickable(true);
			Btconnect.setClickable(false);

		} else {
			Btcl1Relay1.setClickable(false);
			Btcl1Relay2.setClickable(false);
			Btcl2Relay1.setClickable(false);
			Btcl2Relay2.setClickable(false);
			Btcl3Relay1.setClickable(false);
			Btcl3Relay2.setClickable(false);
			BtUpdate.setClickable(true);
			Btconnect.setClickable(true);
			Status.setText("Disconect");
			Status.setTextColor(Color.RED);

		}
	}

	public void SendMessageC1R1(View view) {
		sendDataWithString("1");
	}

	public void SendMessageC1R2(View view) {
		sendDataWithString("2");
	}
	
	public void SendMessageC2R1(View view) {
		sendDataWithString("4");
	}

	public void SendMessageC2R2(View view) {
		sendDataWithString("5");
	}
	public void SendMessageC3R1(View view) {
		sendDataWithString("A");
	}

	public void SendMessageC3R2(View view) {
		sendDataWithString("B");
	}
	public void Update(View view) {
		sendDataWithString("3");
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

}
