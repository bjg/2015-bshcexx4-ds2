import java.io.*;
import org.omg.CORBA.*;
import HelloTie.*;

public class HelloTieClient
{
    public static void main(String args[])
    {
        try{
            // create and initialize the ORB
            ORB orb = ORB.init(args, null);
            TypeCode tc;
    
            // Get the stringified object reference and destringify it.
            String filename = "HelloIOR";
            BufferedReader br = new BufferedReader(new FileReader(filename));
            String ior = br.readLine();
    
            org.omg.CORBA.Object obj = orb.string_to_object(ior);
            Hello helloRef = HelloHelper.narrow(obj);
    
            // call the Hello server object and print results
            Any hello = helloRef.sayHello();
    
            //Lets see what data type was returned to us
            tc = hello.type();
            //System.out.println("TypeCode = " +tc.kind());
            if (tc.kind() == TCKind.tk_string)
                System.out.println(hello.extract_string());
    
        } catch (Exception e) {
            System.out.println("ERROR : " + e) ;
            e.printStackTrace(System.out);
        }
    }
}


