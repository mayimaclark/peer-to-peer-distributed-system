import java.net.*;
import java.io.*;

public class TCPServerRouter1 {
   public static void main(String[] args) throws IOException {
      Socket clientSocket = null; // socket for the thread
      Object [][] RoutingTable = new Object [10][2]; // routing table
      int SockNum = 5555; // port number
      Boolean Running = true;
      int ind = 0; // indext in the routing table	
      String dest_name = null;
      String destination = null;
      BufferedReader in;
      String host_name=null;
   
   		//Accepting connections
      ServerSocket serverSocket = null; // server socket for accepting connections
      try {
         serverSocket = new ServerSocket(5555);
         System.out.println("ServerRouter is Listening on port: 5555.");
      }
      catch (IOException e) {
         System.err.println("Could not listen on port: 5555.");
         System.exit(1);
      }
   		
   		// Creating threads with accepted connections
      while (Running == true)
      {
         try {
            clientSocket = serverSocket.accept();
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            destination = in.readLine(); //initial receive from the S-Router S2
            dest_name = in.readLine(); //initial receive from the S-Router S2
            SThread1 t = new SThread1(RoutingTable, clientSocket, ind, destination, dest_name);
            t.start(); // starts the thread
            ind++; // increments the index
            System.out.println("ServerRouter connected with Client/Server: " + clientSocket.getInetAddress().getHostAddress());
         }
         catch (IOException e) {
            System.err.println("Client/Server failed to connect.");
            System.exit(1);
         }
            
                        
      }//end while
   		
   		//closing connections
      clientSocket.close();
      serverSocket.close();
   
   }
}