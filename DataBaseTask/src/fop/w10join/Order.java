package fop.w10join;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Objects;

public class Order {
    int orderKey;
    int custKey;
    private char orderStatus;
    private float totalPrice;
    private LocalDate orderDate;
    private char[] orderPriority;
    private char[] clerk;
    private int shippingPriority;
    private char[] comment;

    public Order(int orderKey, int custKey, char orderStatus, float totalPrice, LocalDate orderDate,
                 char[] orderPriority, char[] clerk, int shippingPriority, char[] comment) {
        this.orderKey = orderKey;
        this.custKey = custKey;
        this.orderStatus = orderStatus;
        this.totalPrice = totalPrice;
        this.orderDate = orderDate;
        this.orderPriority = orderPriority;
        this.clerk = clerk;
        this.shippingPriority = shippingPriority;
        this.comment = comment;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderKey=" + orderKey +
                ", custKey=" + custKey +
                ", orderStatus=" + orderStatus +
                ", totalPrice=" + totalPrice +
                ", orderDate=" + orderDate +
                ", orderPriority=" + Arrays.toString(orderPriority) +
                ", clerk=" + Arrays.toString(clerk) +
                ", shippingPriority=" + shippingPriority +
                ", comment=" + Arrays.toString(comment) +
                '}';
    }

    public void output() {
        System.out.println(this.toString());
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return orderKey == order.orderKey;
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

    public int getCustKey() {
        return custKey;
    }

    public void setCustKey(int custKey) {
        this.custKey = custKey;
    }

    public char getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(char orderStatus) {
        this.orderStatus = orderStatus;
    }

    public float getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(float totalPrice) {
        this.totalPrice = totalPrice;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }

    public char[] getOrderPriority() {
        return orderPriority;
    }

    public void setOrderPriority(char[] orderPriority) {
        this.orderPriority = orderPriority;
    }

    public char[] getClerk() {
        return clerk;
    }

    public void setClerk(char[] clerk) {
        this.clerk = clerk;
    }

    public int getShippingPriority() {
        return shippingPriority;
    }

    public void setShippingPriority(int shippingPriority) {
        this.shippingPriority = shippingPriority;
    }

    public char[] getComment() {
        return comment;
    }

    public void setComment(char[] comment) {
        this.comment = comment;
    }
}
