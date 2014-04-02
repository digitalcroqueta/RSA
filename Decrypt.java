import java.math.BigInteger;
/**
 * @author Andrea Sancho Silgado [A20315328] asanchos@hawk.iit.edu
 * @version 1.0
 * @since 04/02/2014
 */
public class Decrypt {
    /**
	 * It will take a public key (n,d) and a single character c to encrypted, and
     * returns the plaintext corresponding to that ciphertext
	 */
    
	public static void main(String args[]) {
		int l = args.length;
		if(l !=3) {
			System.out.println("Please type as input the key(n,d) and the encrypted character (int value) \nCorrect use: java Encrypt n d c");
			return;
		}
        BigInteger n = new BigInteger(args[0]);
        BigInteger d = new BigInteger(args[1]);
        BigInteger c = new BigInteger(args[2]);
        
        /* As we need to compute the decryption:
         * plaintext = (c)^d mod(n)
         * I already implementd this function inside Prime.class,
         * so I am going to create a Prime object to compute the result.
         */
        Prime aux = new Prime(1);
        BigInteger result = aux.expModn(c, d, n);
        System.out.println(result);
    }
    
}