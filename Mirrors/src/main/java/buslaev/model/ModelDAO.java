package buslaev.model;

import buslaev.build.Component;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class ModelDAO {
    private final String PATH = "src\\main\\resources\\Models";
    private static final String CSV_SEPARATOR = ",";

    private int createNextCSV(){
        try {
            int counter = 0;
            File newFile = new File(PATH + "\\" + counter + ".csv");
            while (newFile.exists()) {
                counter++;
                newFile = new File(PATH + "\\" + counter + ".csv");
            }
            newFile.createNewFile();
            return counter;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void writeModelToCSV(Model model, String file){
        try
        {
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            writer.write(model.toCSVString(CSV_SEPARATOR));
            writer.close();
        }
        catch (Exception e) {
            System.out.print(e.getMessage());
        }
    }

    public String save(Model model){
        int i = createNextCSV();
        String s = PATH + "\\" + i + ".csv";
        writeModelToCSV(model, s);
        return i + ".csv";
    }

    public Model getModel(int i) throws Exception {
        List<String> readFile = readFile(PATH + "\\" + i + ".csv");
        List<Mirror> mirrors = new ArrayList<>();
        Model res = parseModel(readFile.get(0));
        for (int j = 1; j < readFile.size(); j++) {
            mirrors.add(parseMirror(readFile.get(j)));
        }
        res.setMirrors(mirrors);
        return res;
    }

    public List<String> readFile(String filename){
        List<String> res = new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filename));
            String line;
            while ((line = reader.readLine()) != null) {
                res.add(line);
            }
            reader.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return res;
    }

    public Model parseModel(String s) {
        String[] tokens = s.split(CSV_SEPARATOR);
        int startX = Integer.parseInt(tokens[0]);
        int startY = Integer.parseInt(tokens[0]);
        int directionX = Integer.parseInt(tokens[0]);
        int directionY = Integer.parseInt(tokens[0]);

        return new Model(new ArrayList<>(), startX, startY, directionX, directionY);
    }

    public Mirror parseMirror(String s) {
        String[] tokens = s.split(CSV_SEPARATOR);

        boolean isSphere = Boolean.parseBoolean(tokens[0]);
        Boolean isConcave = Boolean.parseBoolean(tokens[1]);
        Integer R = Integer.parseInt(tokens[3]);
        int posX = Integer.parseInt(tokens[4]);
        int posY = Integer.parseInt(tokens[5]);

        return new Mirror(isSphere, isConcave, R, posX, posY);
    }
}
