package edu.stevens.cs548.clinic.micro.domain;

import edu.stevens.cs548.clinic.service.dto.ProviderDto;
import edu.stevens.cs548.clinic.service.dto.TreatmentDto;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.rest.client.annotation.RegisterProvider;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import java.util.List;
import java.util.UUID;

@RegisterRestClient(configKey="clinic-domain.api")
@RegisterProvider(GsonProvider.class)
@Path("provider")
@Produces("application/json")
@Consumes("application/json")

public interface IProviderMicroService {

	@POST
	@Consumes("application/json")
	public Response addProvider(ProviderDto dto);

	@GET
	@Produces("application/json")
	public List<ProviderDto> getProviders();

	@GET
	@Path("{id}")
	@Produces("application/json")
	public ProviderDto getProvider(@PathParam("id") String id, @QueryParam("treatments") String includeTreatments);

	@POST
	@Path("{id}/treatment")
	@Consumes("application/json")
	public UUID addTreatment(@PathParam("id") String id, TreatmentDto dto);

	@GET
	@Path("{id}/treatment/{tid}")
	@Produces("application/json")
	public TreatmentDto getTreatment(@PathParam("id") String providerId, @PathParam("tid") String treatmentId);

	@DELETE
	public void removeAll();
		
}
