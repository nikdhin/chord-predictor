import java.util.*;

public class ChordPredictor {
    // I–V–vi–IV
    List<Integer> prog1 = Arrays.asList(1, 5, 6, 4);
    // ii-V-I, Jazz
    List<Integer> prog2 = Arrays.asList(2, 5, 1);
    // I-IV-V
    List<Integer> prog3 = Arrays.asList(1, 4, 5);
    // I – V – vi – iii – IV – I – IV – V, known as The Canon
    List<Integer> prog4 = Arrays.asList(1, 5, 6, 3, 4, 1, 4, 5);
    List<List<Integer>> progressions = Arrays.asList(prog1, prog2, prog3, prog4);
    ChordTree tree;

    public ChordPredictor() {
        tree = new ChordTree();

        for(List<Integer> progression : progressions) {
            tree.insertProgression(progression);
        }

        // TODO: remove
        // tree.print();
    }

    // prefix search
    public List<Integer> predictNextChord(List<Integer> currChordProg) {
        return tree.getNextChord(currChordProg);
    }

    /**
     * Outputs potential chord candidates for the next chord,
     * given current chord.
     * @param currChord Current Chord Number
     * @return List of potential chord numbers
     */
    public List<Integer> predictNextChord(int currChord) {
        Set<Integer> result = new HashSet<>();

        // find chord in the popular progressions
        for(List<Integer> progression : progressions) {

            int progLen = progression.size();

            for(int i = 0; i < progLen; i++) {
                int chord = progression.get(i);

                // add next chord to the result
                if(chord == currChord && i != progLen - 1) {
                    result.add(progression.get(i+1));
                } else if (chord == currChord && i == progLen - 1) {
                    // wrap around if we are at the end of chord progression
                    result.add(progression.get(0));
                }
            }
        }

        return new ArrayList<>(result);
    }

}