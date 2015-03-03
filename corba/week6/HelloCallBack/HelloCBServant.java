import HelloCallBack.*;
import java.io.*;

class HelloCBServant implements HelloOperations
{

	ClientOps client;

   	public String registerCB(ClientOps c, String message)
   	{
		client = c ;
		System.out.println("Message received: " + message) ;
		client.callBack(message) ;
		return ("Thanks for message client!") ;
   	}









}
