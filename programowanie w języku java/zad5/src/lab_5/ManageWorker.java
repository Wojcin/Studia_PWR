package lab_5;

import java.util.Scanner;

public class ManageWorker {

	Connect oracle = new Connect();
	private int workerID;
	private int salary;
	private int personID;

	private String firstName;
	private String lastName;
	private int addressID;

	private String cityName;
	private String street;
	private int houseNumber;
	private int flatNumber;

	public void createWorker() {
		Scanner scan = new Scanner(System.in);
		Scanner scan2 = new Scanner(System.in);
		System.out.print("Wprowadz pensje pracownika: ");
		salary = scan.nextInt();
		System.out.print("Wprowadz imie pracownika: ");
		firstName = scan2.nextLine();
		System.out.print("Wprowadz nazwisko pracownika: ");
		lastName = scan2.nextLine();
		System.out.print("Wprowadz miasto: ");
		cityName = scan2.nextLine();
		System.out.print("Wprowadz ulice: ");
		street = scan2.nextLine();
		System.out.print("Wprowadz numer domu: ");
		houseNumber = scan.nextInt();
		System.out.print("Wprowadz numer mieszkania: ");
		flatNumber = scan.nextInt();
		workerID = MainMenu.getArrayListAddresses().size();
		personID = workerID;
		addressID = workerID;
		if (salary < 2500) {
			System.out.println("Pensja musi byc wieksza niz 2500 zl");
		} else if (firstName.equals("")) {
			System.out.println("Pole Imie nie moze byc puste");
		} else if (lastName.equals("")) {
			System.out.println("Pole Nazwisko nie moze byc puste");
		} else if (cityName.equals("")) {
			System.out.println("Pole Miasto nie moze byc puste");
		} else if (street.equals("")) {
			System.out.println("Pole Ulica nie moze byc puste");
		} else if (houseNumber < 0) {
			System.out.println("Pole Numer mieszkania nie moze byc ujemne");
		} else if (flatNumber <= 0) {
			System.out.println("Pole Numer mieszkania nie moze byc ujemne");
		}

		oracle.db_connect();
		oracle.db_createAddress(addressID, cityName, street, houseNumber, flatNumber);
		oracle.db_createPersonalData(personID, firstName, lastName, addressID);
		oracle.db_createWorker(workerID, salary, personID);
		oracle.db_disconnect();

		MainMenu.getArrayListAddresses().add(new Address(addressID, cityName, street, houseNumber, flatNumber));
		MainMenu.getArrayListPersonalData().add(new PersonalData(personID, firstName, lastName, addressID));
		MainMenu.getArrayListWorkers().add(new Worker(workerID, salary, personID));

	}

	public void updateWorker() {
		int choose;
		Scanner scan = new Scanner(System.in);
		Scanner scan2 = new Scanner(System.in);
		System.out.println("\n1. Zmien dane adresowe");
		System.out.println("2. Zmien dane osobowe");
		System.out.println("3. Zmien dane pracownika");
		System.out.print("Wybierz => ");
		choose = scan.nextInt();
		if (choose == 1) {
			System.out.print("Wprowadz ID adresu ");
			addressID = scan.nextInt();
			System.out.print("Wprowadz miasto: ");
			cityName = scan2.nextLine();
			System.out.print("Wprowadz ulice: ");
			street = scan2.nextLine();
			System.out.print("Wprowadz numer domu: ");
			houseNumber = scan.nextInt();
			System.out.print("Wprowadz numer mieszkania: ");
			flatNumber = scan.nextInt();

		}
		if (choose == 2) {
			System.out.print("Wprowadz ID osoby: ");
			personID = scan.nextInt();
			System.out.print("Wprowadz imie pracownika: ");
			firstName = scan2.nextLine();
			System.out.print("Wprowadz nazwisko pracownika: ");
			lastName = scan2.nextLine();
		}
		if (choose == 3) {
			System.out.print("Wprowadz ID pracownika: ");
			workerID = scan.nextInt();

			for (Worker w : MainMenu.arrayListWorkers) {
				if (w.getWorkerID() == workerID) {
					System.out.print("Wprowadz pensje pracownika: ");
					salary = scan.nextInt();
					if (salary < 2500) {
						System.out.println("Pensja musi byc wieksza niz 2500 zl");
					} else {
						oracle.db_connect();
						oracle.db_update_Worker(workerID, salary);
						MainMenu.arrayListWorkers = oracle.db_loadData_workers();
						oracle.db_disconnect();
					}

				} else {
					System.out.print("Nie znaleziono pracownika o podanym ID ");
				}
			}

		} else {
			System.out.println("Wybierz dostepna opcje");
		}

	}

}
