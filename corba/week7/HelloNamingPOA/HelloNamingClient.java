
// HelloStringifiedClient.java, stringified object reference version

import java.io.*;
import org.omg.CORBA.*;
import HelloNaming.*;
import org.omg.CosNaming.* ;
import org.omg.CosNaming.NamingContextPackage.*;


public class HelloNamingClient
{
    public static void main(String args[])
    {
		try{
			NameComponent nc[]= new NameComponent[2];

			// create and initialize the ORB
			ORB orb = ORB.init(args, null);


			org.omg.CORBA.Object objRef = orb.resolve_initial_references("NameService");
			NamingContext rootCtx = NamingContextHelper.narrow(objRef);
			nc[0] = new NameComponent("Hello", "Context");
			nc[1] = new NameComponent("World", "Object");

			//NameComponent path[] = {nc};
			org.omg.CORBA.Object objRefHello = rootCtx.resolve(nc);
			Hello helloRef = HelloHelper.narrow(objRefHello);

			String hello = helloRef.sayHello() ;
			System.out.println(hello);

        } catch (Exception e) {
            System.out.println("ERROR : " + e) ;
            e.printStackTrace(System.out);
		}
    }
}

