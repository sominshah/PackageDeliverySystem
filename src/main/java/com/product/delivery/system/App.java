package com.product.delivery.system;
import com.product.delivery.system.models.Product;
import java.math.BigDecimal;
import java.util.Scanner;

public class App
{
    public static void firstChallenge()
    {
        Scanner scanner = new Scanner(System.in);
        ProductDeliverySystem packageDeliverySystem=null;
        try
        {
            String firstLine = null;
            while (scanner.hasNextLine()) {
                firstLine = scanner.nextLine();
                if (firstLine != null) {
                    firstLine = firstLine.trim();
                    if (!firstLine.isEmpty()) break;
                }
            }
            if (firstLine == null || firstLine.isEmpty()) return;
            String[] strArr = firstLine.split("\\s+");
            if (strArr.length != 2) {
                System.err.println("Invalid first line.");
                System.out.println("Expected Input: <base_delivery_cost> <no_of_packages>");
                return;
            }

            int numberOfPackages;
            int baseDeliveryCost;
            try {
                baseDeliveryCost = Integer.parseInt(strArr[0]);
                numberOfPackages = Integer.parseInt(strArr[1]);
            } catch (Exception e) {
                System.err.println("Invalid first line.");
                System.out.println("Expected Input: <base_delivery_cost> <no_of_packages>");
                return;
            }

            packageDeliverySystem = ProductDeliverySystem.getInstance(new BigDecimal(baseDeliveryCost));
            int readCount = 0;
            while (readCount < numberOfPackages && scanner.hasNextLine()) {
                String packageInfo = scanner.nextLine();
                if (packageInfo == null) break;
                packageInfo = packageInfo.trim();
                if (packageInfo.isEmpty()) continue;
                //System.out.println("Package " + packageInfo);
                String[] packageInfoArray = packageInfo.split("\\s+");

                //System.out.println("Array Size: " + packageInfoArray.length + " Array: " + Arrays.toString(packageInfoArray));
                if (packageInfoArray.length < 3) {
                    continue;
                }


                try {
                    Product product = new Product();
                    product.setPackageLabel(packageInfoArray[0].trim());
                    product.setWeight(new BigDecimal(packageInfoArray[1].trim()));
                    product.setDistance(new BigDecimal(packageInfoArray[2].trim()));
                    String couponCode = (packageInfoArray.length >= 4) ? packageInfoArray[3].trim() : "NA";
                    packageDeliverySystem.addProduct(couponCode, product);
                    readCount++;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (readCount < numberOfPackages)
            {
                System.err.println("Warning: expected " + numberOfPackages + " packages but read " + readCount);
            return;
            }

            packageDeliverySystem.printDeliveryCost();
        }finally {
            scanner.close();
        }


    }




    public static void secondChallenge()
    {
        Scanner scanner = new Scanner(System.in);
        ProductDeliverySystem packageDeliverySystem=null;
        try {
            String firstLine = null;
            while (scanner.hasNextLine()) {
                firstLine = scanner.nextLine();
                if (firstLine != null) {
                    firstLine = firstLine.trim();
                    if (!firstLine.isEmpty()) break;
                }
            }
            if (firstLine == null || firstLine.isEmpty()) return;
            String[] strArr = firstLine.split("\\s+");
            if (strArr.length != 2) {
                System.err.println("Invalid first line.");
                System.out.println("Expected Input: <base_delivery_cost> <no_of_packages>");
                return;
            }

            int numberOfPackages;
            int baseDeliveryCost;
            try {
                baseDeliveryCost = Integer.parseInt(strArr[0]);
                numberOfPackages = Integer.parseInt(strArr[1]);
            } catch (Exception e) {
                System.err.println("Invalid first line.");
                System.out.println("Expected Input: <base_delivery_cost> <no_of_packages>");
                return;
            }

            packageDeliverySystem = ProductDeliverySystem.getInstance(new BigDecimal(baseDeliveryCost));
            int readCount = 0;
            while (readCount < numberOfPackages && scanner.hasNextLine())
            {
                String packageInfo = scanner.nextLine();
                if (packageInfo == null) break;
                packageInfo = packageInfo.trim();
                if (packageInfo.isEmpty()) continue;
                String[] packageInfoArray = packageInfo.split("\\s+");
                if (packageInfoArray.length < 3) continue;
                try {
                    Product product = new Product();
                    product.setPackageLabel(packageInfoArray[0].trim());
                    product.setWeight(new BigDecimal(packageInfoArray[1].trim()));
                    product.setDistance(new BigDecimal(packageInfoArray[2].trim()));
                    String couponCode = (packageInfoArray.length >= 4) ? packageInfoArray[3].trim() : "NA";
                    packageDeliverySystem.addProduct(couponCode, product);
                    readCount++;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (readCount < numberOfPackages)
            {
                System.err.println("Warning: expected " + numberOfPackages + " packages but read " + readCount);
                return;
            }


            if(!scanner.hasNextLine())
            {
                System.err.println("Vehicle Information need to  solve the second challenge, last line of input expected: <no_of_vehicles> <max_speed> <max_carriable_weight>");
                return;
            }

            String vehicleInfo = scanner.nextLine();
            if (vehicleInfo == null || vehicleInfo.isEmpty())
            {
                System.err.println("Vehicle Information need to  solve the second challenge, last line of input expected: <no_of_vehicles> <max_speed> <max_carriable_weight>");
                return;
            }
            vehicleInfo = vehicleInfo.trim();
            String[] packageInfoArray = vehicleInfo.split("\\s+");
            if (packageInfoArray.length < 3)
            {
                    System.err.println("Vehicle Information needed to  solve the second challenge, last line of input expected: <no_of_vehicles> <max_speed> <max_carriable_weight>");
                    return;
            }

            int numberOfVehicles= Integer.parseInt(packageInfoArray[0].trim());
            int maxSpeed = Integer.parseInt(packageInfoArray[1].trim());
            int maxCarriableWeight = Integer.parseInt(packageInfoArray[2].trim());

            ProductDeliverySystem.addVehicles(numberOfVehicles,maxSpeed, maxCarriableWeight);
            packageDeliverySystem.printDeliveryCostWithEstimatedTime();
        }finally {
            scanner.close();
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
        try {
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





//    public static void printSolutionForFirstProblem()
//    {
//        try
//        {
//            System.out.println("Solution For First Problem");
//
//            ProductDeliverySystem packageDeliverySystem = ProductDeliverySystem.getInstance(new BigDecimal(100));
//            Product product = new Product();
//            product.setPackageLabel("PKG1");
//            product.setWeight(new BigDecimal(5));
//            product.setDistance(new BigDecimal(5));
//            packageDeliverySystem.addProduct("OFFR001",product);
//
//            product = new Product();
//            product.setPackageLabel("PKG2");
//            product.setWeight(new BigDecimal(15));
//            product.setDistance(new BigDecimal(5));
//            packageDeliverySystem.addProduct("OFFR002",product);
//
//            product = new Product();
//            product.setPackageLabel("PKG3");
//            product.setWeight(new BigDecimal(10));
//            product.setDistance(new BigDecimal(100));
//            packageDeliverySystem.addProduct("OFFR003",product);
//
//            packageDeliverySystem.printDeliveryCost();
//        }catch(Exception e)
//        {
//            e.printStackTrace();
//        }
//
//    }

//
//    public static void printSolutionForSecondProblem()
//    {
//        try
//        {
//            System.out.println("\n\n \t\t-- Solution For Second Problem \t\t\n");
//            ProductDeliverySystem packageDeliverySystem = ProductDeliverySystem.getInstance(new BigDecimal(100));
//            Product product = new Product();
//            product.setPackageLabel("PKG1");
//            product.setWeight(new BigDecimal(50));
//            product.setDistance(new BigDecimal(30));
//            packageDeliverySystem.addProduct("OFFR001",product);
//
//            product = new Product();
//            product.setPackageLabel("PKG2");
//            product.setWeight(new BigDecimal(75));
//            product.setDistance(new BigDecimal(125));
//            packageDeliverySystem.addProduct("OFFR0008",product);
//
//            product = new Product();
//            product.setPackageLabel("PKG3");
//            product.setWeight(new BigDecimal(175));
//            product.setDistance(new BigDecimal(100));
//            packageDeliverySystem.addProduct("OFFR003",product);
//
//            product = new Product();
//            product.setPackageLabel("PKG4");
//            product.setWeight(new BigDecimal(110));
//            product.setDistance(new BigDecimal(60));
//            packageDeliverySystem.addProduct("OFFR002",product);
//
//            product = new Product();
//            product.setPackageLabel("PKG5");
//            product.setWeight(new BigDecimal(155));
//            product.setDistance(new BigDecimal(95));
//            packageDeliverySystem.addProduct("NA",product);
//
//            ProductDeliverySystem.addVehicles(2,70, 200);
//
//            packageDeliverySystem.printDeliveryCostWithEstimatedTime();
//        }catch(Exception e)
//        {
//            e.printStackTrace();
//        }
//
//
//    }
}
