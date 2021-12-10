import java.util.HashMap;

public class PatriciaTrie {

    PatriciaTrieNodeFail root;

    public PatriciaTrie() {
        root = new PatriciaTrieNodeFail("", -1, new HashMap());
    }

    public void insert (String text, int index) {
        root.insert(text, index);
    }

    public void colapse() {
        root.colapse();
    }

    public int search (String text) {
        return root.search(text);
    }


}