package model;

public class Order {
    private String customerName;
    private String menuName;
    private int quantity;
    private int totalPrice;

    public Order(String customerName, String menuName, int quantity, int menuPrice) {
        this.customerName = customerName;
        this.menuName = menuName;
        this.quantity = quantity;
        this.totalPrice = quantity * menuPrice;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getMenuName() {
        return menuName;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getTotalPrice() {
        return totalPrice;
    }
}
