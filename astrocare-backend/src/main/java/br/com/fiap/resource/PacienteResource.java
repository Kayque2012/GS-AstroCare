package br.com.fiap.resource;

import br.com.fiap.bo.PacienteBO;
import br.com.fiap.entities.Paciente;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;

import java.sql.SQLException;
import java.util.List;

@Path("/pacientes")
public class PacienteResource {

    private PacienteBO pacienteBO = new PacienteBO();

    // GET /pacientes - Listar todos os pacientes
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response selecionarRs() {
        try {
            List<Paciente> lista = pacienteBO.selecionarBo();
            return Response.ok(lista).build();
        } catch (ClassNotFoundException | SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"erro\": \"Erro ao buscar pacientes: " + e.getMessage() + "\"}")
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        }
    }

    // GET /pacientes/{id} - Buscar paciente por ID
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response buscarPorIdRs(@PathParam("id") int id) {
        try {
            Paciente paciente = pacienteBO.buscarPorIdBo(id);
            if (paciente == null) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("{\"erro\": \"Paciente não encontrado.\"}")
                        .type(MediaType.APPLICATION_JSON)
                        .build();
            }
            return Response.ok(paciente).build();
        } catch (ClassNotFoundException | SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"erro\": \"Erro ao buscar paciente: " + e.getMessage() + "\"}")
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        }
    }

    // POST /pacientes - Cadastrar novo paciente
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response inserirRs(Paciente paciente, @Context UriInfo uriInfo) {
        try {
            pacienteBO.inserirBo(paciente);
            UriBuilder builder = uriInfo.getAbsolutePathBuilder();
            builder.path(Integer.toString(paciente.getId()));
            return Response.created(builder.build()).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"erro\": \"" + e.getMessage() + "\"}")
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        } catch (ClassNotFoundException | SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"erro\": \"Erro ao cadastrar paciente: " + e.getMessage() + "\"}")
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        }
    }

    // PUT /pacientes/{id} - Atualizar paciente
    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response atualizarRs(@PathParam("id") int id, Paciente paciente) {
        try {
            paciente.setId(id);
            pacienteBO.atualizarBo(paciente);
            return Response.ok("{\"mensagem\": \"Paciente atualizado com sucesso!\"}").build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"erro\": \"" + e.getMessage() + "\"}")
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        } catch (ClassNotFoundException | SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"erro\": \"Erro ao atualizar paciente: " + e.getMessage() + "\"}")
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        }
    }

    // DELETE /pacientes/{id} - Remover paciente
    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deletarRs(@PathParam("id") int id) {
        try {
            pacienteBO.deletarBo(id);
            return Response.ok("{\"mensagem\": \"Paciente removido com sucesso!\"}").build();
        } catch (ClassNotFoundException | SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"erro\": \"Erro ao remover paciente: " + e.getMessage() + "\"}")
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        }
    }

    // OPTIONS /pacientes - Preflight CORS
    @OPTIONS
    @Path("{path : .*}")
    public Response options() {
        return Response.ok().build();
    }
}
