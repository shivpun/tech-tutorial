package com.example.fitnesse.fixture;

public class CreditsForPayment {
	public double payment;
	public int credits;

	public void setPayment(double payment) {
		this.payment = payment;
	}

	public void execute() {
		this.credits = (int) (payment / 2);
	}

	public int credits() {
		return credits;
	}
}
