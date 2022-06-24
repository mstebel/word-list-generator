package pl.ms.wordlistgenerator;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.Objects;

@Service
class InputFilesService {
    public String covertDataToSqlQuery(String fileName) {
        StringBuilder stringBuilder = new StringBuilder();
        try (
                var fileReader = new FileReader(fileName);
                var reader = new BufferedReader(fileReader);
        ) {
            String nextLine = null;
            String line = "";
            Long id = 1L;
            while ((nextLine = reader.readLine()) != null) {
                if (fileName.equals("corpus_sentences.txt")) {
                    line = prepareSentenceQuery(nextLine);
                } else if (fileName.equals("dictionary.txt")) {
                    line = prepareEntryQuery(nextLine, id);
                    id++;
                }
                stringBuilder.append(line);
                stringBuilder.append('\n');
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return stringBuilder.toString();
    }

    private String prepareEntryQuery(String nextLine, Long id) {
        nextLine = nextLine.replaceAll("'", "`");
        String[] splitLine = nextLine.split("\\|");
        String wordType = splitLine[0];
        String word = splitLine[1];
        String def = splitLine[2];
        return String.format ("INSERT INTO word (id, word_type, name, definition) VALUES (%d, '%s', '%s', '%s');", id, word, wordType, def);
    }

    private String prepareSentenceQuery(String nextLine) {
        int counter = 0;
        int id = -1;
        String data = "";
        nextLine = cleanString(nextLine);
        if (nextLine.contains("'")) {
            nextLine = nextLine.replaceAll("'", "''");
        }
        char[] chars = nextLine.toCharArray();
        for (char character : chars) {
            if (Character.isDigit(character)) {
                counter++;
            } else {
                break;
            }
        }
        id = Integer.parseInt(nextLine.substring(0, counter));

        data = nextLine.substring(counter + 1);
        if (id > 0) {
            return String.format("INSERT INTO sentence (id, content) VALUES (%d, '%s');", id, data);
        } else {
            throw new IllegalStateException();
        }
    }

    private String cleanString(String nextLine) {
        nextLine = nextLine.trim();
        if (nextLine.contains("--")) {
            nextLine = nextLine.replace("-", " ");
        }
        nextLine = nextLine.replaceAll("( )+", " ");
        return nextLine;
    }

    public void exportToDataSql(String sqlStatement, String sqlDictStatement) {
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(Objects.requireNonNull(classLoader.getResource(".")).getFile() + "/data.sql");
        try (
                var fileWriter = new FileWriter(file);
                var writer = new BufferedWriter(fileWriter);
        ) {
            writer.write(sqlStatement);
            writer.newLine();
            writer.write(sqlDictStatement);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
