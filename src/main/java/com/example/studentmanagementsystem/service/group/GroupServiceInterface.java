package com.example.studentmanagementsystem.service.group;

import com.example.studentmanagementsystem.dto.GroupDto;
import com.example.studentmanagementsystem.entity.Group;
import com.example.studentmanagementsystem.search.SearchParams;

import java.util.List;

public interface GroupServiceInterface {
    List<GroupDto> getGroupList(SearchParams params);
    Group addGroup(GroupDto dto);
    Group updateGroup(int id, GroupDto dto);
    String deleteGroup(int id);
    Group findById(int id);
    GroupDto findGroupById(int id);
}
