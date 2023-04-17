package de.dis;

import de.dis.data.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import static de.dis.FormUtil.*;
import static java.lang.Integer.parseInt;

/**
 * Main Class
 */
public class Main {
	/**
	 * Starts the application
	 */
	public static void main(String[] args) {
		showMainMenu();
	}

	/**
	 * Shows the main-menu
	 */
	public static void showMainMenu() {
		// Menüoptionen
		final int MENU_Agent = 0;
		final int MENU_Contract = 1;
		final int MENU_Estate = 2;
		final int QUIT = 3;
		// Erzeuge Menü
		Menu mainMenu = new Menu("Main Menu");
		mainMenu.addEntry("Agent-Management", MENU_Agent);
		mainMenu.addEntry("Contract-Management", MENU_Contract);
		mainMenu.addEntry("Estate-Management", MENU_Estate);
		mainMenu.addEntry("Quit", QUIT);

		// Verarbeite Eingabe
		while (true) {
			int response = mainMenu.show();

			switch (response) {
				case MENU_Agent:
					showAgentPasswordMenu();
					break;
				case MENU_Contract:
					showContractMenu();
					break;
				case MENU_Estate:
					showEstatePasswordMenu();
					break;
				case QUIT:
					return;
			}
		}
	}

	/**
	 * Shows the password check menu
	 */
	public static void showAgentPasswordMenu() {
		// Menu Options
		String password = readString("Type password");
		Boolean login = password.equals("secret");
			if (login) {
				showAgentMenu();
			} else{
				return;
			}
		}

	/**
	 * Shows the password check menu
	 */
	public static void showEstatePasswordMenu() {
		// Menu Options
		String user = readString("Type username");
		String password = readString("Type password");

		Agent n = new Agent();
		Agent m = n.check(user,password);

		if (m != null) {
			System.out.println("Welcome!");
			System.out.println("");
			showEstateMenu();
		} else{
			System.out.println("Wrong credentials!");
			return;
		}
	}

	/**
	 * Shows the Agent Management Menu
	 */
	public static void showAgentMenu() {
		// Menu Options
		final int NEW_Agent = 0;
		final int UPDATE_Agent = 1;
		final int BACK = 3;

		// AgentManagement Menu
		Menu AgentMenu = new Menu("Agent-Management");
		AgentMenu.addEntry("New Agent", NEW_Agent);
		AgentMenu.addEntry("Update or DELETE Agent", UPDATE_Agent);
		AgentMenu.addEntry("Back to Main Menu", BACK);

		// Process the input
		while (true) {
			int response = AgentMenu.show();

			switch (response) {
				case NEW_Agent:
					newAgent();
					break;
				case UPDATE_Agent:
					updateAgent();
					break;
				case BACK:
					return;
			}
		}
	}

	/**
	 * Shows the Contract Management Menu
	 */
	public static void showContractMenu() {
		// Menu Options
		final int NEW_PContract = 0;
		final int NEW_TContract = 1;
		final int NEW_Person = 2;
		final int Overview = 3;
		final int BACK = 4;

		// ContractManagement Menu
		Menu ContractMenu = new Menu("Contract-Management");
		ContractMenu.addEntry("New Purchase Contract", NEW_PContract);
		ContractMenu.addEntry("New Tenancy Contract", NEW_TContract);
		ContractMenu.addEntry("Insert New Person", NEW_Person);
		ContractMenu.addEntry("Contracts Overview", Overview);
		ContractMenu.addEntry("Back to Main Menu", BACK);

		// Process the input
		while (true) {
			int response = ContractMenu.show();

			switch (response) {
				case NEW_TContract:
					newTContract();
					break;
				case NEW_PContract:
					newPContract();
					break;
				case NEW_Person:
					newPerson();
					break;
				case Overview:
					Overview();
					break;
				case BACK:
					return;
			}
		}
	}

	/**
	 * Adds a new Tenancy Contract after the user inputs the corresponding data.
	 */
	public static void newTContract() {
		TContract m = new TContract();
		m.setApartmentid(readInt("Apartment ID"));
		m.setPersonid(readInt("Person ID"));
		m.setContractNumber(readInt("Contract Number"));
		SimpleDateFormat format = new SimpleDateFormat( "MM/dd/yyyy" );
		try {
			java.util.Date myDate = format.parse(readString("Date (MM/dd/yyyy)"));
			java.sql.Date sqlDate = new java.sql.Date(myDate.getTime());
			m.setDate(sqlDate);
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
		m.setPlace(readString("Place"));
		try {
			java.util.Date myDate = format.parse(readString("Start Date (MM/dd/yyyy)"));
			java.sql.Date sqlDate = new java.sql.Date(myDate.getTime());
			m.setStartDate(sqlDate);
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
		m.setDuration(readInt("Duration"));
		m.setAdditionalCosts(readInt("Additional Costs"));
		m.save();

		System.out.println("Tenancy Contract with ID " + m.getId() + " was added.");
	}

	/**
	 * Adds a new Purchase Contract after the user inputs the corresponding data.
	 */
	public static void newPContract() {
		PContract m = new PContract();

		m.setHouseid(readInt("House ID"));
		m.setPersonid(readInt("Person ID"));
		m.setContractNumber(readInt("Contract Number"));
		SimpleDateFormat format = new SimpleDateFormat( "MM/dd/yyyy" );
		try {
			java.util.Date myDate = format.parse(readString("Date  (MM/dd/yyyy)"));
			java.sql.Date sqlDate = new java.sql.Date(myDate.getTime());
			m.setDate(sqlDate);
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
		m.setPlace(readString("Place"));
		m.setNumberOfInstallments(readInt("Number of Installments"));
		m.setInterestrate(readInt("Interest Rate"));
		m.save();

		System.out.println("Purchase Contract with ID " + m.getId() + " was added.");
	}


	/**
	 * Adds a new Person after the user inputs the corresponding data.
	 */
	public static void newPerson() {
		Person m = new Person();
		m.setFirstName(readString("First Name"));
		m.setName(readString("Name"));
		m.setAdress(readString("Adress"));
		m.save();

		System.out.println("Person with ID " + m.getId() + " was added.");
	}
	/**
	 * Shows an overview of all Contracts
	 */
	public static void Overview() {
		Overview m = new Overview();
		m.load();
	}

	/**
	 * Adds a new Agent after the user inputs the corresponding data.
	 */
	public static void newAgent() {
		Agent m = new Agent();

		m.setName(readString("Name"));
		m.setAddress(readString("Adress"));
		m.setLogin(readString("Login"));
		m.setPassword(readString("Passwort"));
		m.save();

		System.out.println("Agent with ID " + m.getId() + " was added.");
	}

	/**
	 * Updates or deletes an Agent after the user inputs the corresponding data.
	 */
	public static void updateAgent() {
		Agent m = new Agent();
		int id = readInt("ID");
		m.setId(id);

		final int Update = 0;
		final int Delete = 1;
		final int NO = 2;
		Menu UpdateMenu = new Menu("Do you want to update or delete Agent information?");
		UpdateMenu.addEntry("UPDATE", Update);
		UpdateMenu.addEntry("DELETE", Delete);
		UpdateMenu.addEntry("EXIT", NO);


		while (true) {
			int response = UpdateMenu.show();
		switch (response) {
			case Update:
				Agent n = new Agent();
				n.setId(m.getId());
				n.setName(readString("Name"));
				n.setAddress(readString("Adress"));
				n.setLogin(readString("Login"));
				n.setPassword(readString("Passwort"));
				n.save();
				System.out.println("Agent with ID " + n.getId() + " was updated.");
				break;
			case Delete:
				int temp = m.getId();
				m.delete(temp);
				System.out.println("Agent with ID " + temp + " was deleted.");
				break;
			case NO:
				return;
			}
		}
	}


/**
 * Shows the Estate Menu
 */
	public static void showEstateMenu() {
		// Menu Options
		final int Apartments = 0;
		final int Houses = 1;
		final int BACK = 2;

		// ContractManagement Menu
		Menu showEstateMenu = new Menu("Estate-Management");
		showEstateMenu.addEntry("Apartments", Apartments);
		showEstateMenu.addEntry("Houses", Houses);

		/**
		 * Shows the Apartment Management Menu
		 */
		showEstateMenu.addEntry("Back to Main Menu", BACK);

		// Process the input
		while (true) {
			int response = showEstateMenu.show();

			switch (response) {
				case Apartments:
					showApartmentMenu();
					break;
				case Houses:
					showHouseMenu();
					break;
				case BACK:
					return;
			}
		}
	}
	public static void showApartmentMenu() {
		// Menu Options
		final int NEW_Apartment = 0;
		final int UPDATE_Apartment = 1;
		final int DELETE_Apartment = 2;
		final int BACK = 3;

		// AgentManagement Menu
		Menu ApartmentMenu = new Menu("Apartment-Management");
		ApartmentMenu.addEntry("Create Apartment", NEW_Apartment);
		ApartmentMenu.addEntry("Update Apartment", UPDATE_Apartment);
		ApartmentMenu.addEntry("Delete Apartment", DELETE_Apartment);
		ApartmentMenu.addEntry("Back to Main Menu", BACK);

		// Process the input
		while (true) {
			int response = ApartmentMenu.show();

			switch (response) {
				case NEW_Apartment:
					newApartment();
					break;
				case UPDATE_Apartment:
					updateApartment();
					break;
				case DELETE_Apartment:
					deleteApartment();
					break;
				case BACK:
					return;
			}
		}
	}

	/**
	 * Adds a new Apartment after the user inputs the corresponding data.
	 */
	public static void newApartment() {
		Apartment m = new Apartment();

		m.setEstateId(parseInt(readString("Estate ID")));
		m.setAgentId(parseInt(readString("Agent ID")));
		m.setFloor(parseInt(readString("Floor")));
		m.setRent(parseInt(readString("Rent")));
		m.setRooms(parseInt(readString("Rooms")));
		m.setBalcony(readString("Balcony (y/n)"));
		m.setKitchen(readString("Built-In Kitchen (y/n)"));
		m.setCity(readString("City"));
		m.setPostalCode(parseInt(readString("Postal Code")));
		m.setStreet(readString("Street"));
		m.setStreetNumber(readString("Street Nr."));
		m.setSquare(parseInt(readString("Square-area")));
		m.save();

		System.out.println("Apartment with ID " + m.getId() + " was added.");
	}

	/**
	 * Updates an Apartment after the user inputs the corresponding data.
	 */
	public static void updateApartment() {
		Apartment n = new Apartment();
		int id = readInt("Give apartment Id");

		Apartment m = n.load(id);

		if (n == null) {
			System.out.println("Apartment was not found");
			return;
		}

		m.setEstateId(parseInt(readString("Estate ID")));
		m.setAgentId(parseInt(readString("Agent ID")));
		m.setFloor(parseInt(readString("Floor")));
		m.setRent(parseInt(readString("Rent")));
		m.setRooms(parseInt(readString("Rooms")));
		m.setBalcony(readString("Balcony (y/n)"));
		m.setKitchen(readString("Built-In Kitchen"));
		m.setCity(readString("City"));
		m.setPostalCode(parseInt(readString("Postal Code")));
		m.setStreet(readString("Street"));
		m.setStreetNumber(readString("Street Nr."));
		m.setSquare(parseInt(readString("Square-area")));
		m.save();

		System.out.println("Apartment with ID " + m.getId() + " was updated.");
	}

	/**
	 * Updates an Apartment after the user inputs the corresponding data.
	 */
	public static void deleteApartment() {
		Apartment m = new Apartment();
		int id = readInt("Give Apartment Id");
		m.setId(id);
		m.delete(id);
		System.out.println("Apartment with ID " + id + " was deleted.");
	}

	public static void showHouseMenu() {
		// Menu Options
		final int NEW_House = 0;
		final int UPDATE_House = 1;
		final int DELETE_House = 2;
		final int BACK = 3;

		// AgentManagement Menu
		Menu HouseMenu = new Menu("House-Management");
		HouseMenu.addEntry("Create House", NEW_House);
		HouseMenu.addEntry("Update House", UPDATE_House);
		HouseMenu.addEntry("Delete House", DELETE_House);
		HouseMenu.addEntry("Back to Main Menu", BACK);

		// Process the input
		while (true) {
			int response = HouseMenu.show();

			switch (response) {
				case NEW_House:
					newHouse();
					break;
				case UPDATE_House:
					updateHouse();
					break;
				case DELETE_House:
					deleteHouse();
					break;
				case BACK:
					return;
			}
		}
	}

	/**
	 * Adds a new House after the user inputs the corresponding data.
	 */
	public static void newHouse() {
		House m = new House();


		m.setAgentId(parseInt(readString("Agent ID")));
		m.setFloor(parseInt(readString("Floor")));
		m.setPrice(parseInt(readString("Rent")));
		m.setGarden(readString("Balcony (y/n)"));
		m.setCity(readString("City"));
		m.setPostalCode(parseInt(readString("Postal Code")));
		m.setStreet(readString("Street"));
		m.setStreetNumber(readString("Street Nr."));
		m.setSquare(parseInt(readString("Square-area")));
		m.save();

		System.out.println("House with ID " + m.getId() + " was added.");
	}

	/**
	 * Updates a House after the user inputs the corresponding data.
	 */
	public static void updateHouse() {
		House n = new House();
		int id = readInt("Give House Id: ");

		House m = n.load(id);

		if (m == null) {
			System.out.println("Hoouse was not found");
			return;
		}

		m.setAgentId(parseInt(readString("Agent ID")));
		m.setFloor(parseInt(readString("Floor")));
		m.setPrice(parseInt(readString("Rent")));
		m.setGarden(readString("Balcony (y/n)"));
		m.setCity(readString("City"));
		m.setPostalCode(parseInt(readString("Postal Code")));
		m.setStreet(readString("Street"));
		m.setStreetNumber(readString("Street Nr."));
		m.setSquare(parseInt(readString("Square-area")));
		m.save();

		System.out.println("House with ID " + m.getId() + " was updated.");
	}

	/**
	 * Updates a House after the user inputs the corresponding data.
	 */
	public static void deleteHouse() {
		House m = new House();
		int id = readInt("Give House Id: ");
		m.setId(id);
		m.delete(id);
		System.out.println("House with ID " + id + " was deleted.");

	}
}
