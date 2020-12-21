package com.nubari.diary.controllers;

import com.nubari.diary.DTOs.APIResponseModel;
import com.nubari.diary.models.Role;
import com.nubari.diary.services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/roles/")
public class RoleController {
    @Autowired
    RoleService roleService;

    @PostMapping("new")
    public ResponseEntity<?> createNewRole(@RequestBody Role role) {
        roleService.createNewRole(role);
        return new ResponseEntity<>(new APIResponseModel(true, "Role created " + role.getName()), HttpStatus.CREATED);
    }
}
