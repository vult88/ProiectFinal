package model;

import java.io.Serializable;

/**
 * Created by Vult on 2019-08-17.
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

    public String getColumnTit() {
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
}
