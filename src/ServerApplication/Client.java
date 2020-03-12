package ServerApplication;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client implements Runnable, PropertyChangeListener
{
  TCPServer tcpServer;
  private Socket socket;
  private PrintWriter out;
  private BufferedReader in;
  private String name;

  public Client(TCPServer tcpServer, Socket socket)
  {
    this.name = "anonymous user";
    this.socket = socket;
    this.tcpServer = tcpServer;
    tcpServer.addListener("message", this);

    try
    {
      in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
      out = new PrintWriter(socket.getOutputStream(), true);
    }
    catch (Exception e)
    {
      System.out.println("Server Error: Something went horribly wrong!!");
    }
  }

  @Override public void run()
  {
    try
    {
      String request;
      while ((request = in.readLine()) != null)
      {
        if (request.equals("help"))
        {
          out.println(tcpServer.getHelp());
        }
        else if (request.equals("history"))
        {
          out.println(tcpServer.getHistory());
        }
        else if (request.startsWith("set name "))
        {
          this.name = request.replace("set name ", "");
        }
        else
        {
          tcpServer.sendMessage(new Message(request, this));
        }

      }

      System.out.println("Server Log: Client (" + socket.getInetAddress()
          + ") has disconnected.");
      socket.close();
    }
    catch (Exception e)
    {
      System.out.println("Server Error: Something went horribly wrong!!");
    }
  }

  public String getName()
  {
    return name;
  }

  public void setName(String name)
  {
    this.name = name;
  }

  @Override public void propertyChange(PropertyChangeEvent event)
  {
    if (event.getPropertyName().equals("message"))
    {
      Message message = (Message) event.getNewValue();
      if (message.getSource() != this)
      {
        out.println(message.getFull());
      }
    }
  }
}
