package ProjectOne;

import java.sql.*;
import java.util.Scanner;
import java.util.NoSuchElementException;

public class ProjectOne {
    public void createTable() {

        try(Connection connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/project_one", "root", "root");
            Statement statement = connection.createStatement()){

            statement.execute("CREATE TABLE IF NOT EXISTS usersData(Name Text, Email Text, Age int, Location Text, Designation Text)");

        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
    public int populateTable(){

        int numberOfTimesToIterate = 0;

        try (Connection connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/project_one", "root", "root")) {
            try (PreparedStatement templateToEnterUsersData = connection.prepareStatement("INSERT INTO usersData(Name, Email, Age, Location, Designation) VALUES(?, ?, ?, ?, ?)")) {
                try (Scanner scanner = new Scanner(System.in)) {

                    do {
                        System.out.println("Please input your name: ");
                        String name = scanner.nextLine();

                        System.out.println("Please input your email: ");
                        String email = scanner.nextLine();

                        System.out.println("Please input your age: ");
                        int age = Integer.parseInt(scanner.nextLine());

                        System.out.println("Please input your location: ");
                        String location = scanner.nextLine();

                        System.out.println("Please input your designation: ");
                        String designation = scanner.nextLine();

                        templateToEnterUsersData.setString(1, name);
                        templateToEnterUsersData.setString(2, email);
                        templateToEnterUsersData.setInt(3, age);
                        templateToEnterUsersData.setString(4, location);
                        templateToEnterUsersData.setString(5, designation);

                        templateToEnterUsersData.execute();

                        numberOfTimesToIterate++;

                    } while (numberOfTimesToIterate < 10);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }

        return numberOfTimesToIterate;
    }

    public static void main(String[] args) throws NoSuchElementException{

        try{

            ProjectOne inTheDatabase = new ProjectOne();
            inTheDatabase.createTable();

            int iteration = inTheDatabase.populateTable();
            System.out.println(iteration);

        } catch (NoSuchElementException e){
            System.out.println("The error message here is " + e.getMessage());
        }
    }
}