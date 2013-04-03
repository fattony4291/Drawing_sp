package TBianco.Drawing.First;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class Connection {
	
	Socket socket;
	ObjectOutputStream out;
	ObjectInputStream in;
	boolean connected = false;
	
	Connection() throws UnknownHostException, IOException {
		socket = new Socket("mirror.etown.edu",9200);
		
		out = new ObjectOutputStream(socket.getOutputStream());
		in = new ObjectInputStream(socket.getInputStream());
		connected = true;
	}
	
	public void write(Object send){
		try {
			out.writeObject(send);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
