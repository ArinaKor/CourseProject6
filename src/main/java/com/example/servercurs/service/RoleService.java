package com.example.servercurs.service;

import com.example.servercurs.entities.Role;
import com.example.servercurs.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleService {
    private final RoleRepository roleRepository;

    public List<Role> findAllRoles() {
        return roleRepository.findAll();
    }

    public Role findById(int id) {
        return roleRepository.findById(id).orElse(null);
    }

    public Role save(Role role) {
        return roleRepository.save(role);
    }

    public void delete(int id) {
        roleRepository.deleteById(id);
    }

    public Role findRoleByRoleName(String name) {
        return roleRepository.findRoleByRoleName(name);
    }


}
