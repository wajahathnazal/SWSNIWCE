


import java.math.BigInteger;
import java.security.SecureRandom;
import java.sql.*;
import java.util.Random;

public class ECC {

	public static String encryption (String msg)
	{
		String encmsg="";
		try{

			Random	r = new Random();
			BigInteger	P = BigInteger.probablePrime(3, r);  
			BigInteger  Q = BigInteger.probablePrime(3, r);  
			BigInteger N =P.multiply(Q);  
			Random rand3 = new Random(); 
			int kval= N.intValue();
			int kres=rand3.nextInt(kval-1);
			BigInteger k=BigInteger.valueOf(kres);
			BigInteger b1=k.multiply(P);
			BigInteger M=new BigInteger(msg.getBytes());
			BigInteger b2=M.add(b1);
			encmsg=b1+","+b2;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return encmsg;
	}

	public static String decrypt(String cipher)
	{
		String spt[]=cipher.split(",");
		BigInteger b1=new BigInteger(spt[0]);
		BigInteger b2=new BigInteger(spt[1]);
		BigInteger m=b2.subtract(b1);
		String filedata=new String(m.toByteArray());	
		return filedata;
	}




	public static void main(String[] args)
	{
		String enc = encryption("asd");
		System.out.println(enc);
		System.out.println(decrypt(enc));
	}


}

