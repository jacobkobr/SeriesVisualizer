import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Cylinder;
import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;
import java.util.List;

public class Surface3D extends Group {

    public Surface3D(List<Main.FunctionBounds> functions, String axis) {
        for (Main.FunctionBounds fb : functions) {
            for (double x = fb.lower; x < fb.upper; x += 0.1) {
                double radius = evaluateFunction(fb.function, x);
                Cylinder segment = new Cylinder(radius * 10, 1);
                segment.setMaterial(new PhongMaterial(Color.GREEN));

                if (axis.equals("x-axis")) {
                    segment.setTranslateX(x * 10);
                    segment.setRotate(90);
                } else {
                    segment.setTranslateY(x * 10);
                }

                this.getChildren().add(segment);
            }
        }
    }

    private double evaluateFunction(String function, double x) {
        Expression expr = new ExpressionBuilder(function).variable("x").build();
        return expr.setVariable("x", x).evaluate();
    }
}