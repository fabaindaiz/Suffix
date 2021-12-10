import java.util.HashMap;

public class PatriciaTrieNode {

    int num = 0;
    int index = -1;
    String data;
    HashMap<Character, PatriciaTrieNode> child;


    public PatriciaTrieNode(String text, int index, HashMap child) {
        this.child = child;
        this.data = text;
        this.index = index;
        this.num = 1;
    }

    public void insert(String text, int index) {
        num ++;
        if (this.data.equals('$') ) {
            return;
        }
        else if (data.length() <= 1) {
            char c = text.charAt(0);
            if (child.containsKey(c)) {
                child.get(c).insert(text.substring(1), index);
            } else {
                child.put(c, new PatriciaTrieNode(text.substring(0), index, new HashMap()));
            }
        } else {
            split(text, index);
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
                    return child.get(text.substring(len)).search(text.substring(len));
        }
        else if (text.length() == len) {
            if (text.equals(data))
                return index;
        }
        return -1;
    }



    private void split(String text, int index) {
        int dist = equalTo(data, text);
        int diff = data.length() - text.length();

        if (dist == -1 && diff == 0) {
            return;
        }

        dist = Math.min(data.length()-1, text.length()-1);

        PatriciaTrieNode temp = new PatriciaTrieNode(data.substring(dist), this.index, this.child);
        PatriciaTrieNode nuevo = new PatriciaTrieNode(text.substring(dist), index, new HashMap());

        this.child = new HashMap<>();
        this.child.put(data.charAt(dist), temp);
        this.child.put(text.charAt(dist), nuevo);

        this.data = data.substring(0, dist);
        this.index = -1;
    }

    private int equalTo(String text1, String text2) {
        int lenght = Math.min(text1.length(), text2.length());
        for (int i = 0; i < lenght; i++) {
            if (! (text1.charAt(i) == text2.charAt(i))) {
                return i;
            }
        }
        return -1;
    }

}