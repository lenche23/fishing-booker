package com.isa.project.service;

import com.isa.project.model.AppUser;
import com.isa.project.repository.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
public class AppUserService {

    @Autowired
    private AppUserRepository appUserRepository;

    public AppUser findOne(long id) { return appUserRepository.findById(id).orElse(null); }

    public List<AppUser> findAll() { return appUserRepository.findAll(); }

    public AppUser save(AppUser appUser) { return appUserRepository.save(appUser); }

    public void remove(long id) { appUserRepository.deleteById(id); }

    public Collection<AppUser> findByEmail(String email) { return appUserRepository.findByEmail(email); }
}
