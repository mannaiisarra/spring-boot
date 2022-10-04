package com.spring.pfe.controllers;

import com.spring.pfe.models.*;
import com.spring.pfe.repository.CoursRepository;
import com.spring.pfe.repository.EtapeRepository;
import com.spring.pfe.utils.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collections;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/cours")
@CrossOrigin("*")
public class CoursController {

    @Autowired
    CoursRepository icours;
    @Autowired
    EtapeRepository itape;

    @Autowired
    private StorageService storageService;

    @RequestMapping(value="/addd/{etape_id_etape}", method= RequestMethod.POST)
    public Response<Cours> addCours(Cours p, @RequestParam("file") MultipartFile file,@PathVariable("etape_id_etape") Long etape_id_etape) {
        try {
            if(p!=null) {
             /*   Date d=new Date();
                String date=""+d.getDay()+d.getMonth()+d.getYear()+d.getHours()+d.getMinutes()+d.getSeconds();*/
                String fileName =storageService.createNameImage(file);
                storageService.store(file,fileName);
                p.setPhoto(fileName);

                Etape e = itape.findById(etape_id_etape).orElse(null);
                p.setEtape(e);
                return new Response<Cours>("200", "Creat cours", icours.save(p));
            } else {
                return new Response<Cours>("500", "cours not found", null);
            }
        } catch (Exception e) {
            return new Response<Cours>("406", e.getMessage(), null);
        }

    }

    @GetMapping("/")
    public Response<List<Cours>> findAll () {
        return new Response<List<Cours>>("200", "Get all demande", icours.findAll());
    }


    @GetMapping("/findCours/{etape_id_etape}")
    public Response<List<Cours>> findCoursByEtape(@PathVariable Long etape_id_etape){
        try {

            return new Response<List<Cours>>("200", "Get all cours by Etape", icours.getAllUserParFormation(etape_id_etape));

        }catch (Exception e){
            return new Response<List<Cours>>("406", e.getMessage(), null);

        }
    }


    @DeleteMapping("/deleteCours/{id}")
    public Response<Cours> deletecours (@PathVariable("id") Long  id){

        icours.deleteById(id);
        try{
            return new Response<Cours>("200", "Formation deleted", null);
        } catch (Exception e){
            return new Response<Cours> ("406",e.getMessage(),null);
        }
    }





    @PutMapping("/updateCours/{id}")
    public Response<Cours> updateFormation(@PathVariable("id") Long id, Cours d,@RequestParam("file") MultipartFile file) {
        try {

            Cours oldCours = icours.findById(id).orElse(null);
            d.setPhoto(d.getPhoto() == null ? oldCours.getPhoto() : d.getPhoto());
                d.setDescription(d.getDescription() == null ? oldCours.getDescription() : d.getDescription());
            d.setDate(d.getDate() == null ? oldCours.getDate() : d.getDate());
            d.setEtape(d.getEtape() == null ? oldCours.getEtape() : d.getEtape());
            d.setId(id);

            String fileName =storageService.createNameImage(file);
            storageService.store(file,fileName);
            d.setPhoto(fileName);
            return new Response<Cours>("200","Formation updated", icours.save(d));
        }catch (Exception e){
            return new Response<Cours>("406", e.getMessage(), null);

        }
    }

    @GetMapping("/findById/{id}")
    public Response<Cours> findById(@PathVariable Long id){
        try {

            return new Response<Cours>("200", "Get Cours by id", icours.findById(id).orElse(null));
        }catch (Exception e){
            return new Response<Cours>("406", e.getMessage(), null);

        }
    }

}
