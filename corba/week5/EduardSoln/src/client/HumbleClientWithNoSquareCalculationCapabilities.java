package client;

import SquareCalculation.SquareCalculator;
import SquareCalculation.SquareCalculatorHelper;
import org.omg.CORBA.ORB;
import org.omg.CosNaming.NameComponent;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;
import org.omg.CosNaming.NamingContextPackage.CannotProceed;
import org.omg.CosNaming.NamingContextPackage.InvalidName;
import org.omg.CosNaming.NamingContextPackage.NotFound;

import java.util.StringTokenizer;

public class HumbleClientWithNoSquareCalculationCapabilities{

    public static void main(String args[]) {
        try {
            NamingContextExt rootCtx;
            rootCtx = initCorbaClient(args);

            SquareCalculator remoteSquareCalculator13 = fetchRemoteSquareCalculator(rootCtx, "A/D/13");
            SquareCalculator remoteSquareCalculator6 = fetchRemoteSquareCalculator(rootCtx, "B/6");

            System.out.println("\nInvoking our object method..");
            double piSquared = remoteSquareCalculator13.getSquare((float) Math.PI);
            System.out.println("According to servant #13, (PI ^ 2) = " + piSquared + " ... well, that's interesting...");
//9.86960495038289
            double piToPowerOf4 = remoteSquareCalculator6.getSquare((float) piSquared);
            System.out.println("According to servant #6, (PI ^ 4) = " + piToPowerOf4 + " ... excellent...");
//97.40876192266211952961
		} catch (Exception e) {
			System.out.println("ERROR : " + e) ;
			e.printStackTrace(System.out);
		}
    }

    /**
     * intializes the connection to Corba ORB
     * @param args parameters used to locate the Object Request Broker
     * @return a reference to the RootContext
     * @throws org.omg.CORBA.ORBPackage.InvalidName if ORB cannot find the NameService (shouldn't happen)
     */
    private static NamingContextExt initCorbaClient(String[] args) throws org.omg.CORBA.ORBPackage.InvalidName {
        NamingContextExt rootCtx;ORB orb = ORB.init(args, null);
        org.omg.CORBA.Object objRef = orb.resolve_initial_references("NameService");
        rootCtx = NamingContextExtHelper.narrow(objRef);
        return rootCtx;
    }


    /**
     * retrieve the remote SquareCalculator
     * @return a reference to the remote SquareCalculator object
     * @param path the path of the servant to retrieve, expressed in the following syntax: Context1/Context2/.../ContextN/Object (using forwardslashes to separate contexts)
     * @return the remote servant
     * @throws NotFound
     * @throws CannotProceed
     * @throws InvalidName
     */
    private static SquareCalculator fetchRemoteSquareCalculator(NamingContextExt rootCtx, String path) throws NotFound, CannotProceed, InvalidName {
        StringTokenizer tokenizer = new StringTokenizer(path, "/");
        int tokens = tokenizer.countTokens();

        NameComponent nc[]= new NameComponent[tokens];
        for (int i = 0; i < tokens - 1; i++) {
            nc[i] = new NameComponent(tokenizer.nextToken(), "Context");
        }
        nc[tokens-1] = new NameComponent(tokenizer.nextToken(), "Object");

        org.omg.CORBA.Object objRefSample = rootCtx.resolve(nc);
        return SquareCalculatorHelper.narrow(objRefSample);
    }
}

