import HelloDynamicInvocation.*;
import java.io.*;

class HelloServant implements HelloOperations
{
    public String sayHello(int audience)
    {
    		System.out.println("Servant has been called with parameter " + audience);
    
    		switch (audience) {
        			case 1: return "\nHello world !!\n";
        			case 2: return "\nHello Ireland !!\n";
        			case 3: return "\nHello Dublin !!\n";
        }
        return "\nSwitch not executed !!\n";
    }
}
