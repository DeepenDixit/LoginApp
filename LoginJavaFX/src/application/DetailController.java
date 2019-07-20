package application;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class DetailController implements Initializable{

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
	}
	
	@FXML
	public TextField tf;
	
	@FXML
	public Label label1;
	
	@FXML
	public Label label2;
	
	public void userDetail(int sessionid)
	{
		try
		{
			Connection connection=DBConnectivity.getConnection();
			String sql="SELECT * FROM users WHERE id = ?";
			PreparedStatement ps=connection.prepareStatement(sql);
			ps.setInt(1,sessionid);
			
			ResultSet rs=ps.executeQuery();
			
			while(rs.next())
			{
				label1.setText(label1.getText()+" "+rs.getString(2));
				label2.setText(label2.getText()+" "+rs.getDate(4));
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	@FXML
	public void logOut(ActionEvent event)
	{
		Node node=(Node)event.getSource();
		Stage primaryStage=(Stage)node.getScene().getWindow();
		primaryStage.close();
	}
}