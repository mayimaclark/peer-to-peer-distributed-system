import java.io.*;
import java.net.*;
import java.lang.Exception;
import java.util.Random;

	
public class SThread1 extends Thread 
{
   private Object [][] RTable; // routing table
   private PrintWriter out, outTo; // writers (for writing back to the machine and to destination)
   private BufferedReader in; // reader (for reading from the machine connected to)
   private String inputLine, outputLine, destination, addr; // communication strings
   private Socket outSocket; // socket for communicating with a destination
   private int ind; // indext in the routing table
   private String dest = null;
   String nam;
   Socket SocketS2 = null; // socket for connecting SRouter S1 with SRouter S2
   BufferedReader inFrom;
   PrintWriter outToS2;
   InetAddress addr1 = InetAddress.getLocalHost();
   String host = addr1.getHostAddress();
   String dest_address;
   String dest_nam;
   String host_nam;
   
   int new_socket;



	// Constructor
   SThread1(Object [][] Table, Socket toClient, int index,  String destination,String dest_name) throws IOException
   {
      out = new PrintWriter(toClient.getOutputStream(), true);
      in = new BufferedReader(new InputStreamReader(toClient.getInputStream()));
      RTable = Table;
      addr = toClient.getInetAddress().getHostAddress();
      RTable[index][0] = addr; // IP addresses 
      RTable[index][1] = toClient; // sockets for communication
      ind = index;
      dest_nam=dest_name;
      dest= destination;
   
   }
	
	// Run method (will run for each machine that connects to the ServerRouter)
   public void run()
   {
      try
      {
      // Initial sends/receives
         
         System.out.println("Forwarding to " + dest_nam +"'s ServerRouter2");
         out.println("Connected to the ServerRouter1.");
         out.println("Looking for " + dest_nam +" on ServerRouter2"); // confirmation of connection
      
      
            
       //Try to connect to S2, S1 will act as "Client in this connection
         SocketS2 = new Socket("192.168.1.18", 5556); //**S2 IP**//
         outTo = new PrintWriter(SocketS2.getOutputStream(), true);       
         inFrom = new BufferedReader(new InputStreamReader(SocketS2.getInputStream()));
         
        
         outTo.println(dest);  //Sends the Clients desired name and asking ip
         outTo.println("M4");
         
         inputLine =  inFrom.readLine() ; 
         System.out.println("ServerRouter2 said: " + inputLine);   
         outTo.println("N1");  //Sends the Clients desired name and asking ip   
                  
                  
         //if it says found it          
         inputLine = inFrom.readLine();// S2 will say it found the destionation                            
         if("Verified".equals(inputLine)){                        
         
            System.out.println("ServerRouter2 said: " + inputLine);
            out.println("Found Destination"); // Alerts Client destination has been found
            new_socket= 5556+(int)(Math.random()*((6000-5556)+1)); //randomly generate socket number
            
            outTo.println(new_socket); //send S2 for new TCP Link
         // waits 5 seconds 
            try{
               Thread.currentThread().sleep(5000); 
            }
            catch(InterruptedException ie){
               System.out.println("Thread interrupted");
            }
            out.println(new_socket);//send N1 socket for new TCP Link
         }
         
      
         
       
      }// end try
      catch (IOException e) {
         System.err.println("Could not listen to socket.");
         System.exit(1);
      }
   }
}