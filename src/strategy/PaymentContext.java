package strategy;

public class PaymentContext {
    private PaymentStrategy strategy;

    public void setStrategy(PaymentStrategy strategy) {
        this.strategy = strategy;
    }

    public void processPayment(double amount) {
        if (strategy == null) {
            System.out.println("Aucune méthode de paiement sélectionnée !");
        } else {
            strategy.pay(amount);
        }
    }
}
