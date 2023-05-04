import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Callback;

public class lab3 extends Application {
	private TableView tableview;
	private Button insertbt = new Button("insert");
	private Button searchbt = new Button("search");
	
	private ObservableList<ObservableList> data;

	public static void main(String[] args) {
		launch(args);
	}

	@Override

	public void start(Stage primaryStage) throws Exception {
		Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
	alert1.setTitle("added");
	alert1.setHeaderText("congrats: Successfully added!");
	
		TabPane tabPane = new TabPane();
		Tab tab0 = new Tab("about me");
		tab0.setClosable(false);
		Tab tab = new Tab("student");
		tab.setClosable(false);
		Tab tab2 = new Tab("advisor");
		tab2.setClosable(false);
		Tab tab3 = new Tab("classroom");
		tab3.setClosable(false);
		Tab tab4 = new Tab("course");
		tab4.setClosable(false);
		Tab tab5 = new Tab("department");
		tab5.setClosable(false);
		Tab tab6 = new Tab("instructor");
		tab6.setClosable(false);
		Tab tab7 = new Tab("prereq");
		tab7.setClosable(false);
		Tab tab8 = new Tab("section");
		tab8.setClosable(false);
		Tab tab9 = new Tab("takes");
		tab9.setClosable(false);
		Tab tab10 = new Tab("teaches");
		tab10.setClosable(false);
		Tab tab11 = new Tab("timeslot");
		tab11.setClosable(false);
//-------------------------------aboutme----------------------------------
		GridPane pane0 = new GridPane();
		pane0.setAlignment(Pos.CENTER);
		pane0.setPadding(new Insets(11.5, 12.5, 13.5, 14.5));
		pane0.setHgap(5.5);
		pane0.setVgap(5.5);
		pane0.add(new Label("ID:202006721 "), 2, 1);
		pane0.add(new Label("Name: RamiAbuayyash "), 2, 2);
		pane0.add(new Label("yep that's me finishing this  project "), 2, 3);
		pane0.add(new Label("and thats me facebook page "), 2, 4);
		
		Image img = new Image("Screenshot(75).png");
		
        ImageView imgView = new ImageView(img);
        imgView.setFitHeight(400);
        imgView.setFitWidth(600);
        pane0.add(imgView,2,7);
//----------------------------------------------------students------------------------------------------
		GridPane pane = new GridPane();
		pane.setAlignment(Pos.CENTER);
		pane.setPadding(new Insets(11.5, 12.5, 13.5, 14.5));
		pane.setHgap(5.5);
		pane.setVgap(5.5);

// Create a combo box
		ComboBox<String> cbo = new ComboBox<>();
		cbo.setPromptText("department name");
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/lab7", "root", "");
			Statement stmt = (Statement) con.createStatement();
			String query1 = "SELECT DISTINCT dept_name from department";
			ResultSet rs = stmt.executeQuery(query1);

			while (rs.next()) {
				cbo.getItems().addAll(rs.getString(1));
			}

			pane.add(new Label("ID: "), 1, 1);
			TextField ID = new TextField();
			pane.add(ID, 1, 2);

			pane.add(new Label("Name: "), 2, 1);
			TextField name = new TextField();
			pane.add(name, 2, 2);

			pane.add(new Label("tot-cred"), 3, 1);
			TextField cred = new TextField();
			pane.add(cred, 3, 2);

			pane.add(cbo, 2, 0);
			pane.add(insertbt, 1, 5);
			pane.add(searchbt, 2, 5);

// Set properties for UI

// Process events
			insertbt.setOnAction(new EventHandler<ActionEvent>() {
				public void handle(ActionEvent e) {
					try {
						Statement stmt = (Statement) con.createStatement();
						String query1 = "INSERT INTO student (ID,name,dept_name,tot_cred) VALUES" + "('" + ID.getText()
								+ "','" + name.getText() + "','" + cbo.getSelectionModel().getSelectedItem() + "','"
								+ cred.getText() + "')";
						stmt.executeUpdate(query1);
						cbo.getSelectionModel().clearSelection();
					
					} catch (SQLException e2) {
						e2.printStackTrace();

						if (e2.getErrorCode() == 1062) {

							System.out.println("there is duplication ");
							Alert alert = new Alert(Alert.AlertType.ERROR);
							alert.setTitle("Error");
							alert.setHeaderText("An error occurred");
							alert.setContentText("It is already existed.");

							alert.showAndWait();

						}

						if (e2.getErrorCode() == 1452) {

							System.out.println(
									"There is no (ID,name,dept_name,tot_cred) in  `student` table.");
							Alert alert = new Alert(Alert.AlertType.ERROR);
							alert.setTitle("Error");
							alert.setHeaderText("An error occurred");
							alert.setContentText(
									"There is no (ID,name,dept_name,tot_cred) in  `student` table.");

							alert.showAndWait();

						}
					}
				}
			});

			searchbt.setOnAction(new EventHandler<ActionEvent>() {
				public void handle(ActionEvent e) {

					String query = "SELECT * from student";

					if (!ID.getText().isEmpty() && !name.getText().isEmpty() && !cred.getText().isEmpty()
							&& !cbo.getSelectionModel().isEmpty()) {
						query = "select * from student where ID ='" + ID.getText() + " ' and name ='" + name.getText()
								+ " ' and tot_cred='" + cred.getText() + " ' and dept_name='"
								+ cbo.getSelectionModel().getSelectedItem() + " '";

					} else if (!ID.getText().isEmpty() && !name.getText().isEmpty()
							&& !cbo.getSelectionModel().isEmpty()) {
						query = "select * from student where ID ='" + ID.getText() + " ' and name ='" + name.getText()
								+ " ' and dept_name='" + cbo.getSelectionModel().getSelectedItem() + " '";

					} else if (!cred.getText().isEmpty() && !cbo.getSelectionModel().isEmpty()) {
						query = "select * from student where tot_cred='" + cred.getText() + " ' and dept_name='"
								+ cbo.getSelectionModel().getSelectedItem() + " '";

					} else if (!ID.getText().isEmpty() && !cbo.getSelectionModel().isEmpty()) {
						query = "select * from student where ID ='" + ID.getText() + " ' and dept_name='"
								+ cbo.getSelectionModel().getSelectedItem() + " '";

					} else if (!name.getText().isEmpty() && !cred.getText().isEmpty()
							&& !cbo.getSelectionModel().isEmpty()) {
						query = "select * from student where name ='" + name.getText() + " ' and tot_cred='"
								+ cred.getText() + " ' and dept_name='" + cbo.getSelectionModel().getSelectedItem()
								+ " '";

					} else if (!name.getText().isEmpty() && !cbo.getSelectionModel().isEmpty()) {
						query = "select * from student where name ='" + name.getText() + " ' and dept_name='"
								+ cbo.getSelectionModel().getSelectedItem() + " '";

					} else if (!ID.getText().isEmpty() && !cred.getText().isEmpty()
							&& !cbo.getSelectionModel().isEmpty()) {
						query = "select * from student where ID ='" + ID.getText() + " ' and tot_cred='"
								+ cred.getText() + " ' and dept_name='" + cbo.getSelectionModel().getSelectedItem()
								+ " '";
					} else if (!ID.getText().isEmpty() && !name.getText().isEmpty() && !cred.getText().isEmpty()) {
						query = "select * from student where ID ='" + ID.getText() + " ' AND name ='" + name.getText()
								+ " ' AND tot_cred='" + cred.getText() + "'";

					} else if (!ID.getText().isEmpty() && !name.getText().isEmpty()) {
						query = "select * from student where ID ='" + ID.getText() + " ' AND name ='" + name.getText()
								+ " '";

					} else if (!ID.getText().isEmpty() && !cred.getText().isEmpty()) {
						query = "select * from student where ID ='" + ID.getText() + " ' AND tot_cred='"
								+ cred.getText() + "'";

					} else if (!name.getText().isEmpty() && !cred.getText().isEmpty()) {
						query = "select * from student where name ='" + name.getText() + " ' AND tot_cred='"
								+ cred.getText() + "'";

					} else if (!ID.getText().isEmpty() && !name.getText().isEmpty()) {
						query = "select * from student where ID ='" + ID.getText() + " 'and ID ='" + ID.getText()
								+ "' and name = '" + name.getText() + "'";

					} else if (!ID.getText().isEmpty()) {
						query = "select * from student where ID = '" + ID.getText() + " '";

					} else if (!name.getText().isEmpty()) {
						query = "select * from student where name = '" + name.getText() + " '";

					} else if (!cred.getText().isEmpty()) {
						query = "select * from student where tot_cred = '" + cred.getText() + " '";

					} else if (!cbo.getSelectionModel().isEmpty()) {
						query = "select * from student where dept_name = '" + cbo.getSelectionModel().getSelectedItem()
								+ " '";

					}

					try {
						stmt.executeQuery(query);
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					tableview = new TableView();
					buildData(query);
					pane.add(tableview, 1, 6, 3, 2);
					cbo.getSelectionModel().clearSelection();
				}
			});
		}  catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
//-----------------------------Advisors--------------------------------
		GridPane pane2 = new GridPane();
		pane2.setAlignment(Pos.CENTER);
		pane2.setPadding(new Insets(11.5, 12.5, 13.5, 14.5));
		pane2.setHgap(5.5);
		pane2.setVgap(5.5);

// Create a combo box
		Button insertbt2 = new Button("insert");
		Button searchbt2 = new Button("search");
		ComboBox<String> StudentID = new ComboBox<>();
		StudentID.setPromptText("Student ID");
		ComboBox<String> InstructorID = new ComboBox<>();
		InstructorID.setPromptText("Instructor ID");
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/lab7", "root", "");
			Statement stmt = (Statement) con.createStatement();
			

			String query1 = "SELECT DISTINCT ID from student";
			ResultSet rs = stmt.executeQuery(query1);
			

			while (rs.next()) {
				StudentID.getItems().addAll(rs.getString(1));
			}
			Statement stmt1 = (Statement) con.createStatement();
			String query2 = "SELECT ID from instructor";
			ResultSet rs2 = stmt1.executeQuery(query2);
			while (rs2.next()) {
			InstructorID.getItems().addAll(rs2.getString(1));
			}
	
			pane2.add(StudentID, 1, 0);
			pane2.add(InstructorID, 2, 0);
			
			pane2.add(insertbt2, 1, 5);
			pane2.add(searchbt2, 2, 5);
			insertbt2.setOnAction(new EventHandler<ActionEvent>() {
				public void handle(ActionEvent e) {
					try {
						Statement stmt = (Statement) con.createStatement();
						String query1 = "INSERT INTO advisor (s_ID,i_ID) VALUES" + "('" + StudentID.getSelectionModel().getSelectedItem()
								+ "','" + InstructorID.getSelectionModel().getSelectedItem()+ "')";
						stmt.executeUpdate(query1);
					}catch (SQLException e2) {
						e2.printStackTrace();

						if (e2.getErrorCode() == 1062) {

							System.out.println("there is duplication ");
							Alert alert = new Alert(Alert.AlertType.ERROR);
							alert.setTitle("Error");
							alert.setHeaderText("An error occurred");
							alert.setContentText("It is already existed.");

							alert.showAndWait();

						}

						
					}
				}
			});
			
			
			searchbt2.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				String query3 = "SELECT * from advisor";
				String Scombo = StudentID.getSelectionModel().isEmpty() ? null
						: "'" + (String) StudentID.getSelectionModel().getSelectedItem() + "'";
				String Icombo = InstructorID.getSelectionModel().isEmpty() ? null
						: "'" + (String) InstructorID.getSelectionModel().getSelectedItem() + "'";
				if (Scombo != null) {
					query3 += " WHERE s_ID =" + Scombo;
					if (Icombo != null) {
						query3 += " AND i_ID =" + Icombo;
					}
				}
				else if (Icombo != null) {
					query3 += " WHERE i_ID =" + Icombo;
					if (Scombo != null) {
						query3 += " AND s_ID =" + Scombo;
					}
				}
				tableview = new TableView();
				buildData(query3);
				pane2.add(tableview, 1, 6, 3, 2);

				System.out.println(query3);
				StudentID.getSelectionModel().clearSelection();
				InstructorID.getSelectionModel().clearSelection();
			}});
		
		}  catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
//--------------------classroom-----------------------------------
		GridPane pane3 = new GridPane();
		pane3.setAlignment(Pos.CENTER);
		pane3.setPadding(new Insets(11.5, 12.5, 13.5, 14.5));
		pane3.setHgap(5.5);
		pane3.setVgap(5.5);


		Button insertbt3 = new Button("insert");
		Button searchbt3 = new Button("search");
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/lab7", "root", "");
			Statement stmt = (Statement) con.createStatement();
			

			String query1 = "SELECT ID from student";
			ResultSet rs = stmt.executeQuery(query1);
			
		
		
			pane3.add(new Label("building: "), 2, 0);
			TextField b = new TextField();
			pane3.add(b, 2, 1);

			pane3.add(new Label("room number: "), 2, 2);
			TextField rn = new TextField();
			pane3.add(rn, 2, 3);
			
			pane3.add(new Label("capacity: "), 2, 4);
			TextField c = new TextField();
			pane3.add(c, 2, 5);
			pane3.add(insertbt3, 2, 6);
			pane3.add(searchbt3, 2, 7);
			
			insertbt3.setOnAction(new EventHandler<ActionEvent>() {
				public void handle(ActionEvent e) {
					try {
						Statement stmt = (Statement) con.createStatement();
						String query1 = "INSERT INTO classroom (building,room_number,capacity) VALUES" + "('" + b.getText()
								+ "','" + rn.getText()+ "','" + c.getText()+ "')";
						stmt.executeUpdate(query1);
					} catch (SQLException e2) {
						e2.printStackTrace();

						if (e2.getErrorCode() == 1062) {

							System.out.println("there is duplication ");
							Alert alert = new Alert(Alert.AlertType.ERROR);
							alert.setTitle("Error");
							alert.setHeaderText("An error occurred");
							alert.setContentText("It is already existed.");

							alert.showAndWait();

						}

						if (e2.getErrorCode() == 1452) {

							System.out.println(
									"There is no (building,room_number,capacity) in  `values` table.");
							Alert alert = new Alert(Alert.AlertType.ERROR);
							alert.setTitle("Error");
							alert.setHeaderText("An error occurred");
							alert.setContentText(
									"There is no (building,room_number,capacity) in  `values` table.");

							alert.showAndWait();

						}
					}
				}
			});
			
			searchbt3.setOnAction(new EventHandler<ActionEvent>() {
				public void handle(ActionEvent e) {
					String query3 = "SELECT * from classroom";

					String bu = b.getText().isEmpty() ? null :"'"+ b.getText()+"'";
					String roomn = rn.getText().isEmpty() ? null : "'" + rn.getText() + "'";
					String ca = c.getText().isEmpty() ? null :"'"+ c.getText()+"'";
					if (bu != null) {

						query3 += " WHERE building = " + bu;

						if (roomn != null)
							query3 += " AND room_number = " + roomn;

						if (ca != null)
							query3 += " AND capacity = " + ca;
					}
					else if (roomn != null) {

						query3 += " WHERE room_number = " + roomn;

						if (bu != null)
							query3 += " AND building = " + bu;

						if (ca != null)
							query3 += " AND capacity = " + ca;
					}
					else if (ca != null) {

						query3 += " WHERE capacity = " + ca;

						if (rn != null)
							query3 += " AND room_number = " + rn;

						if (bu != null)
							query3 += " AND building = " + bu;
					}
					
					tableview = new TableView();
					buildData(query3);
					pane3.add(tableview, 1, 8, 3, 2);

					System.out.println(query3);
					
					
					
				}});
					// TODO: handle exception
			
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	//----------------------course-------------------------------------------------------------------
		
		GridPane pane4 = new GridPane();
		pane4.setAlignment(Pos.CENTER);
		pane4.setPadding(new Insets(11.5, 12.5, 13.5, 14.5));
		pane4.setHgap(5.5);
		pane4.setVgap(5.5);

	ComboBox<String> depName = new ComboBox<>();
			depName.setPromptText("department Name");
		Button insertbt4 = new Button("insert");
		Button searchbt4 = new Button("search");
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/lab7", "root", "");
			Statement stmt = (Statement) con.createStatement();
			

			String query1 = "SELECT DISTINCT dept_name from department";
			ResultSet rs = stmt.executeQuery(query1);
			while (rs.next()) {
				depName.getItems().addAll(rs.getString(1));
			}
			pane4.add(new Label("Course ID : "), 2, 1);
			TextField cid = new TextField();
			pane4.add(cid, 2, 2);

			pane4.add(new Label("title : "), 2, 3);
			TextField t = new TextField();
			pane4.add(t, 2, 4);
			pane4.add(new Label("credits : "), 2, 5);
			TextField cr = new TextField();
			pane4.add(cr, 2, 6);
			pane4.add(depName, 2, 7);
			pane4.add(insertbt4, 2, 8);
			pane4.add(searchbt4, 2, 9);
		
			insertbt4.setOnAction(new EventHandler<ActionEvent>() {
				public void handle(ActionEvent e) {
					try {
						Statement stmt = (Statement) con.createStatement();
						String query1 = "INSERT INTO course (course_id,title,dept_name,credits) VALUES" + "('" + cid.getText()
								+ "','" + t.getText()+ "','" + depName.getSelectionModel().getSelectedItem()+ "','"+ cr.getText()+ "')";
						stmt.executeUpdate(query1);
						depName.getSelectionModel().clearSelection();
					} catch (SQLException e2) {
						e2.printStackTrace();

						if (e2.getErrorCode() == 1062) {

							System.out.println("there is duplication ");
							Alert alert = new Alert(Alert.AlertType.ERROR);
							alert.setTitle("Error");
							alert.setHeaderText("An error occurred");
							alert.setContentText("It is already existed.");

							alert.showAndWait();

						}

						if (e2.getErrorCode() == 1452) {

							System.out.println(
									"There is no (course_id,title,dept_name,credits) in  `course` table.");
							Alert alert = new Alert(Alert.AlertType.ERROR);
							alert.setTitle("Error");
							alert.setHeaderText("An error occurred");
							alert.setContentText(
									"There is no (course_id,title,dept_name,credits) in  `course` table.");

							alert.showAndWait();

						}
					}
				}
			});
			
			searchbt4.setOnAction(new EventHandler<ActionEvent>() {
				public void handle(ActionEvent e) {
					String query3 = "SELECT * from course";
					String cd = cid.getText().isEmpty() ? null : "'" +cid.getText()+"'" ;
					String title = t.getText().isEmpty() ? null : "'" + t.getText() + "'";
					String cred1 = cr.getText().isEmpty() ? null : "'" +cr.getText()+"'";
					String combo1 = depName.getSelectionModel().isEmpty() ? null
							: "'" + (String) depName.getSelectionModel().getSelectedItem() + "'";
					if (cd != null) {

						query3 += " WHERE course_id = " + cd;

						if (title != null)
							query3 += " AND title = " + title;

						if (cred1 != null)
							query3 += " AND credits = " + cred1;

						if (combo1 != null)
							query3 += " AND dept_name = " + combo1;

					}

					else if (title != null) {
						query3 += " WHERE title = " + title;

						if (cred1 != null)
							query3 += " AND credits = " + cred1;

						if (combo1 != null)
							query3 += " AND dept_name = " + combo1;

					}

					else if (cred1 != null) {
						query3 += " WHERE tot_cred = " + cred1;

						if (combo1 != null)
							query3 += " AND dept_name = " + combo1;

					}

					else if (combo1 != null) {
						query3 += " WHERE dept_name = " + combo1;

					}

					tableview = new TableView();
					buildData(query3);
					pane4.add(tableview, 2, 10);

					System.out.println(query3);
					depName.getSelectionModel().clearSelection();
					
				}
			});} catch (SQLException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
//------------------------------------department--------------------------------
			
			GridPane pane5 = new GridPane();
			pane5.setAlignment(Pos.CENTER);
			pane5.setPadding(new Insets(11.5, 12.5, 13.5, 14.5));
			pane5.setHgap(5.5);
			pane5.setVgap(5.5);

		
			Button insertbt5 = new Button("insert");
			Button searchbt5 = new Button("search");
			try {
				Class.forName("com.mysql.jdbc.Driver");
				Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/lab7", "root", "");
				Statement stmt = (Statement) con.createStatement();
				pane5.add(new Label("Department Name: "), 2, 1);
				TextField dep = new TextField();
				pane5.add(dep, 2, 2);

				pane5.add(new Label("bulding : "), 2, 3);
				TextField bu = new TextField();
				pane5.add(bu, 2, 4);
				pane5.add(new Label("budget : "), 2, 5);
				TextField bd = new TextField();
				pane5.add(bd, 2, 6);
				pane5.add(insertbt5, 2, 7);
				pane5.add(searchbt5, 2, 8);
				
				insertbt5.setOnAction(new EventHandler<ActionEvent>() {
					public void handle(ActionEvent e) {
						try {
							Statement stmt = (Statement) con.createStatement();
							String query1 = "INSERT INTO department (dept_name,building,budget) VALUES" + "('" + dep.getText()
									+ "','" + bu.getText()+ "','" +bd.getText()+ "')";
							stmt.executeUpdate(query1);
							depName.getSelectionModel().clearSelection();
						} catch (SQLException e2) {
							e2.printStackTrace();

							if (e2.getErrorCode() == 1062) {

								System.out.println("there is duplication ");
								Alert alert = new Alert(Alert.AlertType.ERROR);
								alert.setTitle("Error");
								alert.setHeaderText("An error occurred");
								alert.setContentText("It is already existed.");

								alert.showAndWait();

							}

							if (e2.getErrorCode() == 1452) {

								System.out.println(
										"There is no (dept_name,building,budget) in  `department` table.");
								Alert alert = new Alert(Alert.AlertType.ERROR);
								alert.setTitle("Error");
								alert.setHeaderText("An error occurred");
								alert.setContentText(
										"There is no (dept_name,building,budget) in  `department` table.");

								alert.showAndWait();

							}
						}
					}
				});
				
				searchbt5.setOnAction(new EventHandler<ActionEvent>() {
					public void handle(ActionEvent e) {
						String query3 = "SELECT * from department";
						String de = dep.getText().isEmpty() ? null : "'" +dep.getText()+"'" ;
						String buil = bu.getText().isEmpty() ? null : "'" + bu.getText() + "'";
						String budget = bd.getText().isEmpty() ? null : "'" +bd.getText()+"'";
						if (de != null) {

							query3 += " WHERE dept_name = " + de;

							if (buil != null)
								query3 += " AND building = " + buil;

							if (budget != null)
								query3 += " AND budget = " + budget;

						}

						else if (buil != null) {
							query3 += " WHERE building = " + buil;

							if (budget != null)
								query3 += " AND budget = " + budget;


						}

						else if (budget != null) {
							query3 += " WHERE budget = " + budget;}

						tableview = new TableView();
						buildData(query3);
						pane5.add(tableview, 2, 10);

						System.out.println(query3);
						depName.getSelectionModel().clearSelection();
						
					}
				});
				
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
//--------------------------------------------instructor-----------------------------		
			GridPane pane6 = new GridPane();
			pane6.setAlignment(Pos.CENTER);
			pane6.setPadding(new Insets(11.5, 12.5, 13.5, 14.5));
			pane6.setHgap(5.5);
			pane6.setVgap(5.5);

		ComboBox<String> depName1 = new ComboBox<>();
				depName1.setPromptText("department Name");
			Button insertbt6 = new Button("insert");
			Button searchbt6 = new Button("search");
			try {
				Class.forName("com.mysql.jdbc.Driver");
				Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/lab7", "root", "");
				Statement stmt = (Statement) con.createStatement();
				

				String query1 = "SELECT dept_name from department";
				ResultSet rs = stmt.executeQuery(query1);
				while (rs.next()) {
					depName.getItems().addAll(rs.getString(1));
				}
				pane6.add(new Label("ID : "), 2, 1);
				TextField id = new TextField();
				pane6.add(id, 2, 2);
				pane6.add(new Label("name : "), 2, 3);
				TextField n = new TextField();
				pane6.add(n, 2, 4);
				pane6.add(new Label("salary : "), 2, 5);
				TextField sa = new TextField();
				pane6.add(sa, 2, 6);
				pane6.add(depName, 2, 7);
				pane6.add(insertbt6, 2, 8);
				pane6.add(searchbt6, 2, 9);
			
				insertbt6.setOnAction(new EventHandler<ActionEvent>() {
					public void handle(ActionEvent e) {
						try {
							Statement stmt = (Statement) con.createStatement();
							String query1 = "INSERT INTO course (id,name,dept_name,salary) VALUES" + "('" + id.getText()
									+ "','" + n.getText()+ "','" + depName.getSelectionModel().getSelectedItem()+ "','"+ sa.getText()+ "')";
							stmt.executeUpdate(query1);
							depName.getSelectionModel().clearSelection();
						} catch (SQLException e2) {
							e2.printStackTrace();

							if (e2.getErrorCode() == 1062) {

								System.out.println("there is duplication ");
								Alert alert = new Alert(Alert.AlertType.ERROR);
								alert.setTitle("Error");
								alert.setHeaderText("An error occurred");
								alert.setContentText("It is already existed.");

								alert.showAndWait();

							}

							if (e2.getErrorCode() == 1452) {

								System.out.println(
										"There is no (id,name,dept_name,salary) in  `course` table.");
								Alert alert = new Alert(Alert.AlertType.ERROR);
								alert.setTitle("Error");
								alert.setHeaderText("An error occurred");
								alert.setContentText(
										"There is no (id,name,dept_name,salary) in  `course` table.");

								alert.showAndWait();

							}
						}
					}
				});
				
				searchbt6.setOnAction(new EventHandler<ActionEvent>() {
					public void handle(ActionEvent e) {
						String query3 = "SELECT * from instructor";
						String iid = id.getText().isEmpty() ? null : "'" +id.getText()+"'" ;
						String name = n.getText().isEmpty() ? null : "'" + n.getText() + "'";
						String sal = sa.getText().isEmpty() ? null : "'" +sa.getText()+"'";
						String combo1 = depName.getSelectionModel().isEmpty() ? null
								: "'" + (String) depName.getSelectionModel().getSelectedItem() + "'";
						if (iid != null) {

							query3 += " WHERE ID = " + iid;

							if (name != null)
								query3 += " AND name = " + name;

							if (sal != null)
								query3 += " AND salary = " + sal;

							if (combo1 != null)
								query3 += " AND dept_name = " + combo1;

						}

						else if (name != null) {
							query3 += " WHERE name = " + name;

							if (sal != null)
								query3 += " AND salary = " + sal;

							if (combo1 != null)
								query3 += " AND dept_name = " + combo1;

						}

						else if (sal != null) {
							query3 += " WHERE salary = " + sal;

							if (combo1 != null)
								query3 += " AND dept_name = " + combo1;

						}

						else if (combo1 != null) {
							query3 += " WHERE dept_name = " + combo1;

						}

						tableview = new TableView();
						buildData(query3);
						pane6.add(tableview, 2, 10);

						System.out.println(query3);
						depName.getSelectionModel().clearSelection();
						
					}
				});} catch (SQLException e) {
					e.printStackTrace();
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
//----------------------------prereq----------------------------------------
			GridPane pane7 = new GridPane();
			pane7.setAlignment(Pos.CENTER);
			pane7.setPadding(new Insets(11.5, 12.5, 13.5, 14.5));
			pane7.setHgap(5.5);
			pane7.setVgap(5.5);

	// Create a combo box
			Button insertbt7 = new Button("insert");
			Button searchbt7 = new Button("search");
			ComboBox<String> cID = new ComboBox<>();
			cID.setPromptText("course ID");
			ComboBox<String> pID = new ComboBox<>();
			pID.setPromptText("prereq ID");
			
			try {
				Class.forName("com.mysql.jdbc.Driver");
				Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/lab7", "root", "");
				Statement stmt = (Statement) con.createStatement();
				

				String query1 = "SELECT DISTINCT course_id from course";
				ResultSet rs = stmt.executeQuery(query1);
				
				while (rs.next()) {
					cID.getItems().addAll(rs.getString(1));
					pID.getItems().addAll(rs.getString(1));
				}
				pane7.add(cID, 1, 0);
				pane7.add(pID, 2, 0);
				pane7.add(insertbt7, 1, 5);
				pane7.add(searchbt7, 2, 5);
				insertbt7.setOnAction(new EventHandler<ActionEvent>() {
					public void handle(ActionEvent e) {
						try {
							Statement stmt = (Statement) con.createStatement();
							String query1 = "INSERT INTO prereq (course_id,prereq_id) VALUES" + "('" + cID.getSelectionModel().getSelectedItem()
									+ "','" + pID.getSelectionModel().getSelectedItem()+ "')";
							stmt.executeUpdate(query1);
						}catch (SQLException e2) {
							e2.printStackTrace();

							if (e2.getErrorCode() == 1062) {

								System.out.println("there is duplication ");
								Alert alert = new Alert(Alert.AlertType.ERROR);
								alert.setTitle("Error");
								alert.setHeaderText("An error occurred");
								alert.setContentText("It is already existed.");

								alert.showAndWait();

							}

							
						}
					}
				});
				
				
	searchbt7.setOnAction(new EventHandler<ActionEvent>() {
				public void handle(ActionEvent e) {
					String query3 = "SELECT * from prereq";
					String ccombo = StudentID.getSelectionModel().isEmpty() ? null
							: "'" + (String) cID.getSelectionModel().getSelectedItem() + "'";
					String pcombo = InstructorID.getSelectionModel().isEmpty() ? null
							: "'" + (String) pID.getSelectionModel().getSelectedItem() + "'";
					if (ccombo != null) {
						query3 += " WHERE course_id =" + ccombo;
						if (pcombo != null) {
							query3 += " AND prereq_id =" + pcombo;
						}
					}
					else if (pcombo != null) {
						query3 += " WHERE prereq_id =" + pcombo;
					
					}
					tableview = new TableView();
					buildData(query3);
					pane7.add(tableview, 1, 6, 3, 2);

					System.out.println(query3);
					cID.getSelectionModel().clearSelection();
					pID.getSelectionModel().clearSelection();
				}});
			
			}  catch (SQLException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
//----------------------------------------------section-------------------------------------
			GridPane pane8 = new GridPane();
			pane8.setAlignment(Pos.CENTER);
			pane8.setPadding(new Insets(11.5, 12.5, 13.5, 14.5));
			pane8.setHgap(5.5);
			pane8.setVgap(5.5);

	// Create a combo box
			Button insertbt8 = new Button("insert");
			Button searchbt8 = new Button("search");
			ComboBox<String> courseID = new ComboBox<>();
			courseID.setPromptText("course ID");
			ComboBox<String> building = new ComboBox<>();
			building.setPromptText("building");
			ComboBox<String> roomnumber = new ComboBox<>();
			roomnumber.setPromptText("room number");
			ComboBox<String> time = new ComboBox<>();
			time.setPromptText("time_slot_ID");
			try {
				Class.forName("com.mysql.jdbc.Driver");
				Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/lab7", "root", "");
				Statement stmt = (Statement) con.createStatement();
				

				String query1 = "SELECT DISTINCT course_id from course";
				ResultSet rs = stmt.executeQuery(query1);
				while (rs.next()) {
					courseID.getItems().addAll(rs.getString(1));
				}
				
				Statement stmt1 = (Statement) con.createStatement();
				String query2 = "SELECT DISTINCT building from classroom";
				ResultSet rs2 = stmt1.executeQuery(query2);
				while (rs2.next()) {
				building.getItems().addAll(rs2.getString(1));
				}
				
				Statement stmt2 = (Statement) con.createStatement();
				String query5 = "SELECT DISTINCT room_number from classroom";
				ResultSet rs3 = stmt2.executeQuery(query5);
				while (rs3.next()) {
				roomnumber.getItems().addAll(rs3.getString(1));
				}
				
				Statement stmt3 = (Statement) con.createStatement();
				String query4 = "SELECT DISTINCT time_slot_id from time_slot";
				ResultSet rs4 = stmt3.executeQuery(query4);
				while (rs4.next()) {
				time.getItems().addAll(rs4.getString(1));
				}
				pane8.add(new Label("section ID: "), 1, 1);
				TextField section = new TextField();
				pane8.add(section, 1, 2);

				pane8.add(new Label("semester : "), 2, 1);
				TextField semester = new TextField();
				pane8.add(semester, 2, 2);
				
				pane8.add(new Label("year : "), 3, 1);
				TextField year = new TextField();
				pane8.add(year, 3, 2);
				
				pane8.add(courseID, 1, 0);
				pane8.add(building, 2, 0);
				pane8.add(roomnumber, 3, 0);
				pane8.add(time, 4, 0);
				pane8.add(insertbt8, 1, 5);
				pane8.add(searchbt8, 2, 5);
				insertbt8.setOnAction(new EventHandler<ActionEvent>() {
					public void handle(ActionEvent e) {
						try {
							Statement stmt = (Statement) con.createStatement();
							String query1 = "INSERT INTO section (course_id,sec_id,semester,year,building,room_number,time_slot_id) VALUES" + 
							"('" + courseID.getSelectionModel().getSelectedItem()
									+ "','" + section.getText()+ "','"  + 
							semester.getText()+"','"+
							year.getText()+"','"+
									building.getSelectionModel().getSelectedItem()+  "','"+
							roomnumber.getSelectionModel().getSelectedItem()
									+ "','" + time.getSelectionModel().getSelectedItem() +"')";
							stmt.executeUpdate(query1);
						} catch (SQLException e2) {
							e2.printStackTrace();

							if (e2.getErrorCode() == 1062) {

								System.out.println("there is duplication ");
								Alert alert = new Alert(Alert.AlertType.ERROR);
								alert.setTitle("Error");
								alert.setHeaderText("An error occurred");
								alert.setContentText("It is already existed.");

								alert.showAndWait();

							}

							if (e2.getErrorCode() == 1452) {

								System.out.println(
										"There is no (`course_id`, `sec_id`, `semester`, `year`) in  `section` table.");
								Alert alert = new Alert(Alert.AlertType.ERROR);
								alert.setTitle("Error");
								alert.setHeaderText("An error occurred");
								alert.setContentText(
										"There is no (`course_id`, `sec_id`, `semester`, `year`) in  `section` table.");

								alert.showAndWait();

							}
						}
					}
				});
				
				
	searchbt8.setOnAction(new EventHandler<ActionEvent>() {
				public void handle(ActionEvent e) {
					String query3 = "SELECT * from section";
					String course = courseID.getSelectionModel().isEmpty() ? null
							: "'" + (String) courseID.getSelectionModel().getSelectedItem() + "'";
					String buil = building.getSelectionModel().isEmpty() ? null
							: "'" + (String) building.getSelectionModel().getSelectedItem() + "'";
					String room = roomnumber.getSelectionModel().isEmpty() ? null
							: "'" + (String) roomnumber.getSelectionModel().getSelectedItem() + "'";
					String t = time.getSelectionModel().isEmpty() ? null
							: "'" + (String) time.getSelectionModel().getSelectedItem() + "'";
					String sec = section.getText().isEmpty() ? null : "'" +section.getText()+"'" ;
					String sem = semester.getText().isEmpty() ? null : "'" +semester.getText()+"'" ;
					String ye = year.getText().isEmpty() ? null : "'" +year.getText()+"'" ;
					if (course != null) {
						query3 += " WHERE course_id =" + course;
						if (buil != null) {
							query3 += " AND building =" + buil;
						}
						if (room != null) {
							query3 += " AND room_number =" + room;
						}
						if (t != null) {
							query3 += " AND time_slot_id =" + t;
						}
						if (sec != null) {
							query3 += " AND section =" + sec;
						}
						if (sem != null) {
							query3 += " AND semester =" + sem;
						}
						if (ye != null) {
							query3 += " AND year =" + ye;
						}
					}
					else if (buil != null) {
						query3 += " WHERE building =" +buil ;
						if (room != null) {
							query3 += " AND room_number =" + room;
						}
						if (t != null) {
							query3 += " AND time_slot_id =" + t;
						}
						if (sec != null) {
							query3 += " AND section =" + sec;
						}
						if (sem != null) {
							query3 += " AND semester =" + sem;
						}
						if (ye != null) {
							query3 += " AND year =" + ye;
						}
					}
				
					else if (room != null) {
						query3 += " WHERE room_number =" + room;
						
						if (t != null) {
							query3 += " AND time_slot_id =" + t;
						}
						if (sec != null) {
							query3 += " AND section =" + sec;
						}
						if (sem != null) {
							query3 += " AND semester =" + sem;
						}
						if (ye != null) {
							query3 += " AND year =" + ye;
						}
					}else if (t != null) {
						query3 += " WHERE time_slot_id =" + t;
						
						if (sec != null) {
							query3 += " AND section =" + sec;
						}
						if (sem != null) {
							query3 += " AND semester =" + sem;
						}
						if (ye != null) {
							query3 += " AND year =" + ye;
						}
					}else if (sec != null) {
						query3 += " WHERE section =" + sec;
			
						if (sem != null) {
							query3 += " AND semester =" + sem;
						}
						if (ye != null) {
							query3 += " AND year =" + ye;
						}
					}else if (sem != null) {
						query3 += " WHERE semester =" + sem;
						if (ye != null) {
							query3 += " AND year =" + ye;
						}
					}else if (ye != null) {
							query3 += " WHERE year =" + ye;
						}
					
					tableview = new TableView();
					buildData(query3);
					pane8.add(tableview, 1, 6, 3, 2);

					System.out.println(query3);
					courseID.getSelectionModel().clearSelection();
					building.getSelectionModel().clearSelection();
					roomnumber.getSelectionModel().clearSelection();
					time.getSelectionModel().clearSelection();
				}});
			
			}catch (SQLException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			//------------------------------------------------takes--------------------------------------------------------------------------------------------	

			// Create a pane and set its properties
			GridPane pane9 = new GridPane();
			pane9.setAlignment(Pos.CENTER);
			pane9.setPadding(new Insets(11.5, 12.5, 13.5, 14.5));
			pane9.setHgap(5.5);
			pane9.setVgap(5.5);

			// Create a combo box
			ComboBox combo_box9 = new ComboBox();
			combo_box9.setPromptText("ID");
			ComboBox combo_box10 = new ComboBox();
			combo_box10.setPromptText("course_id");
			ComboBox combo_box11 = new ComboBox();
			combo_box11.setPromptText("sec__id");
			ComboBox combo_box12 = new ComboBox();
			combo_box12.setPromptText("semester");
			ComboBox combo_box13 = new ComboBox();
			combo_box13.setPromptText("year");

			try {
				Class.forName("com.mysql.jdbc.Driver");
				Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/lab7", "root", "");
				Statement stmt = (Statement) con.createStatement();

				String query1 = "SELECT ID from student";
				ResultSet rs1 = stmt.executeQuery(query1);

				while (rs1.next()) {
					combo_box9.getItems().addAll(rs1.getString(1));
				}

				String query11 = "SELECT DISTINCT course_id from section";
				ResultSet rs11 = stmt.executeQuery(query11);

				while (rs11.next()) {
					combo_box10.getItems().addAll(rs11.getString(1));
				}

				String query111 = "SELECT DISTINCT sec_id from section";
				ResultSet rs111 = stmt.executeQuery(query111);

				while (rs111.next()) {
					combo_box11.getItems().addAll(rs111.getString(1));
				}

				String query1111 = "SELECT DISTINCT semester from section";
				ResultSet rs1111 = stmt.executeQuery(query1111);

				while (rs1111.next()) {
					combo_box12.getItems().addAll(rs1111.getString(1));
				}

				String query11111 = "SELECT DISTINCT year from section";
				ResultSet rs11111 = stmt.executeQuery(query11111);

				while (rs11111.next()) {
					combo_box13.getItems().addAll(rs11111.getString(1));
				}

				pane9.add(combo_box9, 1, 0);
				pane9.add(combo_box10, 1, 1);
				pane9.add(combo_box11, 1, 2);
				pane9.add(combo_box12, 1, 3);
				pane9.add(combo_box13, 1, 4);
				pane9.add(new Label("grade:"), 0, 5);
				TextField grade = new TextField();
				pane9.add(grade, 1, 5);

				Button btinsert = new Button("insert ");
				pane9.add(btinsert, 0, 6);
				GridPane.setHalignment(btinsert, HPos.RIGHT);

				btinsert.setOnAction(e -> {
					try {
						String query2 = "INSERT INTO `takes`(`ID`,`course_id`, `sec_id`, `semester`, `year`, `grade`)"
								+ " VALUES (" + combo_box9.getSelectionModel().getSelectedItem() + ",'"
								+ combo_box10.getSelectionModel().getSelectedItem() + "',"
								+ combo_box11.getSelectionModel().getSelectedItem() + ",'"
								+ combo_box12.getSelectionModel().getSelectedItem() + "',"
								+ combo_box13.getSelectionModel().getSelectedItem() + ",'" + grade.getText() + "')";

						System.out.println(query2);
						stmt.executeUpdate(query2);
						alert1.showAndWait();

					} catch (SQLException e2) {
						e2.printStackTrace();

						if (e2.getErrorCode() == 1062) {

							System.out.println("there is duplication ");
							Alert alert = new Alert(Alert.AlertType.ERROR);
							alert.setTitle("Error");
							alert.setHeaderText("An error occurred");
							alert.setContentText("It is already existed.");

							alert.showAndWait();

						}

						if (e2.getErrorCode() == 1452) {

							System.out.println(
									"There is no (`ID`,`course_id`, `sec_id`, `semester`, `year`, `grade`) in  `takes` table.");
							Alert alert = new Alert(Alert.AlertType.ERROR);
							alert.setTitle("Error");
							alert.setHeaderText("An error occurred");
							alert.setContentText(
									"There is no (`ID`,`course_id`, `sec_id`, `semester`, `year`, `grade`) in  `takes` table.");

							alert.showAndWait();

						}
					}

				});

				Button btSearch = new Button("Search ");
				pane9.add(btSearch, 1, 6);
				GridPane.setHalignment(btSearch, HPos.RIGHT);

				btSearch.setOnAction(e -> {
					String query3 = "SELECT * from takes";

					String combo9 = combo_box9.getSelectionModel().isEmpty() ? null
							: (String) combo_box9.getSelectionModel().getSelectedItem();
					String combo10 = combo_box10.getSelectionModel().isEmpty() ? null
							: "'" + (String) combo_box10.getSelectionModel().getSelectedItem() + "'";
					String combo11 = combo_box11.getSelectionModel().isEmpty() ? null
							: (String) combo_box11.getSelectionModel().getSelectedItem();
					String combo12 = combo_box12.getSelectionModel().isEmpty() ? null
							: "'" + (String) combo_box12.getSelectionModel().getSelectedItem() + "'";
					String combo13 = combo_box13.getSelectionModel().isEmpty() ? null
							: (String) combo_box13.getSelectionModel().getSelectedItem();
					String g = grade.getText().isEmpty() ? null : "'" + grade.getText() + "'";

					if (combo9 != null) {

						query3 += " WHERE ID = " + combo9;

						if (combo10 != null)
							query3 += " AND course_id = " + combo10;

						if (combo11 != null)
							query3 += " AND sec_id = " + combo11;

						if (combo12 != null)
							query3 += " AND semester = " + combo12;

						if (combo13 != null)
							query3 += " AND year = " + combo13;

						if (g != null)
							query3 += " AND grade = " + g;

					}

					else if (combo10 != null) {
						query3 += " WHERE course_id = " + combo10;

						if (combo11 != null)
							query3 += " AND sec_id = " + combo11;

						if (combo12 != null)
							query3 += " AND semester = " + combo12;

						if (combo13 != null)
							query3 += " AND year = " + combo13;

						if (g != null)
							query3 += " AND grade = " + g;

					}

					else if (combo11 != null) {
						query3 += " WHERE sec_id = " + combo11;

						if (combo12 != null)
							query3 += " AND semester = " + combo12;

						if (combo13 != null)
							query3 += " AND year = " + combo13;

						if (g != null)
							query3 += " AND grade = " + g;

					}

					else if (combo12 != null) {
						query3 += " WHERE semester = " + combo12;

						if (combo13 != null)
							query3 += " AND year = " + combo13;

						if (g != null)
							query3 += " AND grade = " + g;

					}

					else if (combo13 != null) {
						query3 += " WHERE year = " + combo13;

						if (g != null)
							query3 += " AND grade = " + g;

					}

					else if (g != null) {
						query3 += " WHERE grade = " + g;

					}

					tableview = new TableView();
					buildData(query3);
					pane9.add(tableview, 1, 7);

					System.out.println(query3);

					combo_box9.getSelectionModel().clearSelection();
					combo_box10.getSelectionModel().clearSelection();
					combo_box11.getSelectionModel().clearSelection();
					combo_box12.getSelectionModel().clearSelection();
					combo_box13.getSelectionModel().clearSelection();

				});

			} catch (SQLException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}

	//------------------------------------------------teaches--------------------------------------------------------------------------------------------

			// Create a pane and set its properties
			GridPane pane10 = new GridPane();
			pane10.setAlignment(Pos.CENTER);
			pane10.setPadding(new Insets(11.5, 12.5, 13.5, 14.5));
			pane10.setHgap(5.5);
			pane10.setVgap(5.5);

			// Create a combo box
			ComboBox combo_box14 = new ComboBox();
			combo_box14.setPromptText("ID");
			ComboBox combo_box15 = new ComboBox();
			combo_box15.setPromptText("course_id");
			ComboBox combo_box16 = new ComboBox();
			combo_box16.setPromptText("sec_id");
			ComboBox combo_box17 = new ComboBox();
			combo_box17.setPromptText("semester");
			ComboBox combo_box18 = new ComboBox();
			combo_box18.setPromptText("year");


			try {
				Class.forName("com.mysql.jdbc.Driver");
				Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/lab7", "root", "");
				Statement stmt = (Statement) con.createStatement();

				String query1 = "SELECT ID from instructor";
				ResultSet rs1 = stmt.executeQuery(query1);

				while (rs1.next()) {
					combo_box14.getItems().addAll(rs1.getString(1));
				}

				String query11 = "SELECT DISTINCT course_id from section";
				ResultSet rs11 = stmt.executeQuery(query11);

				while (rs11.next()) {
					combo_box15.getItems().addAll(rs11.getString(1));
				}

				String query111 = "SELECT DISTINCT sec_id from section";
				ResultSet rs111 = stmt.executeQuery(query111);

				while (rs111.next()) {
					combo_box16.getItems().addAll(rs111.getString(1));
				}

				String query1111 = "SELECT DISTINCT semester from section";
				ResultSet rs1111 = stmt.executeQuery(query1111);

				while (rs1111.next()) {
					combo_box17.getItems().addAll(rs1111.getString(1));
				}

				String query11111 = "SELECT DISTINCT year from section";
				ResultSet rs11111 = stmt.executeQuery(query11111);

				while (rs11111.next()) {
					combo_box18.getItems().addAll(rs11111.getString(1));
				}

				
				pane10.add(combo_box14, 1, 0);
				pane10.add(combo_box15, 1, 1);
				pane10.add(combo_box16, 1, 2);
				pane10.add(combo_box17, 1, 3);
				pane10.add(combo_box18, 1, 4);

				Button btinsert = new Button("insert ");
				pane10.add(btinsert, 0, 5);
				GridPane.setHalignment(btinsert, HPos.RIGHT);

				btinsert.setOnAction(e -> {
					try {
						String query2 = "INSERT INTO `teaches`(`ID`,`course_id`, `sec_id`, `semester`, `year`)"
								+ " VALUES (" + combo_box14.getSelectionModel().getSelectedItem() + ",'"
								+ combo_box15.getSelectionModel().getSelectedItem() + "',"
								+ combo_box16.getSelectionModel().getSelectedItem() + ",'"
								+ combo_box17.getSelectionModel().getSelectedItem() + "',"
								+ combo_box18.getSelectionModel().getSelectedItem() + ")";

						System.out.println(query2);
						stmt.executeUpdate(query2);
						alert1.showAndWait();

					} catch (SQLException e2) {
						e2.printStackTrace();
						if (e2.getErrorCode() == 1062) {

							System.out.println("there is duplication ");
							Alert alert = new Alert(Alert.AlertType.ERROR);
							alert.setTitle("Error");
							alert.setHeaderText("An error occurred");
							alert.setContentText("It is already existed.");

							alert.showAndWait();

						}

						if (e2.getErrorCode() == 1452) {

							System.out.println(
									"There is no (`ID`,`course_id`, `sec_id`, `semester`, `year`) in  `teaches` table.");
							Alert alert = new Alert(Alert.AlertType.ERROR);
							alert.setTitle("Error");
							alert.setHeaderText("An error occurred");
							alert.setContentText(
									"There is no (`ID`,`course_id`, `sec_id`, `semester`, `year`) in  `teaches` table.");

							alert.showAndWait();

						}

					}

				});

				Button btSearch = new Button("Search ");
				pane10.add(btSearch, 1, 5);
				GridPane.setHalignment(btSearch, HPos.RIGHT);

				btSearch.setOnAction(e -> {
					String query3 = "SELECT * from teaches";

					String combo9 = combo_box14.getSelectionModel().isEmpty() ? null
							: (String) combo_box14.getSelectionModel().getSelectedItem();
					String combo10 = combo_box15.getSelectionModel().isEmpty() ? null
							: "'" + (String) combo_box15.getSelectionModel().getSelectedItem() + "'";
					String combo11 = combo_box16.getSelectionModel().isEmpty() ? null
							: (String) combo_box16.getSelectionModel().getSelectedItem();
					String combo12 = combo_box17.getSelectionModel().isEmpty() ? null
							: "'" + (String) combo_box17.getSelectionModel().getSelectedItem() + "'";
					String combo13 = combo_box18.getSelectionModel().isEmpty() ? null
							: (String) combo_box18.getSelectionModel().getSelectedItem();

					if (combo9 != null) {

						query3 += " WHERE ID = " + combo9;

						if (combo10 != null)
							query3 += " AND course_id = " + combo10;

						if (combo11 != null)
							query3 += " AND sec_id = " + combo11;

						if (combo12 != null)
							query3 += " AND semester = " + combo12;

						if (combo13 != null)
							query3 += " AND year = " + combo13;

					}

					else if (combo10 != null) {
						query3 += " WHERE course_id = " + combo10;

						if (combo11 != null)
							query3 += " AND sec_id = " + combo11;

						if (combo12 != null)
							query3 += " AND semester = " + combo12;

						if (combo13 != null)
							query3 += " AND year = " + combo13;

					}

					else if (combo11 != null) {
						query3 += " WHERE sec_id = " + combo11;

						if (combo12 != null)
							query3 += " AND semester = " + combo12;

						if (combo13 != null)
							query3 += " AND year = " + combo13;

					}

					else if (combo12 != null) {
						query3 += " WHERE semester = " + combo12;

						if (combo13 != null)
							query3 += " AND year = " + combo13;

					}

					else if (combo13 != null) {
						query3 += " WHERE year = " + combo13;

					}

					tableview = new TableView();
					buildData(query3);
					pane10.add(tableview, 1, 6);

					System.out.println(query3);

					combo_box14.getSelectionModel().clearSelection();
					combo_box15.getSelectionModel().clearSelection();
					combo_box16.getSelectionModel().clearSelection();
					combo_box17.getSelectionModel().clearSelection();
					combo_box18.getSelectionModel().clearSelection();

				});

			} catch (SQLException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}

	//------------------------------------------------time slot--------------------------------------------------------------------------------------------		

			// Create a pane and set its properties
			GridPane pane11 = new GridPane();
			pane11.setAlignment(Pos.CENTER);
			pane11.setPadding(new Insets(11.5, 12.5, 13.5, 14.5));
			pane11.setHgap(5.5);
			pane11.setVgap(5.5);

			ComboBox day = new ComboBox();
			day.setPromptText("day");
			ComboBox start_hr = new ComboBox();
			start_hr.setPromptText("start_hr");
			ComboBox start_min = new ComboBox();
			start_min.setPromptText("start_min");
			ComboBox end_hr = new ComboBox();
			end_hr.setPromptText("end_hr");
			ComboBox end_min = new ComboBox();
			end_min.setPromptText("end_min");

			try {
				Class.forName("com.mysql.jdbc.Driver");
				Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/lab7", "root", "");
				Statement stmt = (Statement) con.createStatement();

				pane11.add(new Label("time_slot_id:"), 0, 0);
				TextField time_slot_id = new TextField();

				day.getItems().addAll("M");
				day.getItems().addAll("T");
				day.getItems().addAll("W");
				day.getItems().addAll("F");
				day.getItems().addAll("R");

				for (int i = 0; i < 24; i++) {

					start_hr.getItems().addAll(String.valueOf(i));

				}

				for (int i = 0; i < 24; i++) {

					end_hr.getItems().addAll(String.valueOf(i));

				}

				for (int i = 0; i < 61; i++) {

					start_min.getItems().addAll(String.valueOf(i));

				}

				for (int i = 0; i < 61; i++) {

					end_min.getItems().addAll(String.valueOf(i));

				}

				pane11.add(time_slot_id, 1, 0);
				pane11.add(day, 1, 1);
				pane11.add(start_hr, 1, 2);
				pane11.add(start_min, 1, 3);
				pane11.add(end_hr, 1, 4);
				pane11.add(end_min, 1, 5);
				Button btinsert = new Button("insert");
				pane11.add(btinsert, 0, 6);
				GridPane.setHalignment(btinsert, HPos.RIGHT);

				btinsert.setOnAction(e -> {
					try {
						String query2 = "INSERT INTO `time_slot`(`time_slot_id`, `day`, `start_hr`,`start_min`,`end_hr`, `end_min`)"
								+ " VALUES ('" + time_slot_id.getText() + "','" + day.getSelectionModel().getSelectedItem()
								+ "'," + start_hr.getSelectionModel().getSelectedItem() + ","
								+ start_min.getSelectionModel().getSelectedItem() + ","
								+ end_hr.getSelectionModel().getSelectedItem() + ","
								+ end_min.getSelectionModel().getSelectedItem() + ")";

						System.out.println(query2);
						stmt.executeUpdate(query2);
						alert1.showAndWait();

					} catch (SQLException e2) {
						e2.printStackTrace();

						if (e2.getErrorCode() == 1062) {

							System.out.println("there is duplication ");
							Alert alert = new Alert(Alert.AlertType.ERROR);
							alert.setTitle("Error");
							alert.setHeaderText("An error occurred");
							alert.setContentText(
									"It is already existed. \n you have to change at least one of time_slot_id,day,start_hr,or start_min");

							alert.showAndWait();

						}

					}

				});

				Button btSearch = new Button("Search ");
				pane11.add(btSearch, 1, 6);
				GridPane.setHalignment(btSearch, HPos.RIGHT);

				btSearch.setOnAction(e -> {
					String query3 = "SELECT * from time_slot";

					String b = time_slot_id.getText().isEmpty() ? null : "'" + time_slot_id.getText() + "'";
					String rn = day.getSelectionModel().isEmpty() ? null
							: "'" + (String) day.getSelectionModel().getSelectedItem() + "'";
					String c = start_hr.getSelectionModel().isEmpty() ? null
							: (String) start_hr.getSelectionModel().getSelectedItem();
					String a = start_min.getSelectionModel().isEmpty() ? null
							: (String) start_min.getSelectionModel().getSelectedItem();
					String c1 = end_hr.getSelectionModel().isEmpty() ? null
							: (String) end_hr.getSelectionModel().getSelectedItem();
					String a1 = end_min.getSelectionModel().isEmpty() ? null
							: (String) end_min.getSelectionModel().getSelectedItem();

					if (b != null) {

						query3 += " WHERE time_slot_id = " + b;

						if (rn != null)
							query3 += " AND day = " + rn;

						if (c != null)
							query3 += " AND start_hr = " + c;

						if (a != null)
							query3 += " AND start_min = " + a;

						if (c1 != null)
							query3 += " AND end_hr = " + c1;

						if (a1 != null)
							query3 += " AND end_min = " + a1;

					}

					else if (rn != null) {
						query3 += " WHERE day = " + rn;

						if (c != null)
							query3 += " AND start_hr = " + c;

						if (a != null)
							query3 += " AND start_min = " + a;

						if (c1 != null)
							query3 += " AND end_hr = " + c1;

						if (a1 != null)
							query3 += " AND end_min = " + a1;

					}

					else if (c != null) {
						query3 += " WHERE start_hr = " + c;

						if (a != null)
							query3 += " AND start_min = " + a;

						if (c1 != null)
							query3 += " AND end_hr = " + c1;

						if (a1 != null)
							query3 += " AND end_min = " + a1;

					}

					else if (a != null) {
						query3 += " WHERE start_min = " + a;

						if (c1 != null)
							query3 += " AND end_hr = " + c1;

						if (a1 != null)
							query3 += " AND end_min = " + a1;

					}

					else if (c1 != null) {
						query3 += " WHERE end_hr = " + c1;

						if (a1 != null)
							query3 += " AND end_min = " + a1;

					}

					else if (a1 != null) {
						query3 += " WHERE end_min = " + a1;

					}

					tableview = new TableView();
					buildData(query3);
					pane11.add(tableview, 1, 7);

					System.out.println(query3);

					day.getSelectionModel().clearSelection();
					start_hr.getSelectionModel().clearSelection();
					start_min.getSelectionModel().clearSelection();
					end_hr.getSelectionModel().clearSelection();
					end_min.getSelectionModel().clearSelection();

				});

			} catch (SQLException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			
//Set the content for each tab
			 tab0.setContent(pane0);
		 tab.setContent(pane);
		tab2.setContent(pane2);
		tab3.setContent(pane3);
		tab4.setContent(pane4);
		tab5.setContent(pane5);
		tab6.setContent(pane6);
		tab7.setContent(pane7);
		tab8.setContent(pane8);
		tab9.setContent(pane9); 
		tab10.setContent(pane10); 
		tab11.setContent(pane11);
		 

		// Add the tabs to the TabPane
		tabPane.getTabs().addAll(tab0,tab, tab2, tab3, tab4, tab5, tab6, tab7, tab8, tab9, tab10, tab11);

		// Create a scene and place it in the stage
		Scene scene = new Scene(tabPane, 1200, 1000);

		primaryStage.setTitle("ShowGridPane"); // Set the stage title
		primaryStage.setScene(scene); // Place the scene in the stage
		primaryStage.show(); // Display the stage
	}

	public void buildData(String SQL) {
		Connection c;
		data = FXCollections.observableArrayList();
		try {

			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/lab7", "root", "");

// SQL FOR SELECTING ALL OF CUSTOMER

// ResultSet
			ResultSet rs = con.createStatement().executeQuery(SQL);

			tableview.getColumns().clear();
			tableview.getItems().clear();
			/**
			 * ******************************** TABLE COLUMN ADDED DYNAMICALLY *
			 *********************************
			 */
			for (int i = 0; i < rs.getMetaData().getColumnCount(); i++) {
				// We are using non property style for making dynamic table
				final int j = i;
				TableColumn col = new TableColumn(rs.getMetaData().getColumnName(i + 1));
				col.setCellValueFactory(
						new Callback<CellDataFeatures<ObservableList, String>, ObservableValue<String>>() {
							public ObservableValue<String> call(CellDataFeatures<ObservableList, String> param) {
								return new SimpleStringProperty(param.getValue().get(j).toString());
							}
						});

				tableview.getColumns().addAll(col);

			}

			/**
			 * ****************************** Data added to ObservableList *
			 *******************************
			 */
			while (rs.next()) {
// Iterate Row
				ObservableList<String> row = FXCollections.observableArrayList();
				for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
// Iterate Column
					row.add(rs.getString(i));
				}
				System.out.println("Row [1] added " + row);
				data.add(row);

			}

// FINALLY ADDED TO TableView
			tableview.setItems(data);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error on Building Data");
		}
	}
}
