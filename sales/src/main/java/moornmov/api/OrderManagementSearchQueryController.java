package moornmov.api;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import moornmov.query.*;
import org.axonframework.extensions.reactor.queryhandling.gateway.ReactorQueryGateway;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
public class OrderManagementSearchQueryController {

    private final QueryGateway queryGateway;

    private final ReactorQueryGateway reactorQueryGateway;

    public OrderManagementSearchQueryController(
        QueryGateway queryGateway,
        ReactorQueryGateway reactorQueryGateway
    ) {
        this.queryGateway = queryGateway;
        this.reactorQueryGateway = reactorQueryGateway;
    }

    @GetMapping("/orderManagements")
    public CompletableFuture findAll(OrderManagementSearchQuery query) {
        return queryGateway
            .query(
                query,
                ResponseTypes.multipleInstancesOf(
                    OrderManagementReadModel.class
                )
            )
            .thenApply(resources -> {
                List modelList = new ArrayList<EntityModel<OrderManagementReadModel>>();

                resources
                    .stream()
                    .forEach(resource -> {
                        modelList.add(hateoas(resource));
                    });

                CollectionModel<OrderManagementReadModel> model = CollectionModel.of(
                    modelList
                );

                return new ResponseEntity<>(model, HttpStatus.OK);
            });
    }

    @GetMapping("/orderManagements/{id}")
    public CompletableFuture findById(@PathVariable("id") String id) {
        OrderManagementSearchSingleQuery query = new OrderManagementSearchSingleQuery();
        query.setId(id);

        return queryGateway
            .query(
                query,
                ResponseTypes.optionalInstanceOf(OrderManagementReadModel.class)
            )
            .thenApply(resource -> {
                if (!resource.isPresent()) {
                    return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
                }

                return new ResponseEntity<>(
                    hateoas(resource.get()),
                    HttpStatus.OK
                );
            })
            .exceptionally(ex -> {
                throw new RuntimeException(ex);
            });
    }

    EntityModel<OrderManagementReadModel> hateoas(
        OrderManagementReadModel resource
    ) {
        EntityModel<OrderManagementReadModel> model = EntityModel.of(resource);

        model.add(
            Link.of("/orderManagements/" + resource.getId()).withSelfRel()
        );

        model.add(
            Link
                .of("/orderManagements/" + resource.getId() + "/events")
                .withRel("events")
        );

        return model;
    }

    @MessageMapping("orderManagements.all")
    public Flux<OrderManagementReadModel> subscribeAll() {
        return reactorQueryGateway.subscriptionQueryMany(
            new OrderManagementSearchQuery(),
            OrderManagementReadModel.class
        );
    }

    @MessageMapping("orderManagements.{id}.get")
    public Flux<OrderManagementReadModel> subscribeSingle(
        @DestinationVariable String id
    ) {
        OrderManagementSearchSingleQuery query = new OrderManagementSearchSingleQuery();
        query.setId(id);

        return reactorQueryGateway.subscriptionQuery(
            query,
            OrderManagementReadModel.class
        );
    }
}
