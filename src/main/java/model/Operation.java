package model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Operation{
    private final static DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    private String operationType;
    private int sum;
    private String operationName;
    private String familyMember;
    private LocalDate operationDate;

    public Operation() {}

    public Operation(String str){
        String[] substrArr;
        String delimiter = " ";
        substrArr = str.split(delimiter);

        operationType = substrArr[0];
        sum = Integer.parseInt(substrArr[1].substring(0, substrArr[1].length() - 1));
        operationName = substrArr[2];
        familyMember = substrArr[3];
        operationDate = LocalDate.parse(substrArr[4], FORMATTER);
    }

    public String getOperationType() {
        return operationType;
    }

    public void setOperationType(String operationType) {
        this.operationType = operationType;
    }

    public int getSum() {
        return sum;
    }

    public void setSum(int sum) {
        this.sum = sum;
    }

    public String getOperationName() {
        return operationName;
    }

    public void setOperationName(String operationName) {
        this.operationName = operationName;
    }

    public String getFamilyMember() {
        return familyMember;
    }

    public void setFamilyMember(String familyMember) {
        this.familyMember = familyMember;
    }

    public LocalDate getOperationDate() {
        return operationDate;
    }

    public void setOperationDate(LocalDate operationDate) {
        this.operationDate = operationDate;
    }

    @Override
    public String toString() {
        return "model.Operation{" +
                "operationType='" + operationType + '\'' +
                ", sum='" + sum + '\'' +
                ", operationName='" + operationName + '\'' +
                ", familyMember='" + familyMember + '\'' +
                ", operationDate='" + operationDate + '\'' +
                '}';
    }
}

