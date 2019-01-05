package GUI;

import java.io.File;

import javax.management.Notification;

import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils.Text;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;

public class MainFrame extends Application {

	int height;
	int width;
	String path = "C:\\Benutzer\\";

	public AnchorPane root;
	public GridPane context;
	private Scene scene;
	private Label header;
	public TextField pathtext;
	public RadioButton rbo;
	public RadioButton rbs;
	public RadioButton rbt;
	public Button next;
	public ToggleGroup tbg;

	public MainFrame() {
		this.height = 350;
		this.width = 330;
	}

	public MainFrame(int height, int width, String path) {
		this.height = height;
		this.width = width;
		this.path = path; 
	}

	@Override
	public void start(Stage primaryStage) {
		try {
			root = new AnchorPane();
			context = new GridPane();
			scene = new Scene(root, width, height);
			header = new Label("Select Path and Choice");
			pathtext = new TextField(path);
			rbo = new RadioButton("Date (JJJJ.MM.DD)");
			rbs = new RadioButton("Number Desc.");
			rbt = new RadioButton("Custom");
			next = new Button("Next Step -->");
			tbg = new ToggleGroup();

			pathtext.setOnMouseClicked(new EventHandler<Event>() {

				@Override
				public void handle(Event event) {
					DirectoryChooser directoryChooser = new DirectoryChooser();
					File selectedDirectory = directoryChooser.showDialog(new Stage());

					if (selectedDirectory == null) {
						// No Directory selected
					} else {
						if(selectedDirectory.getAbsolutePath().endsWith("\\")) {
							pathtext.setText(selectedDirectory.getAbsolutePath());
						} else {
						pathtext.setText(selectedDirectory.getAbsolutePath() + "\\");
						}
					}
				}

			});

			
			
			next.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent event) {
					if (rbo.isSelected()) {
						ConfigureDateMode configure = new ConfigureDateMode(height, width, pathtext.getText());
						configure.start(primaryStage);
					} else if(rbs.isSelected()) {
						ConfigureNumberMode configure = new ConfigureNumberMode(height, width, pathtext.getText());
						configure.start(primaryStage);
					} else if(rbt.isSelected()) {
						
					} else {
						Alert alert = new Alert(AlertType.INFORMATION);
						alert.setTitle("Don't know what to choose?");
						alert.setHeaderText("Just select one of these four options to name and create a group of Folders.");
						alert.setContentText("Also maybe doublecheck the directory.");
						alert.showAndWait();
					}
				}
			});

			rbo.setToggleGroup(tbg);
			rbs.setToggleGroup(tbg);
			rbt.setToggleGroup(tbg);

			header.setFont(new Font("Arial", 25));

			context.setMargin(pathtext, new Insets(5.0, 0, 0, 0));
			context.setMargin(rbo, new Insets(5.0, 0, 0, 0));
			context.setMargin(rbs, new Insets(3.0, 0, 0, 0));
			context.setMargin(rbt, new Insets(3.0, 0, 0, 0));
			context.add(header, 0, 0);
			context.add(pathtext, 0, 1);
			context.add(rbo, 0, 2);
			context.add(rbs, 0, 3);
			context.add(rbt, 0, 4);

			root.getChildren().add(context);
			root.getChildren().add(next);
			root.setLeftAnchor(context, 10.0);
			root.setTopAnchor(context, 7.0);
			root.setRightAnchor(context, 10.0);
			root.setBottomAnchor(next, 7.0);
			root.setRightAnchor(next, 10.0);

			primaryStage.setMinWidth(330);
			primaryStage.setMinHeight(202);
			primaryStage.centerOnScreen();
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}

}
