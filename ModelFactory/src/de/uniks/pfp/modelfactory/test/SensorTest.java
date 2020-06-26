package de.uniks.pfp.modelfactory.test;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import de.uniks.pfp.modelfactory.hardware.PFPSensor;
import de.uniks.pfp.modelfactory.interfaces.SensorListener;
import lejos.remote.ev3.RMISampleProvider;
import lejos.remote.ev3.RemoteEV3;

public class SensorTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		RemoteEV3 brick = null;
		
		try {
			brick = new RemoteEV3("192.168.0.104");
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		RMISampleProvider s = brick.createSampleProvider("S1", "lejos.hardware.sensor.EV3TouchSensor", null);
		
		ExecutorService executor = Executors.newCachedThreadPool();
		
		PFPSensor sen = new PFPSensor(s, executor);
		
		sen.activate();
		
		SensorListener sl = new SensorListener() {

			@Override
			public void inform(boolean f) {
				// TODO Auto-generated method stub
				System.out.println("I was informed about a " + f);
			}
			
		};
		
		sen.registerListener(sl);
		
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		sen.shutdown();
		
		/*
		
		int counter = 0;
		float sArr[] = new float[5];
		
		
		while (counter < 10) {
			
			try {
				sArr = s.fetchSample();
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			if(sArr[0] != 0) {
				counter++;
				System.out.println("Sensor fired.");
			}
				
			
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		} */
		
		try {
			s.close();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("Im here.");
		
		executor.shutdown();
		
	}

}
