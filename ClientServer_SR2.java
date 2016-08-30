import java.io.*;
import java.net.*;

public class ClientServer_SR2 { // Peer2
   public static void main(String[] args) throws IOException {
   
      String name= "ClientServer_SR2"; // Name of this Node
   
   
   
      	
   // Variables for setting up connection and communication
      Socket Socket = null; // socket to connect with ServerRouter
      PrintWriter out = null; // for writing to ServerRouter
      BufferedReader in = null; // for reading form ServerRouter
      PrintWriter outTo = null; // for writing to node
      BufferedReader inFrom = null; // for reading form node
      InetAddress addr = InetAddress.getLocalHost();
      String host = addr.getHostAddress(); // Server machine's IP			
      String routerName = "10.99.20.108"; // ServerRouter2 host name
      int SockNum = 5556; // port number
      Socket clientSocket = null; // socket for the thread
      
   
      
        		
   		// Tries to connect to the ServerRouter
      try {
         Socket = new Socket(routerName, SockNum);
         out = new PrintWriter(Socket.getOutputStream(), true);
         in = new BufferedReader(new InputStreamReader(Socket.getInputStream()));
      } 
      catch (UnknownHostException e) {
         System.err.println("Don't know about router: " + routerName);
         System.exit(1);
      } 
      catch (IOException e) {
         System.err.println("Couldn't get I/O for the connection to: " + routerName);
         System.exit(1);
      }
   		
   
         
         
         	
      	// Variables for message passing			
      String fromServer; // messages sent to ServerRouter
      String fromnode; // messages received from ServerRouter      
      String address; // destination IP Peer12
      String Node_Name_Dest = "ClientServer_SR1";
      int new_socket;
   
   		
      // Communication process (initial sends/receives with ServerRouter2
      out.println("10.99.23.24");// initial send (Name of the node it would like to communicate with) CleintPeer1
      out.println(Node_Name_Dest);
       
      fromServer = in.readLine();//initial receive from router (verification of connection)
      System.out.println("ServerRouter2: " + fromServer);
      fromServer = in.readLine();//initial receive from router (verification of connection)      
      System.out.println("ServerRouter2: " + fromServer);
      new_socket = Integer.parseInt(fromServer);
   	
      Socket.close();	         
      try{     	
      ServerSocket srvr = new ServerSocket(new_socket);
      Socket skt = srvr.accept();
      System.out.println("Listening");
      
      outTo = new PrintWriter(skt.getOutputStream(), true);
      inFrom = new BufferedReader(new InputStreamReader(skt.getInputStream()));
      fromnode = inFrom.readLine();
      System.out.println("ClientServer_SR1 said: " + fromnode);
      outTo.println("Connected to ClientServer_SR1");

       }
       catch(IOException e){
       System.err.println("Couldnt listen on port");
       
       }
          
                        
                  
   		// Communication while loop
      while ((fromnode = inFrom.readLine()) != null) {
         System.out.println("ClientServer_SR1 said: " + fromnode);
         if (fromnode.equals("Bye.")){// exit statement
            try {
            //thread to sleep for the specified number of milliseconds
               Thread.sleep(5000);
               break;
            } 
            catch ( java.lang.InterruptedException ie) {
               System.out.println(ie);
            }
         }
         fromnode = fromnode.toUpperCase(); // converting received message to upper case
         System.out.println("ClientServer_SR2: " + fromnode);
         outTo.println(fromnode); // sending the converted message back to the Client 
      }
   
         
   
     
   		// closing connections
      out.close();
      in.close();
      Socket.close();
      outTo.close();
      inFrom.close();
      // Socket2.close();
   //       
   }
}