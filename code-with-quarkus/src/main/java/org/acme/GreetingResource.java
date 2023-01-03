package org.acme;

import io.quarkus.hal.HalCollectionWrapper;
import io.quarkus.hal.HalEntityWrapper;
import io.quarkus.hal.HalLink;
import io.quarkus.resteasy.reactive.links.RestLink;
import io.quarkus.resteasy.reactive.links.RestLinksProvider;
import org.jboss.resteasy.reactive.common.util.RestMediaType;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Link;
import javax.ws.rs.core.MediaType;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Path("/data")
public class GreetingResource {

    @Inject
    RestLinksProvider linksProvider;

    private List<ReplenishmentRequest> data;


    @PostConstruct
    void setup(){
        data = List.of(
            new ReplenishmentRequest("id-1", "rr-name-1", 10),
            new ReplenishmentRequest("id-2", "rr-name-2", 20)
        );
    }

    @GET
    @Produces({MediaType.APPLICATION_JSON, RestMediaType.APPLICATION_HAL_JSON })
    @RestLink(rel = "all")
    public List<ReplenishmentRequest> getAll() {
        return data;
    }

    @GET
    @Path("/one/{id}")
    @Produces({MediaType.APPLICATION_JSON, RestMediaType.APPLICATION_HAL_JSON })
    @RestLink(rel = "self")
    public ReplenishmentRequest getOne(@PathParam("id") String id) {
        return this.data.stream().filter(rr -> id.equals(rr.getId())).findFirst().get();
    }

    @GET
    @Path("/links")
    @Produces({MediaType.APPLICATION_JSON, RestMediaType.APPLICATION_HAL_JSON })
    @RestLink(rel = "list")
    public HalCollectionWrapper getLinks() {
        Collection<HalEntityWrapper> collection = this.data.stream()
            .map(rr -> {
                Map<String, HalLink> links = new HashMap<>(){{
                    put("custom-item", new HalLink("http://localhost:8080/custom/"+rr.getId()));
                }};
                Collection<Link> instanceLinks = linksProvider.getInstanceLinks(rr);
                HalEntityWrapper hew = new HalEntityWrapper(rr, links);
                instanceLinks.forEach(il -> hew.addLinks(il));
                return hew;
            })
            .toList();

        HalCollectionWrapper halCollection = new HalCollectionWrapper(collection, "data");
        halCollection.addLinks(Link.fromPath("http://localhost:8080/custom/all").rel("custom-list").build());

        Collection<Link> typeLinks = linksProvider.getTypeLinks(ReplenishmentRequest.class);
        typeLinks.forEach(il -> halCollection.addLinks(il));
        return halCollection;
    }

    @POST
    @Path("/one/{id}/quantity")
    @Produces({MediaType.APPLICATION_JSON, RestMediaType.APPLICATION_HAL_JSON })
    @RestLink(rel = "updateQuantity")
    public List<ReplenishmentRequest> updateQuantity(@PathParam("id") String id, Integer quantity) {
        ReplenishmentRequest replenishmentRequest = this.getOne(id);
        replenishmentRequest.setQuantity(quantity);
        return this.getAll();
    }

}