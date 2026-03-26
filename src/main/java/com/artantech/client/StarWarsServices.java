package com.artantech.client;

import java.time.temporal.ChronoUnit;

import org.eclipse.microprofile.faulttolerance.CircuitBreaker;
import org.eclipse.microprofile.faulttolerance.Fallback;
import org.eclipse.microprofile.faulttolerance.Timeout;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@RegisterRestClient(baseUri = "https://swapi.info/api/")
public interface StarWarsServices {

    public static final String MSG_ERROR = "Fallback: Falha ao buscar starships";

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("starships")
    @Timeout(value = 3000L, unit = ChronoUnit.SECONDS)
    @Fallback(fallbackMethod = "getStarshipsFallback")
    @CircuitBreaker(requestVolumeThreshold = 2, failureRatio = 0.1, delay = 1000000L, successThreshold = 1)
    public String getStarships();

    default String getStarshipsFallback() {
        return MSG_ERROR;
    }
}
