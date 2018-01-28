package model;

public class ReturnInformation {
	private int returnMileage;
	private int pricePerDay;
	private int pricePerKm;
	private int bookingNumber;
	private String returnDate;

	public int getReturnMileage() {
		return returnMileage;
	}

	public void setReturnMileage(int returnMileage) {
		this.returnMileage = returnMileage;
	}

	public int getPricePerDay() {
		return pricePerDay;
	}

	public void setPricePerDay(int pricePerDay) {
		this.pricePerDay = pricePerDay;
	}

	public int getPricePerKm() {
		return pricePerKm;
	}

	public void setPricePerKm(int pricePerKm) {
		this.pricePerKm = pricePerKm;
	}

	public int getBookingNumber() {
		return bookingNumber;
	}

	public void setBookingNumber(int bookingNumber) {
		this.bookingNumber = bookingNumber;
	}

	public void setReturnDate(String returnDate) {
		this.returnDate = returnDate;

	}

	public String getReturnDate() {
		return this.returnDate;
	}
}
