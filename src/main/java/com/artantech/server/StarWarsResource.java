package com.artantech.server;

import com.artantech.client.StarWarsServices;

import org.eclipse.microprofile.rest.client.inject.RestClient;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/starwars")
@Produces(MediaType.APPLICATION_JSON)
public class StarWarsResource {

    @Inject
    @RestClient
    StarWarsServices starWarsServices;

    @GET
    @Path("/starships")
    public String getStarships() {
        return starWarsServices.getStarships();
    }
}
