package files;

import model.ExclusionDefinition;
import model.FileDefinition;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.LinkedList;

import static model.FileDefinitionColumns.*;

public class ReadTabDelimited {

    private static final String delimiter = ";";
    static LinkedList<FileDefinition> fileDefinitions = new LinkedList<>();

    //TODO Change bufferedReader.readLine by Files.lines for better performance
    // https://funnelgarden.com/java_read_file/#1a_FileReader_Default_Encoding
    static void readTabDelimitedFileInputStream(File file) throws Exception {

        FileInputStream fileInputStream = new FileInputStream(file);

        //specify CP-1252 (ANSI) encoding explicitly
        InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, "CP1252");

        try (BufferedReader bufferedReader = new BufferedReader(inputStreamReader)) {
            String line;

            // read Header
            if ((line = bufferedReader.readLine()) != null) {
                String[] splitted = line.split(delimiter);
                String firstColumn = splitted[0].trim().toLowerCase();
                if (!firstColumn.equals("tit")) {
                    throw new IllegalArgumentException("No header found in source file !");
                }
                for (int i = 0; i < splitted.length - 1; i++) {
//                    System.out.println("Column[" + i + "] : " + splitted[i]);
                    switch (splitted[i].trim().toLowerCase()) {
                        case "tit":
                            setPositionColumnTit(i);
                            break;
                        case "etablissements":
                            setPositionColumnEtab(i);
                            break;
                        case "agences":
                            setPositionColumnAgence(i);
                            break;
                        case "fccd":
                            setPositionColumnFCCD(i);
                            break;
                        case "fccg":
                            setPositionColumnFCCG(i);
                            break;
                    }
                }
                fileDefinitions.add(new FileDefinition(
                        splitted[getPositionColumnTit()],
                        splitted[getPositionColumnEtab()],
                        splitted[getPositionColumnAgence()],
                        splitted[getPositionColumnFCCD()],
                        splitted[getPositionColumnFCCG()],
                        "Total"
                ));

            }
            while ((line = bufferedReader.readLine()) != null) {
                String[] splitted = line.split(delimiter);

                FileDefinition row = new FileDefinition();
                row.setColumnTit(splitted.length > getPositionColumnTit() ? splitted[getPositionColumnTit()] : "   ");
                row.setColumnEtab(splitted.length > getPositionColumnEtab() ? formatNumericDigits(5, splitted[getPositionColumnEtab()]) : formatNumericDigits(5, "0"));
                row.setColumnAgence(splitted.length > getPositionColumnAgence() ? formatNumericDigits(5, splitted[getPositionColumnAgence()]) : formatNumericDigits(5, "0"));
                row.setColumnFCCD(splitted.length > getPositionColumnFCCD() ? formatNumericDigits(11, splitted[getPositionColumnFCCD()]) : formatNumericDigits(11, "0"));
                row.setColumnFCCG(splitted.length > getPositionColumnFCCG() ? formatNumericDigits(11, splitted[getPositionColumnFCCG()]) : formatNumericDigits(11, "0"));
                row.setColumnTotal(sumTotal(row.getColumnFCCD(), row.getColumnFCCG()));

                fileDefinitions.add(row);
            }
        }
    }

    public static String formatNumericDigits(int numberOfDigits, String numberToConvert) {
        if (!numberToConvert.isEmpty() && numberToConvert.matches("^[0-9]*$")) {
            return String.format("%0" + numberOfDigits + "d", Long.parseLong(numberToConvert));
        } else {
            return numberToConvert;
        }
    }

    private static String sumTotal(String... n) {
        long sum = 0;
        for (String arg : n) {
            sum = sum + Long.parseLong(arg);
        }
        return formatNumericDigits(11, Long.toString(sum));
    }

    //TODO Replace method removeAllFileDefinitionByExclusionList by a filter (foreach) directly when reading the file line by line
    public static void removeAllFileDefinitionByExclusionList(LinkedList<ExclusionDefinition> exclusionList) throws ArrayIndexOutOfBoundsException {

        for (ExclusionDefinition exclusionDefinition : exclusionList) {
            FileDefinition fileDefinition = new FileDefinition("", exclusionDefinition.getEtab(), exclusionDefinition.getAgence(), "", "", "");
            boolean isOtherElementsToErase;
            do {
                int index = 0;
                isOtherElementsToErase = false;
                // Building the Array for each definition of the exclusion list so we always have the correct index of the found element
                FileDefinition[] fileDefinitionArray = new FileDefinition[fileDefinitions.size()];
                int i = 0;
                for (FileDefinition fileDefinition1 : fileDefinitions) {
                    fileDefinitionArray[i] = fileDefinition1;
                    i++;
                }
                // We parse the array until we find the first line to remove
                for (int j = 0; j < fileDefinitions.size(); j++) {
                    if ((fileDefinition.getColumnEtab().equals("99999") && fileDefinition.getColumnAgence().equals(fileDefinitionArray[j].getColumnAgence()))
                            || (fileDefinition.getColumnEtab().equals(fileDefinitionArray[j].getColumnEtab()) && fileDefinition.getColumnAgence().equals("99999"))) {
                        index = j;
                        isOtherElementsToErase = true;
                        // Force loop exit
                        break;
                    }
                }
                if (isOtherElementsToErase) {
                    fileDefinitions.remove(index);
                }
            } while (isOtherElementsToErase);
        }
    }
}
