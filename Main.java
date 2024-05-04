import java.util.ArrayList;
import java.util.List;

interface Subject {
    void registerInvestor(Investor investor);
    void unregisterInvestor(Investor investor);
    void notifyInvestors();
}

class Stock implements Subject {
    private String symbol;
    private double price;
    private List<Investor> investors;

    public Stock(String symbol, double price) {
        this.symbol = symbol;
        this.price = price;
        this.investors = new ArrayList<>();
    }

    public void updatePrice(double price) {
        this.price = price;
        notifyInvestors();
    }

    public void registerInvestor(Investor investor) {
        investors.add(investor);
    }

    public void unregisterInvestor(Investor investor) {
        investors.remove(investor);
    }

    public void notifyInvestors() {
        for (Investor investor : investors) {
            investor.update(this, price);
        }
    }

    public String getSymbol() {
        return symbol;
    }

    public double getPrice() {
        return price;
    }
}

interface Observer {
    void update(Stock stock, double price);
}

class Investor implements Observer {
    private String name;
    private List<Stock> stocks;

    public Investor(String name) {
        this.name = name;
        this.stocks = new ArrayList<>();
    }

    public void update(Stock stock, double price) {
        System.out.println(name + " received update for " + stock.getSymbol() + ": $" + price);
    }

    public void invest(Stock stock) {
        stock.registerInvestor(this);
        stocks.add(stock);
    }

    public void divest(Stock stock) {
        stock.unregisterInvestor(this);
        stocks.remove(stock);
    }
}

public class Main {
    public static void main(String[] args) {
        Stock appleStock = new Stock("AAPL", 150.0);
        Stock googleStock = new Stock("GOOGL", 2500.0);

        Investor investor1 = new Investor("John");
        Investor investor2 = new Investor("Alice");

        investor1.invest(appleStock);
        investor2.invest(googleStock);

        appleStock.updatePrice(160.0);
        googleStock.updatePrice(2600.0);

        investor1.divest(appleStock);

        appleStock.updatePrice(170.0);
    }
}
