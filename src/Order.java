public class Order {
    private int id = -1;
    private String restaurantName;
    private String foodName;
    private int qty;
    private String customerName;
    private String riderName = "";
    private String status;
    private double totalPrice;

    public Order(int id, String foodName, String restaurantName, int qty, String customerName, String riderName, String status) {
        this.id = id;
        this.foodName = foodName;
        this.restaurantName = restaurantName;
        this.qty = qty;
        this.customerName = customerName;
        this.riderName = riderName;
        this.status = status;
    }

    public Order(String foodName, String restaurantName, int qty, String customerName, String status, double totalPrice) {
        this.foodName = foodName;
        this.restaurantName = restaurantName;
        this.qty = qty;
        this.customerName = customerName;
        this.status = status;
        this.totalPrice = totalPrice;
    }

    public Order(String dataString) {
        String[] split = dataString.split(",");
        this.id = Integer.parseInt(split[0]);
        this.restaurantName = split[1];
        this.foodName = split[2];
        this.qty = Integer.parseInt(split[3]);
        this.customerName = split[4];
        this.riderName = split[5];
        this.status = split[6];
        this.totalPrice = Double.parseDouble(split[7]);
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public String getFoodName() {
        return foodName;
    }

    public int getQty() {
        return qty;
    }

    public void setRiderName(String riderName) {
        this.riderName = riderName;
    }

    public String getRiderName() {
        return riderName;
    }

    public String getStatus() {
        return status;
    }

    public int getId() {
        return id;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public String toInsertString() {
        return String.format("%s,%s,%s,%s,%s,%s,%.2f", restaurantName, foodName, qty, customerName, riderName, status, totalPrice);
    }

    @Override
    public String toString() {
        return String.format("%s,%s,%s,%s,%s,%s,%s,%.2f", id, restaurantName, foodName, qty, customerName, riderName, status, totalPrice);
    }
}
