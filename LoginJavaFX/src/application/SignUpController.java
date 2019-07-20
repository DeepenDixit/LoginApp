package application;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.Random;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class SignUpController {

	@FXML
	public TextField id;
	
	@FXML
	public TextField name;
	
	@FXML
	public PasswordField pass;
	
	@FXML
	public PasswordField pass1;
	
	@FXML
	public DatePicker dob;
	
	@FXML
	public void signUpButton(ActionEvent event)
	{
		try
		{
			String nm=name.getText();
			String pswd=pass.getText();
			String pswd1=pass1.getText();
			LocalDate dt=dob.getValue();
			String idt=id.getText();
			
			if(!nm.equals("") && !pswd.equals("") && !pswd1.equals("") && !dt.equals(null) && !idt.equals(null))
			{
				if(pswd.equals(pswd1))
				{
					Date date=Date.valueOf(dob.getValue());
					Connection connection=DBConnectivity.getConnection();
					String sql="INSERT INTO users VALUES(?,?,?,?)";
					PreparedStatement ps=connection.prepareStatement(sql);
					ps.setInt(1, Integer.parseInt(id.getText()));
					ps.setString(2,nm);
					ps.setString(3,pswd);
					ps.setDate(4,date);
					
					int p=ps.executeUpdate();
					if(p!=0)
					{
						alertInfo("Please remember your id :- "+id.getText(),"Welcome "+nm,"Welcome!!!!");
						Node node=(Node)event.getSource();
						Stage primaryStage=new Stage();
						primaryStage=(Stage)node.getScene().getWindow();
						primaryStage.close();
						Scene scene=new Scene(FXMLLoader.load(getClass().getResource("Main.fxml")));
						primaryStage.setTitle("Login App");
						primaryStage.setScene(scene);
						primaryStage.show();
					}
				}
				else
				{
					alertInfo("Please check password", null,"Paassword error");
				}
			}
			else
			{
					alertInfo("Please Enter all the values",null,"Please check");
			}
		}
		catch(Exception e)
		{
			alertInfo("Something went wrong", null,"ooooops!!!");
		}
	}
	
	public void alertInfo(String contentText,String headerText,String title)
	{
		Alert alert=new Alert(Alert.AlertType.CONFIRMATION);
		alert.setContentText(contentText);
		alert.setHeaderText(headerText);
		alert.setTitle(title);
		alert.showAndWait();
	}
	
	@FXML
	public void resetButton(ActionEvent event)
	{
		name.setText("");
		pass.setText("");
		pass1.setText("");
		dob.setValue(null);
	}
	
	@FXML
	public void refresh(ActionEvent event)
	{
		int id1=idGenerater();
		id.setText(String.valueOf(id1));
	}
	public int idGenerater()
	{
		Random rand=new Random();
		int id=rand.nextInt(10000);
		if(checkIdInDB(id))
		{
			return id;
		}
		else
		{
			return idGenerater();
		}
	}
	
	public boolean checkIdInDB(int id)
	{
		try
		{
			Connection connection=DBConnectivity.getConnection();
			String sql="SELECT * FROM users WHERE id = ?";
			PreparedStatement ps=connection.prepareStatement(sql);
			ps.setInt(1,id);
			ResultSet rs=ps.executeQuery();
			if(rs.next())
			{
				return false;
			}
			else
			{
				return true;
			}
		}
		catch(Exception e) 
		{
			return false;
		}
	}
}