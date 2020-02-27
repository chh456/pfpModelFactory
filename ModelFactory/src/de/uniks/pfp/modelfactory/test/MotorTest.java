package de.uniks.pfp.modelfactory.test;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import lejos.hardware.port.PortException;
import lejos.remote.ev3.RMIRegulatedMotor;
import lejos.remote.ev3.RemoteEV3;

public class MotorTest {

	RemoteEV3 brick;
	RMIRegulatedMotor rmi;
	int currentMeter = 0;
	
	
	public MotorTest(String ip) throws RemoteException, MalformedURLException, NotBoundException {
		brick = new RemoteEV3(ip);
	}
	
	public boolean createMotor(String port, Character type) {
		boolean result = false;
		
		try {
			rmi = brick.createRegulatedMotor(port, type);
			result = true;
		} catch (PortException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return result;
	}
	
	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub

		Thread.sleep(2000);
		
		MotorTest test = null;
		try {
			 test = new MotorTest("192.168.0.103");
		} catch (RemoteException | MalformedURLException | NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if (test != null) {
			// A Motor Anlieferungsförderband. Negativ dreht in Richtung Drehtisch.			
			test.createMotor("A", 'L');
			test.rotate(150); // 150 = Ladungsträger wird 6cm bewegt
			
			test.rotate(-500);
			test.rotate(-1000);
			test.rotate(100);
			
			
			test.close();
		}
		
		System.out.println("Rotated: " + test.currentMeter);
		
	}

	private void close() {
		// TODO Auto-generated method stub
		try {
			rmi.close();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void rotate(int angle) {
		
		if (angle == 0) return;
		
		int desiredRotation = Math.abs(angle);
		int trueRotation = 0;
		
		System.out.println("Start Rotation (r:" + desiredRotation + "|a:"+ angle +")");
				
		int startSingleRotation = -1;
		int endSingleRotation = -1;
		
		try {
			
			/* Fälle:
			 * - Tachostand ist positiv. Es wird positiv oder negativ gedreht. Ergebnis positiv.
			 * - Tachostand ist negativ. Es wird negativ oder positiv gedreht. Ergebnis negativ.
			 * - Tachostand ist positiv. Es wird negativ gedreht. Ergebnis negativ.
			 * - Tachostand ist negativ. Es wird positiv gedreht. Ergebnis positiv.
			 * */
			
			// measureDistance(start, end);
			
			int startRotation = Math.abs(rmi.getTachoCount());
			
			startSingleRotation = rmi.getTachoCount();
			System.out.print("s: " + startRotation + "\t");
			
			rmi.rotate(angle);
			
			int endRotation = Math.abs(rmi.getTachoCount());
			System.out.print("e: " + endRotation + "\t");
			
			trueRotation = (endRotation > startRotation ? (endRotation - startRotation) : (startRotation - endRotation));
			System.out.println("d: " + trueRotation );
			
			currentMeter += trueRotation;
			
			
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
	
}
