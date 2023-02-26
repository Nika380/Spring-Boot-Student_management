package com.example.studentmanagementsystem.controller;

import com.example.studentmanagementsystem.dto.GroupDto;
import com.example.studentmanagementsystem.entity.Group;
import com.example.studentmanagementsystem.search.SearchParams;
import com.example.studentmanagementsystem.service.group.GroupServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/groups")
@RequiredArgsConstructor
@CrossOrigin("*")
public class GroupController {
    private final GroupServiceImpl groupService;

    @GetMapping
    public List<GroupDto> getAllGroups(SearchParams params) {
        return groupService.getGroupList(params);
    }

    @PostMapping("/add-group")
    public Group addGroup(@RequestBody GroupDto dto) {
        return groupService.addGroup(dto);
    }

    @PutMapping("/{id}/update")
    public Group updateGroup(@PathVariable int id, @RequestBody GroupDto dto) {
        return groupService.updateGroup(id, dto);
    }

    @DeleteMapping("/{id}/delete")
    public String deleteGroup(@PathVariable int id) {
        return groupService.deleteGroup(id);
    }

    @GetMapping("/{id}")
    public GroupDto byId(@PathVariable int id) {
        return groupService.findGroupById(id);
    }
}
