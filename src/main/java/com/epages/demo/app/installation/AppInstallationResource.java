package com.epages.demo.app.installation;

import java.net.URI;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Path("installations")
@Produces("application/json")
public class AppInstallationResource {

    @Autowired
    private AppInstallationRepository appInstallationRepository;

    @Context
    private UriInfo uriInfo;

    // curl -i -XGET http://localhost:5000/api/installations
    @GET
    public Response getAll() {
        return Response.ok(appInstallationRepository.findAll()).build();
    }

    // curl -i -XDELETE http://localhost:5000/api/installations
    @DELETE
    public Response removeAll() {
        appInstallationRepository.deleteAll();
        return Response.accepted().build();
    }

    // curl -i -XPOST -H 'Content-Type: application/json' -d '{"apiUrl":"http://localhost:8088/rs/shops/DemoShop","token":"ABCDEF0123456789"}' http://localhost:5000/api/installations
    @POST
    @Consumes("application/json")
    public Response create(AppInstallation appInstallation) {
        appInstallationRepository.save(appInstallation);
        final URI location = uriInfo.getAbsolutePathBuilder() //
                .path("{api_url}") //
                .resolveTemplate("api_url", appInstallation.getApiUrl()) //
                .build();
        return Response.created(location).build();
    }

    // curl -i -XGET http://localhost:5000/api/installations/http:%2F%2Flocalhost:8088%2Frs%2Fshops%2FDemoShop
    @GET
    @Path("{api_url}")
    public Response getByApiUrl(@PathParam("api_url") String apiUrl) {
        final AppInstallation appInstallation = appInstallationRepository.findByApiUrl(apiUrl);
        if (null == appInstallation) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(appInstallation).build();
    }

    // curl -i -XDELETE http://localhost:5000/api/installations/http:%2F%2Flocalhost:8088%2Frs%2Fshops%2FDemoShop
    @DELETE
    @Path("{api_url}")
    public Response removeByApiUrl(@PathParam("api_url") String apiUrl) {
        if (null == appInstallationRepository.findByApiUrl(apiUrl)) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        appInstallationRepository.deleteByApiUrl(apiUrl);
        return Response.accepted().build();
    }
}
