import java.io.*;
import org.omg.CORBA.*;
import HelloTie.*;

public class HelloTieServer {

    public static void main(String args[])
    {
        try{
            // Create and initialize the ORB
            ORB orb = ORB.init(args, null);
    
            // Create servant
            // Don't forget to pass in the Orb parameter to support our Any data type
            Hello helloRef = new Hello_Tie(new HelloTieServant(orb));
    
            //Register our servant with the Orb
            orb.connect(helloRef);
            String str = orb.object_to_string(helloRef);
            String filename = "HelloIOR";
            FileOutputStream fos = new FileOutputStream(filename);
            PrintStream ps = new PrintStream(fos);
            ps.print(str);
            ps.close();
    
            System.out.println("Server started ...");
    
            // wait for invocations from clients
            java.lang.Object sync = new java.lang.Object();
            synchronized (sync) {
                sync.wait();
            }
        } catch (Exception e) {
            System.err.println("ERROR: " + e);
            e.printStackTrace(System.out);
        }
    }
}

