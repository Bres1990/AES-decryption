import java.util.Arrays;
import java.util.List;

/**
 * Created by Adam on 2016-10-14.
 */
public class CryptogramUtil {
    CryptogramData cd = new CryptogramData();

    List<String> cryptograms = Arrays.asList(cd.data);

    public String getCryptogram(int number) {
        return cryptograms.get(number-1);
    }
}
