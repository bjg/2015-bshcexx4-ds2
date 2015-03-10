import java.io.*;
import org.omg.CORBA.*;
import HelloNaming.*;
import org.omg.CosNaming.* ;
import org.omg.CosNaming.NamingContextPackage.*;
import org.omg.PortableServer.*;

public class HelloNamingServer{

public static void main (String args[]) {
    try{
        NameComponent nc[] = new NameComponent[1];
    
        // create and initialize the ORB
        ORB orb = ORB.init(args, null);
        //Delegation model for creating a servant
        HelloPOA helloRef = new HelloPOATie(new HelloServant());
        org.omg.CORBA.Object objRefPOA = orb.resolve_initial_references("RootPOA");
        POA rootPOA = POAHelper.narrow(objRefPOA);
    
        /**
        *
        * This operation generates an Object Id and enters
        * the Object Id and the specified servant in the
        * Active Object Map.
        * @param p_servant servant to be associated with an
        *            object to be activated.
        * @return POA generated object id.
        * @exception ServantAlreadyActive is raised if the
        *            POA has UNIQUE_ID policy and servant is
        *            is already in the Active Object Map.
        * @exception WrongPolicy raised if the SYSTEM_ID and
        *            RETAIN policies are not specified.
        */
        //  byte[] activate_object (org.omg.PortableServer.Servant p_servant) throws org.omg.PortableServer.POAPackage.ServantAlreadyActive, org.omg.PortableServer.POAPackage.WrongPolicy;
    
        byte[] objID = rootPOA.activate_object(helloRef);
        /**
        * If an object with the specified Object Id value is
        * currently active, a reference encapsulating the
        * information used to activate the object is returned.
        *
        * @param oid id of the object for which the
        *                 reference is returned.
        * @return the object reference
        *
        * @exception ObjectNotActive if the Object Id value
        *             is not active in the POA.
        * @exception WrongPolicy if the RETAIN policy is not
        *             present.
        */
        //  org.omg.CORBA.Object id_to_reference (byte[] oid) throws org.omg.PortableServer.POAPackage.ObjectNotActive, org.omg.PortableServer.POAPackage.WrongPolicy;
    
        org.omg.CORBA.Object ServantObjectRef = rootPOA.id_to_reference(objID);
    
        //You need to locate the naming service. The naming serivce helps you locate other objects.
        //The CORBA orb lets you locate certain services by name. The call
        //String[] services = orb.list_initial_services();
        //lists the names of the standard servicesthat the ORB canconnect to. The Naming service
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
        //that is unique among all names witht he same parent component. The kind is some indication
        //of the type of the component. Use
        //-"Context" for name components that have nested names; and
        //-"Object" for object names
    
        nc[0] = new NameComponent("Hello", "Context");
        NamingContext HelloCtx = rootCtx.bind_new_context(nc);
    
        nc[0] = new NameComponent("World", "Object");
        //NameComponent path[] = {nc};
        //Binding the name to an object that is stored in the Naming Context
        HelloCtx.rebind(nc, ServantObjectRef);
    
        rootPOA.the_POAManager().activate();
    
        System.out.println("Server has been started.");
        // wait for invocations from clients
        orb.run();
    
        } catch (Exception e) {
            System.err.println("Error: "+e);
            e.printStackTrace(System.out);
        }

    }
}
