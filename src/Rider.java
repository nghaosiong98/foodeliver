public class Rider {
    private String name;
    private FileManager orderFileManager;

    public Rider (String name) {
        this.name = name;
        this.orderFileManager = new FileManager("./order.txt");
    }
}
