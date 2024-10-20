import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

public class GraphCanvas extends Canvas {

    public GraphCanvas(double width, double height) {
        super(width, height);
        drawAxes();
    }

    private void drawAxes() {
        GraphicsContext gc = getGraphicsContext2D();
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(2);
        double centerX = getWidth() / 2;
        double centerY = getHeight() / 2;
        gc.strokeLine(0, centerY, getWidth(), centerY);
        gc.strokeLine(centerX, 0, centerX, getHeight());
    }


    public void plotFunction(String function, double lower, double upper) {
        GraphicsContext gc = getGraphicsContext2D();
        gc.setStroke(Color.BLUE);
        gc.setLineWidth(2);
        Expression expr = new ExpressionBuilder(function).variable("x").build();
        double prevX = lower;
        double prevY = expr.setVariable("x", lower).evaluate();
        for (double x = lower; x <= upper; x += 0.01) {
            double y = expr.setVariable("x", x).evaluate();
            double scaledPrevX = getWidth() / 2 + prevX * 20;
            double scaledPrevY = getHeight() / 2 - prevY * 20;
            double scaledX = getWidth() / 2 + x * 20;
            double scaledY = getHeight() / 2 - y * 20;
            gc.strokeLine(scaledPrevX, scaledPrevY, scaledX, scaledY);
            prevX = x;
            prevY = y;
        }
    }

    public void clear() {
        GraphicsContext gc = getGraphicsContext2D();
        gc.clearRect(0, 0, getWidth(), getHeight());
        drawAxes();
    }
}
