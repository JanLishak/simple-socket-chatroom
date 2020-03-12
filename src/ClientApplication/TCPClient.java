package ClientApplication;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class TCPClient
{
  public TCPClient() throws IOException
  {
    Socket mySocket = null;
    try
    {
      mySocket = new Socket("localhost", 2244);
    }
    catch (IOException e)
    {
      System.out.println("Error: Cannot connect to the server!");
      return;
    }

    BufferedReader in = new BufferedReader(
        new InputStreamReader(mySocket.getInputStream()));
    PrintWriter out = new PrintWriter(mySocket.getOutputStream(), true);

    Scanner keyboard = new Scanner(System.in);
    String input;
    System.out.println(
        "You have joined the chetroom. Write 'help' to get a list of all commands.\nTo leave the chat room write the command 'exit'.\nWrite a message or use a command: ");

    new Thread(() -> {
      try
      {
        String readLine;
        while ((readLine = in.readLine()) != null)
        {
          System.out.println(readLine);
        }
      }
      catch (Exception e) { }
    }).start();

    while (( input = keyboard.nextLine()) != null && !(input.equals("exit")))
    {
      out.println(input);
    }

    mySocket.close();
    System.out.println("Connection closed.");
  }
}