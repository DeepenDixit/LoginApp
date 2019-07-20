package application;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class MainController {

	public static int sessionId;
	
	@FXML
	public TextField uId;

	@FXML
	public PasswordField Pass;

	Connection connection = DBConnectivity.getConnection();

	@FXML
	public void loginButton(ActionEvent event) {
		try {
			String sql = "SELECT * FROM users WHERE id= ? AND pass= ? ";
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setInt(1, Integer.parseInt(uId.getText().toString()));
			ps.setString(2, Pass.getText().toString());

			ResultSet resultSet = ps.executeQuery();
			if (!resultSet.next()) 
			{
				infoBox("Please enter the correct password and id", null, "Fail");
			} 
			else 
			{
				sessionId=Integer.parseInt(uId.getText());
				infoBox("Wellcome " + resultSet.getString(2) + "!!", null, "Success");
				Stage primaryStage=new Stage();
				Scene scene;

				// To close the current window

				Node node= (Node)event.getSource();
				primaryStage=(Stage)node.getScene().getWindow(); 
				primaryStage.close();


				// To open the new fxml

				/*
				 * DetailController detailController=new DetailController();
				 * 
				 * detailController=(DetailController)new FXMLLoader().getController();
				 * System.out.println(detailController);
				 * 
				 * detailController.userDetail(sessionId);
				 */
				
				FXMLLoader loader=new FXMLLoader();
				Parent root=loader.load(getClass().getResource("Details.fxml").openStream());
				//Don't load file with class name(FXMLLoader), instead use instance of that class
				//so that, the object created by that fxmlloader will be the same as object received by loader.getController()
				//https://stackoverflow.com/a/36187345
				DetailController detailController=(DetailController)loader.getController();
				detailController.userDetail(sessionId);
				scene= new Scene(root);
				primaryStage.setScene(scene); 
				primaryStage.setTitle("Welcome");
				primaryStage.show();
				
			}
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}

	@FXML
	public void signupButton(ActionEvent event) {
		Stage primaryStage = new Stage();
		Scene scene;

		Node node = (Node) event.getSource();
		primaryStage = (Stage) node.getScene().getWindow();
		primaryStage.close();

		try {
			scene = new Scene(FXMLLoader.load(getClass().getResource("SignUp.fxml")));
			primaryStage.setTitle("Registration");
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (Exception e) {
		}

	}

	public void infoBox(String infoMessage, String headerText, String title) {
		Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
		alert.setContentText(infoMessage);
		alert.setTitle(title);
		alert.setHeaderText(headerText);
		alert.showAndWait();
	}
}
