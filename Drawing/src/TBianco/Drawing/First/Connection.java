package TBianco.Drawing.First;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OptionalDataException;
import java.net.Socket;
import java.net.UnknownHostException;

import android.os.AsyncTask;
import android.util.Log;

public class Connection extends AsyncTask<Void, Void, Void> implements Runnable {
	
	boolean connected;
	int destSoc = 9200;
	String address = "mirror.etown.edu";
	
    // Connection info: native socket handle
    private Socket socket;

    // Read stream
    private BufferedOutputStream out;
	
    private int packetInfo[]= new int[5];
    private byte[] packet;

    // Write stream
    private BufferedInputStream in;;
	
	Connection(){
		connected = false;
	}
	
	public void write(Object send){
		try {
			out = new BufferedOutputStream(socket.getOutputStream());
		} catch (IOException e1) {
			Log.d("Network Error", "IOException on output stream create");
			e1.printStackTrace();
		}
		
		try {
			//out.writeObject(send);
			out.close();
		} catch (IOException e) {
			Log.d("Network Error", "IOException on output stream send");
			e.printStackTrace();
		}
		
		
	}

	@Override
	public void run() {
		try {
			socket = new Socket(address,destSoc);
			
			connected = true;
		} catch (UnknownHostException e) {
			Log.d("Network Error", "Unknown host.");
			e.printStackTrace();
		} catch (IOException e) {
			Log.d("Network Error", "IOException on connect");
			e.printStackTrace();
		}
		
		try {
				in = new BufferedInputStream(socket.getInputStream());
			} catch (IOException e) {
				Log.d("Network Error", "IOException on input stream create");
				e.printStackTrace();
			}
		
		while(true)
		{
			
			
		}
	}

	@Override
	protected Void doInBackground(Void... params) {
		run();
		return null;
	}

}
