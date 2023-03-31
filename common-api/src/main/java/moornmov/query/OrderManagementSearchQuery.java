package moornmov.query;

import java.util.Date;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

@Data
public class OrderManagementSearchQuery {

    Integer number;
    String deliveryDestination;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    Date orderDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    Date deliveryDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    Date accountDate;
}
