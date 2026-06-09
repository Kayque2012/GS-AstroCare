package br.com.fiap.resource;

import br.com.fiap.bo.MedicoBO;
import br.com.fiap.entities.Medico;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;

import java.sql.SQLException;
import java.util.List;

@Path("/medicos")
public class MedicoResource {

    private MedicoBO medicoBO = new MedicoBO();

    // GET /medicos - Listar todos os médicos
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response selecionarRs() {
        try {
            List<Medico> lista = medicoBO.selecionarBo();
            return Response.ok(lista).build();
        } catch (ClassNotFoundException | SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"erro\": \"Erro ao buscar médicos: " + e.getMessage() + "\"}")
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        }
    }

    // GET /medicos/{id} - Buscar médico por ID
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response buscarPorIdRs(@PathParam("id") int id) {
        try {
            Medico medico = medicoBO.buscarPorIdBo(id);
            if (medico == null) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("{\"erro\": \"Médico não encontrado.\"}")
                        .type(MediaType.APPLICATION_JSON)
                        .build();
            }
            return Response.ok(medico).build();
        } catch (ClassNotFoundException | SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"erro\": \"Erro ao buscar médico: " + e.getMessage() + "\"}")
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        }
    }

    // POST /medicos - Cadastrar novo médico
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response inserirRs(Medico medico, @Context UriInfo uriInfo) {
        try {
            medicoBO.inserirBo(medico);
            UriBuilder builder = uriInfo.getAbsolutePathBuilder();
            builder.path(Integer.toString(medico.getId()));
            return Response.created(builder.build()).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"erro\": \"" + e.getMessage() + "\"}")
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        } catch (ClassNotFoundException | SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"erro\": \"Erro ao cadastrar médico: " + e.getMessage() + "\"}")
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        }
    }

    // PUT /medicos/{id} - Atualizar médico
    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response atualizarRs(@PathParam("id") int id, Medico medico) {
        try {
            medico.setId(id);
            medicoBO.atualizarBo(medico);
            return Response.ok("{\"mensagem\": \"Médico atualizado com sucesso!\"}").build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"erro\": \"" + e.getMessage() + "\"}")
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        } catch (ClassNotFoundException | SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"erro\": \"Erro ao atualizar médico: " + e.getMessage() + "\"}")
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        }
    }

    // DELETE /medicos/{id} - Remover médico
    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deletarRs(@PathParam("id") int id) {
        try {
            medicoBO.deletarBo(id);
            return Response.ok("{\"mensagem\": \"Médico removido com sucesso!\"}").build();
        } catch (ClassNotFoundException | SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"erro\": \"Erro ao remover médico: " + e.getMessage() + "\"}")
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        }
    }

    // OPTIONS - Preflight CORS
    @OPTIONS
    @Path("{path : .*}")
    public Response options() {
        return Response.ok().build();
    }
}
