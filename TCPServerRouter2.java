	import java.net.*;
   import java.io.*;

    public class TCPServerRouter2 {
       public static void main(String[] args) throws IOException {
         Socket clientSocket = null; // socket for the thread
         Object [][] RoutingTable = new Object [10][2]; // routing table
			int SockNum = 5556; // port number
			Boolean Running = true;
			int ind = 0; // indext in the routing table	
         String dest_name= null;
         String host_name= null;
         BufferedReader in; //reading destination
         String destination;
         

			//Accepting connections
         ServerSocket serverSocket = null; // server socket for accepting connections
         try {
            serverSocket = new ServerSocket(5556);
            System.out.println("ServerRouter is Listening on port: 5556.");
         }
             catch (IOException e) {
               System.err.println("Could not listen on port: 5556.");
               System.exit(1);
            }
			
			// Creating threads with accepted connections
			while (Running == true)
			{
			try {
				clientSocket = serverSocket.accept();
           in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            destination = in.readLine();
            dest_name = in.readLine();
				SThread2 t = new SThread2(RoutingTable, clientSocket, ind, destination, dest_name); // creates a thread with a random port
				t.start(); // starts the thread
				ind++; // increments the index
            System.out.println("ServerRouter connected with Client/ServerRouter1: " + clientSocket.getInetAddress().getHostAddress());
         }
             catch (IOException e) {
               System.err.println("Client/ServerRouter! failed to connect.");
               System.exit(1);
            }
			}//end while
			
			//closing connections
		   clientSocket.close();
         serverSocket.close();

      }
   }