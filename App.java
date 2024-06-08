package citi;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;

public class App extends Application {

    private static final String DJIA_SYMBOL = "^DJI";
    private static final Queue<XYChart.Data<Number, Number>> stockDataQueue = new LinkedList<>();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        NumberAxis xAxis = new NumberAxis();
        NumberAxis yAxis = new NumberAxis();
        LineChart<Number, Number> lineChart = new LineChart<>(xAxis, yAxis);
        XYChart.Series<Number, Number> series = new XYChart.Series<>();
        lineChart.getData().add(series);

        stage.setScene(new Scene(lineChart, 800, 600));
        stage.show();

        new Thread(() -> {
            while (true) {
                try {
                    Stock stock = YahooFinance.get(DJIA_SYMBOL);
                    BigDecimal price = stock.getQuote().getPrice();
                    long timestamp = System.currentTimeMillis();
                    XYChart.Data<Number, Number> dataPoint = new XYChart.Data<>(timestamp, price.doubleValue());
                    stockDataQueue.add(dataPoint);
                    series.getData().add(dataPoint);
                    Thread.sleep(5000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}

