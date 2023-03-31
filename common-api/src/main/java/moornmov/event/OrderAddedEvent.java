package moornmov.event;

import java.util.Date;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class OrderAddedEvent {

    private String id;
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
