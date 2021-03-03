package com.ideas2it.employeemanagement.view;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Date;
import java.util.Scanner;

import com.ideas2it.employeemanagement.controller.EmployeeController;

/**
 * this class used to interact with the user
 * that interactions are taking input from the user
 * and display the details as per user's request
 *
 * @author  sibi
 * @created 2021-03-03
 */
public class EmployeeView {

    private EmployeeController employeeController = new EmployeeController();

    public void view() {
        Scanner scanner = new Scanner(System.in);

        byte option = 0;    // to track user's choice

        System.out.println("select an option to perform a particular operation on an employee's details");
        String optionStatement = "1 - Create , 2 - Display all employees ,"
                + " 3 - Display an employee , 4 - Update , 5 - Delete , 6 - Exit" ;

        while (6 != option) {
            System.out.println(optionStatement);
            option = scanner.nextByte();
            scanner.skip("[\n\r]{2}");

            switch (option) {
                case 1:
                    createEmployee();
                    break;
                case 2:
                    displayEmployees();
                    break;
                case 3:
                    displayEmployee();
                    break;
                case 4:
                    updateEmployee();
                    break;
                case 5:
                    deleteEmployee();
                    break;
                case 6:
                    break;
                default:
                    System.out.println("Enter valid option");
            }
        }
    }

    /*
     * reads the employee details from the user and
     * request the controller to store the details
     */
    void createEmployee() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter employee's details ");
        System.out.print("Name              : ");
        String name = scanner.nextLine();
        System.out.print("DOB (DD/MM/YYYY)  : ");
        Date dob = validateDate();
        System.out.print("Salary            : ");
        float salary = scanner.nextFloat();
        System.out.print("Mobile Number     : ");
        String mobileNumber = validateMobileNumber();

        System.out.println(employeeController
                .createEmployee(name, dob, salary ,mobileNumber));
    }

    /*
     * display details of all the employees
     */
    void displayEmployees() {
        System.out.println("\n-----Employee Database-----\n");

        employeeController.displayEmployees().forEach((employeeDetails) -> {
            System.out.println(employeeDetails);
        });
    }

    /*
     * reads the employeeId from the user and request
     * the controller to give the details of an employee
     */
    void displayEmployee() {
        System.out.println("Enter employee ID to be displayed : ");
        Scanner scanner = new Scanner(System.in);
        int employeeId = scanner.nextInt();

        if (!employeeController.isEmployeePresent(employeeId)) {
            System.out.println("Employee details not present");
        } else {
            System.out.println(employeeController.displayEmployee(employeeId));
        }
    }

    /*
     * updates certain detail of an employee
     */
    void updateEmployee() {
        System.out.println("Enter employeeId to update the details");
        Scanner scanner = new Scanner(System.in);
        int employeeId = scanner.nextInt();

        if (!employeeController.isEmployeePresent(employeeId)) {
            System.out.println("Employee details not present");
        } else {
            System.out.println("Choose which detail need to be updated");
            char option = '\0';
            String updationOptionStatement
                    = "N - Name , D - DOB , S - Salary , M - Mobile number , C - Cancel Updation";

            while ('C' != option) {
                System.out.println(updationOptionStatement);
                char updationParameter = scanner.next().charAt(0);

                switch (updationParameter) {
                    case 'N':
                        System.out.println("Enter updated NAME");
                        scanner.skip("[\r\n]{2}");
                        String name = scanner.nextLine();
                        employeeController.updateName(employeeId,name);
                        System.out.println("Updation Successful");
                        break;
                    case 'D':
                        System.out.println("Enter updated DOB (DD/MM/YYYY)");
                        Date dob = validateDate();
                        employeeController.updateDob(employeeId, dob);
                        System.out.println("Updation Successful");
                        break;
                    case 'S':
                        System.out.println("Enter updated SALARY");
                        float salary = scanner.nextFloat();
                        employeeController.updateSalary(employeeId, salary);
                        System.out.println("Updation Successful");
                        break;
                    case 'M':
                        System.out.println("Enter updated MOBILE NUMBER");
                        String mobileNumber = scanner.next();
                        employeeController.updateMobileNumber(employeeId, mobileNumber);
                        System.out.println("Updation Successful");
                        break;
                    case 'C':
                        System.out.println("Updation cancelled");
                        option = 'C';
                        break;
                    default:
                        System.out.println("Enter valid option");
                        break;
                }
            }
        }
    }

    /*
     * reads the employeeId from the user and request
     * the controller to remove the details of an employee
     */
    void deleteEmployee() {
        System.out.println("Enter employee's ID to delete the employee");
        Scanner scanner = new Scanner(System.in);
        int employeeId = scanner.nextInt();

        if (!employeeController.isEmployeePresent(employeeId)) {
            System.out.println("Employee details not present");
        } else {
            System.out.println(employeeController.deleteEmployee(employeeId));
        }
    }

    /*
     * the mobile number is validated using regex
     */
    private String validateMobileNumber() {
        Scanner scanner = new Scanner(System.in);
        String mobileNumber = scanner.next();
        if (!mobileNumber.matches("[1-9][0-9]{9}")) {
            System.out.println("Enter valid mobile number");
            return validateMobileNumber();
        }
        return mobileNumber;
    }

    /*
     * the date is validated using parsing and exception handling
     */
    private Date validateDate() {
        Scanner scanner = new Scanner(System.in);

        try {
            return new SimpleDateFormat("dd/MM/yyyy").parse(scanner.next());
        } catch (ParseException exception) {
            System.out.println("Enter valid date format");
            return validateDate();
        }
        
    }

}

