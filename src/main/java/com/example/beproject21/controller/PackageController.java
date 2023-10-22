package com.example.beproject21.controller;


import com.example.beproject21.model.Package;
import com.example.beproject21.model.ResponseModel;
import com.example.beproject21.service.PackageService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

@Path("/package")
public class PackageController {
    private final Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
    private final PackageService packageService = new PackageService();

    @Path("/getAll")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Object getAll() {
        try {
            var rs = packageService.selectAll();
            return gson.toJson(new ResponseModel("200", "Success", rs));
        } catch (Exception e) {
            return gson.toJson(new ResponseModel("203", "Error", e.getMessage()));
        }
    }

    @Path("/add")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Object add(Package item) {
        try {
            var rs = packageService.insert(item);
            return gson.toJson(rs);
        } catch (Exception e) {
            return gson.toJson(new ResponseModel("203", "Error", e.getMessage()));
        }
    }

    @Path("/update")
    @PATCH
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Object update(Package item) {
        try {
            var rs = packageService.update(item);
            return gson.toJson(rs);
        } catch (Exception e) {
            return gson.toJson(new ResponseModel("203", "Error", e.getMessage()));
        }
    }

    @Path("/delete/{id}")
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Object delete(@PathParam("id") int id) {
        try {
            var rs = packageService.delete(id);
            return gson.toJson(rs);
        } catch (Exception e) {
            return gson.toJson(new ResponseModel("203", "Error", e.getMessage()));
        }
    }
}
