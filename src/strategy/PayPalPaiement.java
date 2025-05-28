package strategy;

public class PayPalPaiement implements PaymentStrategy {
	@Override
	public void pay(double amount) {
		System.out.println("Paiement depuis paypal : " + amount + " €");
	}
}
