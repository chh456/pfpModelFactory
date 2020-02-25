package de.uniks.pfp.modelfactory.hardware;

import java.rmi.RemoteException;

import lejos.remote.ev3.RMIRegulatedMotor;

public class OurMotor {

	RMIRegulatedMotor motor; 
	Character type; // Large or medium
	String port;
	
	public OurMotor(RMIRegulatedMotor m, String port, Character type) {
		motor = m;
		this.type = type;
		this.port = port;
	}
	
	
	public char getType() {
		return type;
	}

	public void close() {
		System.out.println("Closing motor.");
		// TODO Auto-generated method stub
		try {
			motor.close();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			// We were not able to close the port. Not much we can do anymore.
		}
	}

}
