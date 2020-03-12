package ServerApplication;

import utility.NamedPropertyChangeSubject;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class TCPServer implements Runnable, NamedPropertyChangeSubject
{
  private PropertyChangeSupport property;

  private ArrayList<Message> messages;
  private int port;
  private ServerSocket serverSocket;

  public TCPServer(int port) throws IOException
  {
    this.property = new PropertyChangeSupport(this);
    this.port = port;
    messages = new ArrayList<Message>();
    serverSocket = new ServerSocket(port);

    new Thread(TCPServer::displayStatus).start();
  }

  @Override public void run()
  {
    try
    {
      while (true)
      {
        System.out.println("\rServer Log: Waiting for new Client..");
        Socket newClientSocket = serverSocket.accept();
        new Thread(new Client(this, newClientSocket)).start();
        System.out.println(
            "\rServer Log: New Client (" + newClientSocket.getInetAddress()
                + ") has joined the Server. ");
      }
    }
    catch (Exception e)
    {
    }
  }

  public void sendMessage(Message message)
  {
    System.out.println("\rServer Log: New Message(" + message.getText() + ")");
    messages.add(message);
    property.firePropertyChange("message", null, message);
  }

  @Override public void addListener(String propertyName,
      PropertyChangeListener listener)
  {
    property.addPropertyChangeListener(propertyName, listener);
  }

  @Override public void removeListener(String propertyName,
      PropertyChangeListener listener)
  {
    property.removePropertyChangeListener(propertyName, listener);
  }

  private static void displayStatus()
  {
    try
    {
      System.out.println("Press Ctrl-c to suspend the Server.");
      while (true)
      {
        System.out.print("Server running [\\]");
        Thread.sleep(250);
        System.out.print("\r");
        System.out.print("Server running [/]");
        Thread.sleep(250);
        System.out.print("\r");
        System.out.print("Server running [-]");
        Thread.sleep(250);
        System.out.print("\r");
      }
    }
    catch (Exception e) { }
  }

  public String getHelp()
  {
    return
        "~~~ Available Commands ~~~\n"+
        "server-side commands: 'history', 'help'.\n"+
        "user-side commands: 'help', 'exit'\n"+
        "~~~ ~~~~~~~~~ ~~~~~~~~ ~~~\n";
  }

  public String getHistory()
  {
    String history = "~~~ Message history ~~~\n";
    for (int i = 0; i < messages.size(); i++)
    {
      history += messages.get(i).getFull()+"\n";
    }
    history += "~~~ ~~~~~~~ ~~~~~~~ ~~~";
    return history;
  }
}
