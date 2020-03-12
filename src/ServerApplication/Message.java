package ServerApplication;

public class Message
{
  private String text;
  private Client source;
  private String userName;

  public Message(String text, Client source)
  {
    this.text = text;
    this.source = source;
    this.userName = source.getName();
  }

  public String getText()
  {
    return text;
  }

  public String getFull(){
    return userName + ": " + text;
  }

  public Client getSource()
  {
    return source;
  }

  public String getUserName()
  {
    return userName;
  }
}
