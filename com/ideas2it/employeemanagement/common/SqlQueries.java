package com.ideas2it.employeemanagement.common;

/**
 * this class contains Sql queries fro
 * both employee and project layers
 */
public class SqlQueries {

    public static final String INSERT_EMPLOYEE_QUERY
            = "INSERT INTO employee (name, date_of_birth, salary, mobile_number) VALUES (?, ?, ?, ?)";

    public static final String INSERT_ADDRESS_QUERY = "INSERT INTO address"
            + " (address_type, employee_id, door_number, street, village, district, state, pincode)"
            + " VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

    public static final String ASSIGN_PROJECT_QUERY = "INSERT IGNORE INTO employee_project"
            + " (project_id, employee_id) VALUES (?, ?)";

    public static final String UNASSIGN_PROJECT_QUERY = "DELETE FROM employee_project"
            + " WHERE project_id = ? AND employee_id = ?";

    public static final String GET_EMPLOYEE_QUERY = "SELECT employee.id, employee.name, employee.date_of_birth"
            + ", employee.salary, employee.mobile_number, address.address_id, address.address_type"
            + ", address.door_number, address.street, address.village, address.district, address.state"
            + ", address.pincode FROM employee LEFT JOIN address ON employee.id = address.employee_id"
            + " AND address.is_deleted = false WHERE employee.id = ? AND employee.is_deleted = false";

    public static final String GET_EMPLOYEES_QUERY = "SELECT employee.id, employee.name, employee.date_of_birth"
            + ", employee.salary, employee.mobile_number, address.address_id, address.address_type"
            + ", address.door_number, address.street, address.village, address.district, address.state"
            + ", address.pincode FROM employee LEFT JOIN address ON employee.id = address.employee_id"
            + " AND address.is_deleted = false WHERE employee.is_deleted = false";

    public static final String GET_SPECIFIED_EMPLOYEE_QUERY = "SELECT e.id, e.name, e.date_of_birth, e.salary, e.mobile_number"
            + ",ep.project_id FROM employee e LEFT JOIN employee_project ep ON e.id = ep.employee_id"
            + " WHERE e.id = ? AND e.is_deleted = false";

    public static final String GET_ADDRESSES_QUERY = "SELECT address_id, address_type, door_number, street"
            + ", village, district, state, pincode FROM address WHERE employee_id = ? AND is_deleted = false";

    public static final String UPDATE_EMPLOYEE_QUERY = "UPDATE employee SET name = IFNULL(?, name)"
            + ", date_of_birth = IFNULL(?, date_of_birth), salary = IFNULL(?, salary)"
            + ", mobile_number = IFNULL(?, mobile_number) WHERE id = ?";

    public static final String UPDATE_ADDRESS_VALUES_QUERY = "UPDATE address SET address_type = ?"
            + ", door_number = ?, street = ?, village = ?, district = ?, state = ?"
            + ", pincode = ? WHERE address_id = ? AND employee_id = ?";

    public static final String DELETE_ADDRESS_QUERY = "UPDATE address SET is_deleted = true"
            + " WHERE employee_id = ? AND address_id = ?";

    public static final String DELETE_EMPLOYEE_QUERY = "UPDATE employee as e LEFT JOIN address as a "
            + "ON e.id = a.employee_id SET e.is_deleted = true, a.is_deleted = true WHERE e.id = ?";

    public static final String UNASSIGN_PROJECTS_QUERY = "DELETE from employee_project WHERE employee_id = ?";

    public static final String GET_DELETED_EMPLOYEES_QUERY = "SELECT id, name, date_of_birth, salary"
            + ", mobile_number FROM employee WHERE is_deleted = true";

    public static final String RESTORE_EMPLOYEE_QUERY = "UPDATE employee as e LEFT JOIN address as a "
            + "ON e.id = a.employee_id SET e.is_deleted = false, a.is_deleted = false WHERE e.id = ?";

    public static final String IS_EMPLOYEE_PRESENT_QUERY = "SELECT id FROM employee WHERE id = ? AND is_deleted = false";

    public static final String INSERT_PROJECT_QUERY = "INSERT INTO project (project_title, client_name"
            + ", manager_id, start_date, end_date) VALUES (?, ?, ?, ?, ?)";

    public static final String ASSIGN_EMPLOYEE_QUERY = "INSERT IGNORE INTO employee_project"
            + " (project_id, employee_id) VALUES (?, ?)";

    public static final String GET_PROJECTS_QUERY = "SELECT id, project_title, client_name, manager_id"
            + ", start_date, end_date FROM project WHERE is_deleted = false";

    public static final String GET_PROJECT_QUERY = "SELECT p.id, p.project_title, p.client_name, p.manager_id"
            + ", p.start_date, p.end_date, ep.employee_id FROM project p LEFT JOIN employee_project ep ON"
            + " p.id = ep.project_id WHERE p.id = ? AND p.is_deleted = false";

    public static final String UPDATE_PROJECT_QUERY = "UPDATE project SET project_title = IFNULL(?, project_title)"
            + ", client_name = IFNULL(?, client_name), manager_id = IFNULL(?, manager_id)"
            + ", start_date = IFNULL(?, start_date), end_date = IFNULL(?, end_date)"
            + " WHERE id = ? AND is_deleted = false";

    public static final String UNASSIGN_EMPLOYEE_QUERY = "DELETE from employee_project"
            + " WHERE project_id = ? AND employee_id = ?";

    public static final String DELETE_PROJECT_QUERY = "UPDATE project SET is_deleted = true WHERE id = ?";

    public static final String UNASSIGN_EMPLOYEES_QUERY = "DELETE from employee_project WHERE project_id = ?";

    public static final String GET_DELETED_PROJECTS_QUERY = "SELECT id, project_title, client_name, manager_id"
            + ", start_date, end_date FROM project WHERE is_deleted = true";

    public static final String RESTORE_PROJECT_QUERY = "UPDATE project SET is_deleted = false WHERE id = ?";

    public static final String IS_PROJECT_PRESENT_QUERY = "SELECT id FROM project WHERE id = ? AND is_deleted = false";
}