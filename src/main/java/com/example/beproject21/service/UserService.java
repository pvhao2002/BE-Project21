package com.example.beproject21.service;

import com.example.beproject21.model.ResponseModel;
import com.example.beproject21.model.User;

import java.util.*;

public class UserService {
    static final String INSERT = """
            INSERT INTO users(username, password, email, full_name, address, role_id) 
            VALUES (?, ?, ?, ?, ?, ?)
            """;
    static final String UPDATE = """
            UPDATE users
            SET isDeleted = '0'
            WHERE id = ?
            """;
    static final String DELETE = """
            UPDATE users
            SET isDeleted = '1'
            WHERE id = ?
            """;
    static final String SELECT_ALL = """
            SELECT u.*, r.name AS role_name
            FROM users u
            JOIN role r ON u.role_id = r.id
            """;
    static final String SELECT_BY_ID = """
            SELECT u.id, u.username, u.password, u.email, u.full_name, u.address, u.role_id, r.name AS role_name
            FROM users u
            JOIN role r ON u.role_id = r.id
            WHERE u.id = ? AND u.isDeleted = '0'
            """;
    static final String SELECT_BY_USERNAME = """
            SELECT u.id, u.username, u.password, u.email, u.full_name, u.address, u.role_id, r.name AS role_name
            FROM users u
            JOIN role r ON u.role_id = r.id
            WHERE u.username = ? AND u.isDeleted = '0'
            """;

    public ResponseModel login(User item) {
        var user = selectByUsername(item.getUsername());
        String message = "Tên đăng nhập hoặc mật khẩu không đúng";
        if (user == null) {
            return new ResponseModel("203", message, Boolean.FALSE);
        }
        if (!user.getPassword().equals(item.getPassword())) {
            return new ResponseModel("203", message, Boolean.FALSE);
        }
        return new ResponseModel("200", "Success", user);
    }

    public ResponseModel insert(User item) {
        if (selectByUsername(item.getUsername()) != null) {
            return new ResponseModel("203", "Tên đăng nhập đã tồn tại", Boolean.FALSE);
        }

        try (var cnt = DBConnect.getConnection()) {
            if (cnt != null) {
                var ps = cnt.prepareStatement(INSERT);
                ps.setString(1, item.getUsername());
                ps.setString(2, item.getPassword());
                ps.setString(3, item.getEmail());
                ps.setString(4, item.getFullName());
                ps.setString(5, item.getAddress());
                ps.setInt(6, item.getRoleId());
                var rs = ps.executeUpdate() > 0;
                return new ResponseModel("200", "Success", rs);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseModel("500", "Lỗi kết nối", Boolean.FALSE);
    }

    public Boolean update(User item) {
        try (var cnt = DBConnect.getConnection()) {
            if (cnt != null) {
                var ps = cnt.prepareStatement(UPDATE);
                ps.setInt(1, item.getId());
                return ps.executeUpdate() > 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public ResponseModel delete(Integer id) {
        try (var cnt = DBConnect.getConnection()) {
            if (cnt != null) {
                var ps = cnt.prepareStatement(DELETE);
                ps.setInt(1, id);
                var rs = ps.executeUpdate() > 0;
                return new ResponseModel("200", "Success", rs);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseModel("203", "Lỗi", Boolean.FALSE);
    }

    public List<User> selectAll() {
        List<User> list;
        try (var cnt = DBConnect.getConnection()) {
            if (cnt != null) {
                var ps = cnt.prepareStatement(SELECT_ALL);
                var rs = ps.executeQuery();
                list = new ArrayList<>();
                while (rs.next()) {
                    var item = new User();
                    item.setId(rs.getInt("id"));
                    item.setUsername(rs.getString("username"));
                    item.setPassword(rs.getString("password"));
                    item.setEmail(rs.getString("email"));
                    item.setFullName(rs.getString("full_name"));
                    item.setAddress(rs.getString("address"));
                    item.setRoleId(rs.getInt("role_id"));
                    item.setIsDeleted(rs.getString("isDeleted"));
                    list.add(item);
                }
                return list;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public User selectById(Integer id) {
        try (var cnt = DBConnect.getConnection()) {
            if (cnt != null) {
                var ps = cnt.prepareStatement(SELECT_BY_ID);
                ps.setInt(1, id);
                var rs = ps.executeQuery();
                if (rs.next()) {
                    var item = new User();
                    item.setId(rs.getInt("id"));
                    item.setUsername(rs.getString("username"));
                    item.setPassword(rs.getString("password"));
                    item.setEmail(rs.getString("email"));
                    item.setFullName(rs.getString("full_name"));
                    item.setAddress(rs.getString("address"));
                    item.setRoleId(rs.getInt("role_id"));
                    return item;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public User selectByUsername(String username) {
        try (var cnt = DBConnect.getConnection()) {
            if (cnt != null) {
                var ps = cnt.prepareStatement(SELECT_BY_USERNAME);
                ps.setString(1, username);
                var rs = ps.executeQuery();
                if (rs.next()) {
                    var item = new User();
                    item.setId(rs.getInt("id"));
                    item.setUsername(rs.getString("username"));
                    item.setPassword(rs.getString("password"));
                    item.setEmail(rs.getString("email"));
                    item.setFullName(rs.getString("full_name"));
                    item.setAddress(rs.getString("address"));
                    item.setRoleId(rs.getInt("role_id"));
                    return item;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
