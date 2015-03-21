import java.io.*;
import java.util.*;
import EbookStoreApp.*;
import org.omg.CORBA.*;
import java.util.Date.*;

public class EbookStoreServant implements EbookStoreOperations{
	public static Hashtable<String,EbookAccountDetails> hashtable;
	public static ORB orb;
	public static int ID_Counter;


	public EbookStoreServant (org.omg.CORBA.ORB orb){
		hashtable = new Hashtable();
		this.orb = orb;
	}

	public void RegisterAccount (org.omg.CORBA.Any anyAccountDetails, StringHolder password) {
		String UniqueID = String.format("%04d", ++ID_Counter);
		EbookAccountDetails myEbookAccountDetails = EbookAccountDetailsHelper.extract(anyAccountDetails);
		myEbookAccountDetails.UniqueID = UniqueID;
		hashtable.put(UniqueID, myEbookAccountDetails);
		password.value = UniqueID;
	}

	public void Payforabook (String UniqueID, int amount, AnyHolder anyAccountDetails){
		try{
			EbookAccountDetails myEbookAccountDetails = hashtable.get(UniqueID);
			myEbookAccountDetails.Balance -= amount;
			hashtable.put(UniqueID, myEbookAccountDetails);

			Any anyAccount = orb.create_any();
			EbookAccountDetailsHelper.insert(anyAccount, myEbookAccountDetails);
			anyAccountDetails.value = anyAccount;
		} catch (Exception e) {
			System.err.println(e);
			e.printStackTrace(System.out);
		}
	}
}
