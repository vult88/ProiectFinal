package files;

import model.ExclusionDefinition;
import model.FileDefinition;
import model.FileDefinitionColumns;

import java.io.File;
import java.util.LinkedList;
import java.util.Scanner;

import static model.FileDefinitionColumns.*;

public class ReadTabDelimited {

    public static LinkedList<FileDefinition> fileDefinitions = new LinkedList<>();
    private static boolean isOtherElementsToErase;

    public static void readTabDelimitedFile(File file) throws IllegalArgumentException {
        try {
            Scanner scan = new Scanner(file);

            // Header read
            if (scan.hasNext()) {
                String curLine = scan.nextLine();
                String[] splitted = curLine.split("\t");
                if (!splitted[0].trim().equalsIgnoreCase("tit")) {
                    throw new IllegalArgumentException("No header found in source file !");
                }
                FileDefinitionColumns fileDefinitionColumns = new FileDefinitionColumns();
                for (int i = 0; i < splitted.length; i++) {
                    switch (splitted[i].trim().toLowerCase()) {
                        case "tit":
                            setPositionColumnTit(i);
                            break;
                        case "etablissement":
                            setPositionColumnEtab(i);
                            break;
                        case "agence":
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

            // Body read
            while (scan.hasNext()) {
                String curLine = scan.nextLine();
                String[] splitted = curLine.split("\t");

                fileDefinitions.add(new FileDefinition(
                        splitted[getPositionColumnTit()],
                        formatNumericDigits(5, splitted[getPositionColumnEtab()]),
                        formatNumericDigits(5, splitted[getPositionColumnAgence()]),
                        formatNumericDigits(11, splitted[getPositionColumnFCCD()]),
                        formatNumericDigits(11, splitted[getPositionColumnFCCG()]),
                        sumTotal(formatNumericDigits(11, splitted[getPositionColumnFCCD()]),
                                formatNumericDigits(11, splitted[getPositionColumnFCCG()]))
                ));
            }
            scan.close();
        } catch (Exception e) {
            e.printStackTrace();
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

    public static void removeAllFileDefinitionByExclusionList(LinkedList<ExclusionDefinition> exclusionList) throws ArrayIndexOutOfBoundsException {

        for (ExclusionDefinition exclusionDefinition : exclusionList) {
            FileDefinition fileDefinition = new FileDefinition("", exclusionDefinition.getEtab(), exclusionDefinition.getAgence(), "", "", "");
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
