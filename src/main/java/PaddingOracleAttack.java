/**
 * Created by Adam on 2016-10-18.
 */
public class PaddingOracleAttack {
    static Bruteforce b = new Bruteforce();
    static CryptogramUtil cryptogramUtil = new CryptogramUtil();
    static InitVectorUtil initVectorUtil = new InitVectorUtil();
    static String concatenatedCiphertexts, forgedCiphertext, initVectorInAscii, initVectorInBinary, iv16, iv32, messageInBinary, lastBlock;

    public static void main(String[] args) {
        initVectorInAscii = b.getInitVectorForMessage(1);
        initVectorInBinary = b.asciiToBinary(initVectorInAscii);
        messageInBinary = b.getMessageWithNoWhitespaces(1);
        iv16 = initVectorInAscii.substring(0,16);
        iv32 = initVectorInAscii;
        byte[] iv16_arr = iv16.getBytes();
        int n = messageInBinary.length();
        lastBlock = messageInBinary.substring(n-128, n);
        forgedCiphertext = new String(new char[16]).replace("\0", "00000000");
        concatenatedCiphertexts = forgedCiphertext.concat(lastBlock);
        System.out.println("128+128="+concatenatedCiphertexts.length());   // 256
        AES.decrypt("0000000052be8ed3", iv16, forgedCiphertext+"\\x0f\\x0f\\x0f\\x0f\\x0f\\x0f\\x0f\\x0f\\x0f\\x0f\\x0f\\x0f\\x0f\\x0f\\x0f");
    }
}
