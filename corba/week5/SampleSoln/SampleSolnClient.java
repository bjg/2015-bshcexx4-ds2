import java.io.*;
import org.omg.CORBA.*;
import SampleApp.*;
import org.omg.CosNaming.* ;
import org.omg.CosNaming.NamingContextPackage.*;

public class SampleSolnClient
{
    public static NamingContextExt rootCtx;

    public static void main(String args[]) {
        try {
            NameComponent nc[]= new NameComponent[3];

            // create and initialize the ORB
            ORB orb = ORB.init(args, null);
            org.omg.CORBA.Object objRef = orb.resolve_initial_references("NameService");
            rootCtx = NamingContextExtHelper.narrow(objRef);

            //Search the Name Space for our HelloWorld Object
            nc[0] = new NameComponent("Context 2", "Context");
            nc[1] = new NameComponent("Sub-Context 1", "Context");
            nc[2] = new NameComponent("Object 3", "Object");
            //NameComponent path[] = {nc};
            org.omg.CORBA.Object objRefSample = rootCtx.resolve(nc);
            SampleSoln sampleSolnRef = SampleSolnHelper.narrow(objRefSample);

            System.out.println("\nInvoking our object method..");
            String response = sampleSolnRef.sayHello("CORBA") ;
            System.out.println(response);

		} catch (Exception e) {
			System.out.println("ERROR : " + e) ;
			e.printStackTrace(System.out);
		}
    }
}

