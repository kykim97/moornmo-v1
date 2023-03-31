package moornmov.query;

import java.util.Date;
import java.util.List;
import javax.persistence.*;
import lombok.Data;
import org.springframework.hateoas.server.core.Relation;

@Entity
@Table(name = "EmployeeManagement_table")
@Data
@Relation(collectionRelation = "employeeManagements")
public class EmployeeManagementReadModel {

    @Id
    private String id;

    private String userName;

    private String userId;

    private String department;

    private String position;

    private String phoneNo;

    private Date enteringDate;

    private Boolean isManager;
}
