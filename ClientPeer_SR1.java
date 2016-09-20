import java.io.*;
import java.net.*;
import java.util.*;

     

public class ClientPeer_SR1 {
   public static void main(String[] args) throws IOException {
   
      String name= "ClientPeer_SR1"; // Name of this node
     
      
      ArrayList <Long> elements = new ArrayList<>();
      ArrayList <Integer> elements2 = new ArrayList<>();
         
   	// Variables for setting up connections and communication
      Socket Socket = null; // socket to connect with ServerRouter
      PrintWriter out = null; // for writing to ServerRouter
      BufferedReader in = null; // for reading form ServerRouter
      Socket Socket2 = null; // socket to connect with Node
      InetAddress addr = InetAddress.getLocalHost();
      String host = addr.getHostAddress(); // Client machine's IP
      String routerName = "192.168.1.6";  // ServerRouter1 host name
      int SockNum = 5555; // port number
      int new_socket;
      String address;
      String fromnode;
   
   		
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
      Reader reader = new FileReader("file.txt"); 
      BufferedReader fromFile =  new BufferedReader(reader); // reader for the string file
      String fromServer; // messages received from ServerRouter
      String fromUser; // messages sent to ServerRouter
      String Node_Address ="192.168.1.5";
      String Node_Name_Dest="ClientPeer_SR2";
      long t0, t1, t;
   		
      // Communication process (initial sends/receives with ServerRouter1
      
      out.println(Node_Address);// initial send (Name of the node it would like to communicate with)
      out.println(Node_Name_Dest);// initial send (Name of the node it would like to communicate with)
   
      fromServer = in.readLine();//initial receive from router (verification of connection)
      System.out.println("ServerRouter1: " + fromServer);
      fromServer = in.readLine();//initial receive from router (verification of connection)      
      System.out.println("ServerRouter1: " + fromServer);
      fromServer = in.readLine();//Server found destination     
      System.out.println("ServerRouter1: " + fromServer);
      fromServer = in.readLine();//Server sends socket    
      new_socket = Integer.parseInt(fromServer);
      System.out.println(new_socket);
   
      Socket.close();
   
      Socket skt = new Socket("192.168.1.5", new_socket);
      BufferedReader inFrom = new BufferedReader(new InputStreamReader(skt.getInputStream()));
      PrintWriter outTo = new PrintWriter(skt.getOutputStream(), true);
   
      // Communication process (initial sends/receives with ServerRouter1
      outTo.println("Connected to Node");// initial send 
      
   
      
      t0 = System.currentTimeMillis();     
   		// Communication while loop
   
   
   
      while ((fromnode = inFrom.readLine()) != null) {
         System.out.println("ClientServer_SR2 : " + fromnode);
         t1 = System.currentTimeMillis();
         if (fromnode.equals("Bye.")){ 
               // exit statement //
            break;}
         t = t1 - t0;
         
         elements.add(t);
            
         System.out.println("Cycle time: "+ t);
            
         fromUser = fromFile.readLine();// reading strings from a file
         elements2.add(fromUser.length());
            
            
         System.out.println("CURRENT RUNNING STATS****************");
         double avglength= elements2.stream().mapToLong(val1-> val1).average().getAsDouble();
         System.out.println(" Average message size:"+avglength);
         
         double avgtime = elements.stream().mapToLong(val-> val).average().getAsDouble();
         System.out.println(" Average cycle time:"+avgtime);
         
         
         if(Collections.max(elements)>100){
            elements.remove(Collections.max(elements));
         } 
         
                        
         if (fromUser != null) {
               
            //System.out.println("ClientServer_SR1 : " + fromUser);
            outTo.println(fromUser); // sending the strings to the ServerPeer via ServerRouter S1
            t0 = System.currentTimeMillis();
         }
      }
   
   
      	
   		// closing connections
      out.close();
      in.close();
      Socket.close();
   }
}
