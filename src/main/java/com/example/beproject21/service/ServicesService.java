package com.example.beproject21.service;

import com.example.beproject21.model.ResponseModel;
import com.example.beproject21.model.Services;

import java.util.ArrayList;
import java.util.List;

public class ServicesService {
    static final String INSERT = """
            INSERT INTO services(name, description) VALUES (?,?);
            """;
    static final String SELECT_ALL = """
            SELECT * FROM services;
            """;
    static final String UPDATE = """
            UPDATE services SET name=?, description=? WHERE id=?;
            """;
    static final String DELETE = """
            DELETE FROM services WHERE id=?;
            """;

    public ResponseModel insert(Services item) {
        try (var cnt = DBConnect.getConnection()) {
            if (cnt != null) {
                var ps = cnt.prepareStatement(INSERT);
                ps.setString(1, item.getName());
                ps.setString(2, item.getDescription());
                var rs = ps.executeUpdate() > 0;
                return new ResponseModel("200", "Success", rs);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseModel("203", "Lỗi", Boolean.FALSE);
    }

    public ResponseModel update(Services item) {
        try (var cnt = DBConnect.getConnection()) {
            if (cnt != null) {
                var ps = cnt.prepareStatement(UPDATE);
                ps.setString(1, item.getName());
                ps.setString(2, item.getDescription());
                ps.setInt(3, item.getId());
                var rs =  ps.executeUpdate() > 0;
                return new ResponseModel("200", "Success", rs);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseModel("203", "Lỗi", Boolean.FALSE);
    }

    public ResponseModel delete(Integer item) {
        try (var cnt = DBConnect.getConnection()) {
            if (cnt != null) {
                var ps = cnt.prepareStatement(DELETE);
                ps.setInt(1, item);
                var rs = ps.executeUpdate() > 0;
                return new ResponseModel("200", "Success", rs);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseModel("203", "Lỗi", Boolean.FALSE);
    }

    public List<Services> selectAll() {
        List<Services> list;
        try (var cnt = DBConnect.getConnection()) {
            if (cnt != null) {
                var ps = cnt.prepareStatement(SELECT_ALL);
                var rs = ps.executeQuery();
                list = new ArrayList<>();
                while (rs.next()) {
                    var item = new Services();
                    item.setId(rs.getInt("id"));
                    item.setName(rs.getString("name"));
                    item.setDescription(rs.getString("description"));
                    list.add(item);
                }
                return list;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
