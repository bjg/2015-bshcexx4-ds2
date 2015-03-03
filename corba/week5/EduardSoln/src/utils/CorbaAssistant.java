package utils;

import org.omg.CORBA.ORB;
import org.omg.CORBA.ORBPackage.InvalidName;
import org.omg.CosNaming.NameComponent;
import org.omg.CosNaming.NamingContext;
import org.omg.CosNaming.NamingContextHelper;
import org.omg.CosNaming.NamingContextPackage.AlreadyBound;
import org.omg.CosNaming.NamingContextPackage.CannotProceed;
import org.omg.CosNaming.NamingContextPackage.NotFound;
import server.SquareCalculatorServant;

import java.util.HashMap;
import java.util.Map;

public final class CorbaAssistant {
    private static final String NAME_SERVICE = "NameService";

    private static Map<NamingContext, String> contextNames = new HashMap<NamingContext, String>();

    //private constructor for assistant class with static methods
    private CorbaAssistant() {}


    public static NamingContext initCorba(org.omg.CORBA.Object servant, ORB orb) throws InvalidName {
        orb.connect(servant);
        System.out.println("connected");

        org.omg.CORBA.Object nameService = orb.resolve_initial_references(NAME_SERVICE);
        return NamingContextHelper.narrow(nameService);
    }

    /**
     * add a new named object to the context
     * @param parentContext the context that will become the parent of the new object
     * @param name the name of the object, within the parent context
     * @param objectToBind the object to bind
     */
    public static void bindNewObjectTo(NamingContext parentContext, String name, org.omg.CORBA.Object objectToBind) {
        try {
            parentContext.rebind(new NameComponent[] {new NameComponent(name, "Object")}, objectToBind);
            System.out.println("New Object [" + name + "] bound to context [" + getContextName(parentContext) + "]");
        } catch (NotFound notFound) {
            notFound.printStackTrace();
        } catch (CannotProceed cannotProceed) {
            cannotProceed.printStackTrace();
        } catch (org.omg.CosNaming.NamingContextPackage.InvalidName invalidName) {
            invalidName.printStackTrace();
        }
    }


    /**
     * add a new context to the context graph
     * @param parentContext the parent object that will contain the new context
     * @param name the name of the new context
     * @return the newly created context
     */
    public static NamingContext bindNewContextTo(NamingContext parentContext, String name) {
        try {
            NamingContext newContext = parentContext.bind_new_context(new NameComponent[]{new NameComponent(name, "Context")});
            addNewContextToLocalRegistry(name, newContext);
            System.out.println("New Context [" +name+ "] added to context [" + getContextName(parentContext) + "]");
            return newContext;
        } catch (NotFound notFound) {
            notFound.printStackTrace();
        } catch (AlreadyBound alreadyBound) {
            alreadyBound.printStackTrace();
        } catch (CannotProceed cannotProceed) {
            cannotProceed.printStackTrace();
        } catch (org.omg.CosNaming.NamingContextPackage.InvalidName invalidName) {
            invalidName.printStackTrace();
        }
        return null;
    }

    /**
     * @param orb orb instance, already initialized
     * @param helloRef the object to connect
     * @return a reference to the root context
     * @throws InvalidName if NameService cannot be found (should not happen)
     */
    public static NamingContext corbaConnect(ORB orb, SquareCalculatorServant helloRef) throws InvalidName {
        orb.connect(helloRef);
        org.omg.CORBA.Object nameService = orb.resolve_initial_references("NameService");
        System.out.println("Orb connected.");
        NamingContext rootCtx = (NamingContext) NamingContextHelper.narrow(nameService);
        addNewContextToLocalRegistry("Root Context", rootCtx);
        return rootCtx;
    }

    public static void waitForConnections(ORB orb) {
        orb.run();
        System.out.println("Setup complete, waiting for connections...");
    }

    private static void addNewContextToLocalRegistry(String name, NamingContext newContext) {
        contextNames.put(newContext, name);
    }

    private static String getContextName(NamingContext parentContext) {
        return contextNames.get(parentContext);
    }
}
