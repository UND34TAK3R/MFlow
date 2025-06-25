package com.mdev.mflow.dao;

import com.mdev.mflow.model.Tag;

import java.util.List;

public interface TagDAO {
    boolean createTag(Tag tag);
    boolean updateTag(Tag tag);
    boolean deleteTag(Tag tag);
    Tag getTag(String tagId);
    List<Tag> getAllTags();
}
