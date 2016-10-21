import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;

import static java.lang.Thread.sleep;

/**
 * Created by Adam on 2016-10-16.
 */
public class Keys {
    static Bruteforce b;
    static String str, initVectorInAscii, initVectorInBinary, messageInBinary, iv;
//    static byte[] iv;
    static PrintWriter writer;
    static String[] lines;

    public static void main(String[] args) throws IOException, InterruptedException {
        b = new Bruteforce();
        str = "0123456789abcdef";
        initVectorInAscii = b.getInitVectorForMessage(1);
        initVectorInBinary = b.asciiToBinary(initVectorInAscii);
        messageInBinary = b.getMessageWithNoWhitespaces(1);
        iv = initVectorInBinary;

        //1st, easiest case
/* -------------------------------------------------------------*/
//        writer = new PrintWriter("keys.txt");
//
//        char set[] = {'0','1','2','3','4','5','6','7','8','9'};
//        int k = 8;
//        printAllKLength(set, k);
//        writer.close();
/* --------------------------------------------------------------*/

        lines = StringUtils.split(FileUtils.readFileToString(new File("xaa"), "UTF-8"), "\n");


        for (int i = 0; i < lines.length; i++) {
            distinctPermute(lines[i].toCharArray(), lines[i]);
        }
    }

    //Print all distinct permutations
    public static void distinctPermute(char[] key, String keyString) throws UnsupportedEncodingException, InterruptedException {

        int len = key.length;

        //Sort the string alphabetically.
        Arrays.sort(key);

        //status flag.
        boolean isFinished = false;
        while(!isFinished){
            //System.out.println(str);
            sleep(2000);


//                if((AES.decrypt(keyString+"52be8ed3", iv, messageInBinary)) == null) {
//                    System.out.println(keyString+"52be8ed3: "+AES.decrypt(keyString+"52be8ed3", iv, messageInBinary));
//                }
                b.performBruteforceAttack(keyString+"52be8ed3", 1);


            //Find the rightmost character smaller than its next character.
            int i;
            for(i=len-2;i>=0; --i){
                if(key[i] < key[i+1]){
                    break;
                }
            }

            //if there is no such character, all are sorted in decreasing order,
            //means we just printed the last permutation and we are done.
            if(i == -1){
                isFinished = true;
            }else{
                // Find the ceil of 'first char' in right of first character.
                // Ceil of a character is the smallest character greater than it.
                int ceil = findCeil(key, key[i], i+1, len-1);

                // Swap first and second characters
                swap(key, i, ceil);

                // Sort the string on right of 'first char'
                Arrays.sort(key, i+1, len);
            }
        }
    }

    //Find the index for the ceiling character.
    public static int findCeil(char[] arr, char first, int l, int h){
        int ceil_index = l;

        for(int i=l+1;i<=h;i++){
            if(arr[i] > first && arr[i] < arr[ceil_index]){
                ceil_index = i;
            }
        }
        return ceil_index;
    }

    //Swap characters in an array.
    public static void swap(char[] str, int i, int j){
        char tmp = str[i];
        str[i] = str[j];
        str[j] = tmp;
    }

    // The method that prints all possible strings of length k. It is
    // mainly a wrapper over recursive function printAllKLengthRec()
    static void printAllKLength(char set[], int k) {
        int n = set.length;
        printAllKLengthRec(set, "", n, k);
    }

    // The main recursive method to print all possible strings of length k
    static void printAllKLengthRec(char set[], String prefix, int n, int k) {

        // Base case: k is 0, print prefix
        if (k == 0) {
            writer.println(prefix);
            return;
        }

        // One by one add all characters from set and recursively
        // call for k equals to k-1
        for (int i = 0; i < n; ++i) {

            // Next character of input added
            String newPrefix = prefix + set[i];

            // k is decreased, because we have added a new character
            printAllKLengthRec(set, newPrefix, n, k - 1);
        }
    }
}
