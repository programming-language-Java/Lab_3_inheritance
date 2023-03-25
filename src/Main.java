import java.util.ArrayList;
import java.util.Scanner;

public class Main extends Indent {
    public static ArrayList<ReceiptForElectricity> receiptsForElectricity = new ArrayList<>();
    public static ReceiptForElectricity receiptForElectricity;
    public static ArrayList<ReceiptHotWater> receiptsHotWater = new ArrayList<>();
    public static ReceiptHotWater receiptHotWater;

    static Scanner sc = new Scanner(System.in);
    static String date;
    static double sum;
    static String continueEntering;
    private static ReceiptHotWater maxReceiptHotWater;
    private static boolean isFound;

    public static void main(String[] args) {
        requestReceiptsForElectricity();
        requestReceiptHotWater();
        showReceiptsForElectricity();
        showReceiptsHotWater();
        showReceiptsForElectricityUpSum();
        showReceiptsForHotWaterWithNonMaxCosts();
    }

    public static void requestReceiptsForElectricity() {
        int electricitySpent;
        System.out.println("Введите квитанции за электроэнергию:");
        do {
            System.out.print("Дата: ");
            date = sc.next();
            System.out.print("Сумма: ");
            sum = sc.nextDouble();
            System.out.print("Потрачено электроэнергии: ");
            electricitySpent = sc.nextInt();
            receiptForElectricity = new ReceiptForElectricity(date, sum, electricitySpent);
            receiptsForElectricity.add(receiptForElectricity);
            System.out.print("Продолжить квитанции за электроэнергию? (y/n): ");
            continueEntering = sc.next();
            showEmptyParagraph();
        } while (continueEntering.equals("y"));
    }

    public static void requestReceiptHotWater() {
        int waterSpent;
        System.out.println("Введите квитанции за горячее водоснабжение:");
        do {
            System.out.print("Дата: ");
            date = sc.next();
            System.out.print("Сумма: ");
            sum = sc.nextDouble();
            System.out.print("Потрачено воды: ");
            waterSpent = sc.nextInt();
            receiptHotWater = new ReceiptHotWater(date, sum, waterSpent);
            receiptsHotWater.add(receiptHotWater);
            System.out.print("Продолжить квитанции за горячее водоснабжение? (y/n): ");
            continueEntering = sc.next();
            showEmptyParagraph();
        } while (continueEntering.equals("y"));
    }

    public static void showReceiptsForElectricity() {
        System.out.println("Квитанции за электроэнергию");
        for (ReceiptForElectricity receiptForElectricity : receiptsForElectricity)
            System.out.println(receiptForElectricity.toString());
        showEmptyParagraph();
    }

    public static void showReceiptsForElectricityUpSum() {
        System.out.print("Перечень квитанций за электроэнергию, сумма (руб.) которых не более: ");
        double upSum = sc.nextDouble();
        isFound = false;
        for (ReceiptForElectricity receiptForElectricity : receiptsForElectricity)
            showReceiptForElectricityUpSum(receiptForElectricity, upSum);
        errorMessageNotFound();
        showEmptyParagraph();
    }

    public static void showReceiptForElectricityUpSum(ReceiptForElectricity receiptForElectricity, double upSum) {
        if (receiptForElectricity.getSum() <= upSum) {
            System.out.println(receiptForElectricity.toString());
            isFound = true;
        }
    }

    public static void errorMessageNotFound() {
        if (!isFound)
            System.out.println("Объекты не найдены!");
    }

    public static void showReceiptsHotWater() {
        System.out.println("Квитанции за электроэнергию");
        for (ReceiptHotWater receiptHotWater : receiptsHotWater)
            System.out.println(receiptHotWater.toString());
        showEmptyParagraph();
    }

    public static void showReceiptsForHotWaterWithNonMaxCosts() {
        System.out.println("Перечень квитанций за горячее водоснабжение,\n" +
                "потраченное количество воды согласно которым не является максимальным\n" +
                "среди всех квитанций за горячее водоснабжение");
        maxReceiptHotWater = receiptsHotWater.get(0);
        isFound = false;
        for (ReceiptHotWater receiptHotWater : receiptsHotWater)
            showReceiptForHotWaterWithNonMaxCosts(receiptHotWater);
        errorMessageNotFound();
        showEmptyParagraph();
    }

    public static void showReceiptForHotWaterWithNonMaxCosts(ReceiptHotWater receiptHotWater) {
        if (receiptHotWater.getWaterSpent() < maxReceiptHotWater.getWaterSpent()) {
            System.out.println(receiptHotWater.toString());
            isFound = true;
        } else if (receiptHotWater.getWaterSpent() != maxReceiptHotWater.getWaterSpent()) {
            System.out.println(maxReceiptHotWater.toString());
            maxReceiptHotWater = receiptHotWater;
            isFound = true;
        }
    }
}

class Indent {
    public static void showEmptyParagraph() {
        System.out.println();
    }
}

abstract class Receipt {
    private final String date;
    private final double sum;

    Receipt(String date, double sum) {
        this.date = date;
        this.sum = sum;
    }

    public String getDate() {
        return date;
    }

    public double getSum() {
        return sum;
    }

}

class ReceiptForElectricity extends Receipt {
    private final int electricitySpent;

    ReceiptForElectricity(String date, double sum, int electricitySpent) {
        super(date, sum);
        this.electricitySpent = electricitySpent;
    }

    public int getElectricitySpent() {
        return electricitySpent;
    }

    @Override
    public String toString() {
        return "Дата " + getDate() +
                "\nСумма " + getSum() +
                "\nПотрачено электроэнергии " + electricitySpent + " кВт ч";
    }
}

class ReceiptHotWater extends Receipt {
    private final int waterSpent;

    ReceiptHotWater(String date, double sum, int waterSpent) {
        super(date, sum);
        this.waterSpent = waterSpent;
    }

    public int getWaterSpent() {
        return waterSpent;
    }

    @Override
    public String toString() {
        return "Дата " + getDate() +
                "\nСумма " + getSum() +
                "\nПотрачено воды " + waterSpent + " куб. м";
    }
}