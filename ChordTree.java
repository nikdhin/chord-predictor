import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

class ChordTreeNode {
    Integer value = 0;
    boolean isEnd = false;
    List<ChordTreeNode> next;

    public ChordTreeNode() {
        // empty constructor
        next = new LinkedList<>();
    }

    public ChordTreeNode(Integer value) {
        // empty constructor
        next = new LinkedList<>();
        this.value = value;
    }

    public boolean contains(Integer chord) {
        for(ChordTreeNode node : next) {
            if(node.value.equals(chord)) return true;
        }

        return false;
    }

    public ChordTreeNode get(Integer chord) {
        for(ChordTreeNode node : next) {
            if(node.value.equals(chord)) return node;
        }

        return null;
    }

    public void put(Integer chord) {
        next.add(new ChordTreeNode(chord));
    }

    public void setEnd() {
        isEnd = true;
    }
    public boolean isEnd() {
        return isEnd;
    }
}

public class ChordTree {
    ChordTreeNode root;

    public ChordTree() {
        root = new ChordTreeNode();
    }

    public void insertProgression(List<Integer> progression) {
        ChordTreeNode curr = root;
        for(Integer chord : progression) {
            if(!curr.contains(chord)) {
                curr.put(chord);
            }

            curr = curr.get(chord);
        }

        curr.setEnd();
    }

    public List<Integer> getNextChord(List<Integer> progression) {
        List<Integer> res = new ArrayList<>();
        ChordTreeNode curr = root;
        for(Integer chord : progression) {
            if(!curr.contains(chord)) {
                return res;
            }

            curr = curr.get(chord);
        }

        for(ChordTreeNode chord : curr.next) {
            res.add(chord.value);
        }

        return res;
    }

    public void print() {
        printLevelOrder(root);
    }

    private void printLevelOrder(ChordTreeNode node) {
        LinkedList<ChordTreeNode> q = new LinkedList<>();
        q.push(node);

        while (!q.isEmpty()) {
            for (int i = q.size(); i > 0; i--) {
                ChordTreeNode curr = q.pop();
                System.out.print(curr.value + " ");
                for (ChordTreeNode child : curr.next) q.add(child);
            }
            System.out.println();
        }
    }
}
