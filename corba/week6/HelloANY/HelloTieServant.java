import java.io.*;
import java.util.*;
import org.omg.CORBA.*;
import HelloTie.*;

//Using the Delegation model
class HelloTieServant implements HelloOperations
{
    // Declare our Orb
    public static ORB orb;

    // Initialise our Orb
    public HelloTieServant (org.omg.CORBA.ORB orb){
            this.orb = orb;
    }
    // Out method implementation
    public Any sayHello (){

        // Create our Any data type
        Any anyAB = orb.create_any();
        try {
            anyAB.insert_string("\nHello world !!\n");
        } catch (Exception e) {
            System.err.println("Error E: "+e);
            e.printStackTrace(System.out);
        }
        // Return the Any data type
        return anyAB;
    }
}
