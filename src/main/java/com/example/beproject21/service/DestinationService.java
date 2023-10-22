package com.example.beproject21.service;

import com.example.beproject21.model.Destination;
import com.example.beproject21.model.ResponseModel;

import java.util.ArrayList;
import java.util.List;

public class DestinationService {
    static final String INSERT = """
            INSERT INTO destinations (name, image) VALUES (?, ?)
            """;
    static final String SELECT_ALL = """
            SELECT * FROM destinations WHERE isDeleted=0
            """;
    static final String UPDATE = """
            UPDATE destinations SET isDeleted=0 WHERE id=?
            """;
    static final String DELETE = """
            UPDATE destinations SET isDeleted=1 WHERE id=?
            """;

    public ResponseModel insert(Destination item) {
        try (var cnt = DBConnect.getConnection()) {
            if (cnt != null) {
                var ps = cnt.prepareStatement(INSERT);
                ps.setString(1, item.getName());
                ps.setString(2, item.getImage());
                var rs = ps.executeUpdate() > 0;
                return new ResponseModel("200", "Success", rs);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseModel("203", "Lỗi", Boolean.FALSE);
    }

    public ResponseModel update(Destination item) {
        try (var cnt = DBConnect.getConnection()) {
            if (cnt != null) {
                var ps = cnt.prepareStatement(UPDATE);
                ps.setInt(1, item.getId());
                var rs = ps.executeUpdate() > 0;
                return new ResponseModel("200", "Success", rs);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseModel("203", "Lỗi", Boolean.FALSE);
    }

    public Boolean delete(Integer item) {
        try (var cnt = DBConnect.getConnection()) {
            if (cnt != null) {
                var ps = cnt.prepareStatement(DELETE);
                ps.setInt(1, item);
                return ps.executeUpdate() > 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<Destination> selectAll() {
        List<Destination> list = new ArrayList<>();
        try (var cnt = DBConnect.getConnection()) {
            if (cnt != null) {
                var ps = cnt.prepareStatement(SELECT_ALL);
                var rs = ps.executeQuery();
                while (rs.next()) {
                    var item = new Destination();
                    item.setId(rs.getInt("id"));
                    item.setName(rs.getString("name"));
                    item.setImage(rs.getString("image"));
                    list.add(item);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}
