package com.example.beproject21.controller;


import com.example.beproject21.model.ResponseModel;
import com.example.beproject21.model.Services;
import com.example.beproject21.service.ServicesService;
import com.example.beproject21.service.UserService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

@Path("/service")
public class ServiceController {

    private final ServicesService userService = new ServicesService();
    private final Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();

    @Path("/add")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Object addService(Services item) {
        try {
            var rs = userService.insert(item);
            return gson.toJson(rs);
        } catch (Exception e) {
            return gson.toJson(new ResponseModel("203", "Error", e.getMessage()));
        }
    }


    @Path("/getAll")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Object getAll() {
        try {
            var rs = userService.selectAll();
            return gson.toJson(rs);
        } catch (Exception e) {
            return gson.toJson(new ResponseModel("203", "Error", e.getMessage()));
        }
    }

    @Path("/update")
    @PATCH
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Object update(Services item) {
        try {
            var rs = userService.update(item);
            return gson.toJson(rs);
        } catch (Exception e) {
            return gson.toJson(new ResponseModel("203", "Error", e.getMessage()));
        }
    }

    @Path("/delete/{id}")
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    public Object delete(@PathParam("id") int id) {
        try {
            var rs = userService.delete(id);
            return gson.toJson(rs);
        } catch (Exception e) {
            return gson.toJson(new ResponseModel("203", "Error", e.getMessage()));
        }
    }

}
