package com.example.beproject21.controller;


import com.example.beproject21.model.Destination;
import com.example.beproject21.model.ResponseModel;
import com.example.beproject21.service.DestinationService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

@Path("/destination")
public class DestinationController {
    private final Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
    private final DestinationService destinationService = new DestinationService();

    @Path("/getAll")
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    public Object getAll() {
        try {
            var rs = destinationService.selectAll();
            return gson.toJson(rs);
        } catch (Exception e) {
            return gson.toJson(new ResponseModel("203", "Error", e.getMessage()));
        }
    }

    @Path("/add")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Object add(Destination item) {
        try {
            var rs = destinationService.insert(item);
            return gson.toJson(rs);
        } catch (Exception e) {
            return gson.toJson(new ResponseModel("203", "Error", e.getMessage()));
        }
    }

    @Path("/update")
    @PATCH
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Object update(Destination item) {
        try {
            var rs = destinationService.update(item);
            return gson.toJson(rs);
        } catch (Exception e) {
            return gson.toJson(new ResponseModel("203", "Error", e.getMessage()));
        }
    }

    @Path("/delete/{id}")
    @DELETE
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Object delete(@PathParam("id") int id) {
        try {
            var rs = destinationService.delete(id);
            return gson.toJson(rs);
        } catch (Exception e) {
            return gson.toJson(new ResponseModel("203", "Error", e.getMessage()));
        }
    }

}
