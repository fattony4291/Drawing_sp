package TBianco.Drawing.First;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OptionalDataException;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import android.os.AsyncTask;
import android.util.Log;

public class Connection extends AsyncTask<DrawingActivity, Void, Void> implements Runnable {
	
	//Packet Info
    private static final int PACKET_TYPE_HDR = 0;
    private static final int SIZE_HDR = 1;
    private static final int CLIENT_ID_HDR = 2;
    private static final int PAGE_ID_HDR = 3;
    private static final int LINE_ID_HDR = 4;
    //Message Types
    private static final int LINE_MSG = 0;
    private static final int IMAGE_MSG = 1;
    private static final int TEXT_MSG = 2;
    private static final int SHAPE_MSG = 3;
    private static final int UNDO_MSG = 4;
    private static final int CLEAR_MSG = 5;
    private static final int CLEAR_ALL_MSG = 6;
    private static final int ADD_PAGE_MSG = 7;
    private static final int REQUEST_PAGE_MSG = 8;
    private static final int DELETE_PAGE_MSG = 9;
    private static final int REFRESH_PAGE_REQUEST_MSG = 10;
    private static final int REFRESH_PAGE_MSG = 11;
    private static final int CLIENT_ID_MSG = 12;
    private static final int CLIENT_ID_REQUEST_MSG = 13;
    private static final int REDO_MSG = 14;
    private static final int SESSION_NEW_MSG = 15;
    private static final int SESSION_LOAD_MSG = 16;
    private static final int SESSION_SAVE_MSG = 17;
    private static final int NEW_HOST_MSG = 18;
    private static final int CLOSE_CONNECTION_MSG = 19;
    private static final int CHECK_PASSWORD_MSG = 20;
    private static final int DELETE_MSG = 21;
    private static final int HEARTBEAT_MSG = 22;
	
	
	int destSoc = 9200;
	String address = "mirror.etown.edu";
    private long lastReadTime;
	
    // Connection info: native socket handle
    private Socket socket;

    // Read stream
    private BufferedInputStream buffIn;
	
    private int packetInfo[]= new int[5];
    private byte[] packet;

    // Write stream
    private BufferedOutputStream buffOut;
	
	Connection() throws IOException{
		
		socket = new Socket(address,destSoc);
		
		buffIn = new BufferedInputStream(socket.getInputStream());
        buffOut = new BufferedOutputStream(socket.getOutputStream());
        buffOut.flush();

        for(int i = 0; i < packetInfo.length; i++)
                packetInfo[i] = -2;
	}
	
	public void write(Object send){
		
		
		
	}

	@Override
	public void run() {
		 while(true){//!socket.isClosed()                        

             try{

                     //Check if entire packet header has been read in
                     if(packetInfo[LINE_ID_HDR] == -2){
                             //Still more to read.

                             byte[] b = new byte[4];
                             int result = buffIn.read(b);

                             int value = byteArrayToInt(b);

                             for(int i = 0; i < packetInfo.length; i++)
                             {
                                     if(packetInfo[i] == -2){
                                             packetInfo[i] = value;

                                             break;
                                     }
                             }

                     }
                     else{                                   
                             packet = new byte[packetInfo[SIZE_HDR]];

                             int result = buffIn.read(packet);
                             while(result < packetInfo[SIZE_HDR]){//reads in entire packet
                                     result += buffIn.read(packet,result,packetInfo[SIZE_HDR]-result);
                                     //System.out.print(result + ".");
                             }

                             //process packet to correct client
                             processPacket(packet,packetInfo);

                             if (packetInfo[PACKET_TYPE_HDR] == NEW_HOST_MSG){
                                     //If packet type is this, it means the new host was told they are the new host and this connection can be closed.
                                     close();
                                     break;
                             }

                             //reset packet info.
                             for(int i = 0; i < 5; i++)
                                     packetInfo[i] = -2;


                     }

                     lastReadTime = System.currentTimeMillis();

             }
             catch (SocketTimeoutException t){
                     
                             
                             //close connection
                             close();
                    
             }
             catch (IOException e) {
                     
                             close();
                     
                     break;
             }

     }


	}
	
	private void processPacket(DrawingActivity act, byte[] _packet, int[] _packetInfo) throws IOException{
        byte[] rawPacket = _packet;
        int[] info = _packetInfo;

        String temp;
        String roomName;

        switch (info[PACKET_TYPE_HDR])
        {
        case LINE_MSG:
        case IMAGE_MSG:
        case TEXT_MSG:
        case SHAPE_MSG:
        case UNDO_MSG:
               
                break;
        case CLEAR_MSG:
        case CLEAR_ALL_MSG:

                break;
        case ADD_PAGE_MSG:
                break;
        case REQUEST_PAGE_MSG:
                break;
        case DELETE_PAGE_MSG:
                break;
        case REFRESH_PAGE_REQUEST_MSG:
                
                break;
        case REFRESH_PAGE_MSG:
                
                break;
        
        case REDO_MSG:
                
                break;

        
        case DELETE_MSG:
                
                break;

        }


}
	
	public void sendPacket(byte[] _packet, int[] _packetInfo) throws IOException{
        if(socket.isClosed()){
                close();
        }

        //write header to buffOut
        for(int i = 0; i < 5; i++){
                buffOut.write(intToByteArray(_packetInfo[i]));
        }

        //write data to buffOut         
        buffOut.write(_packet);

        //flush
        buffOut.flush();
	}
	
	public void close(){


		try {
			//close everything up
			buffIn.close();
			buffOut.close();
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private final int byteArrayToInt(byte[] b){
		int value = 0;
		for (int i = 0; i < b.length; i++)
		{
			value += ((int) b[i] & 0xffL) << (8 * i);
		}
		return value;
	}

	private final byte[] intToByteArray(int i){
		return new byte[] {
				(byte)(i),
				(byte)(i >>> 8),
				(byte)(i >>> 16),
				(byte)(i >>> 24)
		};
	}

	@Override
	protected Void doInBackground(DrawingActivity... params) {
		run();
		return null;
	}



}
