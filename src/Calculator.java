import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import java.math.BigDecimal;
import java.util.Scanner;

public class Calculator {
    public static BigDecimal calculateCurrency(double amount, String currencySymbol) {
        NodeList nodeListOfCurrencies = XmlParser.createNodeListOfCurrencies();
        double rate = 0;

        for (int i = 0; i < nodeListOfCurrencies.getLength(); i++) {
            Node currency = nodeListOfCurrencies.item(i);
            if (currency.getNodeType() == Node.ELEMENT_NODE) {
                Element currencyElement = (Element) currency;

                if (currencyElement.getAttribute("currency").equals(currencySymbol)) {
                    rate = Double.parseDouble(currencyElement.getAttribute("rate"));
                }
            }
        }
        return new BigDecimal(rate * amount);
    }

    public static double checkIfValidAmount() {

        Scanner scanner = new Scanner(System.in);
        double amount;
        System.out.println("Enter the amount of money you want to exchange: ");

        do {
            while (!scanner.hasNextDouble()) {
                System.out.println("That's not a number! Please enter correct number: ");
                scanner.next();
            }
            amount = scanner.nextDouble();

            if (amount < 0) {
                System.out.println("You can't exchange negative amount of money. Please enter positive amount: ");
            }
        } while (amount < 0);

        return amount;
    }

    public static String checkIfValidSymbol() {

        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the currency symbol you want to exchange your money to: ");
        String symbol = scanner.nextLine();;
        boolean loopController = true;
        NodeList nodeListOfCurrencies = XmlParser.createNodeListOfCurrencies();

        do {
            for (int i = 0; i < nodeListOfCurrencies.getLength(); i++) {
                Node currency = nodeListOfCurrencies.item(i);
                if (currency.getNodeType() == Node.ELEMENT_NODE) {
                    Element currencyElement = (Element) currency;
                    if (currencyElement.getAttribute("currency").equals(symbol)) {
                        loopController = false;
                    }
                }
            }

            if(loopController) {
                System.out.println("This is not a valid symbol, please enter correct symbol:");
                symbol = scanner.nextLine();
            }
        }while(loopController);

        return symbol;
    }

    public static void main(String[] args) {

            double amount = checkIfValidAmount();
            String currencySymbol = checkIfValidSymbol();

            System.out.println("You would get: " + calculateCurrency(amount, currencySymbol) + " " + currencySymbol);


    }
}
