package com.baonguyen.bai006_vuonthongminh;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

public class ClientThread implements Runnable
{
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
	public ClientThread(){}

	public void run()
	{
		try 
		{
			socket = new Socket(ip, port);
			isConnect = socket.isConnected();
			inputStream = socket.getInputStream();
			outputStream = socket.getOutputStream();
			
			//To monitor if receive Msg from Server
			new Thread()
			{
				@Override
				public void run()
				{
					byte[] buffer = new byte[1024];
					
					final StringBuilder stringBuilder = new StringBuilder();
					try
					{
						while(socket.isConnected())
						{
							int readSize = inputStream.read(buffer);
							
							//If Server is stopping
							if(readSize == -1)
							{
								inputStream.close();
								outputStream.close();
							}
							if(readSize == 0)continue;
							
							//Update the receive editText
							stringBuilder.append(new String(buffer, 0, readSize));
							Message msg = new Message();
							msg.what = 0x123;
							msg.obj = stringBuilder.toString();
							receiveHandler.sendMessage(msg);
                            // xóa bộ đệm cho lần sau
                            stringBuilder.delete(0,stringBuilder.length());
						}
					}
					catch(IOException e)
					{
						e.printStackTrace();
					}
				}
				
			}.start();
			
			//To Send Msg to Server
			Looper.prepare();
			sendHandler = new Handler()
			{
				@Override
				public void handleMessage(Message msg)
				{
					if (msg.what == 0x852)
					{
						try
						{
							outputStream.write((msg.obj.toString() + "\r\n").getBytes());
							outputStream.flush();
						}
						catch (Exception e)
						{
							e.printStackTrace();
						}
					}
				}
			};
			Looper.loop();
			
		} catch (SocketTimeoutException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (UnknownHostException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
