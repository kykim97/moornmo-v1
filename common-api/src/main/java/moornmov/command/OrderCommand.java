package moornmov.command;

import java.util.Date;
import java.util.List;
import lombok.Data;
import lombok.ToString;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@ToString
@Data
public class OrderCommand {

    private String id; // Please comment here if you want user to enter the id directly
    private Date accountDate;
    private String type;
    private Integer number;
    private String client;
    private Date orderDate;
    private Date deliveryDate;
    private String manager;
    private String registerName;
    private Integer orderItem;
    private Integer orderCount;
    private Integer cost;
    private String memo;
}
