import java.math.BigInteger;

/**
 * @author Andrea Sancho Silgado [A20315328] asanchos@hawk.iit.edu
 * @version 1.0
 * @since 03/31/2014
 */
public class KeyGen {
	
	/**
	 * It will generate a (public, private) key pair given two prime numbers
	 */
    
	public static void main(String args[]) {
		int l = args.length;
		if(l !=2) {
			System.out.println("Please type as input two prime integer numbers ");
			return;
		}
		BigInteger p = new BigInteger(args[0]);
		BigInteger q = new BigInteger(args[1]);
		// Check if inputs are prime numbers
		Prime pp = new Prime(p);
		Prime pq = new Prime(q);
		if (!(pp.testPrime(5)) || !(pq.testPrime(5))) {
			System.out.println("The numbers typed are not prime. \n " +
                               "Use 'PrimeGen.java' to generate prime numbers as wished.");
			return;
		}
		// n = pq,
		BigInteger n = p.multiply(q);
		// Phi(n) = Phi(p)Phi(q) = (p ‚àí 1)(q ‚àí 1)
		BigInteger totient = ((p.subtract(BigInteger.ONE)).multiply(q.subtract(BigInteger.ONE)));
		BigInteger e = generateE(totient);
		System.out.println("Public key: (" + n + "," + e + ")");
	}
	
	private static BigInteger generateE(BigInteger tot){
		Prime p = new Prime(4);
		BigInteger k = p.initializeK();
		p.generateCandidate(k);
		while(!(  (tot.remainder(p.getN()) != BigInteger.ZERO)
				&&(p.getN().compareTo(tot) < 0)
				&&(p.testPrime(10)))){
			k = k.add(BigInteger.ONE);
			p.generateCandidate(k);
		}
		return p.getN();
	}
	
	public static BigInteger generateD(BigInteger e, BigInteger tot){
		
		
		return BigInteger.ONE;
	}
	
}