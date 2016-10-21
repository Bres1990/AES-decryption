import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;


public class Main {

    /// key : 44e0d8b652be8ed303d32e55e0f560d63d1e413a1bea275d6e70b119d6570c69
    private static String keySuffix = "52be8ed303d32e55e0f560d63d1e413a1bea275d6e70b119d6570c69";
    private static long  maxHex = 4294967295L;
    private static String IV = "223ac5739bc8bd16f0eed3f69cf3a695";
    private static byte[] IVBytes = DatatypeConverter.parseHexBinary(IV);
    private static String ciphertext = "+5O0zUs64D0wTmJeUqxgPhWUp5YMxffi7rl3FdZY0ujFv9nZhaNBX0MpxsnLuzGbuU4t30eE1UusudjP2c8bXnF2whTev+Mbo6EJpBfjckqRc2W/BU7gFGTV0T2Eqx4PZM+n9nwqgvYJFcOz2GfCfuAtRuuaDovK2zlq36DDBV4=";


    private static class KeyFinder implements Runnable{

        int id;
        long from;
        long to;

        PrintStream stream;

        public KeyFinder(int id, long from, long to) {
            this.id = id;
            this.from = from;
            this.to = to;

            try {
                stream = new PrintStream(new File("result"+id+".txt"));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void run() {
            stream.println("Jo sem "+id);
            stream.println("Szukam od "+from+" do "+to);
            findKey(from, to, stream);
        }
    }


    public static void main(String[] args) throws FileNotFoundException, InterruptedException {

        long range = maxHex/4;

        Thread[] workers = new Thread[4];

        for(int i=0; i<4; i++){
            workers[i] = new Thread(new KeyFinder(i,i*range, i*range+range));
            workers[i].start();
        }

        for(Thread worker: workers){
            worker.join();
        }



    }

    public static void findKey(long from, long to, PrintStream out){
        for(long i = from; i<=to; i++){
            String keyPrefix = String.format("%8s", Long.toHexString(i)).replaceAll(" ","0");
            String fullKey = keyPrefix+keySuffix;

            byte[] fullKeyInBytes = DatatypeConverter.parseHexBinary(fullKey);

            Key key = new SecretKeySpec(fullKeyInBytes, "AES");


            try {



                Cipher c = Cipher.getInstance("AES/CBC/NoPadding");
                c.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(IVBytes));
                byte[] decryptedBytes = c.doFinal(Base64.getDecoder().decode(ciphertext));

                boolean invalidCharacter = false;

                //if(i%10000000 == 0) out.println("Parsed "+ i+" "+fullKey+" "+new String(decryptedBytes));


                CharsetDecoder cs = Charset.forName("UTF-8").newDecoder();


                CharBuffer buffer = cs.decode(ByteBuffer.wrap(decryptedBytes));
                out.println(fullKey);
                out.println(buffer);


            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (NoSuchPaddingException e) {
                e.printStackTrace();
            } catch (InvalidKeyException e) {
                e.printStackTrace();
            } catch (BadPaddingException e) {
                e.printStackTrace();
            } catch (IllegalBlockSizeException e) {
                e.printStackTrace();
            } catch (InvalidAlgorithmParameterException e) {
                e.printStackTrace();
            } catch (CharacterCodingException e) {
            }
        }
    }
}