package moornmov.query;

import java.util.Date;
import java.util.List;
import javax.persistence.*;
import lombok.Data;
import org.springframework.hateoas.server.core.Relation;

@Entity
@Table(name = "OrderManagement_table")
@Data
@Relation(collectionRelation = "orderManagements")
public class OrderManagementReadModel {

    @Id
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
