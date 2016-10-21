import java.io.UnsupportedEncodingException;

public class Bruteforce {
    static CryptogramUtil cryptogramUtil = new CryptogramUtil();
    static InitVectorUtil initVectorUtil = new InitVectorUtil();


    // javax.crypto.BadPaddingException: Given final block not properly padded  == WRONG KEY
    //TODO: Bruteforce with all possible keys
    //TODO: Keep track of a number of checked keys before success (worst-case, average-case, etc.)
    public static void main(String[] args) throws UnsupportedEncodingException, InterruptedException {
        String str = "0123456789abcdef";
        Bruteforce b = new Bruteforce();
//        b.performChainBruteforceAttacks();
//        Keys.distinctPermute(str.toCharArray());


    }






    public void performBruteforceAttackWithKeyFragment(String keyFragment, int message) throws UnsupportedEncodingException {
        performBruteforceAttack(keyFragment+"52be8ed3", message);
    }

    public String performBruteforceAttack(String key, int message) throws UnsupportedEncodingException {
        String iv = getInitVectorForMessage(message).substring(0, 16);
        //String initVectorInAscii = getInitVectorForMessage(message);
        //String iv = asciiToBinary(initVectorInAscii).substring(0, 16);
        String messageInBinary = getMessageWithNoWhitespaces(message);
        String messageInAscii = messageToAscii(message);

        //byte[] iv_arr = iv.getBytes();
        //System.out.println(messageInAscii.length());
        //System.out.println(iv);
//        System.out.println(Arrays.toString(iv_arr));
//        System.out.println(messageInAscii.length());
        //byte[] message_arr = messageInAscii.getBytes();
        //System.out.println(message_arr.length);
        //System.out.println(Arrays.toString(message_arr));
        //System.out.println(messageInAscii);
        String auxMessage = messageInAscii+"\\x04\\x04\\x04\\x04";
        //byte[] auxMessage_arr = auxMessage.getBytes();

        //System.out.println(Arrays.toString(auxMessage_arr).length());
        //System.out.println(auxMessage_arr.length);
        byte[] messageBinary_arr = messageInBinary.getBytes();
        //System.out.println(Arrays.toString(messageBinary_arr));
        System.out.println(auxMessage.length());



        /**
         * Do kryptogramu 1 trzeba dodac 11 bajtow
         */



        //return AES.decrypt(key, iv, messageInBinary);
        return AES.decrypt(key, iv, auxMessage);    /// figure out the right padding!
    }

    public String getMessageWithNoWhitespaces(int message) {
        return cryptogramUtil.getCryptogram(message).replaceAll("\\s+", "");
    }

    public String getInitVectorForMessage(int message) {
        return initVectorUtil.getInitVector(message);
    }

    public String[] getWordsFromCryptogram(int message) {
        return cryptogramUtil.getCryptogram(message).split("\\s+");
    }

    public String binaryToAscii(String binary) {
        int charCode = Integer.parseInt(binary, 2);
        return Character.toString((char) charCode);
    }

    public String asciiToBinary(String ascii) {
        String s = ascii;
        byte[] bytes = s.getBytes();
        StringBuilder binary = new StringBuilder();
        for (byte b : bytes)
        {
            int val = b;
            for (int i = 0; i < 8; i++)
            {
                binary.append((val & 128) == 0 ? 0 : 1);
                val <<= 1;
            }
//            binary.append(' ');
        }

        return String.valueOf(binary);
    }

    public String messageToAscii(int message) {
        String[] words = getWordsFromCryptogram(message);
        String result = "";

        for (String word : words) {
            result += binaryToAscii(word);
        }

        return result;
    }



}
