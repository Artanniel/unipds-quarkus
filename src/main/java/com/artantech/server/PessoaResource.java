package com.artantech.server;

import com.artantech.model.Pessoa;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.faulttolerance.CircuitBreaker;
import org.eclipse.microprofile.faulttolerance.Fallback;
import org.eclipse.microprofile.faulttolerance.Timeout;

import java.time.temporal.ChronoUnit;
import java.util.List;

@Path("/pessoas")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PessoaResource {

    public static final String MSG_ERROR = "Fallback: Falha ao buscar pessoas";

    @GET
    @Timeout(value = 3000L, unit = ChronoUnit.SECONDS)
    //@Fallback(fallbackMethod = "getPessoasFallback")
    //@CircuitBreaker(requestVolumeThreshold = 2, failureRatio = 0.1, delay = 1000000L, successThreshold = 1)
    public List<Pessoa> listar() {
        return Pessoa.listAll();
    }

    @GET
    @Path("/findByAnoNascimento")
    //@Fallback(fallbackMethod = "getPessoasFallback")
    //@CircuitBreaker(requestVolumeThreshold = 2, failureRatio = 0.1, delay = 1000000L, successThreshold = 1)
    public List<Pessoa> findByAnoNascimento(@QueryParam("anoNascimento") int anoNascimento) {
        return Pessoa.findByAnoNascimento(anoNascimento);
    }

    /*
    public String getPessoasFallback() {
        return MSG_ERROR;
    }
     */

    @POST
    @Transactional
    public Pessoa adicionar(Pessoa pessoa) {
        /*if (pessoa == null) {
            throw new BadRequestException("Pessoa inválida");
        }
         */
        pessoa.id = null;
        pessoa.persist();
        return pessoa;
    }

    @PUT
    @Transactional
    public Pessoa atualizar(Pessoa pessoa) {
        Pessoa p = Pessoa.findById(pessoa.id);
        p.nome = pessoa.nome;
        p.email = pessoa.email;
        p.anoNascimento = pessoa.anoNascimento;
        p.persist();
        return p;
    }

    @DELETE
    @Transactional
    public void remover(Long id) {
        Pessoa p = Pessoa.findById(id);
        p.delete();
    }
}
