import java.math.BigInteger;
/**
 * @author Andrea Sancho Silgado [A20315328] asanchos@hawk.iit.edu
 * @version 1.0
 * @since 04/02/2014
 */
public class Encrypt {
    /**
	 * It will take a public key (n,e) and a single character c to encode, and 
     * returns the cyphertext corresponding to the plaintext, m
	 */
    
	public static void main(String args[]) {
		int l = args.length;
		if(l !=3) {
			System.out.println("Please type as input the key(n,e) and the character (int value) \nCorrect use: java Encrypt n e c");
			return;
		}
        BigInteger n = new BigInteger(args[0]);
        BigInteger e = new BigInteger(args[1]);
        BigInteger c = new BigInteger(args[2]);
        
        /* As we need to compute the encryption:
         * ciphertext = (c)^e mod(n)
         * I already implementd this function inside Prime.class,
         * so I am going to create Prime objects to compute the result.
         */
        Prime aux = new Prime(1);
        BigInteger result = aux.expModn(c, e, n);
        System.out.println(result);
    }
    
}
