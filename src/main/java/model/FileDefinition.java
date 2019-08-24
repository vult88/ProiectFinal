package model;

import java.io.Serializable;

/**
 * Created by Vult on 2019-08-17.
 *
 */
public class FileDefinition implements Serializable {

    private String ColumnTit;
    private String ColumnEtab;
    private String ColumnAgence;
    private String ColumnFCCD;
    private String ColumnFCCG;
    private String ColumnTotal;

    public FileDefinition(String columnTit, String columnEtab, String columnAgence, String columnFCCD, String columnFCCG, String columnTotal) {
        ColumnTit = columnTit;
        ColumnEtab = columnEtab;
        ColumnAgence = columnAgence;
        ColumnFCCD = columnFCCD;
        ColumnFCCG = columnFCCG;
        ColumnTotal = columnTotal;
    }

    public FileDefinition() {
    }

    private String getColumnTit() {
        return ColumnTit;
    }

    public void setColumnTit(String columnTit) {
        ColumnTit = columnTit;
    }

    public String getColumnEtab() {
        return ColumnEtab;
    }

    public void setColumnEtab(String columnEtab) {
        ColumnEtab = columnEtab;
    }

    public String getColumnAgence() {
        return ColumnAgence;
    }

    public void setColumnAgence(String columnAgence) {
        ColumnAgence = columnAgence;
    }

    public String getColumnFCCD() {
        return ColumnFCCD;
    }

    public void setColumnFCCD(String columnFCCD) {
        ColumnFCCD = columnFCCD;
    }

    public String getColumnFCCG() {
        return ColumnFCCG;
    }

    public void setColumnFCCG(String columnFCCG) {
        ColumnFCCG = columnFCCG;
    }

    public String getColumnTotal() {
        return ColumnTotal;
    }

    public void setColumnTotal(String columnTotal) {
        ColumnTotal = columnTotal;
    }

    public String toStringNewLine() {
        return this.getColumnTit() + '\t' +
                this.getColumnEtab() + '\t' +
                this.getColumnAgence() + '\t' +
                this.getColumnFCCD() + '\t' +
                this.getColumnFCCG() + '\t' +
                this.getColumnTotal() + '\n';
    }

    public String toStringEndOfFile() {
        return this.getColumnTit() + '\t' +
                this.getColumnEtab() + '\t' +
                this.getColumnAgence() + '\t' +
                this.getColumnFCCD() + '\t' +
                this.getColumnFCCG() + '\t' +
                this.getColumnTotal() + '\n';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FileDefinition that = (FileDefinition) o;

        if (that.ColumnEtab.equals("99999")) {
            that.ColumnEtab = ColumnEtab;
        }
        if (that.ColumnAgence.equals("99999")) {
            that.ColumnAgence = ColumnAgence;
        }

        if (ColumnEtab != null ? !ColumnEtab.equals(that.ColumnEtab) : that.ColumnEtab != null) return false;
        return ColumnAgence != null ? ColumnAgence.equals(that.ColumnAgence) : that.ColumnAgence == null;
    }

    @Override
    public int hashCode() {
        int result = ColumnEtab != null ? ColumnEtab.hashCode() : 0;
        result = 31 * result + (ColumnAgence != null ? ColumnAgence.hashCode() : 0);
        return result;
    }
}
