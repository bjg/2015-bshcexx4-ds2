package server;

import org.omg.CORBA.ORB;
import org.omg.CosNaming.NamingContext;
import utils.CorbaAssistant;

public class SquareCalculatorServer {

    public static void main (String args[]) {

        try{
            ORB orb = ORB.init(args, null);
            SquareCalculatorServant servant = new SquareCalculatorServant();
            NamingContext rootCtx = CorbaAssistant.corbaConnect(orb, servant);

            //Layer 1
            NamingContext contextA = CorbaAssistant.bindNewContextTo(rootCtx, "A");
            NamingContext contextB = CorbaAssistant.bindNewContextTo(rootCtx, "B");
            NamingContext contextC = CorbaAssistant.bindNewContextTo(rootCtx, "C");
            CorbaAssistant.bindNewObjectTo(rootCtx, "1", servant);

            //Layer 2
            NamingContext contextD = CorbaAssistant.bindNewContextTo(contextA, "D");
            NamingContext contextE = CorbaAssistant.bindNewContextTo(contextA, "E");
            CorbaAssistant.bindNewObjectTo(contextB, "6", servant);
            NamingContext contextF = CorbaAssistant.bindNewContextTo(contextC, "F");

            //Layer 3
            CorbaAssistant.bindNewObjectTo(contextD, "13", servant);

            //... and now, we wait.
            CorbaAssistant.waitForConnections(orb);
        } catch (Exception e) {
            System.err.println("Error: "+e);
            e.printStackTrace(System.out);
        }

    }


}
