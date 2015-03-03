import java.io.*;
import org.omg.CORBA.*;
import CA1NamingApp.*;
import org.omg.CosNaming.* ;
import org.omg.CosNaming.NamingContextPackage.*;

public class CA1NamingSolnClient
{
    public static NamingContextExt rootCtx;

    public static void main(String args[]) {
        try {
            org.omg.CORBA.Object objRefCA1Naming;
            CA1NamingSoln sampleSolnRef;
			NameComponent nc[];
			double square1, square2;

            // create and initialize the ORB
            ORB orb = ORB.init(args, null);
            org.omg.CORBA.Object objRef = orb.resolve_initial_references("NameService");
            rootCtx = NamingContextExtHelper.narrow(objRef);

            //Search the Name Space for our first square Object
            nc = new NameComponent[3];
            nc[0] = new NameComponent("A", "Context");
            nc[1] = new NameComponent("D", "Context");
            nc[2] = new NameComponent("13", "Object");
            objRefCA1Naming = rootCtx.resolve(nc);
            sampleSolnRef = CA1NamingSolnHelper.narrow(objRefCA1Naming);

            System.out.println("Invoking object method A->D->13 ...");
            square1 = sampleSolnRef.getSquare(Math.PI);
            System.out.println("pi^2=" + square1);

            //Search the Name Space for our second square Object
            nc = new NameComponent[2];
            nc[0] = new NameComponent("B", "Context");
            nc[1] = new NameComponent("6", "Object");
            objRefCA1Naming = rootCtx.resolve(nc);
            sampleSolnRef = CA1NamingSolnHelper.narrow(objRefCA1Naming);

            System.out.println("Invoking object method B->6 ...");
            square2 = sampleSolnRef.getSquare(square1);
            System.out.println("pi^4=" + square2);

		} catch (Exception e) {
			System.out.println("ERROR : " + e) ;
			e.printStackTrace(System.out);
		}
    }
}

