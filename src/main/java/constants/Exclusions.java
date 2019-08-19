package constants;

import model.ExclusionDefinition;

import java.util.LinkedList;

import static files.ReadTabDelimited.formatNumericDigits;

public class Exclusions {
    private final LinkedList<ExclusionDefinition> exclusionList = new LinkedList<>();

    public LinkedList<ExclusionDefinition> getExclusionList() {
        return exclusionList;
    }

    public String[][] getExclusionListAsArray() {
        int i = 0;
        String[][] exclusionListAsArray = new String[exclusionList.size()][2];
        for (ExclusionDefinition exclusionDefinition : exclusionList) {
            exclusionListAsArray[i][0] = exclusionDefinition.getEtab();
            exclusionListAsArray[i][1] = exclusionDefinition.getAgence();
            i++;
        }
        return exclusionListAsArray;
    }

    public String[][] addExclusionToList(ExclusionDefinition exclusionList) {
        this.exclusionList.add(
                new ExclusionDefinition(
                        formatNumericDigits(5, exclusionList.getEtab()),
                        formatNumericDigits(5, exclusionList.getAgence())
                )
        );
        return getExclusionListAsArray();
    }

    public String[][] removeExclusionFromList(ExclusionDefinition exclusionDefinition) {
        exclusionList.remove(exclusionDefinition);
        return getExclusionListAsArray();
    }

    public void initializeExclusionList() {
        this.exclusionList.clear();
        this.exclusionList.add(new ExclusionDefinition("99999", ""));
        this.exclusionList.add(new ExclusionDefinition("99999", "00000"));
        this.exclusionList.add(new ExclusionDefinition("30003", "99999"));
        this.exclusionList.add(new ExclusionDefinition("30076", "02759"));
        this.exclusionList.add(new ExclusionDefinition("10268", "02019"));
        this.exclusionList.add(new ExclusionDefinition("10268", "02040"));
        this.exclusionList.add(new ExclusionDefinition("10268", "04212"));
        this.exclusionList.add(new ExclusionDefinition("10268", "04370"));
        this.exclusionList.add(new ExclusionDefinition("30077", "02040"));
        this.exclusionList.add(new ExclusionDefinition("30076", "04153"));
        this.exclusionList.add(new ExclusionDefinition("13259", "02181"));
        for (int i = 1; i < 2000; i++) {
            this.exclusionList.add(new ExclusionDefinition("99999", formatNumericDigits(5, Integer.toString(i))));
        }
    }
}
