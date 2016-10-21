import java.util.Arrays;
import java.util.List;

/**
 * Created by Adam on 2016-10-14.
 */
public class InitVectorUtil {
    InitVectorData ivd = new InitVectorData();

    List<String> init_vectors = Arrays.asList(ivd.data);

    public String getInitVector(int number) {
        return init_vectors.get(number-1);
    }
}
