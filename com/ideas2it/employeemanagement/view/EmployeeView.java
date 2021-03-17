package com.ideas2it.employeemanagement.view;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
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
        String optionStatement = "1 - Create Employee , 2 - Display All Employees ,"
                + " 3 - Display Employee , 4 - Update Employee , 5 - Delete Employee , 6 - Restore Employee , 7 - Exit" ;

        while (7 != option) {
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
                    restoreEmployee();
                    break;
                case 7:
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
        String addressTypeStatement 
                = "1 - Permanant Address , 2 - Temporary Address , 3 - Exit from address insertion";
        byte addressTypeOption = 0;
        boolean isPermanentAddressInserted = false;

        while (3 != addressTypeOption) {
            System.out.println(addressTypeStatement);
            addressTypeOption = scanner.nextByte();

            switch (addressTypeOption) {
                case 1:
                    if (isPermanentAddressInserted) {
                        System.out.println("Permanent address inserted already");
                    } else {
                        isPermanentAddressInserted = addresses.add(insertAddress("permanent"));
                    }
                    break;
                case 2:
                    if (addresses.add(insertAddress("temporary"))) {
                        System.out.println("temporary address inserted successfully");
                    } else {
                        System.out.println("temporary address insertion failure");
                    }
                    break;
                case 3:
                    System.out.println("Exited from address insertion");
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
        List<String> employees = employeeController.getEmployees();

        if (0 == employees.size()) {
            System.out.println("Empty Database");
        } else {
            System.out.println("\n-----Employee Database-----\n");
            employees.forEach((employeeDetails) -> {
                System.out.println(employeeDetails);
            });
        }
    }

    /**
     * request controller to
     * give the deails of the employee
     *
     * @param employeeId    which need to be extracted from the database
     */
    public void getEmployee() throws ClassNotFoundException, SQLException {
        System.out.print("Enter employee ID to get details : ");
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
        System.out.print("Enter employeeId to update the details : ");
        int employeeId = scanner.nextInt();

        if (!isEmployeePresent(employeeId)) {
            System.out.println("Employee details not present");
        } else {
            System.out.println("Choose which detail need to be updated");
            byte updationOption = 0;
            String updationOptionStatement
                    = "1 - Update Name , 2 -Update DOB , 3 - Update Salary , 4 - Update Mobile number"
                    + " , 5 - Update Address , 6 - Exit from employee updation menu";

            while (6 != updationOption) {
                System.out.println(updationOptionStatement);
                updationOption = scanner.nextByte();

                switch (updationOption) {
                    case 1:
                        updateName(employeeId);
                        break;
                    case 2:
                        updateDob(employeeId);
                        break;
                    case 3:
                        updateSalary(employeeId);
                        break;
                    case 4:
                        updateMobileNumber(employeeId);
                        break;
                    case 5:
                        updateAddress(employeeId);
                        break;
                    case 6:
                        System.out.println("Exited from updation menu");
                        updationOption = 6;
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
        Map<Integer, String> addresses = employeeController.getAddresses(employeeId);
        List<Integer> addressIds = new ArrayList<>(addresses.keySet());

        byte addressUpdationOption = 0;
        String addressUpdationStatement = "1 - Add Address , 2 - Update Address"
                + " , 3 - Delete Address , 4 - Display Addresses Available , 5 - Exit From Address Updation Menu";

            System.out.println("Select an opeartion to perform on an address");

            while (5 != addressUpdationOption) {
                System.out.println(addressUpdationStatement);
                addressUpdationOption = scanner.nextByte();

                switch (addressUpdationOption) {
                    case 1:
                        addresses = addAddress(employeeId);
                        addressIds = new ArrayList<>(addresses.keySet());
                        break;
                    case 2:
                        addresses = updateAddressValues(employeeId, addressIds);
                        addressIds = new ArrayList<>(addresses.keySet());
                        break;
                    case 3:
                        addresses = deleteAddress(employeeId, addressIds);
                        addressIds = new ArrayList<>(addresses.keySet());
                        break;
                    case 4:
                        getAddresses(employeeId, addressIds, addresses);
                        break;
                    case 5:
                        addressUpdationOption = 5;
                        break;
                    default:
                        System.out.println("Enter valid option");
                        break;
                }
            }  
    }

    /**
     * reads the employeeId from the user and request
     * the controller to add an address to an employee
     *
     * @param employeeId    in which address should be added
     *
     * @return    updated details of the address database
     */
    private Map<Integer, String> addAddress(int employeeId) throws ClassNotFoundException, SQLException {
        System.out.println("Choose address type\n"
                + "1 - Permanent Address , 2 - Temporary Address , 3 - Exit from address insertion");

        byte addressTypeOption = scanner.nextByte();
        String addressType = null;

        switch (addressTypeOption) {
            case 1:
                addressType = "permanent";
                break;
            case 2:
                addressType = "temporary";
                break;
            case 3:
                addressTypeOption = 3;
                break;
            default:
                System.out.println("Incorrect option");
                break;
        }

        if (3 == addressTypeOption) {
            System.out.println("Exited from new address insertion");
        } else if (employeeController.addAddress(employeeId, insertAddress(addressType))) {
            System.out.println("Address added successfully");
        } else {
            System.out.println("Address insertion failure");
        }

        return employeeController.getAddresses(employeeId);
    } 

    /**
     * reads the employeeId from the user and request
     * the controller to update values of an address
     *
     * @param employeeId    for which address should be updated
     * @param addressIds    list of address IDs
     *
     * @return    updated details of the address database
     */
    private Map<Integer, String> updateAddressValues(int employeeId, List<Integer> addressIds)
            throws ClassNotFoundException, SQLException {

        if (0 == addressIds.size()) {
            System.out.println("No addresses stored in the database");
        } else {
            System.out.print("Enter address ID to be updated : ");
            int addressId = scanner.nextInt();

            System.out.println("\nChoose address type\n"
                    + "1 - Permanent Address , 2 - Temporary Address  , 3 - Addression insertion cancelled");

            byte addressTypeOption = scanner.nextByte();
            String addressType = null;

            switch (addressTypeOption) {
                case 1:
                    addressType = "permanent";
                    break;
                case 2:
                    addressType = "temporary";
                    break;
                case 3:
                    addressTypeOption = 3;
                    break;
                default:
                    System.out.println("Enter valid option");
                    break;
            }

            if (3 == addressTypeOption) {
                System.out.println("Exited from address values updation");
            } else if ((1 <= addressId) && (addressIds.size() >= addressId)) {
                if (employeeController
                        .updateAddressValues(employeeId, addressIds.get(addressId - 1), insertAddress(addressType))) {
                    System.out.println("Address values updated successfully");
                } else {
                    System.out.println("Address values updation failure");
                }
            } else {
                System.out.println("Invalid address ID");
            }
        }
        return employeeController.getAddresses(employeeId);
    } 

    /**
     * reads the addressId from the user and request
     * the controller to remove that address of an employee
     *
     * @param employeeId    for which address should be updated
     * @param addressIds    list of address IDs
     *
     * @return    updated details of the address database
     */
    private Map<Integer, String> deleteAddress(int employeeId, List<Integer> addressIds)
            throws ClassNotFoundException, SQLException {
        if (0 == addressIds.size()) {
            System.out.println("No addresses stored in the database");
        } else {
            System.out.print("Enter address ID to be deleted : ");
            int addressId = scanner.nextInt();

            if ((addressId >= 1) && (addressId <= addressIds.size())) {
                if (employeeController.deleteAddress(employeeId, addressIds.get(addressId - 1))) {
                    System.out.println("address deletion successful");
                } else {
                    System.out.println("address deletion failure");
                }
            } else {
                System.out.println("Address not exist");
            }
        }
        return employeeController.getAddresses(employeeId);
    } 

    /**
     * requests service to get addresses for an employee
     *
     * @param employeeId    for which addresses to be displayed
     */
    private void getAddresses(int employeeId, List<Integer> addressIds, Map<Integer, String> addresses)
            throws ClassNotFoundException, SQLException {
        if (0 == addressIds.size()) {
            System.out.println("No addresses stored for this employee");
        } else {
            System.out.println("\n-----The stored addresses of your Employee Id-----");
            addresses.forEach((addressId, address) -> {
                System.out.println(address);
            });
        }
    }

    /**
     * reads the employeeId from the user and request
     * the controller to remove the details of an employee
     */
    private void deleteEmployee() throws ClassNotFoundException, SQLException {
        System.out.print("Enter employee ID to delete the employee : ");
        int employeeId = scanner.nextInt();

        if (!isEmployeePresent(employeeId)) {
            System.out.println("Employee details not present");
        } else if (employeeController.deleteEmployee(employeeId)) {
            System.out.println("Deletion successful");
        } else {
            System.out.println("Deletion failure");
        }
    }

    /**
     * restore the employee details that had been deleted
     */
    private void restoreEmployee() throws ClassNotFoundException, SQLException {
        System.out.println("-----Deleted Employee List-----");
        List<String> deletedEmployees = employeeController.getDeleted();

        if (0 != deletedEmployees.size()) {
            deletedEmployees.forEach((employeeId) -> {
                System.out.println(employeeId);
            });

            byte restoreOption = 0;
            String restoreOptionStatement = "1 - Restore an Employee Details"
                   + " , 2 - Display Deleted , 3 - Exit from restore menu";

            System.out.println("Select from the following option");
            while (3 != restoreOption) {
                System.out.println(restoreOptionStatement);
                restoreOption = scanner.nextByte();

                switch (restoreOption) {
                    case 1:
                        System.out.print("Enter employee ID that to be restored : ");
                        int employeeId = scanner.nextInt();
                    
                        if (isEmployeePresent(employeeId)) {
                                System.out.println("Employee details already in database");
                        } else if (employeeController.restoreDeleted(employeeId)) {
                                System.out.println("Employee restored successfully");
                        } else {
                            System.out.println("Restoration failure");
                        }
                        break;
                    case 2:
                        restoreEmployee();
                        break;
                    case 3:
                        System.out.println("Exited from the restore menu");
                        break;
                    default:
                        System.out.println("Enter valid option");
                        break;
                }
            }
        } else {
            System.out.println("No deleted records");
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

