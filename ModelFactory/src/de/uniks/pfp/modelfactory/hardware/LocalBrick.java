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
	ArrayList <Port<String, OurMotor>> engines = new ArrayList<>();
	ArrayList <Port<Integer, Sensor>> sensors = new ArrayList<>();
	
	static String [] enginePorts = {"A", "B", "C", "D"};
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
		for (String port : enginePorts) {
			Port<String, OurMotor> p = new Port<>();
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
	
	public OurMotor getMotorAtPort(String port) {
		OurMotor motor = null;
		for (Port<String, OurMotor> p : engines) 
			if (p.getPortName().equals(port))
				motor = p.getHardware();
		return motor;
	}

	public Sensor getSensorAtPort(Integer port) {
		Sensor sensor = null;
		for (Port<Integer, Sensor> p : sensors) 
			if (p.getPortName().equals(port))
				sensor = p.getHardware();
		return sensor;
	}
	
	public void createMotor(String port, OurMotor newMotor) {
		
		System.out.println("Creating new motor.");
		
		if (initialized && newMotor != null) {
			
			// We check for an old motor on port. If there is one, we close it.
			OurMotor oldMotor;
			if ((oldMotor = getMotorAtPort(port)) != null)  {
				oldMotor.close();
				System.out.println("Had to close old motor");
			}
				

			for (Port<String, OurMotor> p : engines)
				if (p.getPortName().equals(port))
					p.setHardware(newMotor);
			
		}
		
		
	
	}
	
	public void close() {
		closeSensors();
		closeEngines();
	}

	private void closeEngines() {
		// TODO Auto-generated method stub
		for (Port<String, OurMotor> p : engines) {
			OurMotor currentMotor;
			if ((currentMotor = p.getHardware()) != null)
				p.getHardware().close();
		}
			
	}

	private void closeSensors() {
		// TODO Auto-generated method stub
		for (Port<Integer, Sensor> p : sensors) {
			Sensor currentSensor;
			if ((currentSensor = p.getHardware()) != null)
				p.getHardware().close();
		}
	}
	
}
