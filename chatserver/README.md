# How to make Chating Server

## Client Class
---
:
Each client has its socket and performs receive and send functions.

- receive method
  > You will receive a message from clients coming into the main server. It then sends this message back to all clients connected to the server (send method).
- send method
  > Whenever a message arrives on the main server, it sends a new message to the client.
## Main Class
---
: The server creates a socket for the server and opens the port. It waits for the clients to come in and allocates the threads when the client comes. When a lot of clients come in at once, the threads are amplified, so they are managed using the thread pool. Clients entering the server are managed through a vector called Clients.

** 
For fun, I've added simsim(심심이). simsim is a feature that gives users advice. If your username is '익명' and you enter '심심이' in the chat window, it will give you advice on various studies.

-startServer method
  > 
It opens the port and waits until clients come in. When you are connected, you will be notified that you are connected.
-stopServer method
  > This method terminates the server.