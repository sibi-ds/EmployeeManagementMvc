import com.ideas2it.employeemanagement.view.EmployeeView;

import java.sql.SQLException;

/**
 * this class invokes the user interface class
 * to interact with the user
 *
 *@author  sibi
 *@created 2021-03-03
 */
public class EmployeeManagement {

    public static void main(String args[]) throws ClassNotFoundException, SQLException {
        EmployeeView employeeView = new EmployeeView();
        employeeView.view();
    }
}



