
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;


/**
 *
 * @author yaw
 */
public class Proj2 {

    public static void main(String[] args) {
        RegularExpression re = new RegularExpression("(0001U1)*");
        System.out.println(re.test("0001")); // True.
        System.out.println(re.test("010101"));  // False.
        
        /*
        // NFA creation example.
        String[] states = new String[] {"S1", "S2", "S3", "S4"};
        char[] alphabet = new char[] {'0', '1'};
        
        // Create HashMap of all transitions in NFA.
        HashMap<String, HashMap<Character, HashSet<String>>> transitions = new HashMap<>();
        
        // Create all transitions from S1 with character 0. (There is only one - to S2)
        HashMap<Character, HashSet<String>> transition = new HashMap<>();
        transition.put('0', new HashSet<>(Arrays.asList("S2")));
        transitions.put("S1", transition);
        
        // Create the two epsilon transitions out of S2.
        transition = new HashMap<>();
        transition.put('e', new HashSet<>(Arrays.asList("S3", "S4")));
        transitions.put("S2", transition);
        
        transition = new HashMap<>();
        transition.put('1', new HashSet<>(Arrays.asList("S3")));
        transitions.put("S3", transition);
        
        transition = new HashMap<>();
        transition.put('0', new HashSet<>(Arrays.asList("S1")));
        transitions.put("S4", transition);
        
        transition = new HashMap<>();
        transition.put('1', new HashSet<>(Arrays.asList("S3")));
        transitions.put("S4", transition);
        
        String startState = "S1";
        String[] acceptStates = new String[]{"S3", "S4"};
        
        NFA nfa = new NFA(states, alphabet, transitions, startState, acceptStates);
        
        System.out.println(nfa.accepts("011111"));
        System.out.println(nfa.accepts("0011111"));
        */
    }
}
