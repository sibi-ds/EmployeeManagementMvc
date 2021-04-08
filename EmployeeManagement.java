import java.util.Scanner;

import com.ideas2it.employeemanagement.employee.view.EmployeeView;
import com.ideas2it.employeemanagement.project.view.ProjectView;

/**
 * this class invokes the user interface classes
 * of employee and project entities as per the user's request
 *
 * @author  sibi
 * @created 2021-03-03
 */
public class EmployeeManagement {

    public static void main(String args[]) {
        EmployeeView employeeView = new EmployeeView();
        ProjectView projectView = new ProjectView();
        Scanner scanner = new Scanner(System.in);

        byte option = 0;
        String entitySelectionStatement = "1 - Employee Details Management"
                + " , 2 - Project Details Management , 3 - Exit";

        while (3 != option) {
            System.out.println(entitySelectionStatement);
            option = scanner.nextByte();

            switch (option) {
                case 1:
                    employeeView.view();
                    break;
                case 2:
                    projectView.view();
                    break;
                case 3:
                    System.out.println("Exited from the main menu");
                    break;
                default:
                    System.out.println("Enter valid option");
            }
        }
    }
}



