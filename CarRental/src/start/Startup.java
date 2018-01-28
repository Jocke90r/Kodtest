package start;

import java.sql.SQLException;
import java.util.Scanner;

import integration.DbHandler;
import model.Booking;
import model.Rental;
import model.ReturnCar;
import model.ReturnInformation;

public class Startup {

	public static void main(String[] args) throws SQLException {
		Scanner sc = new Scanner(System.in);
		DbHandler connection = new DbHandler();

		Rental newRental = new Rental(connection);

		while (true) {
			System.out.println("Ange Siffran för vad du vill göra?" + "\n" + "1: Hyra bil" + "\n2: Återlämna bil"
					+ "\n3: Stäng av applikationen");

			while (!sc.hasNextInt()) {
				System.out.println("Ange en av de tre siffrorna!");
				sc.nextLine();
			}

			int val = sc.nextInt();
			if (val == 1) {
				sc.nextLine(); // waste"Handler"
				newRental.startRent();

				System.out.println("Ange de 6 siffrorna i registreringsnumret på den valda bilen");
				String regNr = sc.nextLine();
				while (regNr.length() > 6 || regNr.length() < 6) {
					System.out.println("Fel antal tecken, ange de 6 tecknena i registreringsnumret: ");
					regNr = sc.nextLine();
				}

				System.out.println("Ange kundens 10 siffriga personnummer: ");
				String personalNumber = sc.nextLine();
				if (personalNumber.length() > 10 || personalNumber.length() < 10) {
					System.out.println("Fel antal siffror, ange kundens 10 siffriga personnummer utan bindestreck: ");
					personalNumber = sc.nextLine();
				}

				Booking newBooking = new Booking(regNr, personalNumber);
				newRental.finishBookingInfo(newBooking);
				System.out.println("Då är din bil med registreringsnummer " + regNr + " bokad ");
			} else if (val == 2) {
				ReturnInformation returnInformation = new ReturnInformation();
				sc.nextLine();// waste"Handler"
				System.out.println("Ange bokningsnumret: ");
				returnInformation.setBookingNumber(sc.nextInt());
				System.out.println("Ange nuvarande Mätarställning: ");
				returnInformation.setReturnMileage(sc.nextInt());
				System.out.println("Ange dygnspriset: ");
				returnInformation.setPricePerDay(sc.nextInt());
				System.out.println("Ange kilometerpriset: ");
				returnInformation.setPricePerKm(sc.nextInt());
				ReturnCar returnCar = new ReturnCar(connection);
				double cost = returnCar.startReturnBooking(returnInformation);
				System.out.println("Den totala kostnaden är " + cost);
			} else if (val == 3) {
				System.out.println("Applikationen stängs ner");
				sc.close();
				break;
			}

		}

	}

}
