import java.util.Arrays;

/**
 * A representation of the existing inventory system, which your program will
 * need to work with.
 * 
 * This class provides a functional interface which allows for the Sales System
 * developer to interact with a simulated inventory in the following ways:
 * 
 * retrieve the company name for printing on invoices;
 * 
 * retrieve product description, price and stock level based on caller-supplied
 * product ID;
 * 
 * remove stock from inventory as part of processing a purchase;
 * 
 * and order more stock when an item's stock level runs low.
 * 
 * Note that you are not permitted to change or add to the code below, rather
 * you need to determine how to create an instance of this inventory and invoke
 * the methods it provides as needed when implementing your Sales System
 * functionality, based on the Java docs that have been provided.
 */

public class Inventory {
	/**
	 * The name of the company Inventory is being maintained for.
	 */
	private String companyName;

	// Fixed array storing product ID's in inventory.
	private final String[] productIDs = { "P1", "P2", "P3", "P4", "P5", "P6" };

	// Fixed array storing descriptions for products in inventory.
	private final String[] productDescriptions = { "Lego City Garbage Truck", "Moana Adventure Doll",
			"Grafix Mega Craft Jar - Pink", "Rusty Rivets Rusty Botasaur", "Scrabble Original Board Game",
			"Jungle Pals Baby Playmat" };

	// Fixed array storing prices for products in inventory.
	private final double[] productPrices = { 28.00, 50.00, 17.99, 56.99, 50.00, 39.99 };

	// Fixed array storing stock levels for products in inventory.
	private final int[] productStockLevels = { 8, 6, 5, 2, 1, 4 };

	/**
	 * Returns the list of product ID's currently in the system as an array.
	 * 
	 * @return array containing a copy of the internal list of Product ID's.
	 */
	public String[] getAllProductIDs() {
		return Arrays.copyOf(productIDs, productIDs.length);
	}

	/**
	 * Constructs a newInventory object containing the specified company name
	 * 
	 * NOTE: The new Inventory object will include a starting image of the products
	 * in inventory.
	 * 
	 * @param companyName The name of the company for which inventory is being
	 *                    managed.
	 */
	public Inventory(String companyName) {
		this.companyName = companyName;
	}

	/**
	 * Retrieves the product description for the specified product ID
	 * 
	 * @param productID The ID of the product for which a description is being
	 *                  retrieved.
	 * 
	 * @return A non-null String containing the description if the product ID is
	 *         found, or a null String if the specified product ID was not found.
	 */
	public String getDescription(String productID) {
		int index = productIndex(productID);

		if (index == -1) {
			return null;
		} else {
			return productDescriptions[index];
		}
	}

	/**
	 * Retrieves the product price for the specified product ID
	 * 
	 * @param productID The ID of the product for which a price is being retrieved.
	 * 
	 * @return The price for the corresponding product if the specified product ID
	 *         was found, or Double.NaN if the specified product ID was not found.
	 */
	public double getPrice(String productID) {
		int index = productIndex(productID);

		if (index == -1) {
			return Double.NaN;
		} else {
			return productPrices[index];
		}
	}

	/**
	 * Retrieves the product stock level for the specified product ID
	 * 
	 * @param productID The ID of the product for which a description is being
	 *                  retrieved.
	 * 
	 * @return The current stock level for the corresponding product if the product
	 *         ID is found, or -1 if the specified product ID was not found.
	 */
	public int getStockLevel(String productID) {
		int index = productIndex(productID);

		if (index == -1) {
			return -1;
		} else {
			return productStockLevels[index];
		}
	}

	/**
	 * Updates the stock level for a specified product after a purchase.
	 * 
	 * @param productID The ID of the product for which a description is being
	 *                  retrieved.
	 * @param quantity  The quantity of the product being removed from Inventory.
	 *                  Quantity must be > 0 or request to remove stock from
	 *                  Inventory will be rejected.
	 * 
	 * @return -1 if the product ID is invalid; or -2 if quantity is invalid (<= 0);
	 *         or -3 if the quantity exceeds the current stock level; or the stock
	 *         level remaining if stock level was updated successfully.
	 */
	public int removeStock(String productID, int quantity) {
		// check for invalid quantity (ie. 0)
		if (quantity <= 0) {
			return -2;
		} else {
			int index = productIndex(productID);

			// check if product ID was not found
			if (index == -1) {
				return -1;
			} else if (quantity > productStockLevels[index]) {
				return -3;
			} else {
				// update stock level for corresponding product
				productStockLevels[index] -= quantity;

				// return stock level remaining
				return productStockLevels[index];
			}
		}
	}

	/**
	 * Updates the stock level for a specified product after a re-stock order is
	 * submitted.
	 * 
	 * @param productID The ID of the product for which a description is being
	 *                  retrieved.
	 * @param quantity  The quantity of the product being removed from Inventory.
	 *                  Quantity must be > 0 or request to remove stock from
	 *                  Inventory will be rejected.
	 * 
	 * @return -1 if the product ID is invalid; or -2 if the quantity is invalid (<=
	 *         0); or the stock level remaining if stock level was updated
	 *         successfully.
	 */
	public int orderStock(String productID, int quantity) {
		// check for invalid quantity (ie. 0)
		if (quantity <= 0) {
			return -2;
		} else {
			int index = productIndex(productID);

			// check if product ID was not found
			if (index == -1) {
				return -1;
			} else {
				// update stock level for corresponding product
				productStockLevels[index] += quantity;

				// return stock level remaining
				return productStockLevels[index];
			}
		}
	}

	/**
	 * Converts the specified product ID to an index within the arrays of product
	 * information.
	 * 
	 * @param productID the product ID for which an index position is required.
	 * 
	 * @return index position for corresponding product details if product ID was
	 *         found, or -1 if specified product ID was not found.
	 * 
	 * @see getDescription getPrice getStockLevel updateStockLevel
	 */
	private int productIndex(String productID) {
		switch (productID) {
		case "P1":
			return 0;
		case "P2":
			return 1;
		case "P3":
			return 2;
		case "P4":
			return 3;
		case "P5":
			return 4;
		case "P6":
			return 5;
		default:
			return -1;
		}
	}

}
