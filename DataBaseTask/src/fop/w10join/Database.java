package fop.w10join;

// TODO Import the stuff you need

public class Database {
    private static Path baseDataDirectory = Paths.get("data");

    public static void setBaseDataDirectory(Path baseDataDirectory) {
        Database.baseDataDirectory = baseDataDirectory;
    }

    public static Stream<Customer> processInputFileCustomer() {
        // TODO
    }

    public static Stream<LineItem> processInputFileLineItem() {
        // TODO

        // For quantity of LineItems please use Integer.parseInt(str) * 100. 
    }

    public static Stream<Order> processInputFileOrders() {
        // TODO
    }

    public Database() {
        // TODO
    }

    public static void main(String[] args) {
        // TODO Test your implementation
    }
}
