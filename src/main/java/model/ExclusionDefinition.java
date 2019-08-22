package model;

/**
 * Created by Vult on 2019-08-19 .
 *
 */
public class ExclusionDefinition {
    private String Etab;
    private String Agence;

    public ExclusionDefinition(String etab, String agence) {
        Etab = etab;
        Agence = agence;
    }

    public String getEtab() {
        return Etab;
    }

    public void setEtab(String etab) {
        Etab = etab;
    }

    public String getAgence() {
        return Agence;
    }

    public void setAgence(String agence) {
        Agence = agence;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ExclusionDefinition that = (ExclusionDefinition) o;

        if (Etab != null ? !Etab.equals(that.Etab) : that.Etab != null) return false;
        return Agence != null ? Agence.equals(that.Agence) : that.Agence == null;

    }

    @Override
    public int hashCode() {
        int result = Etab != null ? Etab.hashCode() : 0;
        result = 31 * result + (Agence != null ? Agence.hashCode() : 0);
        return result;
    }
}
