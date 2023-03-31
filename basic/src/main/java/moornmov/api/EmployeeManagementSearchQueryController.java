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
public class EmployeeManagementSearchQueryController {

    private final QueryGateway queryGateway;

    private final ReactorQueryGateway reactorQueryGateway;

    public EmployeeManagementSearchQueryController(
        QueryGateway queryGateway,
        ReactorQueryGateway reactorQueryGateway
    ) {
        this.queryGateway = queryGateway;
        this.reactorQueryGateway = reactorQueryGateway;
    }

    @GetMapping("/employeeManagements")
    public CompletableFuture findAll(EmployeeManagementSearchQuery query) {
        return queryGateway
            .query(
                query,
                ResponseTypes.multipleInstancesOf(
                    EmployeeManagementReadModel.class
                )
            )
            .thenApply(resources -> {
                List modelList = new ArrayList<EntityModel<EmployeeManagementReadModel>>();

                resources
                    .stream()
                    .forEach(resource -> {
                        modelList.add(hateoas(resource));
                    });

                CollectionModel<EmployeeManagementReadModel> model = CollectionModel.of(
                    modelList
                );

                return new ResponseEntity<>(model, HttpStatus.OK);
            });
    }

    @GetMapping("/employeeManagements/{id}")
    public CompletableFuture findById(@PathVariable("id") String id) {
        EmployeeManagementSearchSingleQuery query = new EmployeeManagementSearchSingleQuery();
        query.setId(id);

        return queryGateway
            .query(
                query,
                ResponseTypes.optionalInstanceOf(
                    EmployeeManagementReadModel.class
                )
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

    EntityModel<EmployeeManagementReadModel> hateoas(
        EmployeeManagementReadModel resource
    ) {
        EntityModel<EmployeeManagementReadModel> model = EntityModel.of(
            resource
        );

        model.add(
            Link.of("/employeeManagements/" + resource.getId()).withSelfRel()
        );

        model.add(
            Link
                .of("/employeeManagements/" + resource.getId() + "/events")
                .withRel("events")
        );

        return model;
    }

    @MessageMapping("employeeManagements.all")
    public Flux<EmployeeManagementReadModel> subscribeAll() {
        return reactorQueryGateway.subscriptionQueryMany(
            new EmployeeManagementSearchQuery(),
            EmployeeManagementReadModel.class
        );
    }

    @MessageMapping("employeeManagements.{id}.get")
    public Flux<EmployeeManagementReadModel> subscribeSingle(
        @DestinationVariable String id
    ) {
        EmployeeManagementSearchSingleQuery query = new EmployeeManagementSearchSingleQuery();
        query.setId(id);

        return reactorQueryGateway.subscriptionQuery(
            query,
            EmployeeManagementReadModel.class
        );
    }
}
