import EbookStoreApp.*;
import org.omg.CORBA.*;
import org.omg.CosNaming.*;
import org.omg.CosNaming.NamingContextPackage.*;

public class EbookStoreServer{
	public static void main(String args[]){
		try {
			org.omg.CORBA.ORB orb = ORB.init(args, null);

			org.omg.CORBA.Object nameObj=orb.resolve_initial_references("NameService");

			NamingContext rootCtx=NamingContextHelper.narrow(nameObj);
			NameComponent[] name = new NameComponent[1];
			name[0] = new NameComponent("Book Shop", "Ctx");
			NamingContext EbookStoreCtx = rootCtx.bind_new_context(name);

			name[0] = new NameComponent("EBookOnLine", "Object");
			EbookStore servant = new EbookStore_Tie(new EbookStoreServant(orb));
			EbookStoreCtx.rebind(name, servant);

			System.out.println("Online book store server started ...");
			orb.run();

		}catch (Exception e){
			System.err.println(e);
			e.printStackTrace(System.out);
		}
	}
}
