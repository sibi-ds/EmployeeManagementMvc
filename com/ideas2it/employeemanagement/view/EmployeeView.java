package com.ideas2it.employeemanagement.view;

import java.sql.Date;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;
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
    private Scanner scanner = new Scanner(System.in);

    public void view() throws ClassNotFoundException, SQLException {
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
                    insertEmployee();
                    break;
                case 2:
                    getEmployees();
                    break;
                case 3:
                    getEmployee();
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

    /**
     * reads the employee details from the user and
     * request the controller to store the details
     */
    private void insertEmployee() throws ClassNotFoundException, SQLException {
        System.out.println("Enter employee's details ");
        System.out.print("Name              : ");
        String name = scanner.nextLine();
        System.out.print("DOB (YYYY/MM/DD)  : ");
        Date dob = Date.valueOf(scanner.next());
        System.out.print("Salary            : ");
        float salary = scanner.nextFloat();
        System.out.print("Mobile Number     : ");
        String mobileNumber = validateMobileNumber();

        if (employeeController.insertEmployee(name, dob, salary, mobileNumber)) {
            System.out.println("Employee basic details stored successfully");
            insertAddresses();
        } else {
            System.out.println("Insertion failure");
        }
    }

    /**
     * used to insert addresses of all types
     */
    private void insertAddresses() throws ClassNotFoundException, SQLException {
        System.out.println("\n-----Enter address details-----\n");
        System.out.println("Enter address type from the following");
        String addressTypeOption = "S - Current and Permanent addresses are same "
                + ", P -Permanant Address , C - Current Address , T - Temporary Address , Q - Quit";
        char addressType = '\0';
        boolean isCurrentAddressInserted = false;
        boolean isPermanentAddressInserted = false;

        while ('Q' != addressType) {
            System.out.println(addressTypeOption);
            addressType = scanner.next().charAt(0);

            switch (addressType) {
                case 'S':
                    if ((true == isPermanentAddressInserted) && (true == isCurrentAddressInserted)) {
                        System.out.println("Permanent and Current address already entered\nChoose from other options");
                    } else {
                        if (insertAddress('S')) {
                            isPermanentAddressInserted = true;
                            isCurrentAddressInserted = true;
                            System.out.println("Permanent and Current address stored successfully");
                        } else {
                            System.out.println("Address insertion failure");
                        }
                    }
                    break;
                case 'P':
                    if (true == isPermanentAddressInserted) {
                        System.out.println("Permanent address already entered\nChoose from other options");
                    } else {
                        if (insertAddress('P')) {
                            isPermanentAddressInserted = true;
                            System.out.println("Permanent address stored successfully");
                        } else {
                            System.out.println("Address insertion failure");
                        }
                    }
                    break;
                case 'C':
                    if (true == isCurrentAddressInserted) {
                        System.out.println("Current address already entered\nChoose from other options");
                    } else {
                        if (insertAddress('C')) {
                            isCurrentAddressInserted = true;
                            System.out.println("Current address stored successfully");
                        } else {
                            System.out.println("Address insertion failure");
                        }
                    }
                    break;
                case 'T':
                    if (insertAddress('T')) {
                        System.out.println("Temporary address stored successfully");
                    } else {
                        System.out.println("Address insertion failure");
                    }
                    break;
                case 'Q':
                    addressType = 'Q';
                    System.out.println("Insertion cancelled");
                    break;
                default:
                    System.out.println("Enter valid option");
                    break;
            }
        }
    }

    /**
     * reads employee address details and
     * request controller to store details
     *
     * @param addressType    whether current or permanent or temporary address
     * @param isInserted     check whether address already entered or not
     *
     * @return    true if address stored successfully
     */
    private boolean insertAddress(char addressType) throws ClassNotFoundException, SQLException {
        scanner.skip("[\n\r]{2}");
        System.out.print("Door Number   : ");
        String doorNumber = scanner.nextLine();
        System.out.print("Street        : ");
        String street = scanner.nextLine();
        System.out.print("Village       : ");
        String village = scanner.nextLine();
        System.out.print("District      : ");
        String district = scanner.nextLine();
        System.out.print("State         : ");
        String state = scanner.nextLine();
        System.out.print("Pin Code      : ");
        int pincode = scanner.nextInt();

        return employeeController.insertAddress(addressType, doorNumber, street, village, district, state, pincode);
    }

    /**
     * request controller to
     * give the deails of all employeea
     */
    private void getEmployees() throws ClassNotFoundException, SQLException {
        System.out.println("\n-----Employee Database-----\n");
        employeeController.getEmployees().forEach((employeeId, employeeDetails) -> {
            System.out.println("ID            : " + employeeId + employeeDetails);
        });
    }

    /**
     * request controller to
     * give the deails of the employee
     *
     * @param employeeId    which need to be extracted from the database
     */
    public void getEmployee() throws ClassNotFoundException, SQLException {
        System.out.println("Enter employee ID to get details : ");
        int employeeId = scanner.nextInt();

        if (!isEmployeePresent(employeeId)) {
            System.out.println("Employee not present");
        } else {
            System.out.println(employeeController.getEmployee(employeeId));
        }
    }

    /**
     * updates certain detail of an employee
     */
    private void updateEmployee() throws ClassNotFoundException, SQLException {
        System.out.println("Enter employeeId to update the details");
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
                        updateName(employeeId);
                        break;
                    case 'D':
                        updateDob(employeeId);
                        break;
                    case 'S':
                        updateSalary(employeeId);
                        break;
                    case 'M':
                        updateMobileNumber(employeeId);
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

    /**
     * updates name of an employee
     *
     * @param employeeId    ID for which name should be updated
     */
    private void updateName(int employeeId) throws ClassNotFoundException, SQLException {
        System.out.println("Enter updated NAME");
        scanner.skip("[\r\n]{2}");
        String name = scanner.nextLine();

        if (employeeController.updateName(employeeId, name)) {
            System.out.println("Updation Successful");
        } else {
            System.out.println("Updation failure");
        }
    }

    /**
     * updates Date Of Birth of an employee
     *
     * @param employeeId    ID for which Date Of Birth should be updated
     */
    private void updateDob(int employeeId) throws ClassNotFoundException, SQLException {
        System.out.println("Enter updated DOB (YYYY/MM/DD)");
        Date dob = Date.valueOf(scanner.next());

        if (employeeController.updateDob(employeeId, dob)) {
            System.out.println("Updation Successful");
        } else {
            System.out.println("Updation failure");
        }
    }

    /**
     * updates salary of an employee
     *
     * @param employeeId    ID for which salary should be updated
     */
    private void updateSalary(int employeeId) throws ClassNotFoundException, SQLException {
        System.out.println("Enter updated SALARY");
        float salary = scanner.nextFloat();

        if (employeeController.updateSalary(employeeId, salary)) {
            System.out.println("Updation Successful");
        } else {
            System.out.println("Updation failure");
        }
    }

    /**
     * updates mobile number of an employee
     *
     * @param employeeId    ID for which mobile number should be updated
     */
    private void updateMobileNumber(int employeeId) throws ClassNotFoundException, SQLException {
        System.out.println("Enter updated MOBILE NUMBER");
        String mobileNumber = validateMobileNumber();

        if (employeeController.updateMobileNumber(employeeId, mobileNumber)) {
            System.out.println("Updation Successful");
        } else {
            System.out.println("Updation failure");
        }
    }

    /**
     * reads the employeeId from the user and request
     * the controller to remove the details of an employee
     */
    private void deleteEmployee() throws ClassNotFoundException, SQLException {
        System.out.println("Enter employee ID to delete the employee");
        int employeeId = scanner.nextInt();

        if (!employeeController.isEmployeePresent(employeeId)) {
            System.out.println("Employee details not present");
        } else if (employeeController.deleteEmployee(employeeId)) {
            System.out.println("Deletion successful");
        } else {
            System.out.println("Deletion failure");
        }
    }

    /**
     * used to check whether the details of an employee present or not
     *
     * @param employeeId    employeeId to verify the exixtence
     *
     */
    private boolean isEmployeePresent(int employeeId)
            throws ClassNotFoundException, SQLException {
        return employeeController.isEmployeePresent(employeeId);
    }

    /**
     * the mobile number is validated using regex
     */
    private String validateMobileNumber() {
        String mobileNumber = scanner.next();
        if (!mobileNumber.matches("[1-9][0-9]{9}")) {
            System.out.println("Enter valid mobile number");
            return validateMobileNumber();
        }
        return mobileNumber;
    }
}

