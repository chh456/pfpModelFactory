package de.uniks.pfp.modelfactory.stations;

import java.util.ArrayList;

public class StockSlot {

	private class Movement {
		
		Runnable movement;
		StockSlot to;
		
		public Movement(StockSlot s) {
			to = s;
		}
		
		public Runnable get() {
			return movement;
		}
		
		public void set(Runnable r) {
			movement = r;
		}
		
		public StockSlot getSlot() {
			return to;
		}
		
		@Override
		public String toString() {
			return "This will move the elevator to Slot " + to;
		}
		
	}
	
	ArrayList<Movement> movements;
	
	
	public String name;
	
	public StockSlot(String name) {
		this.name = name;
		movements = new ArrayList<>();
	}
	
	public Movement createMovement(Runnable r, StockSlot s) {
		Movement m = new Movement(s);
		m.set(r);
		setMovement(m);
		return m;
	}
	
	public void setMovement(Movement m) {
		if (!movements.contains(m))
			movements.add(m);
	}
	
	public ArrayList<StockSlot> getPossibleMovements() {
		ArrayList<StockSlot> possibleSlots = new ArrayList<>();
		for (Movement m : movements)
			possibleSlots.add(m.getSlot());
		return possibleSlots;
	}
	
	@Override
	public String toString() {
		return name;
	}
	
	
}
