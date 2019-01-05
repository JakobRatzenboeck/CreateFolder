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
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Tooltip;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class ConfigureDateMode extends Application {

	int height;
	int width;
	String path;

	public AnchorPane root;
	public GridPane context;
	private Scene scene;
	private Label header;

	// Boundaries
	public Text from;
	public DatePicker fromDate;
	public Text to;
	public DatePicker toDate;

	// Interval
	public Text interval;
	public ChoiceBox<String> whatly = new ChoiceBox<>(
			FXCollections.observableArrayList("Dayly", "Weekly", "Monthly", "Yearly"));
	public TextField iterating;

	public RadioButton rbo;
	public RadioButton rbs;
	public ToggleGroup tbg;

	// Progress Changer
	public Button next;
	public Button last;

	public ConfigureDateMode() {
		this.height = 350;
		this.width = 330;

	}

	public ConfigureDateMode(int height, int width, String path) {
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
			header = new Label("Configure Datemode");
			from = new Text("From: ");
			fromDate = new DatePicker(LocalDate.now());
			to = new Text("To: ");
			toDate = new DatePicker(LocalDate.now().plusWeeks(1));

			interval = new Text("How often: ");
			iterating = new TextField("1");
			whatly.setValue("Dayly");
			whatly.setTooltip(new Tooltip("Select the span it should be created in."));
			iterating.setPrefWidth(1);
			iterating.setTooltip(new Tooltip(
					"Select the interval for " + whatly.getValue().toString()
							+ ".\nE.g.: "+whatly.getValue().toString()+": 1, means every "
							+ whatly.getValue().toString().substring(0, whatly.getValue().length()-2)
							+ ";\n        "+whatly.getValue().toString()+": 2, means every second "
							+ whatly.getValue().toString().substring(0, whatly.getValue().length()-2)));

			rbo = new RadioButton("JJJJ.MM.DD");
			rbo.setSelected(true);
			rbs = new RadioButton("JJ.MM.DD");
			tbg = new ToggleGroup();

			last = new Button("<-- Last Step");
			
			next = new Button("Create Folders -->");

			whatly.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {

				@Override
				public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
					iterating.setTooltip(new Tooltip(
							"Select the interval for " + whatly.getValue().toString()
									+ ".\nE.g.: "+whatly.getValue().toString()+": 1, means every "
									+ whatly.getValue().toString().substring(0, whatly.getValue().length()-2)
									+ ";\n        "+whatly.getValue().toString()+": 2, means every second "
									+ whatly.getValue().toString().substring(0, whatly.getValue().length()-2)));

				}
			});

			// force the field to be numeric only
			iterating.textProperty().addListener(new ChangeListener<String>() {
				@Override
				public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
					if (!newValue.matches("\\d*")) {
						iterating.setText(newValue.replaceAll("[^\\d]", ""));
					}
				}
			});

			next.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent event) {
					if(fromDate.getValue().isBefore(toDate.getValue())) {
					System.out.flush();
					FolderManipulation fm = new FolderManipulation();
					fm.createDirectory(path, fm.dateFolderNames(rbo.isSelected(), fromDate.getValue(), toDate.getValue(), (whatly.getItems().indexOf(whatly.getValue())+1), Integer.parseInt(iterating.getText())));
					String[] strings = fm.dateFolderNames(rbo.isSelected(), fromDate.getValue(), toDate.getValue(), (whatly.getItems().indexOf(whatly.getValue())+1), Integer.parseInt(iterating.getText()));
					fm.openDirectory(path+strings[0]);
					} else {
						Alert alert = new Alert(AlertType.INFORMATION);
						alert.setTitle("Don't know what's wrong?");
						alert.setHeaderText("The 'from' Date must be before the 'to' Date.");
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
			context.setMargin(fromDate, new Insets(5.0, 0, 0, 10.0));
			context.setMargin(to, new Insets(5.0, 0, 0, 0));
			context.setMargin(toDate, new Insets(5.0, 0, 0, 10.0));
			context.setMargin(interval, new Insets(5.0, 0, 0, 0));
			context.setMargin(whatly, new Insets(5.0, 0, 0, 10.0));
			context.setMargin(iterating, new Insets(5.0, 0, 0, 90.0));
			context.add(header, 0, 0, 2, 1);
			context.add(rbo, 0, 1);
			context.add(rbs, 1, 1);
			context.add(from, 0, 2);
			context.add(fromDate, 1, 2);
			context.add(to, 0, 3);
			context.add(toDate, 1, 3);
			context.add(interval, 0, 4);
			context.add(whatly, 1, 4);
			context.add(iterating, 1, 4);

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
