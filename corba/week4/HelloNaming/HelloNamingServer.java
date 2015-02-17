import java.io.*;
import org.omg.CORBA.*;
import HelloNaming.*;
import org.omg.CosNaming.* ;
import org.omg.CosNaming.NamingContextPackage.*;

public class HelloNamingServer{

    public static void main (String args[]) {

        try{
            NameComponent nc[] = new NameComponent[1];

            // create and initialize the ORB
            ORB orb = ORB.init(args, null);

            HelloServant helloRef = new HelloServant();

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
            System.out.println("Found NameService.");

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
            nc[0] = new NameComponent("Hello", "Context");
            NamingContext HelloCtx = rootCtx.bind_new_context(nc);
            System.out.println("Context Hello added to Name Space.");

            nc[0] = new NameComponent("World", "Object");
            //NameComponent path[] = {nc};
            //Binding the name to an object that is stored in the Naming Context
            HelloCtx.rebind(nc, helloRef);
            System.out.println("Object World added to Hello Context.");

            nc[0] = new NameComponent("Hello2", "Context");
            NamingContext Hello2Ctx = rootCtx.bind_new_context(nc);
            System.out.println("Context Hello2 added to Name Space.");

            nc[0] = new NameComponent("World2", "Object");
            Hello2Ctx.rebind(nc, helloRef);
            System.out.println("Object World2 added to Hello2 Context.");

            nc[0] = new NameComponent("Hello3", "Context");
            NamingContext Hello3Ctx = rootCtx.bind_new_context(nc);
            System.out.println("Context Hello3 added to Name Space.");

            nc[0] = new NameComponent("Hello4", "Context");
            NamingContext Hello4Ctx = Hello2Ctx.bind_new_context(nc);
            System.out.println("Context Hello4 added to Hello2 context.");

            // wait for invocations from clients
            orb.run();
        } catch (Exception e) {
            System.err.println("Error: "+e);
            e.printStackTrace(System.out);
        }

    }
}
