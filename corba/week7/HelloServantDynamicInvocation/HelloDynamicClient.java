import java.io.*;
import java.lang.*;
import org.omg.CORBA.*;
import HelloDynamicInvocation.*;
import org.omg.CosNaming.* ;
import org.omg.CosNaming.NamingContextPackage.*;


public class HelloDynamicClient
{
    public static void main(String args[])
    {
        try{
            // create and initialize the ORB
            ORB orb = ORB.init(args, null);
        
            org.omg.CORBA.Object objRef =    orb.resolve_initial_references("NameService");
            NamingContext rootCtx = NamingContextHelper.narrow(objRef);
            NameComponent nc = new NameComponent("Hello", "");
            NameComponent path[] = {nc};
            Hello helloRef = HelloHelper.narrow(rootCtx.resolve(path));
        
            BufferedReader b_in = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("Please enter a number representing who you want to say hello to");
            System.out.println("(eg 1 . World; 2. Ireland; 3. Dublin");
            int audience = (Integer.valueOf(b_in.readLine())).shortValue();
            b_in.close();
        
            //create Request object. Note operation name is sayHello
            Request request = helloRef._request("sayHello");
            Any arg = request.add_in_arg(); //add an argument to the Request object
            arg.insert_long(audience);      //Inserting a value into the any
            request.set_return_type(orb.get_primitive_tc(org.omg.CORBA.TCKind.tk_string));
        
            /**
            * Makes a synchronous invocation using the
            * information in the Request object. Exception information is
            * placed into the Request object's environment object.
            */
            //    public abstract void invoke();
            request.invoke();
        
            String hello = request.result().value().extract_string();
            System.out.println("result" + hello );
        
            System.out.println("Just completed sending a oneway request to the HellowWorld server");
        
        } catch (Exception e) {
            System.out.println("ERROR : " + e) ;
            e.printStackTrace(System.out);
        }
    }
}
