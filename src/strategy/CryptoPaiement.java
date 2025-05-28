package strategy;

public class CryptoPaiement implements PaymentStrategy {
	@Override
	public void pay(double amount) {
		System.out.println("Paiement en cryptomonnaie : " + amount + " btc");
	}
}
