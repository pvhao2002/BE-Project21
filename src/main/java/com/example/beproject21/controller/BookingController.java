package com.example.beproject21.controller;


import com.example.beproject21.model.Booking;
import com.example.beproject21.model.ResponseModel;
import com.example.beproject21.service.BookingService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

@Path("/booking")
public class BookingController {

    private final Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
    private final BookingService bookingService = new BookingService();

    @Path("/add")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Object addBooking(Booking item) {
        try {
            var rs = bookingService.insert(item);
            return gson.toJson(rs);
        } catch (Exception e) {
            return gson.toJson(e.getMessage());
        }
    }

    @Path("/getAll")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Object getAll() {
        try {
            var rs = bookingService.selectAll();
            return gson.toJson(rs);
        } catch (Exception e) {
            return gson.toJson(e.getMessage());
        }
    }

    @Path("/update")
    @PATCH
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Object update(Booking item) {
        try {
            var rs = bookingService.update(item);
            return gson.toJson(rs);
        } catch (Exception e) {
            return gson.toJson(e.getMessage());
        }
    }

    @Path("/getByUser/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Object getByUser(@PathParam("id") int id) {
        try {
            var rs = bookingService.selectByUid(id);
            return gson.toJson(rs);
        } catch (Exception e) {
            return gson.toJson(new ResponseModel("203", "Error", e.getMessage()));
        }
    }
}
