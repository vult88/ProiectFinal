package exclusions;

import model.ExclusionDefinition;

import java.util.LinkedList;

import static files.ReadTabDelimited.formatNumericDigits;

public class Exclusions {
    private static final LinkedList<ExclusionDefinition> exclusionList = new LinkedList<>();

    public static LinkedList<ExclusionDefinition> getExclusionList() {
        if (exclusionList.isEmpty()) {
            initializeExclusionList();
        }
        return exclusionList;
    }

    public static void initializeExclusionList() {
        exclusionList.clear();
        exclusionList.add(new ExclusionDefinition("99999", ""));
        exclusionList.add(new ExclusionDefinition("99999", "00000"));
        exclusionList.add(new ExclusionDefinition("30003", "99999"));
        exclusionList.add(new ExclusionDefinition("30076", "02759"));
        exclusionList.add(new ExclusionDefinition("10268", "02019"));
        exclusionList.add(new ExclusionDefinition("10268", "02040"));
        exclusionList.add(new ExclusionDefinition("10268", "04212"));
        exclusionList.add(new ExclusionDefinition("10268", "04370"));
        exclusionList.add(new ExclusionDefinition("30077", "02040"));
        exclusionList.add(new ExclusionDefinition("30076", "04153"));
        exclusionList.add(new ExclusionDefinition("13259", "02181"));
        for (int i = 1; i < 2000; i++) {
            exclusionList.add(new ExclusionDefinition("99999", formatNumericDigits(5, Integer.toString(i))));
        }
    }

    public static boolean isPresentInExclusionList(String Etab, String Agence) {
        for (ExclusionDefinition exclusion : exclusionList) {
            if (("99999").equals(exclusion.getEtab()) && Agence.equals(exclusion.getAgence())
                    || (Etab.equals(exclusion.getEtab()) && ("99999").equals(exclusion.getAgence())))
                return true;
        }
        return false;
    }

    private String[][] getExclusionListAsArray() {
        int i = 0;
        String[][] exclusionListAsArray = new String[exclusionList.size()][2];
        for (ExclusionDefinition exclusionDefinition : exclusionList) {
            exclusionListAsArray[i][0] = exclusionDefinition.getEtab();
            exclusionListAsArray[i][1] = exclusionDefinition.getAgence();
            i++;
        }
        return exclusionListAsArray;
    }

    public String[][] addExclusionToList(ExclusionDefinition exclusionListPassed) {
        if (exclusionListPassed.getEtab().trim().equalsIgnoreCase("all")) {
            exclusionListPassed.setEtab("99999");
        }
        if (exclusionListPassed.getAgence().trim().equalsIgnoreCase("all")) {
            exclusionListPassed.setAgence("99999");
        }
        exclusionList.add(
                new ExclusionDefinition(
                        !exclusionListPassed.getEtab().isEmpty() ? formatNumericDigits(5, exclusionListPassed.getEtab()) : exclusionListPassed.getEtab(),
                        !exclusionListPassed.getAgence().isEmpty() ? formatNumericDigits(5, exclusionListPassed.getAgence()) : exclusionListPassed.getAgence()
                )
        );
        return getExclusionListAsArray();
    }

    public String[][] removeExclusionFromList(ExclusionDefinition exclusionDefinition) {
        if (exclusionDefinition.getEtab().trim().equalsIgnoreCase("all")) {
            exclusionDefinition.setEtab("99999");
        }
        if (exclusionDefinition.getAgence().trim().equalsIgnoreCase("all")) {
            exclusionDefinition.setAgence("99999");
        }
        exclusionList.remove(exclusionDefinition);
        return getExclusionListAsArray();
    }
}
