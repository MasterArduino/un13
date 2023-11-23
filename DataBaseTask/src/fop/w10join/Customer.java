package fop.w10join;

import java.util.Arrays;
import java.util.Objects;

public class Customer {
    int custKey;
    String mktsegment;
    private char[] varChar;
    private int nationkey;
    private char[] phone;
    private float acctbal;
    private char[] comment;

    public Customer(int custKey, char[] varChar, int nationkey, char[] phone, float acctbal, String mktsegment,
                    char[] comment) {
        this.custKey = custKey;
        this.varChar = varChar;
        this.nationkey = nationkey;
        this.phone = phone;
        this.acctbal = acctbal;
        this.mktsegment = mktsegment;
        this.comment = comment;
    }

    public void output() {
        System.out.println(this.toString());
    }

    @Override
    public String toString() {
        return "Customer{" +
                "custKey=" + custKey +
                ", varChar=" + Arrays.toString(varChar) +
                ", nationkey=" + nationkey +
                ", phone=" + Arrays.toString(phone) +
                ", acctbal=" + acctbal +
                ", mktsegment=" + mktsegment +
                ", comment=" + Arrays.toString(comment) +
                '}';
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return custKey == customer.custKey &&
                nationkey == customer.nationkey &&
                Float.compare(customer.acctbal, acctbal) == 0 &&
                Arrays.equals(varChar, customer.varChar) &&
                Arrays.equals(phone, customer.phone) &&
                Objects.equals(mktsegment, customer.mktsegment) &&
                Arrays.equals(comment, customer.comment);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(custKey, mktsegment, nationkey, acctbal);
        result = 31 * result + Arrays.hashCode(varChar);
        result = 31 * result + Arrays.hashCode(phone);
        result = 31 * result + Arrays.hashCode(comment);
        return result;
    }

    public int getCustKey() {
        return custKey;
    }

    public void setCustKey(int custKey) {
        this.custKey = custKey;
    }

    public String getMktsegment() {
        return mktsegment;
    }

    public void setMktsegment(String mktsegment) {
        this.mktsegment = mktsegment;
    }

    public char[] getVarChar() {
        return varChar;
    }

    public void setVarChar(char[] varChar) {
        this.varChar = varChar;
    }

    public int getNationkey() {
        return nationkey;
    }

    public void setNationkey(int nationkey) {
        this.nationkey = nationkey;
    }

    public char[] getPhone() {
        return phone;
    }

    public void setPhone(char[] phone) {
        this.phone = phone;
    }

    public float getAcctbal() {
        return acctbal;
    }

    public void setAcctbal(float acctbal) {
        this.acctbal = acctbal;
    }

    public char[] getComment() {
        return comment;
    }

    public void setComment(char[] comment) {
        this.comment = comment;
    }
}
