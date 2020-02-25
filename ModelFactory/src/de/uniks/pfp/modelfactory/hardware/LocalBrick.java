package de.uniks.pfp.modelfactory.hardware;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;

import lejos.remote.ev3.RMIRegulatedMotor;
import lejos.remote.ev3.RemoteEV3;

public class LocalBrick {

	String ip;
	RemoteEV3 brick = null;
	Boolean initialized = false;
	
	// The brick has 4 ports for engines addressed by a letter (= Character) and 4 ports for sensors addressed by a number (= Integer)
	ArrayList <Port<Character, OurMotor>> engines = new ArrayList<>();
	ArrayList <Port<Integer, Sensor>> sensors = new ArrayList<>();
	
	static Character [] enginePorts = {'A', 'B', 'C', 'D'};
	static Integer [] sensorPorts = {1, 2, 3, 4}; 
	

	public class Port <U, W> {
		private U portName;
		private W hardware;
		
		public void setPortName(U portName) {
			this.portName = portName;
		}
		
		public void setHardware(W hardware) {
			this.hardware = hardware;
		}
		
		public U getPortName() {
			return portName;
		}
		
		public W getHardware() {
			return hardware;
		}
	}
	
	public LocalBrick(String ip) {
		this.ip = ip;
		
		try {
			brick = new RemoteEV3(ip);
		} catch (RemoteException | MalformedURLException | NotBoundException e) {
			// TODO create custom brick not init exception and throw it
			e.printStackTrace();
		}

		// We null initialize the ports on this brick		
		init();
		
		// After this point our brick can take 4 engines and 4 sensors
		if (brick != null && engines.size() == 4 && sensors.size() == 4)
			initialized = true;
		
	}

	private void init() {
		for (Character port : enginePorts) {
			Port<Character, OurMotor> p = new Port<>();
			p.setPortName(port);
			p.setHardware(null);
			engines.add(p);
		}
		
		for (Integer port : sensorPorts) {
			Port<Integer, Sensor> p = new Port<>();
			p.setPortName(port);
			p.setHardware(null);
			sensors.add(p);
		}
	}
	
	public boolean motorPortAvailable(Character port) {
		boolean result = false;
		for (Port<Character, OurMotor> p : engines) 
			if (p.getPortName().equals(port))
				if (p.getHardware() == null)
					result = true;
		return result;
	}
	
	public OurMotor getMotorAtPort(Character port) {
		OurMotor motor = null;
		for (Port<Character, OurMotor> p : engines) 
			if (p.getPortName().equals(port))
				motor = p.getHardware();
		return motor;
	}

	public boolean sensorPortAvailable(Integer port) {
		boolean result = false;
		for (Port<Integer, Sensor> p : sensors) 
			if (p.getPortName().equals(port))
				if (p.getHardware() == null)
					result = true;
		return result;
	}
	
	public void createMotor(Character port, OurMotor newMotor) {
		
		if (initialized && newMotor != null) {
			
			// We check for an old motor on port. If there is one, we close it.
			OurMotor oldMotor;
			if ((oldMotor = getMotorAtPort(port)) != null) 
				oldMotor.close();

			for (Port<Character, OurMotor> p : engines)
				if (p.getPortName().equals(port))
					p.setHardware(newMotor);
			
		}
		
		
	
	}
	
	
	
}
