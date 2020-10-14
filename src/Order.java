public class Order {
    private int id = -1;
    private String restaurantName;
    private String foodName;
    private int qty;
    private String customerName;
    private String status;

    public Order(int id, String foodName, String restaurantName, int qty, String customerName, String status) {
        this.id = id;
        this.foodName = foodName;
        this.restaurantName = restaurantName;
        this.qty = qty;
        this.customerName = customerName;
        this.status = status;
    }

    public Order(String foodName, String restaurantName, int qty, String customerName, String status) {
        this.foodName = foodName;
        this.restaurantName = restaurantName;
        this.qty = qty;
        this.customerName = customerName;
        this.status = status;
    }

    public String toRequestString() {
        return String.format("%s,%s,%s,%s,%s", restaurantName, foodName, qty, customerName, status);
    }

    @Override
    public String toString() {
        return String.format("%s,%s,%s,%s,%s,%s", id, restaurantName, foodName, qty, customerName, status);
    }
}
