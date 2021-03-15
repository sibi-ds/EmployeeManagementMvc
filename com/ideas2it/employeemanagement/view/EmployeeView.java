package com.ideas2it.employeemanagement.view;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
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

        if (employeeController.insertEmployee(name, dob, salary, mobileNumber, insertAddresses())) {
            System.out.println("Employee details stored successfully");
        } else {
            System.out.println("Insertion failure");
        }
    }

    /**
     * method to store addresses of all types of an employee
     *
     * @return    list of addresses
     */
    private List<List<String>> insertAddresses() throws ClassNotFoundException, SQLException {
        List<List<String>> addresses = new ArrayList<List<String>>();

        System.out.println("\n-----Enter address details-----\n");
        System.out.println("Enter address type from the following");
        String addressTypeOption = "S - Current and Permanent addresses are same "
                + ", P -Permanant Address , C - Current Address , T - Temporary Address , Q - Quit";
        char addressType = '\0';
        boolean isPermanentAddressInserted = false;
        boolean isCurrentAddressInserted = false;

        while ('Q' != addressType) {
            System.out.println(addressTypeOption);
            addressType = scanner.next().charAt(0);

            switch (addressType) {
                case 'S':
                    if (isCurrentAddressInserted && isPermanentAddressInserted) {
                        System.out.println("Permanent and Current address inserted already");
                    } else {
                        boolean isBothAddressesInserted = addresses.add(insertAddress("permanent and current"));
                        isPermanentAddressInserted = isBothAddressesInserted;
                        isCurrentAddressInserted = isBothAddressesInserted;
                    }
                    break;
                case 'P':
                    if (isPermanentAddressInserted) {
                        System.out.println("Permanent address inserted already");
                    } else {
                        isPermanentAddressInserted = addresses.add(insertAddress("permanent"));
                    }
                    break;
                case 'C':
                    if (isCurrentAddressInserted) {
                        System.out.println("Permanent address inserted already");
                    } else {
                        isCurrentAddressInserted = addresses.add(insertAddress("current"));
                    }
                    break;
                case 'T':
                    if (addresses.add(insertAddress("temporary"))) {
                        System.out.println("temporary address inserted successfully");
                    } else {
                        System.out.println("temporary address insertion failure");
                    }
                    break;
                case 'Q':
                    System.out.println("Insertion cancelled");
                    break;
                default:
                    System.out.println("Enter valid option");
                    break;
            }
        }
        return addresses;
    }

    /**
     * used to create address object
     *
     * @param addressType    permanent or current or temporary
     *
     * @return    list of employee address details
     */
    private List<String> insertAddress(String addressType) throws ClassNotFoundException, SQLException {
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
        String pincode = scanner.nextLine();

        List<String> address = new ArrayList<String>();

        address.add(addressType);
        address.add(doorNumber);
        address.add(street);
        address.add(village);
        address.add(district);
        address.add(state);
        address.add(pincode);

        return address;
    }

    /**
     * request controller to
     * give the deails of all employeea
     */
    private void getEmployees() throws ClassNotFoundException, SQLException {
        System.out.println("\n-----Employee Database-----\n");
        employeeController.getEmployees().forEach((employeeDetails) -> {
            System.out.println(employeeDetails);
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
                    = "N - Name , D - DOB , S - Salary , M - Mobile number , A - Address , C - Cancel Updation";

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
                    case 'A':
                        updateAddress(employeeId);
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
     * updates address of an employee
     *
     * @param employeeId    ID for which address should be updated
     */
    private void updateAddress(int employeeId) throws ClassNotFoundException, SQLException {
        System.out.println("\n-----The addresses for your Employee Id-----");

        employeeController.getAddresses(employeeId).forEach((address) -> {
            System.out.println(address);
        });

        System.out.print("Enter corresponding Address ID to update : ");
        int addressId = scanner.nextInt();

        System.out.println("Enter address type\n"
                + "S - Permanent and current addresses are same , P - Permanent "
                + ", C - Current , T - Temporary , Q - Quit");

        char addressTypeOption = scanner.next().charAt(0);
        String addressType = null;

        switch (addressTypeOption) {
            case 'S':
                addressType = "permanent and current";
                break;
            case 'P':
                addressType = "permanent";
                break;
            case 'C':
                addressType = "current";
                break;
            case 'T':
                addressType = "temporary";
                break;
            case 'Q':
                System.out.println("Updation cancelled");
                break;
            default:
                System.out.println("Option is incorrect");
                break;
        }        

        if (employeeController.updateAddress(employeeId, addressId, insertAddress(addressType))) {
            System.out.println("Address updated successfully");
        } else {
            System.out.println("Address updation failure");
        }
    }

    /**
     * reads the employeeId from the user and request
     * the controller to remove the details of an employee
     */
    private void deleteEmployee() throws ClassNotFoundException, SQLException {
        System.out.print("Enter employee ID to delete the employee : ");
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

