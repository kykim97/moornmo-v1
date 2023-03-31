package moornmov.command;

import java.util.Date;
import java.util.List;
import lombok.Data;
import lombok.ToString;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@ToString
@Data
public class RegistrationCommand {

    private String id; // Please comment here if you want user to enter the id directly
    private String userName;
    private String userId;
    private String department;
    private String position;
    private String phoneNo;
    private Date enteringDate;
    private Boolean isManager;
}
