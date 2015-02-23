import java.io.*;
import org.omg.CORBA.*;
import SampleApp.*;
import org.omg.CosNaming.* ;
import org.omg.CosNaming.NamingContextPackage.*;

public class SampleSolnServer{

    public static void main (String args[]) {

        try{
            NameComponent nc[] = new NameComponent[1];

            // create and initialize the ORB
            ORB orb = ORB.init(args, null);

            SampleSolnServant helloRef = new SampleSolnServant();

            //connecting the servant to the orb
            orb.connect(helloRef);
            System.out.println("Orb connected.");

            //You need to locate the naming service. The naming serivce helps you locate other objects.
            //The CORBA orb lets you locate certain services by name. The call
            //String[] services = orb.list_initial_services();
            //lists the names of the standard services that the ORB can connect to. The Naming service
            //has the standard name NameService. To obtain an object reference to the service you use
            //resolve_initial_reference. It returns a generic CORBA object. Note you have to use
            //org.omg.CORBA.Object otherwise the compiler assumes that you mean java.lang.Object
            org.omg.CORBA.Object objRef = orb.resolve_initial_references("NameService");

            //Next you need to convert this reference into a NamingContext reference
            //so that you can invoke the methods of the NamingContext interface.
            //This is achieved by using the narrow function of the helper class
            NamingContext rootCtx = NamingContextHelper.narrow(objRef);

            //Now that we have the naming context we can use it to place a server object
            //Names are nested sequences of name components. You can use the nesting levels
            //to organize hierarchies of names much like you use directories in a file system.
            //A Name Component consists of an ID and a kind. The ID is a name for the component
            //that is unique among all names with the same parent component. The kind is some indication
            //of the type of the component. Use
            //-"Context" for name components that have nested names; and
            //-"Object" for object names
			NamingContext ctxs[] = new NamingContext[4];
            nc[0] = new NameComponent("Context 1", "Context");
            ctxs[0] = rootCtx.bind_new_context(nc);
            System.out.println("Context 1 added to Root Context");

            nc[0] = new NameComponent("Object 1", "Object");
            ctxs[0].rebind(nc, helloRef);
            System.out.println("Object 1 added to Context 1");

            nc[0] = new NameComponent("Context 2", "Context");
            ctxs[1] = rootCtx.bind_new_context(nc);
            System.out.println("Context 2 added to Root Context");

            nc[0] = new NameComponent("Object 2", "Object");
            ctxs[1].rebind(nc, helloRef);
            System.out.println("Object 2 added to Context 2");

            nc[0] = new NameComponent("Object 4", "Object");
            ctxs[1].rebind(nc, helloRef);
            System.out.println("Object 4 added to Context 2");

            nc[0] = new NameComponent("Context 3", "Context");
            ctxs[2] = rootCtx.bind_new_context(nc);
            System.out.println("Context 3 added to Root Context.");

            nc[0] = new NameComponent("Object 5", "Object");
            ctxs[2].rebind(nc, helloRef);
            System.out.println("Object 5 added to Context 3");

            nc[0] = new NameComponent("Sub-Context 1", "Context");
            ctxs[2] = ctxs[1].bind_new_context(nc);
            System.out.println("Sub-Context 1 added to Context 2");

            nc[0] = new NameComponent("Object 3", "Object");
            ctxs[2].rebind(nc, helloRef);
            System.out.println("Object 3 added to Sub-Context 1");


            // wait for invocations from clients
            orb.run();
        } catch (Exception e) {
            System.err.println("Error: "+e);
            e.printStackTrace(System.out);
        }

    }
}
