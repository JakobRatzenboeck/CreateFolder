package GUI;

import java.time.LocalDate;

import Model.FolderManipulation;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Tooltip;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class ConfigureNumberMode extends Application {

	int height;
	int width;
	String path;

	public AnchorPane root;
	public GridPane context;
	private Scene scene;
	private Label header;

	public RadioButton rbo;
	public RadioButton rbs;
	public ToggleGroup tbg;

	// Boundaries
	public Text from;
	public Spinner<Integer> fromNumber;
	public Text to;
	public Spinner<Integer> toNumber;

	public TextField description;

	// Progress Changer
	public Button next;
	public Button last;

	public ConfigureNumberMode() {
		this.height = 350;
		this.width = 330;

	}

	public ConfigureNumberMode(int height, int width, String path) {
		this.height = height;
		this.width = width;
		this.path = path;
	}

	@Override
	public void start(Stage arg0) {
		try {
			root = new AnchorPane();
			context = new GridPane();
			scene = new Scene(root, width, height);
			header = new Label("Configure NumberMode");
			from = new Text("From: ");
			fromNumber = new Spinner<Integer>(0, 1000, 0, 1);
			to = new Text("To: ");
			toNumber = new Spinner<Integer>(1, 1000, 1, 1);
			description = new TextField("");
			description.setVisible(false);
			rbo = new RadioButton("Number");
			rbo.setTooltip(new Tooltip("1\n2\n3\n4\n5\n6\n7\n8"));
			rbo.setSelected(true);
			rbs = new RadioButton("Number Description");
			rbs.setTooltip(new Tooltip("1 Math\n2 Math\n3 Math\n4 Math\n5 Math\n6 Math\n7 Math\n8 Math"));
			tbg = new ToggleGroup();
			last = new Button("<-- Last Step");
			next = new Button("Create Folders -->");

			rbo.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent event) {
					description.setVisible(false);
					description.setText("");
				}
			});

			rbs.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent event) {
					description.setText("Description");
					description.setVisible(true);
				}
			});

			next.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent event) {
					System.out.println(fromNumber.getValue());
					System.out.println(toNumber.getValue());
					System.out.println(description.getText());
					if(fromNumber.getValue() < toNumber.getValue() && ((description.isVisible() && !description.getText().isEmpty())|| (!description.isVisible()&&description.getText().isEmpty()))) {
					FolderManipulation fm = new FolderManipulation();
					if(!description.getText().isEmpty()) {
						description.setText(" " + description.getText());
					}
					fm.createDirectory(path, fm.NumberFolderNames(fromNumber.getValue(), toNumber.getValue(), description.getText()));
					fm.openDirectory(path+fm.NumberFolderNames(fromNumber.getValue(), toNumber.getValue(), description.getText())[0]);
					} else {
						Alert alert = new Alert(AlertType.INFORMATION);
						alert.setTitle("Don't know what's wrong?");
						alert.setHeaderText("The to Number must be greater than the from Number! ");
						alert.setContentText("Please select other bounderies. ");
						if(description.isVisible() && description.getText().isEmpty()) {
						alert.setHeaderText(alert.getHeaderText()+"The description must not be empty! ");
						alert.setContentText(alert.getContentText()+"Please enter some text or select without description. ");
						}
						alert.showAndWait();
					}
				}
			});
			
			last.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent event) {
					MainFrame mainFrame = new MainFrame(height, width, path);
					mainFrame.start(arg0);
				}
			});

			rbo.setToggleGroup(tbg);
			rbs.setToggleGroup(tbg);

			header.setFont(new Font("Arial", 25));

			context.setMargin(rbo, new Insets(5.0, 0, 0, 0));
			context.setMargin(rbs, new Insets(3.0, 0, 0, 10.0));
			context.setMargin(from, new Insets(5.0, 0, 0, 0));
			context.setMargin(fromNumber, new Insets(5.0, 0, 0, 10.0));
			context.setMargin(to, new Insets(5.0, 0, 0, 0));
			context.setMargin(toNumber, new Insets(5.0, 0, 0, 10.0));
			context.setMargin(description, new Insets(5.0, 0, 0, 0));
			context.add(header, 0, 0, 2, 1);
			context.add(rbo, 0, 1);
			context.add(rbs, 1, 1);
			context.add(from, 0, 2);
			context.add(fromNumber, 1, 2);
			context.add(to, 0, 3);
			context.add(toNumber, 1, 3);
			context.add(description, 0, 4, 2, 1);

			root.getChildren().add(context);
			root.getChildren().add(next);
			root.getChildren().add(last);
			root.setLeftAnchor(context, 10.0);
			root.setTopAnchor(context, 7.0);
			root.setRightAnchor(context, 10.0);
			root.setBottomAnchor(last, 7.0);
			root.setBottomAnchor(next, 7.0);
			root.setLeftAnchor(last, 10.0);
			root.setRightAnchor(next, 10.0);

			arg0.setMinWidth(330);
			arg0.setMinHeight(252);
			arg0.centerOnScreen();
			arg0.setScene(scene);
			arg0.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
