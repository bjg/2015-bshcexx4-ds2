import java.io.*;
import java.lang.*;
import org.omg.CORBA.*;
import HelloCallBack.*;
import org.omg.CosNaming.* ;
import org.omg.CosNaming.NamingContextPackage.*;

import java.net.*;


public class HelloCBClient
{
    public static void main(String args[])
    {
	try{
	    // create and initialize the ORB
	    ORB orb = ORB.init(args, null);


		org.omg.CORBA.Object objRef =	orb.resolve_initial_references("NameService");
		NamingContext rootCtx = NamingContextHelper.narrow(objRef);
		NameComponent nc = new NameComponent("Hello", "");
		NameComponent path[] = {nc};
		Hello helloRef = HelloHelper.narrow(rootCtx.resolve(path));

		ClientOps callBackRef = new ClientOps_Tie(new ClientOpsImpl()) ;

   		String returnedString = helloRef.registerCB(callBackRef, "Hello from Client");
		System.out.println(returnedString) ;

		System.out.println("Client and ClientOps threads joined.");

		} catch (Exception e) {
		    System.out.println("ERROR : " + e) ;
		    e.printStackTrace(System.out);
			}

	}
}

class ClientOpsImpl implements ClientOpsOperations{
	public void callBack(String message) {
		System.out.println("Message via callBack from server is " + message) ;

	}
}


