import java.util.HashMap;

public class PatriciaTrieNodeFail {

    int num = 0;
    int index = -1;
    public String data;
    HashMap<Character, PatriciaTrieNodeFail> child;


    public PatriciaTrieNodeFail(String text, int index, HashMap child) {
        this.child = child;
        this.data = text;
        this.index = index;
        this.num = 0;
    }

    public void insert(String text, int index) {
        PatriciaTrieNodeFail node = insert_(text, index);
        while (true) {
            if (node == null)
                return;
            text = text.substring(1);
            node = node.insert_(text, index);
        }
    }

    public PatriciaTrieNodeFail insert_(String text, int index) {
        num ++;
        if (this.data.equals("$")) {
            this.index = index;
            return null;
        }
        char c = text.charAt(0);
        if (!child.containsKey(c)) {
            child.put(c, new PatriciaTrieNodeFail(text.substring(0,1), index, new HashMap()));
        }
        return child.get(c);

    }



    public void colapse() {
        if (data.equals("$")) {
            return;
        }
        if (child.size() == 1) {
            PatriciaTrieNodeFail node = (PatriciaTrieNodeFail) this.child.values().toArray()[0];
            this.data += node.data;
            this.child = node.child;
            this.index = index;
            this.num += num;
            this.colapse();
        }
        else {
            child.forEach((k, v)-> v.colapse());
        }
    }

    public int search(String text) {
        int len = data.length();
        if (text.length() < len) {
            if (text.substring(0, text.length()).equals(data.substring(0, text.length())))
                return index;
        }
        else if (text.length() > len) {
            if (text.substring(0, len).equals(data))
                if (child.containsKey(text.charAt(len)))
                    return child.get(text.charAt(len)).search(text.substring(len));
        }
        else if (text.length() == len) {
            if (text.equals(data))
                return index;
        }
        return -1;
    }

}