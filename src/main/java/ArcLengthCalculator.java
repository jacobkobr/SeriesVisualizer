import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

import java.util.List;

public class ArcLengthCalculator {

    public static double computeTotalArcLength(List<Main.FunctionBounds> functions) {
        double totalLength = 0;
        for (Main.FunctionBounds fb : functions) {
            totalLength += computeArcLength(fb.function, fb.lower, fb.upper);
        }
        return totalLength;
    }

    private static double computeArcLength(String function, double lower, double upper) {
        Expression expr = new ExpressionBuilder(function).variable("x").build();
        double totalLength = 0;
        double step = 0.01;

        for (double x = lower; x < upper; x += step) {
            double yAtX = expr.setVariable("x", x).evaluate();
            double yAtNextX = expr.setVariable("x", x + step).evaluate();
            double dy = yAtNextX - yAtX;
            double segmentLength = Math.sqrt(step * step + dy * dy);
            totalLength += segmentLength;
        }

        return totalLength;
    }
}

