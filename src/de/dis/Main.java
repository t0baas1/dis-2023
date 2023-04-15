package de.dis;

import de.dis.data.Agent;

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
		final int QUIT = 1;

		// Erzeuge Menü
		Menu mainMenu = new Menu("Main Menu");
		mainMenu.addEntry("Agent-Management", MENU_Agent);
		mainMenu.addEntry("Quit", QUIT);

		// Verarbeite Eingabe
		while (true) {
			int response = mainMenu.show();

			switch (response) {
				case MENU_Agent:
					showAgentMenu();
					break;
				case QUIT:
					return;
			}
		}
	}

	/**
	 * Shows the Agent Management Menu
	 */
	public static void showAgentMenu() {
		// Menu Options
		final int NEW_Agent = 0;
		final int BACK = 1;

		// AgentManagement Menu
		Menu AgentMenu = new Menu("Agent-Management");
		AgentMenu.addEntry("New Agent", NEW_Agent);
		AgentMenu.addEntry("Back to Main Menu", BACK);

		// Process the inpu
		while (true) {
			int response = AgentMenu.show();

			switch (response) {
				case NEW_Agent:
					newAgent();
					break;
				case BACK:
					return;
			}
		}
	}

	/**
	 * Adds a new Agent after the user inputs the corresponding data.
	 */
	public static void newAgent() {
		Agent m = new Agent();

		m.setName(FormUtil.readString("Name"));
		m.setAddress(FormUtil.readString("Adress"));
		m.setLogin(FormUtil.readString("Login"));
		m.setPassword(FormUtil.readString("Passwort"));
		m.save();

		System.out.println("Agent with ID " + m.getId() + " was added.");
	}
}
