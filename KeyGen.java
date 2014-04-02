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
		// Totient(n) = Totient(p)Totient(q) = (p - 1)(q - 1)
		BigInteger totient = ((p.subtract(BigInteger.ONE)).multiply(q.subtract(BigInteger.ONE)));
		BigInteger e = generateE(totient);
		System.out.println("Public key: (" + n + "," + e + ")");
        // de = 1 mod(totient(n))
        BigInteger d = generateD(e,totient);
        System.out.println("Private key: (" + n + "," + d + ")");
	}
	
    /**
	 * Generates a prime number e that does not divide totient(n).
     * This is equivalent as saying that gcd(e,totient(n)) = 1.
     * The number must be in the range: 1 < e < totient(n)
	 *
	 * @param	BigInteger totient(n)
	 * @return BigInteger  e
	 */
	private static BigInteger generateE(BigInteger totient){
		int nbits = 1;
        int aux = 1;
        Prime p = new Prime(nbits);
		BigInteger k = p.initializeK();
		p.generateCandidate(k);
		while(!(  ((totient.remainder(p.getN())).compareTo(BigInteger.ZERO) != 0)
				&&(p.getN().compareTo(totient) < 0)
				&&(p.testPrime(5)))){
            // To make sure we test the lowest prime candidates
            // we need to increment nbits and k slowly and not
            // at the same time:
            if (aux%2 == 0){
                nbits = nbits + 1;
                p.setB(nbits);
            } else if(aux%3 == 0){
                k = k.subtract(BigInteger.ONE);
            } else{
                k = k.add(BigInteger.ONE);
            }
            aux ++;
            p.generateCandidate(k);
		}
		return p.getN();
	}
    
    /**
	 * Using the Euclidean Algorithm, this function finds the multiplicative
     * inverse of e mod (totient(n)). [ d*e = 1 mod(totient(n)) ]
     * To simplify things we could have used the built-in function of
     * BigIntegers: BigIntefer.modInverse(BigInteger val)
	 *
	 * @param	BigInteger e, totient(n)
	 * @return BigInteger  d
	 */
    private static BigInteger generateD(BigInteger e, BigInteger totient){
        BigInteger x2 = BigInteger.ONE;
        BigInteger x1 = BigInteger.ZERO;
        BigInteger y2 = BigInteger.ZERO;
        BigInteger y1 = BigInteger.ONE;
        BigInteger t = totient;
        BigInteger x, y, q, r;
        while (totient.compareTo(BigInteger.ZERO) == 1) {
            q = e.divide(totient);
            r = e.subtract(q.multiply(totient));
            x = x2.subtract(q.multiply(x1));
            y = y2.subtract(q.multiply(y1));
            e = totient;
            totient = r;
            x2 = x1;
            x1 = x;
            y2 = y1;
            y1 = y;
        }
        return x2.add(t);
    }
}