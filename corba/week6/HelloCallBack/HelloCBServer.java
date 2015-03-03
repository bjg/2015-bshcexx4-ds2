import java.io.*;
import org.omg.CORBA.*;
import HelloCallBack.*;
import org.omg.CosNaming.* ;
import org.omg.CosNaming.NamingContextPackage.*;

public class HelloCBServer{

	public static void main (String args[]) {
		try{

	    	// create and initialize the ORB
	   		ORB orb = ORB.init(args, null);
	   		//Delegation model for creating a servant
			Hello helloRef = new Hello_Tie(new HelloCBServant());
			//connecting the servant to the orb
			orb.connect(helloRef);

			org.omg.CORBA.Object objRef = orb.resolve_initial_references("NameService");
			NamingContext rootCtx = NamingContextHelper.narrow(objRef);
			NameComponent nc = new NameComponent("Hello", "");
			NameComponent path[] = {nc};
			rootCtx.rebind(path, helloRef);

			System.out.println("Server has been started ...");
		    // wait for invocations from clients
			orb.run();

		} catch (Exception e) {
			System.err.println("Error: "+e);
			e.printStackTrace(System.out);
		}

	}
}