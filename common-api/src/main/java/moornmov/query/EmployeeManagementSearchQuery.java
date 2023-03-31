package moornmov.query;

import lombok.Data;

@Data
public class EmployeeManagementSearchQuery {

    String username;
    String department;
    Boolean employmentStatus;
}
