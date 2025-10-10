package com.product.delivery.system;
import com.product.delivery.system.models.Product;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class App
{


    public static int [] readHeader(Scanner scanner)
    {
        while (scanner.hasNextLine())
        {
            String firstLine = scanner.nextLine().trim();
            if(!firstLine.isEmpty())
            {
                String [] firstLineSplit = firstLine.split("\\s+");
                    if (firstLineSplit.length!= 2) break;
                    try
                    {
                        return new int[]{Integer.parseInt(firstLineSplit[0]), Integer.parseInt(firstLineSplit[1])};
                    } catch (NumberFormatException ignored)
                    {

                    }

            }
        }
        System.err.println("Invalid input. Expected: <base_delivery_cost> <no_of_packages>");
        return null;
    }

    public static List<Product> readProducts(Scanner scanner,int numberOfPackages, ProductDeliverySystem productDeliverySystem)
    {
        List<Product> products = new ArrayList<>();
        int counter = 0;
        while (counter < numberOfPackages && scanner.hasNextLine())
        {
            String line = scanner.nextLine().trim();
            if(line.isEmpty())continue;
            String[] lineSplit = line.split("\\s+");
            if(lineSplit.length < 3)continue;
            try
            {
                Product product = new Product();
                product.setPackageLabel(lineSplit[0]);
                product.setWeight(new BigDecimal(lineSplit[1]));
                product.setDistance(new BigDecimal(lineSplit[2]));
                String couponCode = (lineSplit.length >= 4) ? lineSplit[3] : "NA";
                productDeliverySystem.addProduct(couponCode, product);
                products.add(product);
                counter++;
            }catch (Exception e)
            {
                System.err.println("Invalid product input: " + line);
            }

        }
        if (counter < numberOfPackages)
        {
            System.err.println("Warning: expected " + numberOfPackages + " packages but read " + counter);
        }
        return products;
    }

    public static boolean readVehicles(Scanner scanner)
    {
        if (!scanner.hasNextLine())
        {
            System.err.println("Vehicle information missing: <no_of_vehicles> <max_speed> <max_carriable_weight>");
            return false;
        }
        String vehicleLine = scanner.nextLine().trim();
        String[] parts = vehicleLine.split("\\s+");
        if (parts.length < 3)
        {
            System.err.println("Vehicle information incomplete: <no_of_vehicles> <max_speed> <max_carriable_weight>");
            return false;
        }

        try
        {
            int numberOfVehicles = Integer.parseInt(parts[0]);
            int maxSpeed = Integer.parseInt(parts[1]);
            int maxCarriableWeight = Integer.parseInt(parts[2]);
            ProductDeliverySystem.addVehicles(numberOfVehicles, maxSpeed, maxCarriableWeight);
            return true;
        } catch (NumberFormatException e)
        {
            System.err.println("Invalid vehicle numbers");
            return false;
        }
    }
    public static void firstChallenge()
    {
        try (Scanner scanner = new Scanner(System.in)) {
            int[] header = readHeader(scanner);
            if (header == null) return;

            ProductDeliverySystem system = ProductDeliverySystem.getInstance(new BigDecimal(header[0]));
            readProducts(scanner, header[1], system);
            system.printDeliveryCost();
        }
    }
    public static void secondChallenge()
    {
        try (Scanner scanner = new Scanner(System.in))
        {
            int[] header = readHeader(scanner);
            if (header == null) return;
            ProductDeliverySystem system = ProductDeliverySystem.getInstance(new BigDecimal(header[0]));
            readProducts(scanner, header[1], system);
            if (!readVehicles(scanner)) return;
            system.printDeliveryCostWithEstimatedTime();
        }
    }


    public static void main(String[] args)
    {
    System.out.println("\n\n \t\t-- Welcome to Package Delivery System-- \t\t\n");
    java.util.Scanner menuScanner = new java.util.Scanner(System.in);
    while (true)
    {
        System.out.println("\nPlease choose an option:");
        System.out.println("  1) Run first challenge (costs only)");
        System.out.println("  2) Run second challenge (costs + estimated times)");
        System.out.println("  3) Exit");
        System.out.print("Enter choice [1-3]: ");
        String line = null;
        try
        {
            if (!menuScanner.hasNextLine()) break;
            line = menuScanner.nextLine().trim();
            if (line.isEmpty()) {
                System.out.println("No input. Please enter 1, 2 or 3.");
                continue;
            }
            int ch;
            try {
                ch = Integer.parseInt(line);
            } catch (NumberFormatException nfe) {
                System.out.println("Invalid input. Please enter a number (1-3).");
                continue;
            }
            if (ch == 1) {
                System.out.println("Enter input for challenge Number one: ");
                firstChallenge();
                break;
            } else if (ch == 2) {
                System.out.println("Enter input for challenge Number two: ");
                secondChallenge();
                break;
            } else if (ch == 3) {
                System.out.println("Good-Bye!");
                break;
            } else {
                System.out.println("Invalid input. Please try again.");
            }
        } catch (Exception ex) {
            System.err.println("An error occurred: " + ex.getMessage());
            ex.printStackTrace(System.err);
            break;
        }
    }
    try {
        menuScanner.close();
    } catch (Exception ignored) { }
}

}
