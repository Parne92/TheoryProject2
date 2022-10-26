import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;

public class RegularExpression {

    private String regularExpression;
    private NFA nfa;
    private int counter = 1;

    // You are not allowed to change the name of this class or this constructor at all.
    public RegularExpression(String regularExpression) {
        this.regularExpression = regularExpression.replaceAll("\\s+", "");
        nfa = generateNFA();
    }

    // TODO: Complete this method so that it returns the nfa resulting from unioning the two input nfas.
    private NFA union(NFA nfa1, NFA nfa2) {
        String[] states = new String[nfa1.getStates().length + nfa2.getStates().length];
        int j = 0;
        for (String i:nfa1.getStates())
        {
            states[++j] = i;
        }
        for (String i:nfa2.getStates())
        {
            states[++j] = i;
        }
        j = 0;
        char[] alphabet = new char[nfa1.getAlphabet().length + nfa2.getAlphabet().length];
        for (char i:nfa1.getAlphabet())
        {
            alphabet[++j] = i;
        }
        for (char i:nfa2.getAlphabet())
        {
            alphabet[++j] = i;
        }
        j = 0;
        //HashMap<String, HashMap<Character, HashSet<String>>> transitions;   //state -> (character -> states)
        String startState = "S" + counter;
        String[] acceptStates = new String[nfa1.getAcceptStates().length + nfa2.getAcceptStates().length];
        for (String i:nfa1.getAcceptStates())
        {
            acceptStates[++j] = i;
        }
        for (String i:nfa1.getAcceptStates())
        {
            acceptStates[++j] = i;
        }
        j = 0;
        HashMap<String, HashMap<Character, HashSet<String>>> transitions = new HashMap<>();
        HashMap<Character, HashSet<String>> transition = new HashMap<>();
        transitions.putAll(nfa1.getTransitions());
        transitions.putAll(nfa2.getTransitions());
        transition.put('e', new HashSet(Arrays.asList(nfa1.getStartState())));
        transitions.put((startState), transition);
        transition = new HashMap<>();
        transition.put('e', new HashSet(Arrays.asList(nfa2.getStartState())));
        transitions.put((startState), transition);
        NFA newNFA = new NFA(states, alphabet, transitions, startState, acceptStates);
        return newNFA;
    }

    // TODO: Complete this method so that it returns the nfa resulting from concatenating the two input nfas.
    private NFA concatenate(NFA nfa1, NFA nfa2) {
        String[] states = new String[nfa1.getStates().length + nfa2.getStates().length];
        System.arraycopy(nfa1.getStates(), 0, states, 0, nfa1.getStates().length);
        System.arraycopy(nfa2.getStates(), 0, states, nfa1.getStates().length, nfa2.getStates().length);

        char[] alphabet = {'0', '1'};
        String startState = nfa1.getStartState();
        String[] acceptStates = nfa2.getAcceptStates();

        HashMap<String, HashMap<Character, HashSet<String>>> transitions = new HashMap<>();
        HashMap<Character, HashSet<String>> transition = new HashMap<>();
        transitions.putAll(nfa1.getTransitions());
        transitions.putAll(nfa2.getTransitions());
        transition.put('e', new HashSet<>(Arrays.asList(nfa2.getStartState())));
        for (String state: nfa1.getAcceptStates()){
            transitions.put((state), transition);
        }

        //System.out.println(Arrays.toString(states)+"\n"+transitions+" Start: "+ startState+" Accept: "+Arrays.toString(acceptStates));

        NFA new_nfa = new NFA(states, alphabet, transitions, startState, acceptStates);
        return new_nfa;
    }

    // TODO: Complete this method so that it returns the nfa resulting from "staring" the input nfa.
    private NFA star(NFA nfa) {
        String[] states = new String[nfa.getStates().length];
        int j = 0;
        for(String i: nfa.getStates()){
            states[j++] = i;
        }
        String startState = "s1";
        System.arraycopy(nfa.getStates(), 0, states, 0, nfa.getStates().length);


        char[] alphabet = nfa.getAlphabet();
        String[] acceptStates = nfa.getAcceptStates();
        String[] tempAcceptState = {"s1"};

        System.arraycopy(tempAcceptState, 0, acceptStates, acceptStates.length-1,nfa.getAcceptStates().length);


        HashMap<String, HashMap<Character, HashSet<String>>> transitions = new HashMap<>();
        HashMap<Character, HashSet<String>> transition = new HashMap<>();
        transitions.putAll(nfa.getTransitions());

        transition.put('e', new HashSet<>(Arrays.asList(nfa.getStartState())));
        for (String state: nfa.getAcceptStates()){
            transitions.put((state), transition);
        }

        System.out.println(Arrays.toString(states)+"\n"+transitions+" Start: "+ startState+" Accept: "+Arrays.toString(acceptStates));

       NFA new_nfa = new NFA(states, alphabet, transitions, startState, acceptStates);
       return new_nfa;
    }

    // TODO: Complete this method so that it returns the nfa resulting from "plussing" the input nfa.
    private NFA plus(NFA nfa) {
        return null;
    }

    // TODO: Complete this method so that it returns the nfa that only accepts the character c.
    private NFA singleCharNFA(char c) {
        String[] states = {"s" + counter, "s" + (counter + 1)};
        char[] alphabet = {'0', '1'};
        String startState = states[0];
        String[] acceptStates = {states[1]};
        
        HashMap<String, HashMap<Character, HashSet<String>>> transitions = new HashMap<>();
        HashMap<Character, HashSet<String>> transition = new HashMap<>();
        transition.put(c, new HashSet<>(Arrays.asList(states[1])));
        transitions.put(states[0], transition);
        
        NFA new_nfa = new NFA(states, alphabet, transitions, startState, acceptStates);
        counter += 2;

        //System.out.println(Arrays.toString(states)+"\n"+transitions.toString()+" Start: "+ startState+" Accept: "+Arrays.toString(acceptStates));

        return new_nfa;
    }

    // You are not allowed to change this method's header at all.
    public boolean test(String string) {
        return nfa.accepts(string);
    }

    // Parser. I strongly recommend you do not change any code below this line.
    // Do not change any of the characters recognized in the regex (e.g., U, *, +, 0, 1)
    private int position = -1, ch;

    public NFA generateNFA() {
        nextChar();
        return parseExpression();
    }

    public void nextChar() {
        ch = (++position < regularExpression.length()) ? regularExpression.charAt(position) : -1;
    }

    public boolean eat(int charToEat) {
        if (ch == charToEat) {
            nextChar();
            return true;
        }
        return false;
    }

    public NFA parseExpression() {
        NFA nfa = parseTerm();
        while (true) {
            if (eat('U')) {
                // Create the nfa that is the union of the two passed nfas.
                nfa = union(nfa, parseTerm());
            } else {
                return nfa;
            }
        }
    }

    public NFA parseTerm() {
        NFA nfa = parseFactor();
        while (true) {
            // Concatenate NFAs.
            if (ch == '0' || ch == '1' || ch == '(') {
                // Create the nfa that is the concatentaion of the two passed nfas.
                nfa = concatenate(nfa, parseFactor());
            } else {
                return nfa;
            }
        }
    }

    public NFA parseFactor() {
        NFA nfa = null;
        if (eat('(')) {
            nfa = parseExpression();
            if (!eat(')')) {
               throw new RuntimeException("Missing ')'");
            }
        } else if (ch == '0' || ch == '1') {
            // Create the nfa that only accepts the character being passed (regularExpression.charAt(position) == '0' or '1').
            nfa = singleCharNFA(regularExpression.charAt(position));
            nextChar();
        }

        if (eat('*')) {
            // Create the nfa that is the star of the passed nfa.
            nfa = star(nfa);
        } else if (eat('+')) {
            // Create the nfa that is the plus of the passed nfa.
            nfa = plus(nfa);
        }

        return nfa;
    }
}
