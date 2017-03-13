import java.math.*;
import java.util.*;
import java.io.*;

class RSA {
	static Scanner scanner = new Scanner(System.in);
	static Random rngsus = new Random();
	
	public static void main(String[] args) {
		BigInteger prime1, prime2, product, phiN, d, k;
		BigInteger[] encMessage; 
		
		File file = new File("msg.txt");

		// INPUT 
		System.out.print("Enter First Prime: ");
		prime1 = scanner.nextBigInteger();

		System.out.print("Enter Second Prime: ");
		prime2 = scanner.nextBigInteger();

		// PROCESSING
		product = prime1.multiply(prime2);

		// phi(N) = (p - 1)(q - 1)
		phiN = (prime1.subtract(BigInteger.ONE)).multiply(prime2.subtract(BigInteger.ONE));

		// guess a starting value for the primes so a different one is used each time
		k = BigInteger.valueOf(rngsus.nextInt(100) + 2);
		// cycle upwards untill a coprime is found
		while(!phiN.gcd(k).equals(BigInteger.ONE)) {
			k = k.add(BigInteger.ONE);
		}
		// calculate the other coprime from the first
		d = k.modInverse(phiN);

		// OUTPUT
		System.out.println("\n\nPRIME ONE: " + prime1.toString());
		System.out.println("PRIME TWO: " + prime2.toString());
		System.out.println("PRODUCT  : " + product.toString());
		System.out.println("PHI N    : " + phiN.toString());
		System.out.println("K        : " + k.toString());
		System.out.println("D        : " + d.toString());

		encMessage = encrypt(file, k, product);
		decrypt(encMessage, d, product);
	}

	private static BigInteger[] encrypt(File file, BigInteger key, BigInteger product) {
		FileInputStream fileInputStream = null;
		byte[] bFile = new byte[(int) file.length()];

		// bigint array to store encrypted message as the numbers can become very large
		BigInteger[] bigIntFile = new BigInteger[(int) file.length()];

		try
		{
			// convert whole file into array of bytes
			fileInputStream = new FileInputStream(file);
			fileInputStream.read(bFile);
			fileInputStream.close();
	  	}
	  	catch (Exception e)
	  	{
			System.out.println("\nFATAL ERROR: \"msg.txt\" NOT FOUND");
	  	}
	  	
	  	// print the message as numeric values
	  	System.out.println("\nMESSAGE: ");
	  	for(byte b : bFile) {
	  		System.out.print("" + b + " ");
	  	}

	  	// cycle over the byte array and encrypt each character
	  	System.out.println("\n\nENCRYPTED MESSAGE: ");
	  	for(int a = 0; a < (int)file.length(); a++) {
	  		bigIntFile[a] = BigInteger.valueOf(bFile[a]);

	  		// encrypted data = (data ^ key) mod(product)
	  		bigIntFile[a] = bigIntFile[a].modPow(key, product);
	  		System.out.print(bigIntFile[a].toString() + " ");
	  	}

	  	return bigIntFile;
	}

	private static void decrypt(BigInteger[] data, BigInteger key, BigInteger product) {
		System.out.println("\n\nDECRYPTED MESSAGE: ");

		// cycle over the given array and decrypt each element
	  	for(int a = 0; a < data.length; a++) {
	  		// decrypted data = (encData ^ key) mod(product)
	  		data[a] = data[a].modPow(key, product);
	  		System.out.print(data[a].toString() + " ");
	  	}
	}

	/*public static boolean checkPrime(BigInteger x) {
		// holy sweet jesus of bad for loops
		for(BigInteger i = BigInteger.valueOf(2l); i.compareTo(x) < 0; i = i.add(BigInteger.ONE)) {
			if(x.mod(i).equals(BigInteger.ZERO)) {
				return false;
			}
		}

		return true;
	}*/
}