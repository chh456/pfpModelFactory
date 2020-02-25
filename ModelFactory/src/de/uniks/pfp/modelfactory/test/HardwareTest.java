package de.uniks.pfp.modelfactory.test;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import de.uniks.pfp.modelfactory.hardware.PFPBrick;
import de.uniks.pfp.modelfactory.hardware.PFPMotor;
import lejos.remote.ev3.RMIRegulatedMotor;
import lejos.remote.ev3.RemoteEV3;

public class HardwareTest {

	public static void main(String[] args) throws RemoteException {
		// TODO Auto-generated method stub

		RemoteEV3 brick = null;
		String ip = "192.168.0.103";
		
		try {
			brick = new RemoteEV3(ip);
		} catch (RemoteException | MalformedURLException | NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if (brick != null)
			System.out.println("Init successful");
		
		PFPBrick test = new PFPBrick(ip);
		
		
		RMIRegulatedMotor m1 = brick.createRegulatedMotor("A", 'L');
		PFPMotor om1 = new PFPMotor(m1, "A", 'L');
		PFPMotor om2 = new PFPMotor(m1, "A", 'L');
		test.createMotor("A", om1);
		test.createMotor("A", om2);
		
		
		try {
			m1.rotate(200);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			m1.close();
			//m2.close();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		test.close();
	}

}
