import EbookStoreApp.*;
import java.io.*;
import org.omg.CORBA.*;
import org.omg.CosNaming.*;
import org.omg.CosNaming.NamingContextPackage.*;

public class EbookStoreClient{

	public static void main(String args[]){
		try{
			org.omg.CORBA.ORB orb = ORB.init(args, null);

			//Retrieve BAnking Object reference from the NameService
			org.omg.CORBA.Object nameObj=orb.resolve_initial_references("NameService");
			NamingContext rootCtx=NamingContextHelper.narrow(nameObj);
			NameComponent[] name = new NameComponent[2];
			name[0] = new NameComponent("Book Shop", "Ctx");
			name[1] = new NameComponent("EBookOnLine", "Object");
			org.omg.CORBA.Object obj = rootCtx.resolve(name);
			EbookStore servantref=EbookStoreHelper.narrow(rootCtx.resolve(name));

			EbookAccountDetails myAccountDetails = new EbookAccountDetails();
			Any anyAccountDetails;
			String tempName;
			int menuChoice;
			StringHolder password = new StringHolder();
			Any anyMyAccountDetails = orb.create_any();

			BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
			do {
				System.out.println("Please enter desired menu option");
				System.out.println(" 1 - Register for an account");
				System.out.println(" 2 - Buy a book");
				System.out.println(" 3 - Quit");
				menuChoice = Integer.parseInt(reader.readLine());

				switch (menuChoice){
					case 1: {
						System.out.println("Please enter a name : ");
						myAccountDetails.CustomerName=reader.readLine();
						System.out.println("Please enter an address : ");
						myAccountDetails.CustomerAddress = reader.readLine();
						System.out.println("Please enter a Balance : ");
						myAccountDetails.Balance = Integer.parseInt(reader.readLine());
						myAccountDetails.UniqueID = "";

						EbookAccountDetailsHelper.insert(anyMyAccountDetails, myAccountDetails);

						//create Request object. Note operation name is sayHello
						Request request = servantref._request("RegisterAccount");
						Any arg = request.add_in_arg(); 		//add an argument to the Request object
						arg.insert_any(anyMyAccountDetails);     //Inserting a value into the any
						Any arg2 = request.add_out_arg();

						request.set_return_type(orb.get_primitive_tc(org.omg.CORBA.TCKind.tk_string));
						request.invoke();

						String passwordValue = request.return_value().extract_string();
						System.out.println("Your secret password is : " + passwordValue);

						continue;
					}// End case 1
					case 2: {
						System.out.println("Please enter your secret number : ");
						tempName = reader.readLine();
						System.out.println("Please enter the book price:");
						int amount = Integer.parseInt(reader.readLine());

						//create Request object. Note operation name is sayHello
						Request request = servantref._request("Payforabook");
						Any arg = request.add_in_arg(); 	//add an argument to the Request object
						arg.insert_string(tempName);      	//Inserting a value into the any
						Any arg2 = request.add_in_arg();
						arg2.insert_long(amount);
						Any arg3 = request.add_out_arg();

						request.set_return_type(orb.get_primitive_tc(org.omg.CORBA.TCKind.tk_any));
						request.invoke();

						anyAccountDetails = request.return_value().extract_any();
						EbookAccountDetails tempEbookAccountDetails = EbookAccountDetailsHelper.extract(anyAccountDetails);
						//Check TypeCode
						TypeCode tcEbookAccountDetails = anyAccountDetails.type();
						if (EbookAccountDetailsHelper.type().equal(tcEbookAccountDetails)) {
							System.out.println("\nName 	: "+tempEbookAccountDetails.CustomerName);
							System.out.println("Addr 	: "+tempEbookAccountDetails.CustomerAddress);
							System.out.println("Balance : "+tempEbookAccountDetails.Balance);
							System.out.println("--- End of Transaction ---\n");
						}
						else
							System.out.println("Wrong Type returned");
						continue;
					}//End case 2
			}// End switch
			} while (menuChoice!=3);
		}catch (Exception e) {
			System.err.println("Error E: "+e);
			e.printStackTrace(System.out);
		}
	}
}
