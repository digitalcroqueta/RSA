
import java.math.BigInteger;

/**
 * @author Andrea Sancho Silgado [A20315328] asanchos@hawk.iit.edu
 * @version 1.1
 * @since 03/26/2014
 */

public class PrimeCheck {
	// Look for prime numbers to check at http://www.bigprimes.net/archive/prime/
	public static void main(String args[]){
		if (args.length !=1) {
			System.out.println("Please, insert an integer number as argument");
			return;
		}
        BigInteger number = new BigInteger(args[0]);
		// All numbers equal or less than 3 are primes!
		if(number.compareTo(BigInteger.valueOf(4)) == -1) {
			System.out.println("TRUE\n");
			return;
		}
		Prime p = new Prime(number);
		// The argument for testPrime is the number of test to perform on the prime candidate
		if (p.testPrime(10)) System.out.println("TRUE\n");
		else System.out.println("FALSE\n");
	}
}