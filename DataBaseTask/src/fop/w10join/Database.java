package fop.w10join;


import com.google.gson.*;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Database {
    private static Path baseDataDirectory = Paths.get("data");

    private Map<Integer, String> customerMarketSegments = new HashMap<>();
    private Map<Integer, Customer> customers;
    private Map<Integer, LineItem> lineItems;
    private Map<Integer, Order> orders;
    private Map<Integer, LineItem> lineItemMap;
    public Database(List<LineItem> lineItems) {
        // Инициализация lineItemMap
        this.lineItemMap = lineItems.stream()
                .collect(Collectors.toMap(LineItem::getOrderKey, Function.identity(),
                        (existing, replacement) -> {
                            System.err.println("Duplicate key " + existing.getOrderKey());
                            System.err.println("Existing LineItem: " + existing);
                            System.err.println("Replacement LineItem: " + replacement);
                            // Замените на вашу логику обработки дубликатов
                            return existing; // Возвращаем существующий элемент
                        }));

        // Удаление дубликатов из lineItems с сохранением порядка
        this.lineItems = lineItems.stream()
                .distinct()
                .collect(Collectors.toMap(LineItem::getOrderKey, Function.identity(),
                        (existing, replacement) -> existing,
                        LinkedHashMap::new));

        // Инициализация других коллекций
        this.customers = processInputFileCustomer()
                .collect(Collectors.toMap(Customer::getCustKey, Function.identity()));

        this.orders = processInputFileOrders()
                .collect(Collectors.toMap(Order::getOrderKey, Function.identity()));
    }




    public static void setBaseDataDirectory(Path baseDataDirectory) {
        Database.baseDataDirectory = baseDataDirectory;
    }

    public Map<Integer, LineItem> getLineItemMap() {
        return lineItemMap;
    }

    public static Stream<Customer> processInputFileCustomer() {
        Path customerPath = Paths.get("D:\\JavaProgram\\StreamAPI\\DataBaseTask\\data\\customer.tbl");
        try {
            BufferedReader reader = new BufferedReader(new FileReader(customerPath.toFile()));
            return reader.lines()
                    .map(line -> {
                        String[] parts = line.split("\\|");
                        if (parts.length == 7) {
                            return new Customer(
                                    Integer.parseInt(parts[0]),
                                    parts[1].toCharArray(),
                                    Integer.parseInt(parts[2]),
                                    parts[3].toCharArray(),
                                    Float.parseFloat(parts[4]),
                                    parts[5],
                                    parts[6].toCharArray()
                            );
                        } else {
                            // Обработка ошибки: выводите сообщение или выбрасывайте исключение по вашему усмотрению.
                            return null;
                        }
                    })
                    .filter(Objects::nonNull); // Фильтр для удаления объектов, которые могли быть null из-за ошибки.
        } catch (IOException e) {
            throw new RuntimeException("Ошибка при чтении файла с информацией о клиентах", e);
        }
    }



    public static Stream<LineItem> processInputFileLineItem() {
        Path lineItemPath = baseDataDirectory.resolve("D:\\JavaProgram\\StreamAPI\\DataBaseTask\\data\\lineitem.tbl");
        try {
            BufferedReader reader = new BufferedReader(new FileReader(lineItemPath.toFile()));
            return reader.lines()
                    .map(line -> {
                        String[] parts = line.split("\\|");
                        return new LineItem(
                                Integer.parseInt(parts[0]),
                                Integer.parseInt(parts[1]),
                                Integer.parseInt(parts[2]),
                                Integer.parseInt(parts[3]),
                                Integer.parseInt(parts[4]),
                                Float.parseFloat(parts[5]),
                                Float.parseFloat(parts[6]),
                                Float.parseFloat(parts[7]),
                                parts[8].charAt(0),
                                parts[9].charAt(0),
                                LocalDate.parse(parts[10]),
                                LocalDate.parse(parts[11]),
                                LocalDate.parse(parts[12]),
                                parts[13].toCharArray(),
                                parts[14].toCharArray(),
                                parts[15].toCharArray()
                        );
                    });
        } catch (IOException e) {
            throw new RuntimeException("Ошибка при чтении файла с информацией о заказах", e);
        }
    }



    public static Stream<Order> processInputFileOrders() {
        Path orderPath = baseDataDirectory.resolve("D:\\JavaProgram\\StreamAPI\\DataBaseTask\\data\\orders.tbl");
        try {
            BufferedReader reader = new BufferedReader(new FileReader(orderPath.toFile()));
            return reader.lines()
                    .map(line -> {
                        String[] parts = line.split("\\|");
                        return new Order(
                                Integer.parseInt(parts[0]),
                                Integer.parseInt(parts[1]),
                                parts[2].charAt(0),
                                Float.parseFloat(parts[3]),
                                LocalDate.parse(parts[4]),
                                parts[5].toCharArray(),
                                parts[6].toCharArray(),
                                Integer.parseInt(parts[7]),
                                parts[8].toCharArray()
                        );
                    });
        } catch (IOException e) {
            throw new RuntimeException("Ошибка при чтении файла с информацией о заказах", e);
        }
    }
    public Database() {
        List<Customer> customerList = processInputFileCustomer().collect(Collectors.toList());
        List<LineItem> lineItemList = processInputFileLineItem().collect(Collectors.toList());
        List<Order> orderList = processInputFileOrders().collect(Collectors.toList());

        customers = customerList.stream()
                .collect(Collectors.toMap(Customer::getCustKey, Function.identity()));

        lineItems = lineItemList.stream()
                .collect(Collectors.toMap(LineItem::getOrderKey, Function.identity()));

        orders = orderList.stream()
                .collect(Collectors.toMap(Order::getOrderKey, Function.identity()));

        // Заполняем карту customerMarketSegments
        customers.forEach((key, value) -> customerMarketSegments.put(key, value.getMktsegment()));
    }


    public long getAverageQuantityPerMarketSegment(String marketSegment) {
        Map<Integer, List<LineItem>> orderLineItemMap = processInputFileLineItem()
                .collect(Collectors.groupingBy(LineItem::getOrderKey));

        Map<Integer, Long> orderTotalQuantityMap = orderLineItemMap.entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> entry.getValue().stream().mapToLong(LineItem::getQuantity).sum(),
                        (existing, replacement) -> {
                            System.err.println("Duplicate key: " + existing + " and " + replacement);
                            System.err.println("Duplicate entries: ");
                            System.err.println(orderLineItemMap.get(existing));
                            System.err.println(orderLineItemMap.get(replacement));
                            // Use your logic to handle duplicates; for now, using the existing value
                            return existing;
                        }
                ));

        long totalQuantity = orderTotalQuantityMap.values().stream().mapToLong(Long::longValue).sum();
        long orderCount = orderTotalQuantityMap.size();

        return totalQuantity / orderCount;
    }








    public void createCustomerJson(int custKey) {
        if (customers != null) {
            Customer customer = customers.get(custKey);
            if (customer != null) {
                String json = new Gson().toJson(customer);
                writeJsonToFile(json, "customer_" + custKey + ".json");
            }
        }
    }


    public void createOrderJson(int orderKey) {
        // Создаем Gson с пользовательским адаптером для LocalDate
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                .create();

        // Получаем заказ, клиента и товарную позицию
        Order order = orders.get(orderKey);
        Customer customer = customers.get(order.getCustKey());
        LineItem lineItem = lineItems.get(order.getOrderKey());

        // Создаем карту данных для заказа, клиента и товарной позиции
        Map<String, Object> data = new HashMap<>();
        data.put("orders", order);
        data.put("customer", customer);
        data.put("lineItem", lineItem);

        // Сериализуем карту в JSON-строку с использованием настроек Gson
        String json = gson.toJson(data);

        // Записываем JSON-строку в файл
        writeJsonToFile(json, "orders_" + orderKey + ".json");
    }

    // Создаем пользовательский адаптер для LocalDate
    class LocalDateAdapter extends TypeAdapter<LocalDate> {
        @Override
        public void write(JsonWriter out, LocalDate value) throws IOException {
            out.value(value.toString());
        }

        @Override
        public LocalDate read(JsonReader in) throws IOException {
            return LocalDate.parse(in.nextString());
        }
    }



    private void writeJsonToFile(String json, String fileName) {
        Path filePath = baseDataDirectory.resolve(fileName);
        try (FileWriter writer = new FileWriter(filePath.toFile())) {
            writer.write(json);
        } catch (IOException e) {
            throw new RuntimeException("Error writing JSON file", e);
        }
    }

    public static void main(String[] args) {
        // Создаем список LineItem
        List<LineItem> lineItems = Arrays.asList(
                new LineItem(1, 156, 4, 1, 17, 17954.55f, 0.04f, 0.02f, 'N', 'O', LocalDate.of(1996, 3, 13),
                        LocalDate.of(1996, 2, 12), LocalDate.of(1996, 3, 22), "DELIVER IN PERSON".toCharArray(),
                        "TRUCK".toCharArray(), "regular courts above the".toCharArray()),
                new LineItem(1, 68, 9, 2, 36, 34850.16f, 0.09f, 0.06f, 'N', 'O', LocalDate.of(1996, 4, 12),
                        LocalDate.of(1996, 2, 28), LocalDate.of(1996, 4, 20), "TAKE BACK RETURN".toCharArray(),
                        "MAIL".toCharArray(), "ly final dependencies: slyly bold".toCharArray())
                // Добавьте другие объекты LineItem по мере необходимости
        );

        // Создаем базу данных
        Database database = new Database(lineItems);
        long averageQuantity = database.getAverageQuantityPerMarketSegment("AUTOMOBILE");
        System.out.println("Average Quantity: " + averageQuantity);

        // Создание JSON для клиента с custKey = 1
        database.createCustomerJson(1);

        // Создание JSON для заказа с orderKey = 1
        database.createOrderJson(1);
    }
}



//
//import java.nio.file.Files;
//import java.nio.file.Path;
//import java.nio.file.Paths;
//import java.time.LocalDate;
//import java.util.Arrays;
//import java.util.stream.Stream;
//
//public class Database {
//    // Указываем путь к директории с данными
//    private static Path baseDataDirectory = Paths.get("data");
//
//    // Метод для установки новой директории с данными
//    public static void setBaseDataDirectory(Path baseDataDirectory) {
//        Database.baseDataDirectory = baseDataDirectory;
//    }
//
//
//
//    private static int extractCustomerId(String input) {
//        StringBuilder numericPart = new StringBuilder();
//
//        for (char c : input.toCharArray()) {
//            if (Character.isDigit(c)) {
//                numericPart.append(c);
//            }
//        }
//
//        if (numericPart.length() > 0) {
//            return Integer.parseInt(numericPart.toString());
//        } else {
//            throw new RuntimeException("Ошибка при извлечении числовой части из строки: " + input);
//        }
//    }
//
//
//
//
//
//    // Метод для обработки файла с информацией о клиентах
//    public static Stream<Customer> processInputFileCustomer() {
//        // Формируем путь к файлу
//        Path path = baseDataDirectory.resolve("D:\\JavaProgram\\StreamAPI\\DataBaseTask\\data\\customer.tbl");
//        try {
//            // Читаем строки из файла, разбираем их и создаем объекты Customer
//            return Files.lines(path)
//                    .map(line -> {
//                        try {
//                            String[] parts = line.split("\\|");
//                            // Извлекаем числовую часть из первого столбца (идентификатор клиента)
//                            int customerId = extractCustomerId(parts[0]);
//
//                            // Проверяем, что все остальные поля не пусты
//                            if (!parts[1].isEmpty() && !parts[2].isEmpty() && !parts[3].isEmpty() && !parts[4].isEmpty() && !parts[5].isEmpty() && !parts[6].isEmpty()) {
//                                return new Customer(
//                                        customerId,
//                                        parts[1].toCharArray(),
//                                        Integer.parseInt(parts[2]),
//                                        parts[3].toCharArray(),
//                                        Float.parseFloat(parts[4]),
//                                        parts[5],
//                                        parts[6].toCharArray()
//                                );
//                            } else {
//                                throw new RuntimeException("Ошибка при обработке строки (отсутствие значения): " + line);
//                            }
//                        } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
//                            // В случае ошибки выбрасываем исключение с дополнительной информацией
//                            throw new RuntimeException("Ошибка при обработке строки: " + line, e);
//                        }
//                    });
//        } catch (Exception e) {
//            // В случае ошибки выбрасываем исключение
//            throw new RuntimeException("Ошибка при чтении файла с информацией о клиентах", e);
//        }
//    }
//
//
//
//
//
//
//
//
//
//
//
//
//    // Метод для обработки файла с информацией о позициях заказов
//    public static Stream<LineItem> processInputFileLineItem() {
//        // Формируем путь к файлу
//        Path path = baseDataDirectory.resolve("D:\\JavaProgram\\StreamAPI\\DataBaseTask\\data\\lineitem.tbl");
//        try {
//            // Читаем строки из файла, разбираем их и создаем объекты LineItem
//            return Files.lines(path)
//                    .map(line -> {
//                        String[] parts = line.split("\\|");
//                        return new LineItem(
//                                Integer.parseInt(parts[0]),
//                                Integer.parseInt(parts[1]),
//                                Integer.parseInt(parts[2]),
//                                Integer.parseInt(parts[3]),
//                                Integer.parseInt(parts[4]),
//                                Float.parseFloat(parts[5]),
//                                Float.parseFloat(parts[6]),
//                                Float.parseFloat(parts[7]),
//                                parts[8].charAt(0),
//                                parts[9].charAt(0),
//                                LocalDate.parse(parts[10]),
//                                LocalDate.parse(parts[11]),
//                                LocalDate.parse(parts[12]),
//                                parts[13].toCharArray(),
//                                parts[14].toCharArray(),
//                                parts[15].toCharArray()
//                        );
//                    });
//        } catch (Exception e) {
//            // В случае ошибки выбрасываем исключение
//            throw new RuntimeException("Ошибка при чтении файла с информацией о позициях заказов", e);
//        }
//    }
//
//    // Метод для обработки файла с информацией о заказах
//    public static Stream<Order> processInputFileOrders() {
//        // Формируем путь к файлу
//        Path path = baseDataDirectory.resolve("D:\\JavaProgram\\StreamAPI\\DataBaseTask\\data\\order.tbl");
//        try {
//            // Читаем строки из файла, разбираем их и создаем объекты Order
//            return Files.lines(path)
//                    .map(line -> {
//                        String[] parts = line.split("\\|");
//                        return new Order(
//                                Integer.parseInt(parts[0]),
//                                Integer.parseInt(parts[1]),
//                                parts[2].charAt(0),
//                                Float.parseFloat(parts[3]),
//                                LocalDate.parse(parts[4]),
//                                parts[5].toCharArray(),
//                                parts[6].toCharArray(),
//                                Integer.parseInt(parts[7]),
//                                parts[8].toCharArray()
//                        );
//                    });
//        } catch (Exception e) {
//            // В случае ошибки выбрасываем исключение
//            throw new RuntimeException("Ошибка при чтении файла с информацией о заказах", e);
//        }
//    }
//
//    // Основной метод для тестирования реализации
//    public static void main(String[] args) {
//        // Пример использования: выводим информацию о клиентах, позициях заказов и заказах
//        processInputFileCustomer().forEach(Customer::output);
//        processInputFileLineItem().forEach(LineItem::output);
//        processInputFileOrders().forEach(Order::output);
//    }
//}
