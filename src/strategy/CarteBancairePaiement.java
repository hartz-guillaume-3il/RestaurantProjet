package strategy;

public class CarteBancairePaiement implements PaymentStrategy {
    @Override
    public void pay(double amount) {
        System.out.println("Paiement par carte bancaire : " + amount + " €");
    }
}
