package moornmov.event;

import java.util.Date;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class EmployeeAddedEvent {

    private String id;
    private String userName;
    private String userId;
    private String department;
    private String position;
    private String phoneNo;
    private Date enteringDate;
    private Boolean isManager;
}
