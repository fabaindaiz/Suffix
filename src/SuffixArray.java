import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class SuffixArray {

    List<Integer> array;
    String text;

    public SuffixArray(String text) {
        this.text = text;
    }

    /**
     * Genera el arreglo de sufijos a partir del texto
     */
    public void loadText() {
        array = IntStream.range(0, text.length()).boxed().collect(Collectors.toList());

        Collections.sort(array, (i, j) -> {
            int end_i = (i + 20 > text.length())? text.length(): i+20;
            int end_j = (j + 20 > text.length())? text.length(): j+20;
            int compare = text.substring(i, end_i).compareTo(text.substring(j, end_j));
            if (compare == 0) {
                return text.substring(i).compareTo(text.substring(j));
            }
            else {
                return compare;
            }
        });
    }

    /**
     * Carga el arreglo de sufijos desde un archivo
     * @param file Archivo de entrada
     */
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
    }

    /**
     * Genera un string para comparar evitando out of index
     * @param i Elemento del array de sufijos
     * @param lenght Largo de la consulta
     * @return String del texto que se debe comparar
     */
    public String text_compare(int i, int lenght) {
        return (array.get(i) + lenght > text.length()) ? text.substring(array.get(i)) : text.substring(array.get(i), array.get(i) + lenght);
    }

    /**
     * Busca un string en el texto e imprime el autocompletado
     * @param searchString Cadena de texto a buscar
     */
    public void search(String searchString) {
        if (searchString.equals("")){
            System.out.println("Se esta buscando el string vacio");
            return;
        }

        int i = BinarySearch(0, text.length()-1, searchString);
        int coincidencias = 0;
        if (i == -1) {
            System.out.println("No hay coincidencias");
            return;
        }

        while (i < array.size() && text_compare(i, searchString.length()).compareTo(searchString) == 0) {
            int start, end;
            String s_start, s_end;

            if (array.get(i) < 20) {
                start = 0;
                s_start = "";
            } else {
                start = array.get(i) - 20;
                s_start = "[...] ";
            }

            if (array.get(i) + searchString.length() +20 > text.length()-1) {
                end = text.length();
                s_end = "";
            } else {
                end = array.get(i) +searchString.length() + 20;
                s_end = " [...]";
            }

            System.out.println(coincidencias+1 +". ("+ i +"): "+s_start +text.substring(start, end)+ s_end);
            coincidencias ++;
            i++;
        }

        System.out.println("Se encontraron " +coincidencias+ " coincidencias");
    }

    /**
     * Busqueda binaria en el texto utilizando el arreglo de sufijos
     * @param start Indice de inicio
     * @param end Indice de termino
     * @param searchString Cadena de texto a buscar
     * @return Ubicación del primer resultado para autocompletar
     */
    public int BinarySearch(int start, int end, String searchString) {
        int i = (start + end)/2;
        int compare = text_compare(i, searchString.length()).compareTo(searchString);

        if (compare == 0) {
            while (i >= 0 && text_compare(i, searchString.length()).compareTo(searchString) == 0)
                i--;
            return i + 1;
        } else if(start == end + 1) {
            return -1;
        } else if (compare < 0) {
            return BinarySearch(i+1, end, searchString);
        } else {
            return BinarySearch(start, i-1, searchString);
        }
    }

    /**
     * Guarda el arrego de sufijos en un archivo
     * @param file Archivo de salida
     */
    public void save(String file) {
        try {
            PrintWriter writer = new PrintWriter(new FileWriter(file));
            array.forEach(i -> writer.println(i));
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void main (String... args) throws IOException {
        Scanner sc = new Scanner(System.in);
        System.out.print("Nombre del archivo : ");
        String file = sc.nextLine();

        File temp = new File("res\\text\\" + file);
        File suffix = new File("res\\array\\" + file);
        if (!temp.exists()) {
            System.out.println("No existe el archivo");
            return;
        }
        String content = Files.readString(Path.of(temp.getPath()), StandardCharsets.US_ASCII);
        SuffixArray suffixArray = new SuffixArray(content);

        if (!suffix.exists()){
            System.out.println("Generando arreglo de sufijos");
            suffixArray.loadText();
            suffixArray.save(suffix.getPath());
        } else {
            System.out.println("Cargando arreglo de sufijos");
            suffixArray.loadArray(suffix);
        }
        while(true) {
            System.out.print("Ingrese un texto: ");
            suffixArray.search(sc.nextLine());
        }
    }
}