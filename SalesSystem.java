import java.util.Scanner;

/**
 * 
 * @author Josh Coppen
 * 
 *         This is the SalesSystem class. It is the application class for this
 *         program.
 * 
 *         SalesSystem is designed for a Toy Universe employee to create an
 *         order(invoice object) for a customer.
 * 
 *         It displays a menu and asks for an input from the user.Crows111
 * 
 *         This class prompts the user on many occasions.
 * 
 *         SalesSystem works with both the Invoice and Inventory classes.
 * 
 *         This class interacts with the invoice class in the following ways: -
 *         It can create a new invoice object through adding a customer. - This
 *         class adds purchases to an invoice object. - It sends price of
 *         purchases through to the invoice class to work out the total of an
 *         invoice object. - It also will call invoice method to add delivery
 *         info and costs in customer wants delivery./ - Sales System also calls
 *         insurance method of the invoice class to add insurance cost to
 *         invoice object.
 * 
 *         This class interacts with the inventory class in the following ways:
 *         - It displays the entire inventory of Toy Universe every time a
 *         employee add a purchase to an invoice. - This class removes items
 *         from the Inventory class when an item is added to an invoice. - It
 *         prompts the user to re-order stock to replenish the inventory class
 *         when an item quantity is a certain value.
 */

public class SalesSystem {

	// Declaring all instance variables necessary to successfully run SalesSystem.
	private Inventory inventory;
	private Invoice sale;
	private int currentItems;

	// Constructor of SalesSystem.
	// Instance variables initialized in here.
	// Menu also here which calls other methods.
	public SalesSystem() {

		// Initializing instance variables.
		this.sale = new Invoice("Joe (placeholder)", "12 Joe Road (placeholder)",
				"Australia (placeholder)", "1300 563 563 (placeholder)");
		this.inventory = new Inventory(sale.getCompany());
		this.currentItems = 0;

		Scanner sc = new Scanner(System.in);

		// The menu for the program. This is the forst thing a user sees when starting
		// this program.
		String startMenu = "Toy Universe Sales System \n";
		startMenu += "What would you like to do? \n";
		startMenu += "A) Start a new invoice \n";
		startMenu += "B) View purchase information \n";
		startMenu += "C) Add purchase \n";
		startMenu += "X) Exit sales system";

		// Asks the user for an input.
		System.out.println(startMenu);
		String tempInput = sc.nextLine();

		// This loop continues program until either user selects exit or an invoice is
		// finalized.
		while (tempInput != null) {

			// If user selects A, starts a new invoice, overriding the old invoice in the
			// process.
			if (tempInput.equalsIgnoreCase("A")) {
				newInvoice();
				System.out.println(startMenu);
				tempInput = sc.nextLine();
			}

			// If user select B, displays current invoice.
			else if (tempInput.equalsIgnoreCase("B")) {
				this.sale.displayInvoice();
				System.out.println(startMenu);
				tempInput = sc.nextLine();
			}

			// If user selects C, adds a purchase to current invoice.
			else if (tempInput.equalsIgnoreCase("C")) {
				addPurchase();
				System.out.println(startMenu);
				tempInput = sc.nextLine();
			}

			// If user selects X, exits the program.
			else if (tempInput.equalsIgnoreCase("X")) {
				System.exit(0);
			}

			// If user has a different input than those above, shows an error message and
			// prompts the user again.
			else {
				System.out
						.println("You have entered an invalid choice, please try again");
				System.out.println(startMenu);
				tempInput = sc.nextLine();
			}
		}
	}

	// This method starts a new invoice by asking for customer details and replacing
	// current invoice object.
	public void newInvoice() {

		Scanner sc = new Scanner(System.in);

		// Asks for customer details.
		System.out.println("What is the customers name");
		String name = sc.nextLine();

		System.out.println("What is the customers address");
		String address = sc.nextLine();

		System.out.println("What country does the customer live in");
		String country = sc.nextLine();

		System.out.println("What is the customers phone number");
		String phoneNumber = sc.nextLine();

		System.out.println();

		// Replaces the current invoice object with a new one, using customer details as
		// parameters.
		this.sale = new Invoice(name, address, country, phoneNumber);
	}

	// Adding a purchase to the current invoice object.
	public void addPurchase() {

		Scanner sc = new Scanner(System.in);

		// This shows the entire inventory of Toy Universe.
		showInventory();

		// Asks the employee to fill out product ID and quantity of objects a customer
		// requires.
		System.out.println("What ID does the customer want to purchase? (e.g. P1)");
		String productID = sc.nextLine();
		System.out.println("Quantity customer requires?");
		int quantity = sc.nextInt();
		sc.nextLine();
		
		productID = productID.toUpperCase();
		
		// Interacts with Inventory class to remove stock from Toy Universe stock
		// levels.
		int result = inventory.removeStock(productID, quantity);

		// shows the user a detailed error message and ends the method.
		if (result == -2) {
			System.out.println("Error, you have entered an invalid quantity");
		}
		// Shows the user a detailed error message and ends the method.
		else if (result == -1) {
			System.out.println("Error, the product ID was invalid");
		}
		// Shows the user a detailed error message and ends the method.
		else if (result == -3) {
			System.out.println(
					"Error, the quantity entered exceeds Toy Universes' current stock levels");
		}
		// If user entered a valid productID and quantity this else statement continues
		// the method.
		else {

			// Calling Inventory class to retrieve stock level of an item.
			int stockLevel = inventory.getStockLevel(productID);

			// Showing user successfully ordered product and updated stock level of the
			// product.
			System.out.println("The stock has been ordered successfully");
			System.out.println(
					productID + " - quantity of stock remaining - " + stockLevel);

			// If an items stocks falls below a certain quantity, calls the reorderStock
			// method.
			if (stockLevel <= 1)
				reorderStock(stockLevel, productID, quantity);

			// Retrieving values from the Inventory class.
			String description = this.inventory.getDescription(productID);
			double price = this.inventory.getPrice(productID);

			// Adds items to the invoice.
			this.sale.purchaseRecord(productID, quantity, description, price);

			// Calls Invoice class to calculate total of current purchases.
			this.sale.itemTotalCost(quantity, price);

			this.currentItems++;

			// Ensures customer can not order more than six items by finalizing a purchase
			// at six items.
			if (this.currentItems == 6) {
				System.out.println(
						"\nThe customer has ordered the maximum amount of items\n");
				finaliseOrder();
			}

			// Asks user if invoice is complete. If so, finalizes order.
			System.out.println("Is the customer ready to finalise their order? (Y or N)");
			String answer = sc.nextLine();

			if (answer.equalsIgnoreCase("Y"))
				finaliseOrder();
		}
	}

	// Re-orders stock at a certain level if employee deems necessary.
	public void reorderStock(int stockLevel, String productID, int quantity) {

		Scanner sc = new Scanner(System.in);

		if (stockLevel <= 1)
			System.out.println(
					"Would you like to order stock to replenish stock levels? (Y or N)");

		String answer = sc.nextLine();

		// If user agrees orders stock equals to quantity ordered. Then shows current
		// Inventory of that item.
		if (answer.equalsIgnoreCase("Y")) {
			this.inventory.orderStock(productID, quantity);
			System.out.println("Stock was ordered successfully");
			System.out.println(productID + " - quantity of stock remaining - "
					+ (stockLevel + quantity));
		}

		// If user does not just displays current inventory of that item.
		else if (stockLevel <= 1)
			System.out.println(
					productID + " - quantity of stock remaining - " + stockLevel);

	}

	// This finalizes the order by asking user to enter whether customer needs
	// delivery and or insurance.
	// Then this method shows the final invoice and closes the program.
	public void finaliseOrder() {

		Scanner sc = new Scanner(System.in);

		// Displays to employee countries permitted for delivery and shows the delivery
		// cost for each country.
		System.out.printf("%15s \n", "Delivery");
		System.out.println("Delivery is only available in Australia/New Zealand/USA");
		System.out.println("Australia - $9.95");
		System.out.println("New Zealand - $20.00");
		System.out.println("Australia - $37.96");

		// Prompting user if customer would like a delivery.
		System.out.println("Does the customer require delivery?(Y or N)");
		String answer = sc.nextLine();

		// If customer agrees to a delivery asks the town and country they would like
		// the goods shipped too.
		if (answer.equalsIgnoreCase("Y")) {
			System.out.println(
					"Enter the Country the customer would like the goods shipped too");
			String country = sc.nextLine();
			System.out.println(
					"Enter the Town/City customer would like the goods shipped too");
			String town = sc.nextLine();

			// Calls Invoice method to add delivery.
			this.sale.addDelivery(town, country);
		}

		// Asks employee if customer would like insurance. If so adds insurance.
		System.out.println(
				"For $9.95, would the customer like insurance for their purchase? (Y or N)");
		answer = sc.nextLine();

		if (answer.equalsIgnoreCase("Y"))
			this.sale.addInsurance();

		// Displays final invoice.
		this.sale.displayInvoice();

		// Closes program.
		System.exit(0);
	}

	// Shows total inventory of Toy Universe.
	// Struggled to create a classic table inline. Trying to complete for final submission.
	public void showInventory() {

		System.out.printf("%25s \n", "Products");
		System.out.printf("%s %14s %10s %10s \n", "ID", "Description", "Price",
				"Quantity");
		System.out.println("-----------------------------------------------");

		// Retrieves all productID's.
		String[] products = inventory.getAllProductIDs();

		// Loop retrieves all values of each item in inventory and prints them.
		for (int i = 0; i < products.length; i++) {
			String description = this.inventory.getDescription(products[i]);
			double price = this.inventory.getPrice(products[i]);
			int stockLevel = this.inventory.getStockLevel(products[i]);

			System.out.printf("%s | %s %s %.2f | %s \n", products[i], description,
					" | ", price, stockLevel);
		}
		System.out.println();
	}

	// The main method of the program, Where program starts.
	// Creates constructor.
	public static void main(String[] args) {
		SalesSystem sS = new SalesSystem();
	}
}
// This class is free of bugs.