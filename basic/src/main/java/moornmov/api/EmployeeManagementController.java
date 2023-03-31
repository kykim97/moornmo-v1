package moornmov.api;

import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;
import moornmov.aggregate.*;
import moornmov.command.*;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmployeeManagementController {

    private final CommandGateway commandGateway;
    private final QueryGateway queryGateway;

    public EmployeeManagementController(
        CommandGateway commandGateway,
        QueryGateway queryGateway
    ) {
        this.commandGateway = commandGateway;
        this.queryGateway = queryGateway;
    }

    @RequestMapping(value = "/employeeManagements", method = RequestMethod.POST)
    public CompletableFuture registration(
        @RequestBody RegistrationCommand registrationCommand
    ) throws Exception {
        System.out.println(
            "##### /employeeManagement/registration  called #####"
        );

        // send command
        return commandGateway
            .send(registrationCommand)
            .thenApply(id -> {
                EmployeeManagementAggregate resource = new EmployeeManagementAggregate();
                BeanUtils.copyProperties(registrationCommand, resource);

                resource.setId((String) id);

                return new ResponseEntity<>(hateoas(resource), HttpStatus.OK);
            });
    }

    @Autowired
    EventStore eventStore;

    @GetMapping(value = "/employeeManagements/{id}/events")
    public ResponseEntity getEvents(@PathVariable("id") String id) {
        ArrayList resources = new ArrayList<EmployeeManagementAggregate>();
        eventStore.readEvents(id).asStream().forEach(resources::add);

        CollectionModel<EmployeeManagementAggregate> model = CollectionModel.of(
            resources
        );

        return new ResponseEntity<>(model, HttpStatus.OK);
    }

    EntityModel<EmployeeManagementAggregate> hateoas(
        EmployeeManagementAggregate resource
    ) {
        EntityModel<EmployeeManagementAggregate> model = EntityModel.of(
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
}
