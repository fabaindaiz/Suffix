import Trie.PatriciaTrie;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

public class SuffixTrie {

    PatriciaTrie trie;
    String text;

    public SuffixTrie(String text) {
        this.text = text;
    }

    /**
     * Genera el arreglo de sufijos a partir del texto
     */
    public void loadText() {
        trie = new PatriciaTrie();

        for (int i = 0; i < text.length(); i++) {
            trie.insert(text.substring(i), i);
        }
        trie.colapse();

    }

    /**
     * Genera un string para comparar evitando out of index
     * @param start Elemento inicial del texto
     * @param lenght Largo de la consulta
     * @return String del texto que se debe insertar
     */
    public String text_compare(int start, int lenght) {
        return (start + lenght > text.length()) ? text.substring(start) : text.substring(start, start + lenght);
    }

    public void search(String key) {
        int i = trie.search(key);
        if (i == -1) {
            System.out.println("No hay coincidencias");
            return;
        } else {
            System.out.println(text_compare(i, 100));
        }


    }

    /**
     * Carga el arreglo de sufijos desde un archivo
     * @param file Archivo de entrada
    public void loadArray(File file) {
        array = new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String linea;
            while((linea = reader.readLine())!=null)
                array.add(Integer.valueOf(linea));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/

    /**
     * Guarda el arrego de sufijos en un archivo
     * @param file Archivo de salida
    public void save(String file) {
        try {
            PrintWriter writer = new PrintWriter(new FileWriter(file));
            array.forEach(i -> writer.println(i));
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/


    public static void main (String... args) throws IOException {
        Scanner sc = new Scanner(System.in);
        System.out.print("Nombre del archivo : ");
        String file = sc.nextLine();

        File temp = new File("res\\text\\" + file);
        File suffix = new File("res\\trie\\" + file);
        if (!temp.exists()) {
            System.out.println("No existe el archivo");
            return;
        }
        String content = Files.readString(Path.of(temp.getPath()), StandardCharsets.US_ASCII);
        content += "$";
        SuffixTrie suffixTrie = new SuffixTrie(content);

        if (!suffix.exists()){
            System.out.println("Generando árbol de sufijos");
            suffixTrie.loadText();
            //suffixTrie.save(suffix.getPath());
        } else {
            System.out.println("Cargando árbol de sufijos");
            //suffixTrie.loadArray(suffix);
        }
        while(true) {
            System.out.print("Ingrese un texto: ");
            suffixTrie.search(sc.nextLine());
        }
    }
}