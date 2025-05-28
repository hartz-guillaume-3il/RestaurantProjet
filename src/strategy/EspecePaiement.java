package strategy;

public class EspecePaiement implements PaymentStrategy {
    @Override
    public void pay(double amount) {
        System.out.println("Paiement en espèces : " + amount + " €");
    }
}
