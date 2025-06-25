//REVISON HISTORY:
//      DATE            NAME                COMMENTS
//  2025/06/15      DERRICK MANGARI      Created DAO functions that allows users to create, update, delete Tags

package com.mdev.mflow.dao.impl;

import com.mdev.mflow.dao.TagDAO;
import com.mdev.mflow.model.Tag;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public abstract class TagDAOImpl implements TagDAO {

    private final DataSource dataSource;

    public TagDAOImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

//ALLOWS TO CREATE TAGS
    @Override
    public boolean createTag(Tag tag){
        String sql = "{CALL insert_tag(?, ?, ?, ?)}";
        try (Connection conn = dataSource.getConnection();
             CallableStatement stmt = conn.prepareCall(sql)) {
            String tagId = UUID.randomUUID().toString();
            stmt.setString(1, tagId);
            stmt.setString(2, tag.getName());
            stmt.setString(3, tag.getUserId());
            stmt.setString(4, tag.getColor());
            stmt.execute();
            return true;

        } catch (SQLException e) {
            System.err.println("Failed to create tag: " + e.getMessage());
            return false;
        }
    }

//ALLOWS TO UPDATE AN EXISTING TAG
    @Override
    public boolean updateTag(Tag tag) {
        String sql = "{CALL update_tag(?, ?, ?, ?)}";
        try (Connection conn = dataSource.getConnection();
             CallableStatement stmt = conn.prepareCall(sql)) {
            stmt.setString(1, tag.getTagId());
            stmt.setString(2, tag.getName());
            stmt.setString(3, tag.getUserId());
            stmt.setString(4, tag.getColor());
            stmt.execute();
            return true;

        } catch (SQLException e) {
            System.err.println("Failed to update tag: " + e.getMessage());
            return false;
        }
    }

//ALLOWS TO DELETE AN EXISTING TAG
    @Override
    public boolean deleteTag(Tag tag) {
        String sql = "{CALL delete_tag(?)}";
        try(Connection conn = dataSource.getConnection();
            CallableStatement stmt = conn.prepareCall(sql)){
            stmt.setString(1, tag.getTagId());
            stmt.execute();
            return true;
        }catch(SQLException e){
            System.err.println("Failed to delete tag: " + e.getMessage());
            return false;
        }
    }

//ALLOWS TO LOAD A TAG
    @Override
    public Tag getTag(String p_tag_id) {
        String sql = "{CALL select_one_tag(?)}";
        try (Connection conn = dataSource.getConnection();
             CallableStatement stmt = conn.prepareCall(sql)) {

            stmt.setString(1, p_tag_id);
            boolean hasResult = stmt.execute();

            if (hasResult) {
                try (ResultSet rs = stmt.getResultSet()) {
                    if (rs.next()) {
                        String tag_id = rs.getString("tag_id");
                        String name = rs.getString("name");
                        String user_id = rs.getString("user_id");
                        String color = rs.getString("color");
                        return new Tag(tag_id, name, color, user_id);
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Failed to get tag: " + e.getMessage());
        }
        return null;
    }

//ALLOWS TO SAVE ALL TAGS INSIDE A LIST
    @Override
    public List<Tag> getAllTags() {
        List<Tag> tags = new ArrayList<>();
        String sql = "{CALL select_all_tags()}";
        try (Connection conn = dataSource.getConnection();
             CallableStatement stmt = conn.prepareCall(sql)) {
            boolean hasResult = stmt.execute();
            if (hasResult) {
                try (ResultSet rs = stmt.getResultSet()) {
                    while (rs.next()) {
                        String tag_id = rs.getString("tag_id");
                        String name = rs.getString("name");
                        String user_id = rs.getString("user_id");
                        String color = rs.getString("color");
                        tags.add(new Tag(tag_id, name, color, user_id));
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Failed to get all tags: " + e.getMessage());
        }
        return tags;
    }
}