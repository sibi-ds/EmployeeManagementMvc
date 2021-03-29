package com.ideas2it.employeemanagement.project.view;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.ideas2it.employeemanagement.project.controller.ProjectController;

/**
 * this class used to interact with the user
 * that interactions are taking input from the user
 * and display the details as per user's request
 *
 * @author  sibi
 * @created 2021-03-24
 */
public class ProjectView {

    private ProjectController projectController = new ProjectController();
    private Scanner scanner = new Scanner(System.in);

    public void view() {
        byte option = 0;

        System.out.println("Select an operation to perform on a project");
        String optionStatement = "1 - Create Project , 2 - Assign employees to a project"
                + " , 3 - Display All Projects , 4 - Display Project , 5 - Update Project"
                + " , 6 - Delete Project , 7 - Restore Project , 8 - Exit" ;

        while (8 != option) {
            System.out.println(optionStatement);
            option = scanner.nextByte();

            switch (option) {
                case 1:
                    insertProject();
                    break;
                case 2:
                    assignEmployees();
                    break;
                case 3:
                    getProjects();
                    break;
                case 4:
                    getProject();
                    break;
                case 5:
                    updateProject();
                    break;
                case 6:
                    deleteProject();
                    break;
                case 7:
                    restoreProject();
                    break;
                case 8:
                    break;
                default:
                    System.out.println("Enter valid option");
            }
        }
    }

    /**
     * reads the project details from the user and
     * request the controller to store the details
     */
    private void insertProject() {
        System.out.println("Enter employee's details ");
        System.out.print("Project Title      : ");
        scanner.skip("[\n\r]{2}");
        String projectTitle = scanner.nextLine();
        System.out.print("Client Name        : ");
        String clientName = scanner.nextLine();
        System.out.print("Project Manager ID : ");
        int managerId = scanner.nextInt();
        System.out.print("Project Start Date : ");
        Date startDate = Date.valueOf(validateDate());
        System.out.print("Project End Date   : ");
        Date endDate = Date.valueOf(validateDate());

        if (projectController.insertProject(projectTitle
                , clientName, managerId, startDate, endDate)) {
            System.out.println("Project details stored successfully");
        } else {
            System.out.println("Insertion failure");
        }
    }

    /**
     * assign employees to a project
     */
    private void assignEmployees() {
        System.out.print("Enter Project ID to assign employees : ");
        int projectId = scanner.nextInt();

        if (!isProjectPresent(projectId)) {
            System.out.println("Project not present");
        } else {
            List<Integer> employeeIds = new ArrayList<Integer>();
            byte option = 0;
            String employeeIdStatement = "Enter Employee ID : ";
            String optionStatement = "1 - Assign employee , 2 - Save , 3 - Exit";
            System.out.println("Enter employee IDs to assign");

            while (2 != option) {
                System.out.println(optionStatement);
                option = scanner.nextByte();

                if (1 == option) {
                    System.out.print(employeeIdStatement);
                    int employeeId = scanner.nextInt();

                    if (isEmployeePresent(employeeId)) {
                        employeeIds.add(employeeId);
                    } else {
                        System.out.println("Employee not present");
                    }
                } else if (2 == option) {
                    if (projectController.assignEmployees(projectId, employeeIds)) {
                        System.out.println("Employees assigned successfully");
                    } else {
                        System.out.println("Employees assignation failure");
                    }
                } else if (3 == option) {
                    System.out.println("Exited from assignation menu");
                } else {
                    System.out.println("Enter valid option");
                }
            }
        }
    }

    /**
     * requests controller to give the details of all projects
     */
    private void getProjects() {
        List<String> projects = projectController.getProjects();

        if (projects.isEmpty()) {
            System.out.println("Empty Database");
        } else {
            System.out.println("\n-----Project Database-----\n");
            projects.forEach((project) -> {
                System.out.println(project);
            });
        }
    }

    /**
     * requests controller to give the details of an project
     *
     * @param projectId    whose details to be retrieved from the database
     */
    private void getProject() {
        System.out.print("Enter Project ID to get details : ");
        int projectId = scanner.nextInt();

        if (!isProjectPresent(projectId)) {
            System.out.println("Project not present");
        } else {
            System.out.println(projectController.getProject(projectId));
        }
    }

    /**
     * updates details of a project and employees assignation
     */
    private void updateProject() {
        System.out.print("Enter Project ID to update the details : ");
        int projectId = scanner.nextInt();

        if (!isProjectPresent(projectId)) {
            System.out.println("Project details not present");
        } else {
            System.out.println("Enter from the following updation option");
            byte option =0;
            String updationStatement = "1 - Update project details"
                    + " , 2 - Update employees assigned "
                    + " , 3 - Exit from updation menu";

            while (3 != option) {
                System.out.println(updationStatement);
                option = scanner.nextByte();

                switch (option) {
                    case 1:
                        updateProjectDetails(projectId);
                        break;
                    case 2:
                        updateEmployeesAssigned(projectId);
                        break;
                    case 3:
                        System.out.println("Exited from project updation menu");
                        break;
                    default:
                        System.out.println("Enter valid option");
                }
            }
        }
        
    }

    /**
     * updates basic details of a project
     *
     * @param projectId    for which details to be updated
     */
    private void updateProjectDetails(int projectId) {
        String projectTitle = null;
        String clientName = null;
        int managerId = 0;
        Date startDate = null;
        Date endDate = null;

        System.out.println("Choose which detail need to be updated");
        byte updationOption = 0;
        String updationOptionStatement = "1 - Update Project Title , 2 - Update Client Name"
                + " , 3 - Update Manager ID , 4 - Update Start Date , 5 - Update End Date"
                + " , 6 - Save and Exit from basic details updation menu";

        while (6 != updationOption) {
            System.out.println(updationOptionStatement);
            updationOption = scanner.nextByte();

            switch (updationOption) {
                case 1:
                    projectTitle = updateProjectTitle();
                    break;
                case 2:
                    clientName = updateClientName();
                    break;
                case 3:
                    managerId = updateManagerId();
                    break;
                case 4:
                    startDate = updateStartDate();
                    break;
                case 5:
                    endDate = updateEndDate();
                    break;
                case 6:
                    updationOption = 6;
                    updateProjectDetails(projectId, projectTitle, clientName
                            , managerId, startDate, endDate);
                    System.out.println("Exited from project updation menu");
                    break;
                default:
                    System.out.println("Enter valid option");
                    break;
            }
        }
    }

    /**
     * updates title of a project
     *
     * @returns    updated project title
     */
    private String updateProjectTitle() {
        System.out.print("Enter updated Project Title : ");
        scanner.skip("[\r\n]{2}");
        String projectTitle = scanner.nextLine();
        return projectTitle;
    }

    /**
     * updates client name of a project
     *
     * @returns    updated client name
     */
    private String updateClientName() {
        System.out.print("Enter updated Client Name : ");
        scanner.skip("[\r\n]{2}");
        String clientName = scanner.nextLine();
        return clientName;
    }

    /**
     * updates manager Id of a project
     *
     * @returns    updated managerId
     */
    private int updateManagerId() {
        System.out.print("Enter updated Manager's ID : ");
        int managerId = scanner.nextInt();
        return managerId;
    }

    /**
     * updates start date of a project
     *
     * @returns    updated start date
     */
    private Date updateStartDate() {
        System.out.print("Enter updated Start Date : ");
        Date startDate = Date.valueOf(validateDate());
        return startDate;
    }

    /**
     * updates end date of a project
     *
     * @returns    updated end date
     */
    private Date updateEndDate() {
        System.out.print("Enter updated End Date : ");
        Date endDate = Date.valueOf(validateDate());
        return endDate;
    }

    /**
     * requests controller to update the project
     *
     * @param projectId      for which details to be updated
     * @param projectTitle   updated project title
     * @param clientName     updated client name
     * @param managerId      updated managerId
     * @param startDate      updated start date
     * @param endDate        updated end date
     */
    private void updateProjectDetails(int projectId, String projectTitle
            , String clientName, int managerId, Date startDate, Date endDate) {
        if (projectController.updateProject(projectId, projectTitle
            , clientName, managerId, startDate, endDate)) {
            System.out.println("Project updation successful");
        } else {
            System.out.println("Project updation failure");
        }
    }

    /**
     * updates employees assigned to a project
     *
     * @param projectId    for which employees assignation should be updated
     */
    private void updateEmployeesAssigned(int projectId) {
        System.out.println("Enter from the following option");
        byte option = 0;
        String updationStatement = "1 - Assign employee , 2 - Delete assigned employee"
               + " , 3 - Exit from the employee assignation updation menu";

        while (3 != option) {
            System.out.println(updationStatement);
            option = scanner.nextByte();

            switch (option) {
                case 1:
                    addEmployee(projectId);
                    break;
                case 2:
                    removeEmployee(projectId);
                    break;
                case 3:
                    System.out.println("Exited from employee assignation updation menu");
                    break;
                default:
                    System.out.println("Enter valid option");
            }
        }
    }

    /**
     * add employee to a project
     *
     * @param projectId    for which employee should be added
     */
    private void addEmployee(int projectId) {
        System.out.print("Enter Employee ID to be assigned : ");
        int employeeId = scanner.nextInt();

        if (isEmployeePresent(employeeId)) {
            if (projectController.addEmployee(projectId, employeeId)) {
                System.out.println("Employee assignation successful");
            } else {
                System.out.println("Employee aassignation failure");
            }
        } else {
            System.out.println("Employee not present");
        }
    }

    /**
     * delete employee from a project
     *
     * @param projectId    for which employee should be removed
     */
    private void removeEmployee(int projectId) {
        System.out.print("Enter Employee ID to be deleted : ");
        int employeeId = scanner.nextInt();

        if (isEmployeePresent(employeeId)) {
            if (projectController.removeEmployee(projectId, employeeId)) {
                System.out.println("Employee successfully removed from a project");
            } else {
                System.out.println("Employee removal failure");
            }
        } else {
            System.out.println("Employee not present");
        }
    }

    /**
     * reads the projectId and request the controller
     * to remove the details of a project
     */
    private void deleteProject() {
        System.out.print("Enter Project ID to delete the project : ");
        int projectId = scanner.nextInt();

        if (!isProjectPresent(projectId)) {
            System.out.println("Project details not present");
        } else if (projectController.deleteProject(projectId)) {
            System.out.println("Project Deletion successful");
        } else {
            System.out.println("Project Deletion failure");
        }
    }

    /**
     * restores the project details that had been deleted
     */
    private void restoreProject() {
        List<String> deletedProjects = projectController.getDeletedProjects();

        if (!deletedProjects.isEmpty()) {
            byte restoreOption = 0;
            String restoreOptionStatement = "1 - Restore a project details"
                   + " , 2 - Display deleted projects , 3 - Exit from restore menu";

            System.out.println("Select from the following option");

            while (3 != restoreOption) {
                System.out.println(restoreOptionStatement);
                restoreOption = scanner.nextByte();

                switch (restoreOption) {
                    case 1:
                        restoreDeletedProject();
                        break;
                    case 2:
                        displayDeletedProjects(deletedProjects);
                        break;
                    case 3:
                        System.out.println("Exited from the restore menu");
                        restoreOption = 3;
                        break;
                    default:
                        System.out.println("Enter valid option");
                        break;
                }

                deletedProjects = projectController.getDeletedProjects();

                if (deletedProjects.isEmpty()) {
                    System.out.println("All projects are restored");
                    break;
                }
            }
        } else {
            System.out.println("No deleted records");
        }
    }

    /**
     * reads project ID and restores that project
     */
    private void restoreDeletedProject() {
        System.out.print("Enter project ID that to be restored : ");
        int projectId = scanner.nextInt();

        if (isProjectPresent(projectId)) {
            System.out.println("Project details already in the database");
        } else {
            if (projectController.restoreDeleted(projectId)) {
                System.out.println("Project restored successfully");
            } else {
                System.out.println("Restoration failure");
            }
        }
    }

    /**
     * displays the deleted projects
     */
    private void displayDeletedProjects(List<String> deletedProjects) {
        if (deletedProjects.isEmpty()) {
            System.out.println("No deleted projects");
        } else {
            deletedProjects.forEach((project) -> {
                System.out.println(project);
            });
        }
    }

    /**
     * used to check whether the project details present or not
     *
     * @param projectId    projectId to verify the exixtence
     *
     * @return    true if project present in database else false
     */
    private boolean isProjectPresent(int projectId) {
        return projectController.isProjectPresent(projectId);
    }

    /**
     * used to check whether the employee present or not
     *
     * @param employeeId    employeeId to verify the exixtence
     *
     * @return    true if employee present in database else false
     */
    private boolean isEmployeePresent(int employeeId) {
        return projectController.isEmployeePresent(employeeId);
    }

    /**
     * the date is validated using regex validation
     *
     * @return    valid date
     */
    private String validateDate() {
        String date = scanner.next();
        if (!date.matches("^((19|20)\\d\\d)-(0?[1-9]|1[012])-(0?[1-9]|[12][0-9]|3[01])$")) {
            System.out.print("Enter valid date format (YYYY-MM-DD) : ");
            return validateDate();
        }
        return date;
    }
}