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

public class Invoice {

	// Declaring all instance variables necessary to complete an invoice.
	private String company, name, address, country, phoneNumber;
	private String formatDateTime, deliveryInformation;
	private String[] purchases;
	private int currentPurchases;
	private double itemTotal, insurance, delivery;

	// Constructor of Invoice class. Here all instance variables are initialized.
	// To create an Invoice object, customer details must be passed.
	public Invoice(String name, String address, String country, String phoneNumber) {

		// All instance variable being initialized.
		this.name = name;
		this.address = address;
		this.country = country;
		this.phoneNumber = phoneNumber;
		this.company = "Toy Universe";
		this.deliveryInformation = "";
		this.purchases = new String[6];
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
		// Current purchase added to String Array.
		this.purchases[this.currentPurchases] = String.format("%s | %s | %.2f | %s \n",
				productID, description, price, quantity);
		this.currentPurchases++;
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
					"Sorry, we do not deliver to that country, the customer must pick up \n");
	}

	// If called adds interest to invoice.
	public void addInsurance() {
		this.insurance = 9.95;
	}

	// This method creates and displays the invoice object.
	// Displays invoice at any time during the order, when called.
	//  Struggled to create a classic table inline. Trying to complete for final submission.
	public void displayInvoice() {
		System.out.println("\n");
		System.out.printf("%20s %s \n  %20s \n ", "Invoice ID:", "743285", this.company);
		System.out.println("\nDate and Time: " + this.formatDateTime);
		System.out.println("Customer Name: " + this.name);
		System.out.println("Customer Address: " + this.address);
		System.out.println("Customer Country: " + this.country);
		System.out.println("Customer Phone Number: " + this.phoneNumber + "\n");
		System.out.printf("%s %7s %13s %23s %10s \n", "Item No", "ID", "Description",
				"Price", "Quantity");
		System.out.println(
				"----------------------------------------------------------------");
		for (int i = 0; i < this.currentPurchases; i++)
			System.out.println("Item no: " + (i + 1) + " | " + this.purchases[i]);
		System.out.println();
		System.out.printf("%s %.2f \n", "Total Cost of Items: $", this.itemTotal);
		System.out.printf("%s %7s %.2f \n", "Delivery Cost:", "$", this.delivery);
		System.out.printf("%s %6s %.2f \n", "Insurance Cost:", "$", this.insurance);
		System.out.printf("%s %4s %.2f \n", "Grand Total Cost:", "$",
				(this.itemTotal + this.delivery + this.insurance));
		if (this.deliveryInformation != null && !this.deliveryInformation.isEmpty())
			System.out.println("Delivery Location: " + this.deliveryInformation + "\n");
		else
			System.out.println("Pick up in-store \n");
		System.out.printf("%20s \n\n\n", "Thankyou for shopping at Toy Universe!");

	}
}
// There are no bugs in this class.
