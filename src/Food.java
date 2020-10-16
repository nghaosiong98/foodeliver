public class Food {
    private int id;
    private String name;

    public Food(String dataString) {
        String[] split = dataString.split(",");
        this.id = Integer.parseInt(split[0]);
        this.name = split[1];
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
