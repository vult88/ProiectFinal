package files;

import exclusions.Exclusions;
import model.FileDefinition;

import java.io.File;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.LinkedList;
import java.util.stream.Stream;

import static exclusions.Exclusions.isPresentInExclusionList;
import static model.FileDefinitionColumns.*;

public class ReadTabDelimited {

    private static final Character delimiter = '\u003B';
    static LinkedList<FileDefinition> fileDefinitions = new LinkedList<>();

    static void readTabDelimitedFile(File file) throws Exception {
        LinkedList exclusionList = Exclusions.getExclusionList();

        Stream lineReadStream = Files.lines(file.toPath(), Charset.forName("Cp1252"));
        String lineRead = lineReadStream.filter(line -> line.toString().contains("TIT")).findFirst().get().toString();
        String[] header = lineRead.split(delimiter.toString());
        for (int i = 0; i < header.length - 1; i++) {
            switch (header[i].trim().toUpperCase()) {
                case "TIT":
                    setPositionColumnTit(i);
                    break;
                case "ETABLISSEMENTS":
                    setPositionColumnEtab(i);
                    break;
                case "AGENCES":
                    setPositionColumnAgence(i);
                    break;
                case "FCCD":
                    setPositionColumnFCCD(i);
                    break;
                case "FCCG":
                    setPositionColumnFCCG(i);
                    break;
            }
        }
        fileDefinitions.add(new FileDefinition(
                header[getPositionColumnTit()],
                header[getPositionColumnEtab()],
                header[getPositionColumnAgence()],
                header[getPositionColumnFCCD()],
                header[getPositionColumnFCCG()],
                "Total"
        ));

        try (Stream linesStream = Files.lines(file.toPath(), Charset.forName("Cp1252"))) {
            linesStream.filter(line -> {
                String lineReadInStream = line.toString();
                String[] splited = lineReadInStream.split(delimiter.toString());
                return !splited[0].toUpperCase().equals("TIT") &&
                        !isPresentInExclusionList((splited.length > getPositionColumnEtab() ? formatNumericDigits(5, splited[getPositionColumnEtab()]) : formatNumericDigits(5, "0")),
                                (splited.length > getPositionColumnAgence() ? formatNumericDigits(5, splited[getPositionColumnAgence()]) : formatNumericDigits(5, "0")));
            }).forEach(ReadTabDelimited::addToFileDefinitionList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void addToFileDefinitionList(Object line) {
        String lineRead = line.toString();
        String[] splited = lineRead.split(delimiter.toString());

        FileDefinition row = new FileDefinition();
        row.setColumnTit(splited.length > getPositionColumnTit() ? splited[getPositionColumnTit()] : "   ");
        row.setColumnEtab(splited.length > getPositionColumnEtab() ? formatNumericDigits(5, splited[getPositionColumnEtab()]) : formatNumericDigits(5, "0"));
        row.setColumnAgence(splited.length > getPositionColumnAgence() ? formatNumericDigits(5, splited[getPositionColumnAgence()]) : formatNumericDigits(5, "0"));
        row.setColumnFCCD(splited.length > getPositionColumnFCCD() ? formatNumericDigits(11, splited[getPositionColumnFCCD()]) : formatNumericDigits(11, "0"));
        row.setColumnFCCG(splited.length > getPositionColumnFCCG() ? formatNumericDigits(11, splited[getPositionColumnFCCG()]) : formatNumericDigits(11, "0"));
        row.setColumnTotal(sumTotal(row.getColumnFCCD(), row.getColumnFCCG()));

        fileDefinitions.add(row);
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
}
