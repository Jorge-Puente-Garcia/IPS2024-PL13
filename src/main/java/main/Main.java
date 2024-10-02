package main;

import giis.controller.TiendaController;
import giis.util.Database;

public class Main {
	
	private static Database db = new Database();
	
	public static void main(String[] args) {
		db.createDatabase(false);
		db.loadDatabase();
		
		new TiendaController(db);
	}

}
