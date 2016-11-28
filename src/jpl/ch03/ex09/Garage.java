package jpl.ch03.ex09;

import jpl.ch03.ex08.Battery;
import jpl.ch03.ex08.GasTank;
import jpl.ch03.ex08.EnergySource;
import jpl.ch03.ex08.Vehicle;

/**
 * 複数のVehicleインスタンスを保持することのできるGarageクラス.
 * @author okuno
 *
 */
public class Garage implements Cloneable {
	
	// ++++++ Fileds ++++++++
	/**
	 * Vehicleインスタンスを保持する配列
	 */
	private Vehicle[] vehicles;
	

	// ++++++ Constructors ++++++++
	public Garage() {
		super();
	}
	
	public Garage(Vehicle[] vehicles) {
		this();
		this.vehicles = vehicles;
	}


	// ++++++ Getters, setters ++++++++
	public Vehicle[] getVehicles() {
		return vehicles;
	}

	public void setVehicles(Vehicle[] vehicles) {
		this.vehicles = vehicles;
	}


	// ++++++ Methods ++++++++
	/**
	 * vehicles配列の中身ごとコピーするcloneメソッド.
	 */
	public Garage clone() {
		Vehicle[] clonedVehicles = new Vehicle[vehicles.length];
		for (int i = 0; i < clonedVehicles.length; i++) {
			clonedVehicles[i] = vehicles[i].clone();
		}
		return new Garage(clonedVehicles);
	}
	
	/**
	 * Garageインスタンスをcloneし、そのcloneが成功しているか確認する.
	 * @param args
	 */
	public static void main(String[] args) {
		Vehicle[] vehicles = new Vehicle[2];
		try {
			EnergySource[] eSources1 = new EnergySource[2];
			eSources1[0] = (EnergySource)new GasTank(60);
			eSources1[1] = new Battery(90);
			vehicles[0] = new Vehicle(eSources1, "main1");
			
			EnergySource[] eSources2 = new EnergySource[2];
			eSources2[0] = new GasTank(18);
			eSources2[1] = new Battery(98);
			vehicles[1] = new Vehicle(eSources2, "main2");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		Garage[] garages = new Garage[2];
		garages[0] = new Garage(vehicles);
		garages[1] = garages[0].clone();
		
		System.out.println("#1 General garage comparison");
		for(int i = 0; i < garages.length; i++) {
			System.out.println("Garage" + i + ": " + garages[i].vehicles.length + " vehicles");
			for (int j = 0; j < garages[i].vehicles.length; j++) {
				System.out.println("Vehicle No." + garages[i].vehicles[j].getId());
			}
		}
		
		System.out.println("");
		
		System.out.println("#2 Vehicle comparison");
		boolean sameVehicles = true;
		if (garages[0].vehicles.length != garages[1].vehicles.length) {
			System.out.println("Something wrong!");
			return;
		}
		for (int j = 0; j < garages[0].vehicles.length; j++) {
			Vehicle vehicleFromGarage0 = garages[0].vehicles[j];			
			Vehicle vehicleFromGarage1 = garages[1].vehicles[j];
			
			boolean sameSpeed = vehicleFromGarage0.getSpeed() == vehicleFromGarage1.getSpeed();
			boolean sameDirection = vehicleFromGarage0.getDirection() == vehicleFromGarage1.getDirection();
			boolean sameOwner = vehicleFromGarage0.getOwner().equals(vehicleFromGarage1.getOwner());
			boolean sameEnergySource = true;
			if (garages[0].vehicles[j].getEnergySources().length != garages[1].vehicles[j].getEnergySources().length) {
				sameEnergySource = false;
			}
			for (int k = 0; k < garages[0].vehicles[j].getEnergySources().length; k++) {
				if (!garages[0].vehicles[j].getEnergySources()[k].equals(garages[1].vehicles[j].getEnergySources()[k])) {
					sameEnergySource = false;
				}
			}
			if (!sameSpeed && sameDirection && sameOwner && sameEnergySource) {
				sameVehicles = false;
			}
			
		}
		System.out.println("All the vehicles in each garage are same: " + sameVehicles);
	}

}
