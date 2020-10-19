public class Food {
    private int id;
    private String name;
    private double price;

    public Food(String dataString) {
        String[] split = dataString.split(",");
        this.id = Integer.parseInt(split[0]);
        this.name = split[1];
        this.price = Double.parseDouble(split[2]);
    }

    public Food(String name, double price) {
        this.name = name;
        this.price = price;
    }

    public Food(int id, String name, double price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public String toInsertString() {
        return String.format("%s,%s", name, price);
    }

    @Override
    public String toString() {
        return String.format("%s,%s,%s", id, name, price);
    }
}
