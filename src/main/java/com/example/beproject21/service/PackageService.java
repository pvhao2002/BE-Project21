package com.example.beproject21.service;

import com.example.beproject21.model.Destination;
import com.example.beproject21.model.Package;
import com.example.beproject21.model.ResponseModel;

import java.util.ArrayList;
import java.util.List;

public class PackageService {
    static final String INSERT = """
            INSERT INTO packages(name, price, destination_id, days, number_of_people, description, image) 
            VALUES (?,?,?,?,?,?,?);
            """;
    static final String SELECT_ALL = """
            SELECT p.*, d.name as destination_name
            FROM packages p
            INNER JOIN destinations d ON p.destination_id = d.id
            WHERE p.isDeleted = '0'
            """;
    static final String UPDATE = """
            UPDATE  packages
            SET isDeleted = '0'
            WHERE id = ?
            """;
    static final String DELETE = """
            UPDATE packages
            SET isDeleted = '1'
            WHERE id = ?
            """;

    public ResponseModel insert(Package item) {
        try (var cnt = DBConnect.getConnection()) {
            if (cnt != null) {
                var ps = cnt.prepareStatement(INSERT);
                ps.setString(1, item.getName());
                ps.setDouble(2, item.getPrice());
                ps.setInt(3, item.getDestinationId());
                ps.setInt(4, item.getDays());
                ps.setInt(5, item.getNumberOfPeople());
                ps.setString(6, item.getDescription());
                ps.setString(7, item.getImage());
                var rs = ps.executeUpdate() > 0;
                return new ResponseModel("200", "Success", rs);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseModel("203", "Lỗi", Boolean.FALSE);
    }

    public ResponseModel update(Package item) {
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

    public List<Package> selectAll() {
        List<Package> list;
        try (var cnt = DBConnect.getConnection()) {
            if (cnt != null) {
                var ps = cnt.prepareStatement(SELECT_ALL);
                var rs = ps.executeQuery();
                list = new ArrayList<>();
                while (rs.next()) {
                    var item = new Package();
                    item.setId(rs.getInt("id"));
                    item.setName(rs.getString("name"));
                    item.setPrice(rs.getDouble("price"));
                    item.setDestinationId(rs.getInt("destination_id"));
                    item.setDays(rs.getInt("days"));
                    item.setNumberOfPeople(rs.getInt("number_of_people"));
                    item.setDescription(rs.getString("description"));
                    item.setImage(rs.getString("image"));
                    item.setIsDeleted(rs.getBoolean("isDeleted"));

                    Destination destination = new Destination();
                    destination.setName(rs.getString("destination_name"));
                    item.setDestination(destination);
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
