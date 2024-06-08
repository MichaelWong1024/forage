import de.sfuhrm.YahooFinanceAPI.YahooFinance;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class App {

    private static final String DJIA_SYMBOL = "^DJI";
    private static final YahooFinance yahooFinance = new YahooFinance();
    private static final Queue<String> stockDataQueue = new LinkedBlockingQueue<>();

    public static void main(String[] args) {
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        
        Runnable stockRetrievalTask = () -> {
            try {
                double djiaPrice = yahooFinance.getStock(DJIA_SYMBOL).getQuote().getPrice();
                String timestamp = new java.text.SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(new java.util.Date());
                stockDataQueue.offer("Timestamp: " + timestamp + ", DJIA Price: " + djiaPrice);
                System.out.println("Data fetched and stored in queue.");
            } catch (Exception e) {
                System.err.println("Failed to retrieve stock data: " + e.getMessage());
            }
        };

        // Schedule the stock retrieval task to run every 5 seconds
        scheduler.scheduleAtFixedRate(stockRetrievalTask, 0, 5, TimeUnit.SECONDS);
    }
}

