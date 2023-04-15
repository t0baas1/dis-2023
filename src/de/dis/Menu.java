package de.dis;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * A small helper class for menus.
 * Add menu options by using addEntry,
 * and you can show the menu with it's options using show(),
 * which will return the option chosen by the user as an integer.
 *
 * Example:
 * Menu m = new Menu("Main Menu");
 * m.addEntry("Work Hard", 0);
 * m.addEntry("Relax", 1);
 * m.addEntry("Go Home", 2);
 * int choice = m.show();
 * 
 * Then the menu will look like this:
 * Main Menu:
 * [1] Work Hard
 * [2] Relax
 * [3] Go Home
 * --
 */
public class Menu {
	private String title;
	private ArrayList<String> labels = new ArrayList<String>();
	private ArrayList<Integer> returnValues = new ArrayList<Integer>();
	
	/**
	 * Constructor.
	 * @param title Titel of the menu Example: "Main Menu"
	 */
	public Menu(String title) {
		super();
		this.title = title;
	}
	
	/**
	 * Add a menu option to the menu.
	 * @param label Name of the option.
	 * @param returnValue the returned constant once this option is chosen.
	 */
	public void addEntry(String label, int returnValue) {
		this.labels.add(label);
		this.returnValues.add(Integer.valueOf(returnValue));
	}
	
	/**
	 * Show the menu
	 * @return the constant that corresponds to the chosen menu option.
	 */
	public int show()  {
		int selection = -1;
		
		BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
		
		while(selection == -1) {
			System.out.println(title+":");
			
			for(int i = 0; i < labels.size(); ++i) {
				System.out.println("["+(i+1)+"] "+labels.get(i));
			}
			
			System.out.print("-- ");
			try {
				selection = Integer.parseInt(stdin.readLine());
			} catch (NumberFormatException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			if(selection < 1 || selection > returnValues.size()) {
				System.err.println("Invalid Input!");
				selection = -1;
			} 
		}
		
		return returnValues.get(selection-1);
	}
}
