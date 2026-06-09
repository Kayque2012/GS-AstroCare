package br.com.fiap.resource;

import br.com.fiap.bo.ConsultaBO;
import br.com.fiap.entities.Consulta;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;

import java.sql.SQLException;
import java.util.List;

@Path("/consultas")
public class ConsultaResource {

    private ConsultaBO consultaBO = new ConsultaBO();

    // GET /consultas - Listar todas as consultas
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response selecionarRs() {
        try {
            List<Consulta> lista = consultaBO.selecionarBo();
            return Response.ok(lista).build();
        } catch (ClassNotFoundException | SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"erro\": \"Erro ao buscar consultas: " + e.getMessage() + "\"}")
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        }
    }

    // GET /consultas/{id} - Buscar consulta por ID
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response buscarPorIdRs(@PathParam("id") int id) {
        try {
            Consulta consulta = consultaBO.buscarPorIdBo(id);
            if (consulta == null) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("{\"erro\": \"Consulta não encontrada.\"}")
                        .type(MediaType.APPLICATION_JSON)
                        .build();
            }
            return Response.ok(consulta).build();
        } catch (ClassNotFoundException | SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"erro\": \"Erro ao buscar consulta: " + e.getMessage() + "\"}")
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        }
    }

    // GET /consultas/paciente/{idPaciente} - Buscar consultas de um paciente
    @GET
    @Path("/paciente/{idPaciente}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response buscarPorPacienteRs(@PathParam("idPaciente") int idPaciente) {
        try {
            List<Consulta> lista = consultaBO.buscarPorPacienteBo(idPaciente);
            return Response.ok(lista).build();
        } catch (ClassNotFoundException | SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"erro\": \"Erro ao buscar consultas do paciente: " + e.getMessage() + "\"}")
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        }
    }

    // POST /consultas - Agendar nova consulta
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response inserirRs(Consulta consulta, @Context UriInfo uriInfo) {
        try {
            consultaBO.inserirBo(consulta);
            UriBuilder builder = uriInfo.getAbsolutePathBuilder();
            builder.path(Integer.toString(consulta.getId()));
            return Response.created(builder.build()).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"erro\": \"" + e.getMessage() + "\"}")
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        } catch (ClassNotFoundException | SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"erro\": \"Erro ao agendar consulta: " + e.getMessage() + "\"}")
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        }
    }

    // PUT /consultas/{id} - Atualizar consulta
    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response atualizarRs(@PathParam("id") int id, Consulta consulta) {
        try {
            consulta.setId(id);
            consultaBO.atualizarBo(consulta);
            return Response.ok("{\"mensagem\": \"Consulta atualizada com sucesso!\"}").build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"erro\": \"" + e.getMessage() + "\"}")
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        } catch (ClassNotFoundException | SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"erro\": \"Erro ao atualizar consulta: " + e.getMessage() + "\"}")
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        }
    }

    // DELETE /consultas/{id} - Cancelar/remover consulta
    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deletarRs(@PathParam("id") int id) {
        try {
            consultaBO.deletarBo(id);
            return Response.ok("{\"mensagem\": \"Consulta removida com sucesso!\"}").build();
        } catch (ClassNotFoundException | SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"erro\": \"Erro ao remover consulta: " + e.getMessage() + "\"}")
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
