package com.example.beproject21.service;

import com.example.beproject21.model.Booking;
import com.example.beproject21.model.Package;
import com.example.beproject21.model.ResponseModel;

import java.util.ArrayList;
import java.util.List;

public class BookingService {
    static final String INSERT = """
            INSERT INTO bookings(user_id, full_name, email, tour_date, package_id, request, status) VALUES (?, ?, ?, ?, ?, ?, ?);
            """;

    static final String SELECT_BY_UID = """
            SELECT b.*, p.name as package_name 
            FROM bookings b 
            INNER JOIN packages p on b.package_id = p.id
            WHERE user_id = ?;
            """;
    static final String SELECT_ALL = """
            SELECT b.*, p.name as package_name
            FROM bookings b
            INNER JOIN packages p on b.package_id = p.id
            """;

    static final String UPDATE = """
            UPDATE bookings SET status = ? WHERE id = ?;
            """; // status: 0: pending, 1: accepted, 2: rejected

    public ResponseModel insert(Booking item) {
        try (var cnt = DBConnect.getConnection()) {
            if (cnt != null) {
                var ps = cnt.prepareStatement(INSERT);
                ps.setInt(1, item.getUserId());
                ps.setString(2, item.getFullName());
                ps.setString(3, item.getEmail());
                ps.setString(4, item.getTourDate().toString());
                ps.setInt(5, item.getPackageId());
                ps.setString(6, item.getRequest());
                ps.setString(7, item.getStatus());
                var rs = ps.executeUpdate() > 0;
                return new ResponseModel("200", "Success", rs);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseModel("203", "Lỗi", Boolean.FALSE);
    }

    public ResponseModel update(Booking item) {
        try (var cnt = DBConnect.getConnection()) {
            if (cnt != null) {
                var ps = cnt.prepareStatement(UPDATE);
                ps.setString(1, item.getStatus());
                ps.setInt(2, item.getId());
                var rs = ps.executeUpdate() > 0;
                return new ResponseModel("200", "Success", rs);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseModel("203", "Lỗi", Boolean.FALSE);
    }

    public List<Booking> selectByUid(Integer uid) {
        List<Booking> rs = null;
        try (var cnt = DBConnect.getConnection()) {
            if (cnt != null) {
                var ps = cnt.prepareStatement(SELECT_BY_UID);
                ps.setInt(1, uid);
                var rs1 = ps.executeQuery();
                rs = new ArrayList<>();
                while (rs1.next()) {
                    var item = new Booking();
                    item.setId(rs1.getInt("id"));
                    item.setUserId(rs1.getInt("user_id"));
                    item.setFullName(rs1.getString("full_name"));
                    item.setEmail(rs1.getString("email"));
                    item.setTourDate(rs1.getDate("tour_date"));
                    item.setPackageId(rs1.getInt("package_id"));
                    item.setRequest(rs1.getString("request"));
                    item.setStatus(rs1.getString("status"));

                    Package pack = new Package();
                    pack.setId(rs1.getInt("package_id"));
                    pack.setName(rs1.getString("package_name"));
                    item.setPack(pack);
                    rs.add(item);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rs;
    }

    public List<Booking> selectAll() {
        List<Booking> rs = null;
        try (var cnt = DBConnect.getConnection()) {
            if (cnt != null) {
                var ps = cnt.prepareStatement(SELECT_ALL);
                var rs1 = ps.executeQuery();
                rs = new ArrayList<>();
                while (rs1.next()) {
                    var item = new Booking();
                    item.setId(rs1.getInt("id"));
                    item.setUserId(rs1.getInt("user_id"));
                    item.setFullName(rs1.getString("full_name"));
                    item.setEmail(rs1.getString("email"));
                    item.setTourDate(rs1.getDate("tour_date"));
                    item.setPackageId(rs1.getInt("package_id"));
                    item.setRequest(rs1.getString("request"));
                    item.setStatus(rs1.getString("status"));

                    Package pack = new Package();
                    pack.setId(rs1.getInt("package_id"));
                    pack.setName(rs1.getString("package_name"));
                    item.setPack(pack);
                    rs.add(item);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rs;
    }
}
