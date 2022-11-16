package jdbc.entities;

public class OrderLine {
    private int orderId;
    private int productId;
    private double unitPrice;
    private int qty;
    private double discount;

    public int getOrderId() {
        return orderId;
    }

    public int getProductId() {
        return productId;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public int getQty() {
        return qty;
    }

    public double getDiscount() {
        return discount;
    }
}
