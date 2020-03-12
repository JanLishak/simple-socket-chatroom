package ServerApplication;

import ClientApplication.TCPClient;

import java.io.IOException;

public class Main
{
  public static void main(String[] args) throws InterruptedException, IOException
  {
    if (args.length>0)
    {
      if (args[0].equals("server")){
        new Thread(new TCPServer(2244)).start();
      }

      else if (args[0].equals("client")){
        new TCPClient();
      }
      else{
        System.out.println("Error: Invalid argument.");
      }
    }
    else
      System.out.println("Error: Add 'server' or 'client' argument.");
  }
}

