
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;

/**
 *
 * @author yaw
 */
public class NFA {

    private String[] states;
    private char[] alphabet;
    private HashMap<String, HashMap<Character, HashSet<String>>> transitions;   //state -> (character -> states)
    private String startState;
    private String[] acceptStates;

    public NFA(String[] states, char[] alphabet, HashMap<String, HashMap<Character, HashSet<String>>> transitions, String startState, String[] acceptStates) {
        this.states = states;
        this.alphabet = alphabet;
        this.transitions = transitions;
        this.startState = startState;
        this.acceptStates = acceptStates;
    }

    public String[] getStates() {
        return states;
    }

    public char[] getAlphabet() {
        return alphabet;
    }

    public HashMap<String, HashMap<Character, HashSet<String>>> getTransitions() {
        return transitions;
    }

    public String getStartState() {
        return startState;
    }

    public String[] getAcceptStates() {
        return acceptStates;
    }

    // Determines whether or not the NFA accepts the input string.
    public boolean accepts(String string) {
        HashSet<String> currentStates = new HashSet<>();
        currentStates.addAll(extension(startState));
        for (char character : string.toCharArray()) {
            HashSet<String> newStates = new HashSet<>();
            for (String currentState : currentStates) {
                if (transitions.containsKey(currentState) && transitions.get(currentState).containsKey(character)) {
                    for (String newState : transitions.get(currentState).get(character)) {
                        newStates.addAll(extension(newState));
                    }
                }
            }
            currentStates = newStates;
        }
        return !Collections.disjoint(currentStates, Arrays.asList(acceptStates));
    }

    // Determines the set of states that can be reached via epsilon transitions from the provided state.
    private HashSet<String> extension(String startingState) {
        HashSet<String> oldReachableStates = new HashSet<>();
        HashSet<String> newReachableStates = new HashSet<>();
        newReachableStates.add(startingState);

        while (newReachableStates.size() != oldReachableStates.size()) {
            oldReachableStates = newReachableStates;
            newReachableStates = new HashSet<>();
            for (String state : oldReachableStates) {
                newReachableStates.add(state);

                // Get epsilon destinations
                if (transitions.containsKey(state) && transitions.get(state).containsKey('e')) {
                    newReachableStates.addAll(transitions.get(state).get('e'));
                }
            }
        }

        return newReachableStates;
    }
}
