mport java.math.*;
import java.util.Random;

/**
 * @author Andrea Sancho Silgado [A20315328] asanchos@hawk.iit.edu
 * @version 1.2
 * @since 04/01/2014
 */

public class Prime {
	
	public static final BigInteger TWO = BigInteger.ONE.add(BigInteger.ONE);
	private BigInteger n;
	private int b;
	
	/**
	 * Constructor used for testing primes
	 * @param BigInteger
	 */
	public Prime(BigInteger number) {
		setN(number);
	}
	public BigInteger getN() {
		return n;
	}
	public void setN(BigInteger number) {
		this.n = number;
	}
	public Prime(int nbits) {
		setB(nbits);
	}
	public int getB() {
		return b;
	}
	public void setB(int bits) {
		this.b = bits;
	}
	
	/**
	 * Computes [base^(exponent)] (modulus n) using the binary exponent notation as shortcut.
	 * If n is a prime number, and base=n-1, then the result of this method will be 1.
	 *
	 * @param	BigInteger base,exp,mod
	 * @return BigInteger  [a^(n-1)] (mod n)
	 */
	private BigInteger expModn(BigInteger base, BigInteger exp, BigInteger mod) {
		BigInteger x = BigInteger.ONE;
		while (exp.compareTo(BigInteger.ZERO) > 0) {
			BigInteger exp2 = exp.remainder(TWO);
			if ((exp2.compareTo(BigInteger.ONE) == 0)) {
				x = x.multiply(base);
				x = x.remainder(mod);
			}
			base = base.multiply(base);
			base = base.remainder(mod);
			exp = exp.divide(TWO);
		}
		return x;
	}
	
	/**
	 * Picks any integer a between 2 and n-1 (inclusive). The precise integer
	 * picked isn't important. Also, since the parameters for [base] are inclusive, 2
	 * and n-1 themselves are both valid choices.
	 *
	 * @param Random
	 * @return BigInteger
	 */
	private BigInteger generateBase(Random r){
		int low = 2;
		BigInteger high = BigInteger.valueOf((int) Math.pow(2, 30) - low);
		// We need to take into account the fact that Integer.MAX_VALUE
		// may be less than the actual (n-1) value, which is a BigInteger.
		// So we set the upper bound as the minimum of these two:
		BigInteger minimum = high.min(getN().subtract(BigInteger.ONE));
		int h = minimum.intValue();
		int q = r.nextInt(h - low) + low;
		return BigInteger.valueOf(q);
	}
	
	/**
	 * Check whether a^n-1 (mod n) = 1 If not, n is composite. If true, n is
	 * likely, (but not certainly) prime. See http://primes.utm.edu/index.html
	 *
	 * Repeating the test (trials times) with
	 * different values for a increase the probability in the outcome, though
	 * there are rare composite numbers that satisfy the Fermat condition for
	 * all values of a: Carmichael numbers.
	 *
	 * @param int number of trials
	 * @return true if the candidate set in n passes all tests
	 */
	public boolean testPrime(int trials) {
		BigInteger mod = getN();
		BigInteger exp = mod.subtract(BigInteger.ONE);
		BigInteger base;
		Random r = new Random();
		for (int i = 0; i < trials; i++) {
			base = generateBase(r);
			if (expModn(base, exp, mod).compareTo(BigInteger.ONE) != 0) return false;
		}
		return testCarmichael();
	}
    
	/**
	 * Performs the Miller-Rabin test on a probable prime number
	 *
	 * This test is to detect false probable primes, called
	 * Carmichael numbers, which will pass the Fermat test.
	 * If x and n are positive integers such that x^2 = 1 (mod n)
	 * but x != 1 (mod n), then n is composite.
	 *
	 * @return true if the candidate is passes the test, so it is not
	 * a Carmichael number
	 */
	private boolean testCarmichael(){
		Random r = new Random();
		BigInteger mod = getN();
		BigInteger exp = TWO;
		BigInteger base = generateBase(r);
		if(expModn(base, exp, mod).compareTo(BigInteger.ONE) == 0){
			if(expModn(base, BigInteger.ONE, mod).compareTo(BigInteger.ONE) != 0) return false;
		}
		return true;
	}
	
	/**
	 * Generates an initial BigInteger as k = (n-5)/6,
	 * which is in the range fixed by the specified number of bits.
	 *
	 * @return BigInteger
	 */
	public BigInteger initializeK(){
		int bits = getB();
		if (bits > 3) {
			BigInteger k = BigInteger.ONE.shiftLeft(bits-1).subtract(BigInteger.valueOf(5));
			k = k.divide(BigInteger.valueOf(6));
			return k.add(BigInteger.ONE);
		}else {
			return BigInteger.ZERO;
		}
	}
	/**
	 * Checks if the candidate is in the ranged imposed by the number of
	 * bits specified by the user.
	 *
	 * @param candidate BigInteger (candidate for prime number to evaualte)
	 * @return true (when the number is in the range 2^[nbits-1] < candidate < 2^[nbits]
	 */
	public boolean checkRange() {
		int nbits = getB();
		if (nbits <= 3){
			return true;
		}
		else {
			BigInteger max = BigInteger.ONE.shiftLeft(nbits);
			BigInteger min = BigInteger.ONE.shiftLeft(nbits - 1);
			if ((getN().compareTo(max) < 0) && (getN().compareTo(min) >= 0))
				return true;
			return false;
		}
	}
	
	/**
	 * Generates a number as n = 6k + 5,
	 * this will be a prime candidate.
	 *
	 * The algorithm is conflictive when generating
	 * prime numbers of less than 3 bits (because k = 0)
	 *
	 * @param BigInteger
	 */
	public void generateCandidate(BigInteger k) {
		int bits = getB();
		if (bits<=3){ // Conflictive cases
			if(bits == 1) setN(BigInteger.ONE);
			else if(bits == 2) setN(TWO.add(BigInteger.ONE));
			else if(bits == 3) setN(BigInteger.valueOf(5));
		} else {
			BigInteger n = k.multiply(BigInteger.valueOf(6));
			n = n.add(BigInteger.valueOf(5));
			setN(n);
		}
	}
}