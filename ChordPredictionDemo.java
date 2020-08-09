import java.io.IOException;
import java.net.URL;
import java.util.*;

import javax.sound.sampled.*;


class ChordPredictorDemo {
  private static final String CHORD_FILE_SUFFIX = "chord_Amin.wav";
  private static final String CHORD_RESOURCE_PATH = "chord_sounds";

  public static void main(String[] args) {
    ChordPredictorDemo demo = new ChordPredictorDemo();
    ChordPredictor chordPred = new ChordPredictor();
    Scanner reader = new Scanner(System.in);
    List<Integer> currProg = new ArrayList<>();

    // build a progression with 4 chords
    while(currProg.size() < 4) {
      System.out.printf("Pick a chord to add to the progression: ");
      int currChord = reader.nextInt();
      currProg.add(currChord);

      if(currProg.size() < 4) {
        List<Integer> nextChords = chordPred.predictNextChord(currChord);
        System.out.println("Compatible next chords: " + nextChords);
      }
    }
    reader.close();

    System.out.println("Chord Progression Built: " + currProg);
    System.out.println("Looping Chord Progression.. ");

    while(true) {
      for (Integer chord : currProg) {
        String chordFile = chord + CHORD_FILE_SUFFIX;

        try {
          demo.playFile(chordFile, CHORD_RESOURCE_PATH);
        } catch (UnsupportedAudioFileException | IOException
                | LineUnavailableException | InterruptedException e) {
          e.printStackTrace();
        }
      }
    }
  }

  private void playFile(String filename, String resourcePath) throws UnsupportedAudioFileException,
          IOException, LineUnavailableException, InterruptedException {
    URL songUrl = this.getClass().getResource(resourcePath + "/" + filename);
    Clip clip = AudioSystem.getClip();
    AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(songUrl);
    // load audio file
    clip.open(audioInputStream);
    clip.start();
    do Thread.sleep(1); while (clip.isActive());
  }
}

/**
 *
 */
class ChordPredictor {  
  // I–V–vi–IV
  List<Integer> prog1 = Arrays.asList(1, 5, 6, 4);
  // ii-V-I, Jazz
  List<Integer> prog2 = Arrays.asList(2, 5, 1);
  // I-IV-V
  List<Integer> prog3 = Arrays.asList(1, 4, 5);
  // I – V – vi – iii – IV – I – IV – V, known as The Canon
  List<Integer> prog4 = Arrays.asList(1, 5, 6, 3, 4, 1, 4, 5);

  List<List<Integer>> progressions = Arrays.asList(prog1, prog2, prog3, prog4);
  
  
  public ChordPredictor() {
    // empty constructor
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