# How to make Chat Client program

## JAVAFX for GUI
---

  
With SceneBuilder, gui can be implemented through the gui environment. You do not have to write code yourself, but you can use the scene builder to organize and decorate layouts.

  The overall layout of the program is boderPane, root. And this root consists of HBox, TextArea, and pane.

  - HBox
    >IP address, user name, and port number.
  - TextArea
    >   This is the window that is displayed when the user enters a message.
  - Pane
    > This window contains login, chat input window, and submit button.
    

  
## Main function
---

The main functions of the client programe are divided into start, stop, receive message, and send message. It is very similar to what you did in the server program, but the client does not use the thread pool because it only needs one thread.

- startClient
  > Open the client's socket and run the receive method to get the new chat
- stopClient
  > It closes the socket and terminates the program.
- receive
  >  It confirms whether a new message has come and puts a message in the text window. 
- send
  > It creates a message and sends it to the server.