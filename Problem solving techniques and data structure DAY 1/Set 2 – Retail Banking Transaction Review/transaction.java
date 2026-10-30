public class Transaction {
    int id;
    String account;
    double amount;
    String merchant;
    String date;

    Transaction(int id, String account, double amount,
                String merchant, String date) {
        this.id = id;
        this.account = account;
        this.amount = amount;
        this.merchant = merchant;
        this.date = date;
    }

    public String toString() {
        return id + " | " + account + " | " +
               amount + " | " + merchant + " | " + date;
    }
}
