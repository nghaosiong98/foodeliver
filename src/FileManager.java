import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class FileManager {

    private String filePath;
    private String metadataFile;
    private final String TEMP_FILE = "./temp.csv";

    public FileManager(String filePath) {
        this.filePath = filePath;
        File file = new File(filePath);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                System.out.println("Something went wrong when creating " + file.getName());
            }
        }

        this.metadataFile = String.format("%s-metadata.txt", filePath.split(".csv")[0]);
    }

    public void insert(String row) throws IOException {
        int id = increaseIdCounter();
        File file = new File(filePath);
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file, true));
        bufferedWriter.append(String.format("%s,%s", id, row)).append("\n");
        bufferedWriter.close();
    }

    public void insertWithoutId(String row) throws IOException {
        File file = new File(filePath);
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file, true));
        bufferedWriter.append(String.format("%s", row)).append("\n");
        bufferedWriter.close();
    }

    public ArrayList<String> readAll() throws IOException {
        ArrayList<String> rows = new ArrayList<>();
        File file = new File(filePath);
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String line;
        while ((line = reader.readLine()) != null) {
            rows.add(line);
        }
        reader.close();
        return rows;
    }

    public ArrayList<String> readByField(int columnIndex, String fieldValue) throws IOException {
        ArrayList<String> rows = new ArrayList<>();
        File file = new File(filePath);
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String line;
        while((line = reader.readLine()) != null) {
            String[] row = line.split(",");
            if (row[columnIndex].equals(fieldValue)) {
                rows.add(line);
            }
        }
        reader.close();
        return rows;
    }

    public String readById(int id) throws IOException {
        File file = new File(filePath);
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String line = "";
        while((line = reader.readLine()) != null) {
            String[] row = line.split(",");
            if (Integer.parseInt(row[0]) == id) {
                break;
            }
        }
        reader.close();
        return line;
    }

    public void updateById(int id, String row) throws IOException {
        File oriFile = new File(filePath);
        File tempFile = new File(TEMP_FILE);
        BufferedReader reader = new BufferedReader(new FileReader(oriFile));
        BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));
        String line;
        while ((line = reader.readLine()) != null) {
            String[] data = line.split(",");
            if(Integer.parseInt(data[0]) == id) {
                writer.write(row + "\n");
            } else {
                writer.write(line + "\n");
            }
        }
        reader.close();
        writer.close();
        oriFile.delete();
        tempFile.renameTo(oriFile);
        tempFile.delete();
    }

    public void deleteById(int id) throws IOException {
        File oriFile = new File(filePath);
        File tempFile = new File(TEMP_FILE);
        BufferedReader reader = new BufferedReader(new FileReader(oriFile));
        BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));
        String line;
        while((line = reader.readLine()) != null) {
            String[] data = line.split(",");
            if (Integer.parseInt(data[0]) == id) {
                continue;
            } else {
                writer.write(line + "\n");
            }
        }
        reader.close();
        writer.close();
        oriFile.delete();
        tempFile.renameTo(oriFile);
        tempFile.delete();
    }

    public int increaseIdCounter() throws IOException {
        final String ID_COUNTER_KEY = "ID_COUNTER";
        File metadataFile = new File(this.metadataFile);
        File tempFile = new File("meta-temp.csv");
        String line;
        int idCounter = 1;
        if (!metadataFile.exists()) {
            BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));
            writer.write(String.format("%s=%s", ID_COUNTER_KEY, idCounter));
            writer.close();
            metadataFile.delete();
            tempFile.renameTo(metadataFile);
        } else {
            BufferedReader reader = new BufferedReader(new FileReader(metadataFile));
            BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));
            while((line = reader.readLine()) != null) {
                String[] metadata = line.split("=");
                if (metadata[0].equals(ID_COUNTER_KEY)) {
                    idCounter = Integer.parseInt(metadata[1]);
                    writer.write(String.format("%s=%s", ID_COUNTER_KEY, ++idCounter));
                    break;
                }
            }
            reader.close();
            writer.close();
            metadataFile.delete();
            tempFile.renameTo(metadataFile);
            tempFile.delete();
        }
        return idCounter;
    }

    public void deleteFiles() {
        File file = new File(filePath);
        File metadataFile = new File(this.metadataFile);
        file.delete();
        metadataFile.delete();
    }
}
