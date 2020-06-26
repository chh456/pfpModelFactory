package de.uniks.pfp.modelfactory.test;

import java.util.ArrayList;

import de.uniks.pfp.modelfactory.stations.Stock;
import de.uniks.pfp.modelfactory.stations.StockSlot;

public class StockTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		StockSlot s1 = new StockSlot("s1");
		StockSlot s2 = new StockSlot("s2");
		StockSlot s3 = new StockSlot("s3");
		StockSlot s4 = new StockSlot("s4");
		
		s1.createMovement(new Runnable() {

			@Override
			public void run() {
				try {
					Thread.sleep(3000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
			
		}, s2);
		
		s1.createMovement(new Runnable() {

			@Override
			public void run() {
				try {
					Thread.sleep(5000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
			
		}, s3);
		
		
		ArrayList<StockSlot> slots = new ArrayList<>();
		slots.add(s1);
		slots.add(s2);
		slots.add(s3);
		slots.add(s4);
		
		Stock stock = new Stock(slots);
		
		for (StockSlot s : stock.getSlots()) {
			System.out.println(s + " movements to: ");
			for (StockSlot sl : s.getPossibleMovements()) {
				System.out.println(sl);
			}
		}
		
		
	}

}
