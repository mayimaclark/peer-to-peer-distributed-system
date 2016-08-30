import java.io.*;
import java.net.*;
import java.lang.Exception;

	
public class SThread2 extends Thread 
{
   private Object [][] RTable; // routing table
   private PrintWriter out, outTo; // writers (for writing back to the machine and to destination)
   private BufferedReader in, inFrom; // reader (for reading from the machine connected to)
   private String inputLine, outputLine, destination, addr; // communication strings
   private Socket outSocket; // socket for communicating with a destination
   private int ind; // indext in the routing table
   String dest_nam;
   String host_nam;
   String dest;
   
   

	// Constructor
   SThread2(Object [][] Table, Socket toClient, int index, String destination, String dest_name) throws IOException
   {
      out = new PrintWriter(toClient.getOutputStream(), true);
      in = new BufferedReader(new InputStreamReader(toClient.getInputStream()));
      RTable = Table;
      addr = toClient.getInetAddress().getHostAddress();
      RTable[index][0] = addr; // IP addresses 
      RTable[index][1] = toClient; // sockets for communication
      ind = index;
      dest_nam=dest_name;
      dest=destination;
    
      
   }
	
	// Run method (will run for each machine that connects to the ServerRouter)
   public void run()
   {
      long x0=0;
      long x1=0;
      long x=0;
   
      try
      {
         x0= System.currentTimeMillis();
         
         
      
      // Initial sends/receives
        out.println("Connected to the ServerRouter2."); // confirmation of connection
        inputLine = in.readLine();
         
         System.out.println("Forwarding to " + dest_nam + "'s ServerRouter1");
         
      
      // waits 10 seconds to let the routing table fill with all machines' information
         try{
            Thread.currentThread().sleep(10000); 
         }
         catch(InterruptedException ie){
            System.out.println("Thread interrupted");
         }
      
      // loops through the routing table to find the destination
         
         for ( int i=0; i<10; i++) //finding the ServerRouter1 in the Routing Table
         {
           
            if (dest.equals((String) RTable[i][0])){
           
               outSocket = (Socket) RTable[i][1]; // gets the socket for communication from the table
               System.out.println("Rtable look up time "+ ((System.currentTimeMillis()-x0)/1000)+ " Found destination: " + dest_nam );
               outTo = new PrintWriter(outSocket.getOutputStream(), true); // assigns a writer
               inFrom = new BufferedReader(new InputStreamReader(outSocket.getInputStream()));
               
               out.println("Verified");
               inputLine = in.readLine();
               System.out.println(inputLine);
               outTo.println(inputLine);
               
               
               
               
                }
                        
         }
         
         out.println(inputLine);
      
      
      
          
         
         
                     
      }// end try
      catch (IOException e) {
         System.err.println("Could not listen to socket.");
         System.exit(1);
      }
   }
}