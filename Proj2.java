
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;


/**
 *
 * @author yaw
 */
public class Proj2 {

    public static void main(String[] args) {
        RegularExpression re = new RegularExpression("((11)+U(00)+)*");
        System.out.println(re.test("")); // True.
        System.out.println(re.test("0000"));  // True.
        System.out.println(re.test("1111"));  // True.
        System.out.println(re.test("1010"));  // False.
    }
}
