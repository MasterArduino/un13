package fop.w10join;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Objects;

public class LineItem {
    int orderKey;
    int quantity;
    private int partKey;
    private int suppKey;
    private int lineItem;
    private float extendedPrice;
    private float discount;
    private float tax;
    private char returnFlag;
    private char lineStatus;
    private LocalDate shipDate;
    private LocalDate commitDate;
    private LocalDate receiptDate;
    private char[] shipInstruct;
    private char[] shipMode;
    private char[] comment;

    public LineItem(int orderKey, int partKey, int suppKey, int lineItem, int quantity, float extendedPrice,
                    float discount, float tax, char returnFlag, char lineStatus, LocalDate shipDate,
                    LocalDate commitDate, LocalDate receiptDate, char[] shipInstruct, char[] shipMode, char[] comment) {
        this.orderKey = orderKey;
        this.partKey = partKey;
        this.suppKey = suppKey;
        this.lineItem = lineItem;
        this.quantity = quantity;
        this.extendedPrice = extendedPrice;
        this.discount = discount;
        this.tax = tax;
        this.returnFlag = returnFlag;
        this.lineStatus = lineStatus;
        this.shipDate = shipDate;
        this.commitDate = commitDate;
        this.receiptDate = receiptDate;
        this.shipInstruct = shipInstruct;
        this.shipMode = shipMode;
        this.comment = comment;
    }

    @Override
    public String toString() {
        return "LineItem{" +
                "orderKey=" + orderKey +
                ", partKey=" + partKey +
                ", suppKey=" + suppKey +
                ", lineItem=" + lineItem +
                ", quantity=" + quantity +
                ", extendedPrice=" + extendedPrice +
                ", discount=" + discount +
                ", tax=" + tax +
                ", returnFlag=" + returnFlag +
                ", lineStatus=" + lineStatus +
                ", shipDate=" + shipDate +
                ", commitDate=" + commitDate +
                ", receiptDate=" + receiptDate +
                ", shipInstruct=" + Arrays.toString(shipInstruct) +
                ", shipMode=" + Arrays.toString(shipMode) +
                ", comment=" + Arrays.toString(comment) +
                '}';
    }

    public void output() {
        System.out.println(this.toString());
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        LineItem lineItem = (LineItem) obj;
        return orderKey == lineItem.orderKey;
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderKey);
    }

    public int getOrderKey() {
        return orderKey;
    }

    public void setOrderKey(int orderKey) {
        this.orderKey = orderKey;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getPartKey() {
        return partKey;
    }

    public void setPartKey(int partKey) {
        this.partKey = partKey;
    }

    public int getSuppKey() {
        return suppKey;
    }

    public void setSuppKey(int suppKey) {
        this.suppKey = suppKey;
    }

    public int getLineItem() {
        return lineItem;
    }

    public void setLineItem(int lineItem) {
        this.lineItem = lineItem;
    }

    public float getExtendedPrice() {
        return extendedPrice;
    }

    public void setExtendedPrice(float extendedPrice) {
        this.extendedPrice = extendedPrice;
    }

    public float getDiscount() {
        return discount;
    }

    public void setDiscount(float discount) {
        this.discount = discount;
    }

    public float getTax() {
        return tax;
    }

    public void setTax(float tax) {
        this.tax = tax;
    }

    public char getReturnFlag() {
        return returnFlag;
    }

    public void setReturnFlag(char returnFlag) {
        this.returnFlag = returnFlag;
    }

    public char getLineStatus() {
        return lineStatus;
    }

    public void setLineStatus(char lineStatus) {
        this.lineStatus = lineStatus;
    }

    public LocalDate getShipDate() {
        return shipDate;
    }

    public void setShipDate(LocalDate shipDate) {
        this.shipDate = shipDate;
    }

    public LocalDate getCommitDate() {
        return commitDate;
    }

    public void setCommitDate(LocalDate commitDate) {
        this.commitDate = commitDate;
    }

    public LocalDate getReceiptDate() {
        return receiptDate;
    }

    public void setReceiptDate(LocalDate receiptDate) {
        this.receiptDate = receiptDate;
    }

    public char[] getShipInstruct() {
        return shipInstruct;
    }

    public void setShipInstruct(char[] shipInstruct) {
        this.shipInstruct = shipInstruct;
    }

    public char[] getShipMode() {
        return shipMode;
    }

    public void setShipMode(char[] shipMode) {
        this.shipMode = shipMode;
    }

    public char[] getComment() {
        return comment;
    }

    public void setComment(char[] comment) {
        this.comment = comment;
    }
}
