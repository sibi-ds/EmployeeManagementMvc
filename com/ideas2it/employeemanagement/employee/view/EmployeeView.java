package com.ideas2it.employeemanagement.employee.view;

import java.sql.Date;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import com.ideas2it.employeemanagement.employee.controller.EmployeeController;

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

    public void view() {
        byte option = 0;

        System.out.println("select an option to perform a particular operation on an employee's details");
        String optionStatement = "1 - Create Employee , 2 - Assign project , 3 - Unassign project, 4 - Display All Employees"
                + " , 5 - Display Employee (Along with Addresses) , 6 - Display Employee (Along with projects)"
                + " , 7 - Update Employee , 8 - Delete Employee , 9 - Restore Employee , 10 - Exit" ;

        while (10 != option) {
            System.out.println(optionStatement);
            option = scanner.nextByte();
            scanner.skip("[\n\r]{2}");

            switch (option) {
                case 1:
                    insertEmployee();
                    break;
                case 2:
                    assignProject();
                    break;
                case 3:
                    unassignProject();
                    break;
                case 4:
                    getEmployees();
                    break;
                case 5:
                    getEmployeeAndAddresses();
                    break;
                case 6:
                    getEmployeeAndProjects();
                    break;
                case 7:
                    updateEmployee();
                    break;
                case 8:
                    deleteEmployee();
                    break;
                case 9:
                    restoreEmployee();
                    break;
                case 10:
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
    private void insertEmployee() {
        System.out.println("Enter employee's details ");
        System.out.print("Name              : ");
        String name = scanner.nextLine();
        System.out.print("DOB (YYYY-MM-DD)  : ");
        Date dob = Date.valueOf(validateDate());
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
     * reads addresses of an employee and store them in a list of addresses
     *
     * @return    list of employee addresses
     */
    private List<List<String>> insertAddresses() {
        List<List<String>> addresses = new ArrayList<List<String>>();

        String addressInsertionStatement = "1 - Insert address , 2 - Exit from address insertion menu";
        String addressTypeStatement  = "1 - Permanant Address , 2 - Temporary Address";
        byte addressInsertionOption = 0;
        boolean isPermanentAddressInserted = false;

        while (2 != addressInsertionOption) {
            System.out.println(addressInsertionStatement);
            addressInsertionOption = scanner.nextByte();

            if (1 == addressInsertionOption) {
                System.out.println("\n-----Enter address details-----\n");
                System.out.println("Enter address type from the following");
                System.out.println(addressTypeStatement);
                byte addressTypeOption = scanner.nextByte();

                switch (addressTypeOption) {
                    case 1:
                        if (isPermanentAddressInserted) {
                            System.out.println("Permanent address inserted already");
                        } else {
                            isPermanentAddressInserted = addresses.add(insertAddress("permanent"));

                            if (isPermanentAddressInserted) {
                                System.out.println("permanent address added to the list");
                            } else {
                                System.out.println("permanent address not added to the list");
                            }
                        }
                        break;
                    case 2:
                        if (addresses.add(insertAddress("temporary"))) {
                            System.out.println("temporary address added to the list");
                        } else {
                            System.out.println("temporary address not added to the list");
                        }
                        break;
                    default:
                        System.out.println("Enter valid option");
                        break;
                }
            } else if (2 == addressInsertionOption) {
                System.out.println("Exited from address insertion");
            } else {
                System.out.println("Enter valid option");
            }
        }
        return addresses;
    }

    /**
     * reads address of an employee and store it in a list of address
     *
     * @param addressType    permanent or current or temporary
     *
     * @return    list of employee address details
     */
    private List<String> insertAddress(String addressType) {
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
     * used to assign project to an employee
     */
    private void assignProject() {
        System.out.print("Enter Employee ID to assign Projects : ");
        int employeeId = scanner.nextInt();

        if (!isEmployeePresent(employeeId)) {
            System.out.println("Employee not present");
        } else {
            System.out.println("Enter from the following options");
            String optionStatement = "1 - Assign project to an employee"
                   + " , 2 - Exit from the project assignation";
            byte option = 0;

            while (2 != option) {
                System.out.println(optionStatement);
                option = scanner.nextByte();

                switch (option) {
                    case 1:
                        assignProjectDetails(employeeId);
                        break;
                    case 2:
                        break;
                    default:
                        System.out.println("Enter valid option");
                }
            }
        }
    }

    /**
     * requests controller to assign project to an employee
     *
     * @param employeeId    for which projects to be assigned
     */
    private void assignProjectDetails(int employeeId) {
        System.out.print("Enter Project ID : ");
        int projectId = scanner.nextInt();

        if (!isProjectPresent(projectId)) {
            System.out.println("Project not present");
        } else {
            if (employeeController.assignProject(employeeId, projectId)) {
                System.out.println("Project assigned successfully");
            } else {
                System.out.println("Project assignation failure");
            }
        }
    }

    /**
     * used to unassign project to an employee
     */
    private void unassignProject() {
        System.out.print("Enter Employee ID to unassign Projects : ");
        int employeeId = scanner.nextInt();

        if (!isEmployeePresent(employeeId)) {
            System.out.println("Employee not present");
        } else {
            System.out.println("Enter from the following options");
            String optionStatement = "1 - Unassign project from an employee"
                   + " , 2 - Exit from the project assignation";
            byte option = 0;

            while (2 != option) {
                System.out.println(optionStatement);
                option = scanner.nextByte();

                switch (option) {
                    case 1:
                        unassignProjectDetails(employeeId);
                        break;
                    case 2:
                        break;
                    default:
                        System.out.println("Enter valid option");
                }
            }
        }
    }

    /**
     * requests controller to unassign project
     *
     * @param employeeId    for which projects to be unassigned
     */
    private void unassignProjectDetails(int employeeId) {
        System.out.print("Enter Project ID : ");
        int projectId = scanner.nextInt();

        if (!isProjectPresent(projectId)) {
            System.out.println("Project not present");
        } else {
            if (employeeController.unassignProject(employeeId, projectId)) {
                System.out.println("Project unassigned");
            } else {
                System.out.println("Project unassignation failure");
            }
        }
    }

    /**
     * requests controller to give the details of all employees
     */
    private void getEmployees() {
        List<String> employees = employeeController.getEmployees();

        if (employees.isEmpty()) {
            System.out.println("Empty Database");
        } else {
            System.out.println("\n-----Employee Database-----\n");
            employees.forEach((employeeDetails) -> {
                System.out.println(employeeDetails);
            });
        }
    }

    /**
     * requests controller to give the details of an employee
     */
    public void getEmployeeAndAddresses() {
        System.out.print("Enter employee ID to get details : ");
        int employeeId = scanner.nextInt();

        if (!isEmployeePresent(employeeId)) {
            System.out.println("Employee not present");
        } else {
            System.out.println(employeeController.getEmployeeAndAddresses(employeeId));
        }
    }

    /**
     * requests controller to give the details of an employee
     */
    public void getEmployeeAndProjects() {
        System.out.print("Enter employee ID to get details : ");
        int employeeId = scanner.nextInt();

        if (!isEmployeePresent(employeeId)) {
            System.out.println("Employee not present");
        } else {
            System.out.println(employeeController.getEmployeeAndProjects(employeeId));
        }
    }

    /**
     * updates details of an employee
     */
    private void updateEmployee() {
        System.out.print("Enter employeeId to update the details : ");
        int employeeId = scanner.nextInt();

        if (!isEmployeePresent(employeeId)) {
            System.out.println("Employee details not present");
        } else {
            System.out.println("Choose which detail need to be updated");
            byte updationOption = 0;
            String updationOptionStatement
                    = "1 - Update Basic Details , 2 - Update Address , 3 - Exit from updation menu";

            while (3 != updationOption) {
                System.out.println(updationOptionStatement);
                updationOption = scanner.nextByte();

                switch (updationOption) {
                    case 1:
                        updateEmployeeDetails(employeeId);
                        break;
                    case 2:
                        updateAddress(employeeId);
                        break;
                    case 3:
                        updationOption = 3;
                        System.out.println("Exited from updation menu");
                        break;
                    default:
                        System.out.println("Enter valid option");
                        break;
                }
            }
        }
    }

    /**
     * updates basic details of an employee
     *
     * @param employeeId    ID for which details should be updated
     */
    private void updateEmployeeDetails(int employeeId) {
        String name = null;
        Date dob = null;
        float salary = 0.0f;
        String mobileNumber = null;

        System.out.println("Choose which detail need to be updated");
        byte updationOption = 0;
        String updationOptionStatement
                = "1 - Update Name , 2 - Update DOB , 3 - Update Salary , 4 - Update Mobile Number"
                + " , 5 - Save and Exit from basic details updation menu";

        while (5 != updationOption) {
            System.out.println(updationOptionStatement);
            updationOption = scanner.nextByte();

            switch (updationOption) {
                case 1:
                    name = updateName();
                    break;
                case 2:
                    dob = updateDob();
                    break;
                case 3:
                    salary = updateSalary();
                    break;
                case 4:
                    mobileNumber = updateMobileNumber();
                    break;
                case 5:
                    updationOption = 5;
                    if (employeeController.updateEmployee(employeeId, name, dob
                            , salary, mobileNumber)) {
                        System.out.println("Updation successful");
                    } else {
                        System.out.println("Updation failure");
                    }
                    System.out.println("Exited from basic details updation menu");
                    break;
                default:
                    System.out.println("Enter valid option");
                    break;
            }
        }
    }

    /**
     * updates name of an employee
     *
     * @return    updated name
     */
    private String updateName() {
        System.out.print("Enter updated NAME : ");
        scanner.skip("[\r\n]{2}");
        String name = scanner.nextLine();
        return name;
    }

    /**
     * updates Date Of Birth of an employee
     *
     * @return    updated date of birth
     */
    private Date updateDob() {
        System.out.print("Enter updated DOB (YYYY-MM-DD) : ");
        Date dob = Date.valueOf(validateDate());
        return dob;
    }

    /**
     * updates salary of an employee
     *
     * @return    updated salary
     */
    private float updateSalary() {
        System.out.print("Enter updated SALARY : ");
        float salary = scanner.nextFloat();
        return salary;
    }

    /**
     * updates mobile number of an employee
     *
     * @return    updated mobile number
     */
    private String updateMobileNumber() {
        System.out.print("Enter updated MOBILE NUMBER : ");
        String mobileNumber = validateMobileNumber();
        return mobileNumber;
    }

    /**
     * updates address of an employee
     *
     * @param employeeId    ID for which address should be updated
     */
    private void updateAddress(int employeeId) {
        Map<Integer, String> addresses = employeeController.getAddresses(employeeId);
        List<Integer> addressIds = new ArrayList<>(addresses.keySet());

        byte addressUpdationOption = 0;
        String addressUpdationStatement = "1 - Add Address , 2 - Update Address"
                + " , 3 - Delete Address , 4 - Display Addresses Available , 5 - Exit From Address Updation Menu";

        System.out.println("Select an opeartion to perform on address");

        while (5 != addressUpdationOption) {
            System.out.println(addressUpdationStatement);
            addressUpdationOption = scanner.nextByte();

            switch (addressUpdationOption) {
                case 1:
                    addAddress(employeeId);
                    break;
                case 2:
                    updateAddressValues(employeeId, addressIds);
                    break;
                case 3:
                    deleteAddress(employeeId, addressIds);
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

        addresses = employeeController.getAddresses(employeeId);
        addressIds = new ArrayList<>(addresses.keySet());
        }  
    }

    /**
     * reads the employeeId from the user and request
     * the controller to add an address to an employee
     *
     * @param employeeId    in which address should be added
     */
    private void addAddress(int employeeId) {
        System.out.println("Choose address type\n"
                + "1 - Permanent Address , 2 - Temporary Address");

        byte addressTypeOption = scanner.nextByte();
        String addressType = null;

        switch (addressTypeOption) {
            case 1:
                addressType = "permanent";
                break;
            case 2:
                addressType = "temporary";
                break;
            default:
                addressTypeOption = 0;
                break;
        }

        if (3 == addressTypeOption) {
            System.out.println("Exited from new address insertion");
        } else if (0 == addressTypeOption) {
            System.out.println("Incorrect option");
        } else {
            if (employeeController.addAddress(employeeId, insertAddress(addressType))) {
                System.out.println("Address added successfully");
            } else {
                System.out.println("Address insertion failure");
            }
        }
    } 

    /**
     * reads the employeeId from the user and request
     * the controller to update values of an address
     *
     * @param employeeId    for which address should be updated
     * @param addressIds    list of address IDs
     */
    private void updateAddressValues(int employeeId, List<Integer> addressIds) {

        if (addressIds.isEmpty()) {
            System.out.println("No addresses stored in the database");
        } else {
            System.out.print("Enter corresponding serial number to be updated : ");
            int addressId = scanner.nextInt();

            if ((1 <= addressId) && (addressIds.size() >= addressId)) {
                System.out.println("\nChoose address type\n"
                        + "1 - Permanent Address , 2 - Temporary Address");

                byte addressTypeOption = scanner.nextByte();
                String addressType = null;

                switch (addressTypeOption) {
                    case 1:
                        addressType = "permanent";
                        break;
                    case 2:
                        addressType = "temporary";
                        break;
                    default:
                        System.out.println("Enter valid option");
                        break;
                }

                if (employeeController.updateAddressValues(employeeId, (addressId - 1)
					    , addressIds.get(addressId - 1), insertAddress(addressType))) {
                    System.out.println("Address values updated successfully");
                } else {
                    System.out.println("Address values updation failure");
                }
            } else {
                System.out.println("Invalid address ID");
            }
        }
    } 

    /**
     * reads the addressId from the user and requests
     * the controller to remove that address from the employee
     *
     * @param employeeId    for which address should be updated
     * @param addressIds    list of address IDs
     */
    private void deleteAddress(int employeeId, List<Integer> addressIds) {
        if (addressIds.isEmpty()) {
            System.out.println("No addresses stored in the database");
        } else {
            System.out.print("Enter address ID to be deleted : ");
            int addressId = scanner.nextInt();

            if ((addressId >= 1) && (addressId <= addressIds.size())) {
                if (employeeController.deleteAddress(employeeId, (addressId - 1))) {
                    System.out.println("address deletion successful");
                } else {
                    System.out.println("address deletion failure");
                }
            } else {
                System.out.println("Address not exist");
            }
        }
    } 

    /**
     * requests service to get addresses of an employee
     *
     * @param employeeId    for which addresses to be displayed
     */
    private void getAddresses(int employeeId, List<Integer> addressIds, Map<Integer, String> addresses) {
        if (addressIds.isEmpty()) {
            System.out.println("No addresses stored for this employee");
        } else {
            System.out.println("\n-----The stored addresses of your Employee Id-----\n");
            addresses.forEach((addressId, address) -> {
                System.out.println(address);
            });
        }
    }

    /**
     * reads the employeeId and request
     * the controller to remove the details of an employee
     */
    private void deleteEmployee() {
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
     * restores the employee details that had been deleted
     */
    private void restoreEmployee() {
        List<String> deletedEmployees = employeeController.getDeletedEmployees();

        if (!deletedEmployees.isEmpty()) {
            byte restoreOption = 0;
            String restoreOptionStatement = "1 - Restore an Employee Details"
                   + " , 2 - Display Deleted , 3 - Exit from restore menu";

            System.out.println("Select from the following option");

            while (3 != restoreOption) {
                System.out.println(restoreOptionStatement);
                restoreOption = scanner.nextByte();

                switch (restoreOption) {
                    case 1:
                        restoreDeletedEmployeeDetails();
                        break;
                    case 2:
                        displayDeletedEmployees(deletedEmployees);
                        break;
                    case 3:
                        System.out.println("Exited from the restore menu");
                        restoreOption = 3;
                        break;
                    default:
                        System.out.println("Enter valid option");
                        break;
                }

                deletedEmployees = employeeController.getDeletedEmployees();

                if (deletedEmployees.isEmpty()) {
                    System.out.println("All details are restored");
                    break;
                }
            }
        } else {
            System.out.println("No deleted records");
        }
    }

    /**
     * reads employee ID and restores that employee
     */
    private void restoreDeletedEmployeeDetails() {
        System.out.print("Enter employee ID that to be restored : ");
        int employeeId = scanner.nextInt();

        if (isEmployeePresent(employeeId)) {
            System.out.println("Employee details already in the database");
        } else {
			if (!isEmployeePresent(employeeId)) {
				System.out.println("Employee never exist");
            } else if (employeeController.restoreEmployee(employeeId)) {
                System.out.println("Employee restored successfully");
            } else {
                System.out.println("Restoration failure");
            }
        }
    }

    /**
     * displays the deleted employees details
     */
    private void displayDeletedEmployees(List<String> deletedEmployees) {
        if (deletedEmployees.isEmpty()) {
            System.out.println("No deleted employees");
        } else {
            deletedEmployees.forEach((employee) -> {
                System.out.println(employee);
            });
        }
    }

    /**
     * used to check whether the details of an employee present or not
     *
     * @param employeeId    employeeId to verify the exixtence
     *
     * @return    true if employee present in database else false
     */
    private boolean isEmployeePresent(int employeeId) {
        return employeeController.isEmployeePresent(employeeId);
    }

    /**
     * checks whether project present or not
     *
     * @param projectId    Project ID
     *
     * @return    true if project present else false
     */
    private boolean isProjectPresent(int projectId) {
        return employeeController.isProjectPresent(projectId);
    }

    /**
     * the mobile number is validated using regex validation
     *
     * @return    valid mobile number
     */
    private String validateMobileNumber() {
        String mobileNumber = scanner.next();
        if (!mobileNumber.matches("[1-9][0-9]{9}")) {
            System.out.print("Enter valid mobile number : ");
            return validateMobileNumber();
        }
        return mobileNumber;
    }

    /**
     * the date is validated using regex validation
     *
     * @return    valid date
     */
    private String validateDate() {
        String dob = scanner.next();
        if (!dob.matches("^((19|20)\\d\\d)-(0?[1-9]|1[012])-(0?[1-9]|[12][0-9]|3[01])$")) {
            System.out.print("Enter valid date format (YYYY-MM-DD) : ");
            return validateDate();
        }
        return dob;
    }
}

