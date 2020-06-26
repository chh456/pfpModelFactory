package de.uniks.pfp.modelfactory.stations;

import java.util.ArrayList;

public class Stock {
	
	ArrayList<StockSlot> slots;
	
	public Stock(ArrayList<StockSlot> slots) {
		this.slots = slots;
	}

	public ArrayList<StockSlot> getSlots() {
		return slots;
	}
	
}
