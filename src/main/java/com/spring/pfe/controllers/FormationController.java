package com.spring.pfe.controllers;

import com.spring.pfe.models.*;
import com.spring.pfe.repository.FormationRepository;
import com.spring.pfe.repository.UserRepository;
import com.spring.pfe.utils.StorageService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.core.io.Resource;

import javax.validation.Valid;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;
@RestController
@RequestMapping("/formation")
@CrossOrigin("*")
public class FormationController {

    @Autowired
    private FormationRepository FormationRepository;
    @Autowired
    private UserRepository iuser;
    @Autowired
    private StorageService storageService;

    @RequestMapping(value="/add", method= RequestMethod.POST)
    public Response<Formation> addFormation(Formation p, @RequestParam("file") MultipartFile file) {
        try {
            if(p!=null) {
               /* Date d=new Date();
                String date=""+d.getDay()+d.getMonth()+d.getYear()+d.getHours()+d.getMinutes()+d.getSeconds();
                p.setPhoto(date+file.getOriginalFilename());*/
                String fileName =storageService.createNameImage(file);
                storageService.store(file,fileName);
                p.setPhoto(fileName);
                p.setArchiver(false);
                return new Response<Formation>("200", "Creat formation", FormationRepository.save(p));
            } else {
                return new Response<Formation>("500", "formation not found", null);
            }
        } catch (Exception e) {
            return new Response<Formation>("406", e.getMessage(), null);
        }

    }
    @RequestMapping(value="/adUser/{users_id}", method= RequestMethod.POST)
    public Response<Formation> addUserToFormation(Formation p, @RequestParam("id_user") Long users_id) {
        try {
            if(p!=null) {
             User u = iuser.findById(users_id).orElse(null);
             System.out.println(u);

                return new Response<Formation>("200", "Creat user To formation", FormationRepository.save(p));
            } else {
                return new Response<Formation>("500", "User not found", null);
            }
        } catch (Exception e) {
            return new Response<Formation>("406", e.getMessage(), null);
        }

    }





    @GetMapping("/")
    public Response<List<Formation>> findAllFormationNonArchiver () {
        return new Response<List<Formation>>("200", "Get all Formation Non Archiver", FormationRepository.getAllFormationByArchiver(false));
    }

    @GetMapping("/archive")
    public Response<List<Formation>> findAllFormationArchiver () {
        return new Response<List<Formation>>("200", "Get all Formation Archiver", FormationRepository.getAllFormationByArchiver(true));
    }

    @DeleteMapping("/deleteFormation/{id}")
    public Response<Formation> deleteFormation (@PathVariable("id") Long  id){


        Formation f = FormationRepository.findById(id).orElse(null);

        f.setDescription(f.getDescription());

f.setArchiver(true);

        try{
            return new Response<Formation>("200", "Formation deleted",  FormationRepository.save(f));
        } catch (Exception e){
            return new Response<Formation> ("406",e.getMessage(),null);
        }
    }

    @GetMapping("/findById/{id}")
    public Response<Formation> findById(@PathVariable Long id){
        try {

            return new Response<Formation>("200", "Get Formation by id", FormationRepository.findById(id).orElse(null));
        }catch (Exception e){
            return new Response<Formation>("406", e.getMessage(), null);

        }
    }

    @PutMapping("/updateFormation/{id}")
    public Response<Formation> updateFormation(@PathVariable("id") Long id, Formation f,@RequestParam("file") MultipartFile file) {
        try {

            Formation oldUser = FormationRepository.findById(id).orElse(null);
            f.setTitre(f.getTitre() == null ? oldUser.getTitre() : f.getTitre());
            f.setDescription(f.getDescription() == null ? oldUser.getDescription() : f.getDescription());
            f.setPhoto(f.getPhoto() == null ? oldUser.getPhoto() : f.getPhoto());
            f.setDate_deDebut(f.getDate_deDebut() == null ? oldUser.getDate_deDebut() : f.getDate_deDebut());
            f.setDate_defin(f.getDate_defin() == null ? oldUser.getDate_defin() : f.getDate_defin());
            f.setArchiver(f.getArchiver() == null ? oldUser.getArchiver() : f.getArchiver());
            f.setId(id);

            String fileName =storageService.createNameImage(file);
            storageService.store(file,fileName);
            f.setPhoto(fileName);
            return new Response<Formation>("200","Formation updated", FormationRepository.save(f));
        }catch (Exception e){
            return new Response<Formation>("406", e.getMessage(), null);

        }
    }
/*
    @PostMapping("/active")
    public Response<List<Formation>>AllFormationActive(@RequestBody Formation f){
        try {

            return new Response<List<Formation>>("200", "Get Formation Active", FormationRepository.getAllFormationActive(f.getFin_date(),false));
        }catch (Exception e){
            return new Response<List<Formation>>("406", e.getMessage(), null);

        }
    }

    @PostMapping("/Notactive")
    public Response<List<Formation>>AllFormationNotActive(@RequestBody Formation f){
        try {

            return new Response<List<Formation>>("200", "Get Formation Not Active", FormationRepository.getAllFormationNotActive(f.getFin_date()));
        }catch (Exception e){
            return new Response<List<Formation>>("406", e.getMessage(), null);

        }
    }

    // get all formation archiver
    // get By id formation archiver

    @PostMapping("/allformation")
    public Response<List<Formation>>AllFormationByUserId(@RequestBody Formation f){
        try {

            return new Response<List<Formation>>("200", "Get Formation Active", FormationRepository.getAllFormationActive(f.getFin_date(),false));
        }catch (Exception e){
            return new Response<List<Formation>>("406", e.getMessage(), null);

        }
    }

*/


    @DeleteMapping("/delete/{id}")
    public Response<Formation> deleteformation (@PathVariable("id") Long  id){

        FormationRepository.deleteById(id);
        try{
            return new Response<Formation>("200", "formation deleted", null);
        } catch (Exception e){
            return new Response<Formation> ("406",e.getMessage(),null);
        }
    }

    @PutMapping("/updateFormationProgress/{id}/{resultProgres}")
    public Response<Formation> updateProgress(@PathVariable("id") Long id,@PathVariable("resultProgres") String resultProgres,  Formation f) {
        try {

            Formation oldUser = FormationRepository.findById(id).orElse(null);
            f.setTitre(f.getTitre() == null ? oldUser.getTitre() : f.getTitre());
            f.setDescription(f.getDescription() == null ? oldUser.getDescription() : f.getDescription());
            f.setPhoto(f.getPhoto() == null ? oldUser.getPhoto() : f.getPhoto());
            f.setDate_deDebut(f.getDate_deDebut() == null ? oldUser.getDate_deDebut() : f.getDate_deDebut());
            f.setDate_defin(f.getDate_defin() == null ? oldUser.getDate_defin() : f.getDate_defin());
            f.setArchiver(f.getArchiver() == null ? oldUser.getArchiver() : f.getArchiver());
            f.setResultProgres(resultProgres);
            f.setId(id);


            return new Response<Formation>("200","Video updated", FormationRepository.save(f));
        }catch (Exception e){
            return new Response<Formation>("406", e.getMessage(), null);

        }
    }
}

