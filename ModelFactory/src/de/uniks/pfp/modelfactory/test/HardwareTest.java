package de.uniks.pfp.modelfactory.test;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import de.uniks.pfp.modelfactory.hardware.LocalBrick;
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
		
		// LocalBrick test = new LocalBrick(ip);

		RMIRegulatedMotor m1 = brick.createRegulatedMotor("A", 'L');
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
		
	}

}
