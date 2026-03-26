package com.artantech.health;

import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Readiness;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import com.artantech.client.StarWarsServices;

@Readiness
public class ReadinessCheck implements HealthCheck {

    @RestClient
    StarWarsServices starWarsServices;

    @Override
    public HealthCheckResponse call() {
        // System.out.println(starWarsServices.getStarships());
        if (starWarsServices.getStarships().contains(StarWarsServices.MSG_ERROR)) {
            return HealthCheckResponse.down("Readiness check: Algo não está pronto!");
        }
        return HealthCheckResponse.up("Readiness check: Tudo pronto!");
    }
}
