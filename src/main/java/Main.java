import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import java.util.ArrayList;
import java.util.List;

public class Main extends Application {

    private List<FunctionBounds> functions = new ArrayList<>();
    private Label arcLengthLabel = new Label("Arc Length: N/A");
    private ListView<String> functionListView = new ListView<>();

    @Override
    public void start(Stage primaryStage) {
        BorderPane root = new BorderPane();
        Scene scene = new Scene(root, 1000, 600);

        ToolBar toolBar = new ToolBar();
        TextField functionInput = new TextField("x^2");
        TextField lowerBound = new TextField("0");
        TextField upperBound = new TextField("5");
        Button addButton = new Button("Add Function");
        Button plotButton = new Button("Plot");
        ToggleButton toggle3D = new ToggleButton("3D Mode");
        ComboBox<String> axisSelector = new ComboBox<>(FXCollections.observableArrayList("x-axis", "y-axis"));
        axisSelector.setValue("x-axis");

        toolBar.getItems().addAll(
                new Label("Function: "), functionInput,
                new Label("Lower: "), lowerBound,
                new Label("Upper: "), upperBound,
                addButton, plotButton, toggle3D, axisSelector
        );

        addButton.setOnAction(e -> {
            String function = functionInput.getText();
            double lower = Double.parseDouble(lowerBound.getText());
            double upper = Double.parseDouble(upperBound.getText());
            functions.add(new FunctionBounds(function, lower, upper));
            functionListView.getItems().add(function + " [" + lower + ", " + upper + "]");
        });

        GraphCanvas canvas = new GraphCanvas(800, 400);

        plotButton.setOnAction(e -> {
            if (toggle3D.isSelected()) {
                root.setCenter(new Surface3D(functions, axisSelector.getValue()));
            } else {
                canvas.clear();
                for (FunctionBounds fb : functions) {
                    canvas.plotFunction(fb.function, fb.lower, fb.upper);
                }
                root.setCenter(canvas);
            }
            double totalArcLength = ArcLengthCalculator.computeTotalArcLength(functions);
            arcLengthLabel.setText("Arc Length: " + totalArcLength);
        });

        BorderPane topPanel = new BorderPane();
        topPanel.setTop(toolBar);
        topPanel.setBottom(arcLengthLabel);

        root.setTop(topPanel);
        root.setCenter(canvas);
        root.setRight(functionListView);
        functionListView.setPrefWidth(200);

        primaryStage.setTitle("SeriesVisualizer");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    static class FunctionBounds {
        String function;
        double lower, upper;

        FunctionBounds(String function, double lower, double upper) {
            this.function = function;
            this.lower = lower;
            this.upper = upper;
        }
    }
}
