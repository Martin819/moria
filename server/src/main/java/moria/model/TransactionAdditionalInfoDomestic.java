package moria.model;

import javax.persistence.*;
import java.io.Serializable;

@Embeddable
public class TransactionAdditionalInfoDomestic implements Serializable {

    private String constantSymbol;
    private String variableSymbol;
    private String specificSymbol;

    public TransactionAdditionalInfoDomestic() {
    }

    public TransactionAdditionalInfoDomestic(String constantSymbol, String variableSymbol, String specificSymbol) {
        this.constantSymbol = constantSymbol;
        this.variableSymbol = variableSymbol;
        this.specificSymbol = specificSymbol;
    }

    public String getConstantSymbol() {
        return constantSymbol;
    }

    public void setConstantSymbol(String constantSymbol) {
        this.constantSymbol = constantSymbol;
    }

    public String getVariableSymbol() {
        return variableSymbol;
    }

    public void setVariableSymbol(String variableSymbol) {
        this.variableSymbol = variableSymbol;
    }

    public String getSpecificSymbol() {
        return specificSymbol;
    }

    public void setSpecificSymbol(String specificSymbol) {
        this.specificSymbol = specificSymbol;
    }
}
