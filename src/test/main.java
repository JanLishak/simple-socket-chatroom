package test;

import java.util.Scanner;

public class main
{
  public static void main(String[] args)
  {
    new Thread(new runnable()).start();
    Scanner kb = new Scanner(System.in);
    System.out.println("you wrote:" + kb.nextLine());
  }
}
