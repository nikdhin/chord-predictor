public class ChordNode {
    enum ChordType {
        Major,
        Minor,
        Diminished
    }

    int chordNum;
    ChordType chordType;

    public ChordNode(int chordNum, ChordType chordType) {
        this.chordNum = chordNum;
        this.chordType = chordType;
    }

    public boolean equals(ChordNode chord) {
        return this.chordNum == chord.chordNum && this.chordType == chord.chordType;
    }

    @Override
    public String toString() {
        return this.chordNum + " " + this.chordType;
    }
}