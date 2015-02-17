import DateApp.*;
import org.omg.CORBA.*;
import java.io.*;
import java.net.*;

public class DateClient{
    public static void main(String args[]){
        try{
            // create and initialize the ORB
            // TODO

            // Get and convert the stringified object reference
            // to a generic CORBA object reference
            ObjectInputStream in = new ObjectInputStream(new FileInputStream("IOR")) ;
            String ior = (String) in.readObject() ;
            in.close() ;
            org.omg.CORBA.Object genericObjRef = orb.string_to_object(ior) ;

            // Cast, or narrow the generic object reference to a
            // true object reference.
            // TODO

            // Call the TheDateInterface server object and
            // print results
			// TODO

            // Delay program termination so that the console
            // won't disappear from the screen when running
            // under control of a batch file.
			Thread.sleep(5000);
        } catch (Exception e) {
            System.out.println("ERROR : " + e) ;
            e.printStackTrace(System.out);
        }//end catch block
    }//end main() method

}//end DateClient class
