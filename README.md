# peer-to-peer-distributed-system
A peer-to-peer distributed system using TCP connections for direct communication between server and client without the use of a central router.

This paradigm, along with interprocess communication and protocols, detail the communication process between computers. The system created allows for an exchange of synchronized messages between the server and client.

To successfully run this simulation, configuration is IMPORTANT. 4 separate CPUs in total are required to allow for 100 different nodes. One computer will have ClientPeer_SR1, a second will have ClientServer_SR2 adn file.txt, a third will have TCPServerRouter1 and SThread1, and the fourth will have TCPServerRouter2.

For the machine chosen to have ClientServer_SR2, the IP address listed should be changed to the IP address of the computer chosen to have ServerRouter2. In ServerRouter2, the IP address needs to be changed to the IP address of the comptuer chosen to be ClientPeer_SR1. In SThread1, the IP address needs to be changed to the IP address of the machine with ServerRouter2. Once the IP addresses have all be changed, the programs should compile and be ready to run.

Finally, to set up ClientServer_SR2, the router address needs to be changed to the IP address for ServerRouter1's computer. Within the same program, the IP address is changed to the machine with ClientServer_SR2 in two locations. The machines are to be connected to the same network, using the IPv4 address of each machine. After all programs have been configured correctly, they can be executed.

To execute, ServerRouter1 and ServerRouter2 need to be executed first and the matter does not matter. ServerRouter1 listens on socket 555 and ServerRouter2 listens on 5556. Client and Server can now be executed.

Again, 4 computers are needed for this simulation. The project can be imported into Eclipse.
