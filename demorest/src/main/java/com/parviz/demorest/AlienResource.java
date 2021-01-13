package com.parviz.demorest;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("aliens")
public class AlienResource {
	
	AlienRepository repo = new AlienRepository();
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Alien> getAliens() {
		
		return repo.getAliens();
	}
	
	@GET
	@Path("alien/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Alien getAlien(@PathParam("id") int id) {
		
//		for(Alien a : repo.getAliens()) {
//			if(a.getId() == id) {
//				return a;
//			}
//		}
		
		Alien a = repo.getAlien(id);
		
		return a;
	}
	
	@POST
	@Path("alien")
	@Consumes(MediaType.APPLICATION_JSON)
	public String createAlien(Alien alien) {
		
		if(repo.create(alien)) {
			return "Alien added successfully";
		}
		return "Failed to save";
	}
	
	@PUT
	@Path("alien")
	@Consumes(MediaType.APPLICATION_JSON)
	public String updateAlien(Alien alien) {
		
		if(repo.update(alien)) {
			return "Alien updated successfully";
		}
		return "Failed to update";
	}
	
	@DELETE
	@Path("alien/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	public String deleteAlien(@PathParam("id") int id) {
		
		if(repo.delete(id)) {
			return "Alien deleted successfully";
		}
		return "Alien not found, nothing deleted";
	}
}
