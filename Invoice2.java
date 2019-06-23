import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * An Invoice class stores all of the data needed for a single Invoice object.
 * 
 * This class allows a Sales System user to interact with this class in the
 * following ways:
 * 
 * Creating a Invoice object.
 * 
 * Adding items to the invoice from the inventory class.
 * 
 * Calculating total cost of the invoice.
 * 
 * Adding delivery and insurance costs to the invoice.
 * 
 * Displaying final invoice object.
 */

public class Invoice2 {

	// Declaring all instance variables necessary to complete an invoice.
	private String company, name, address, country, phoneNumber;
	private String formatDateTime, deliveryInformation;
	private String[] purchaseDescriptions, purchaseIDs;
	private double[] purchasePrices;
	private int[] purchaseQuantities;
	private int currentPurchases;
	private double itemTotal, insurance, delivery;

	// Constructor of Invoice class. Here all instance variables are initialized.
	// To create an Invoice object, customer details must be passed.
	public Invoice2(String name, String address, String country, String phoneNumber) {

		// All instance variable being initialized.
		this.name = name;
		this.address = address;
		this.country = country;
		this.phoneNumber = phoneNumber;
		this.company = "Toy Universe";
		this.deliveryInformation = "";
		this.purchaseIDs = new String[6];
		this.purchasePrices = new double[6];
		this.purchaseQuantities = new int[6];
		this.purchaseDescriptions = new String[6];
		this.currentPurchases = 0;
		this.itemTotal = 0;
		this.insurance = 0;
		this.delivery = 0;

		// Reading current time off local machine.
		LocalDateTime now = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
		this.formatDateTime = now.format(formatter);
	}

	// Accessor too let SalesSystem see company name.
	public String getCompany() {
		return this.company;
	}

	// Stores current purchases of the current invoice object.
	public void purchaseRecord(String productID, int quantity, String description,
			double price) {

		this.purchaseIDs[this.currentPurchases] = productID;
		this.purchaseDescriptions[this.currentPurchases] = description;
		this.purchasePrices[this.currentPurchases] = price;
		this.purchaseQuantities[this.currentPurchases] = quantity;

		this.currentPurchases++;
	}

	// Formats All purchases too readable strings, aligned to fit in a table.
	private String[] formatItemStrings() {

		// Finds the length of the largest description string
		String[] formattedPurchases = new String[this.currentPurchases + 2];

		int maxDescriptionLength = 0;
		for (int i = 0; i < this.currentPurchases; i++) {

			int currentDescriptionLength = this.purchaseDescriptions[i].length();

			if (currentDescriptionLength > maxDescriptionLength)
				maxDescriptionLength = currentDescriptionLength;
		}

		// Format the header of the table with a uniform description column width.
		String titleDescription = extendStringWhiteSpace("Description",
				maxDescriptionLength);
		formattedPurchases[0] = String.format("%s %7s   %s   %s   %s", "Item No", "ID",
				titleDescription, "Price", "Quantity");

		char[] lineSeparator = new char[formattedPurchases[0].length()];

		for (int i = 0; i < lineSeparator.length; i++)
			lineSeparator[i] = '-';

		formattedPurchases[1] = new String(lineSeparator);

		// Format each purchase with a uniform description column width.
		for (int index = 0; index < this.currentPurchases; index++) {
			String productID = this.purchaseIDs[index];
			String description = this.purchaseDescriptions[index];
			double price = this.purchasePrices[index];
			int quantity = this.purchaseQuantities[index];

			description = extendStringWhiteSpace(description, maxDescriptionLength);

			formattedPurchases[index + 2] = String.format(
					"Item No. %s | %s | %s | %.2f | %s \n", (index + 1), productID,
					description, price, quantity);
		}

		return formattedPurchases;
	}

	public String extendStringWhiteSpace(String description, int totalDescriptionLength) {

		int currentLength = description.length();
		int spaceCount = totalDescriptionLength - currentLength;

		if (spaceCount <= 0)
			return description;

		char[] whiteSpaceChars = new char[spaceCount];

		String whiteSpace = new String(whiteSpaceChars);

		return description + whiteSpace;

	}

	// Calculates total cost of items added to invoice object.
	public void itemTotalCost(int quantity, double price) {
		this.itemTotal += price * quantity;
	}

	// If called adds delivery cost to invoice, cost changes depending on delivery
	// location.
	public void addDelivery(String city, String country) {

		// Adds delivery cost for Australian customer.
		if (country.equalsIgnoreCase("Australia") || country.equalsIgnoreCase("Aus")) {
			this.delivery = 9.95;
			this.deliveryInformation = city + " - " + country;
		}
		// Adds delivery cost for New Zealand customer.
		else if (country.equalsIgnoreCase("New Zealand")
				|| country.equalsIgnoreCase("NZ")) {
			this.delivery = 20.00;
			this.deliveryInformation = city + " - " + country;
		}
		// Adds delivery cost for American customer.
		else if (country.equalsIgnoreCase("USA") || country.equalsIgnoreCase("America")
				|| country.equalsIgnoreCase("United States of America")) {
			this.delivery = 37.96;
			this.deliveryInformation = city + " - " + country;

			// If customer from other country, this statement appears.
		} else
			System.out.println(
					"Sorry, we do not deliver to that country, the customer must pick up");
	}

	// If called adds interest to invoice.
	public void addInsurance() {
		this.insurance = 9.95;
	}

	// This method creates and displays the invoice object.
	// Displays invoice at any time during the order, when called.
	public void displayInvoice() {
		System.out.println("\n");
		System.out.printf("%18s %s \n  %20s \n ", "Invoice ID:", "743285", this.company);
		System.out.println("\nDate and Time: " + this.formatDateTime);
		System.out.println("Customer Name: " + this.name);
		System.out.println("Customer Address: " + this.address);
		System.out.println("Customer Country: " + this.country);
		System.out.println("Customer Phone Number: " + this.phoneNumber + "\n");

		String[] formattedProducts = formatItemStrings();
		for (int i = 0; i < formattedProducts.length; i++)
			System.out.println(formattedProducts[i]);
		
		System.out.println();
		System.out.printf("%s %1s%.2f \n", "Total Cost of Items:","$", this.itemTotal);
		System.out.printf("%s %7s%.2f \n", "Delivery Cost:","$", this.delivery);
		System.out.printf("%s %6s%.2f \n", "Insurance Cost:","$", this.insurance);
		System.out.printf("%s %4s%.2f \n", "Grand Total Cost:", "$",
				(this.itemTotal + this.delivery + this.insurance));
		if (this.deliveryInformation != null && !this.deliveryInformation.isEmpty())
			System.out.println("\nDelivery Location: " + this.deliveryInformation + "\n");
		else
			System.out.println("\nPick up in-store \n");
		System.out.printf("%20s \n\n\n", "Thankyou for shopping at Toy Universe!");

	}
}
// There are no bugs in this class.