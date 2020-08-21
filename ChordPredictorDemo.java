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
        List<Integer> nextChords = chordPred.predictNextChord(currProg);
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