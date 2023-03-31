package moornmov.query;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import moornmov.aggregate.*;
import moornmov.event.*;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.queryhandling.QueryHandler;
import org.axonframework.queryhandling.QueryUpdateEmitter;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@ProcessingGroup("orderManagementSearch")
public class OrderManagementSearchCQRSHandlerReusingAggregate {

    @Autowired
    private OrderManagementReadModelRepository repository;

    @Autowired
    private QueryUpdateEmitter queryUpdateEmitter;

    @QueryHandler
    public List<OrderManagementReadModel> handle(
        OrderManagementSearchQuery query
    ) {
        return repository.findAll();
    }

    @QueryHandler
    public Optional<OrderManagementReadModel> handle(
        OrderManagementSearchSingleQuery query
    ) {
        return repository.findById(query.getId());
    }

    @EventHandler
    public void whenOrderAdded_then_CREATE(OrderAddedEvent event)
        throws Exception {
        OrderManagementReadModel entity = new OrderManagementReadModel();
        OrderManagementAggregate aggregate = new OrderManagementAggregate();
        aggregate.on(event);

        BeanUtils.copyProperties(aggregate, entity);

        repository.save(entity);

        queryUpdateEmitter.emit(
            OrderManagementSearchQuery.class,
            query -> true,
            entity
        );
    }
}
