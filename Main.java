// req min java 11
// open cmd/terminal
// run "javac Main.java"
// run "java Main"

import java.math.BigDecimal;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) {
        class Product {
            private Integer no;
            private String name;
            private BigDecimal price;
            private Long quantity;

            public Product(Integer no, String name, BigDecimal price, Long quantity) {
                this.no = no;
                this.name = name;
                this.price = price;
                this.quantity = quantity;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public BigDecimal getPrice() {
                return price;
            }

            public void setPrice(BigDecimal price) {
                this.price = price;
            }

            public Long getQuantity() {
                return quantity;
            }

            public void setQuantity(Long quantity) {
                this.quantity = quantity;
            }

            public Integer getNo() {
                return no;
            }

            public void setNo(Integer no) {
                this.no = no;
            }

            @Override
            public String toString() {
                return "Product{" +
                        "no=" + no +
                        ", name='" + name + '\'' +
                        ", price=" + price +
                        ", quantity=" + quantity +
                        '}';
            }
        }

        List<BigDecimal> MONEY = List.of(
                BigDecimal.valueOf(2000),
                BigDecimal.valueOf(5000),
                BigDecimal.valueOf(10000),
                BigDecimal.valueOf(20000),
                BigDecimal.valueOf(50000)
        );

        List<Product> products = List.of(
                new Product(1, "Biskuit", BigDecimal.valueOf(6000), 100L),
                new Product(2, "Chips", BigDecimal.valueOf(8000), 100L),
                new Product(3, "Oreo", BigDecimal.valueOf(10000), 100L),
                new Product(4, "Tango", BigDecimal.valueOf(12000), 100L),
                new Product(5, "Cokelat", BigDecimal.valueOf(15000), 100L)
        );

        Boolean isOrder = true;

        BigDecimal incomes = BigDecimal.ZERO;

        Scanner keyboard = new Scanner(System.in);

        while (isOrder) {
            System.out.println("============WELCOME TO VENDING MACHINE============");

            for (Product product :
                    products) {
                System.out.printf("%s. %s %s (%s)%n", product.getNo(), product.getName(), product.getPrice(), product.getQuantity());
            }

            System.out.print("Enter there is no product you want to buy:");

            Integer productNumber = handleInput(keyboard);

            if (productNumber == null) return;

            Integer finalProductNumber = productNumber;

            List<Product> products1 = products
                    .stream()
                    .filter(product -> product.getNo().equals(finalProductNumber))
                    .collect(Collectors.toList());

            if (products1.isEmpty()) {
                System.err.println("Incorrect data entered");
                return;
            }

            if (products1.get(0).getQuantity() <= 0) {
                System.err.println("Stock not available");
                return;
            }

            System.out.print("Enter your money (2000, 5000, 10000, 20000, 50000):");

            Integer payment = handleInput(keyboard);

            if (payment == null) return;

            List<BigDecimal> pay = MONEY
                    .stream()
                    .filter(bigDecimal -> bigDecimal.equals(BigDecimal.valueOf(payment)))
                    .collect(Collectors.toList());

            if (pay.isEmpty()) {
                System.err.println("Incorrect data entered");
                return;
            }

            if (BigDecimal.valueOf(payment).compareTo(products1.get(0).getPrice()) < 0) {
                System.err.println("Your money is not enough");
                System.out.printf("Your money back: %s%n", payment);
                return;
            }

            products = products
                    .stream()
                    .map(product -> {
                        if (product.getNo().equals(productNumber)) {
                            product.setQuantity(product.getQuantity() - 1);
                        }
                        return product;
                    })
                    .collect(Collectors.toList());

            incomes = incomes.add(products1.get(0).getPrice());

            System.out.println("Payment successfully!");
            System.out.printf("Your money back: %s%n", BigDecimal.valueOf(payment).subtract(products1.get(0).getPrice()));
            System.out.printf("Enjoy your %s!%n", products1.get(0).getName());
            System.out.print("Order again?(Y/N):");
            String repeatOrder = keyboard.next();

            isOrder = repeatOrder != null && repeatOrder.equalsIgnoreCase("Y");
        }

        System.out.println("============CURRENT STOCK============");
        for (Product product :
                products) {
            System.out.printf("%s. %s %s (%s)%n", product.getNo(), product.getName(), product.getPrice(), product.getQuantity());
        }

        System.out.println("============INCOMES============");
        System.out.println(incomes);
    }

    private static Integer handleInput(Scanner keyboard) {
        Integer value;
        try {
            value = keyboard.nextInt();
        } catch (Exception ex) {
            System.err.println("Incorrect data entered");
            return null;
        }
        return value;
    }
}
