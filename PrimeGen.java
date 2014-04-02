
import java.math.BigInteger;

/**
 * @author Andrea Sancho Silgado [A20315328] asanchos@hawk.iit.edu
 * @version 1.1
 * @since 03/26/2014
 */

public class PrimeGen {
	
	public static void main(String args[]){
		if (args.length !=1){
			System.out.println("Please, insert an integer number as argument");
			return;
		}
		int nbits = Integer.parseInt(args[0]);
		if (nbits <=0){
			System.out.println("Please, insert a positive number as argument");
			return;
		}
		Prime p = new Prime(nbits);
		BigInteger k = p.initializeK();
		p.generateCandidate(k);
		if(nbits > 3) {
			while(!(p.testPrime(10)&&p.checkRange())){
				k = k.add(BigInteger.ONE);
				p.generateCandidate(k);
			}
		}
		System.out.println(p.getN());
	}
}
