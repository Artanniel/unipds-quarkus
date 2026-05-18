package com.artantech.server;

import com.artantech.model.Pessoa;
import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.RequestScoped;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.jwt.JsonWebToken;
import jakarta.inject.Inject;

import java.util.HashMap;
import java.util.Map;

@Path("/secure")
@RequestScoped
public class SecureResource {

    @Inject
    JsonWebToken jwt;

    @GET
    @Path("/claim/{id}")
    @RolesAllowed("Subscriber")
    @Produces(MediaType.APPLICATION_JSON)
    public Response claim(@PathParam("id") Long id) {
        Pessoa p = Pessoa.findById(id);
        if (p == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        Map<String, Object> result = new HashMap<>();
        result.put("usuario", p);
        result.put("name", jwt.getName());
        result.put("birthdate", jwt.getClaim("birthdate"));
        result.put("token_issuer", jwt.getIssuer());

        return Response.ok(result).build();
    }
}
