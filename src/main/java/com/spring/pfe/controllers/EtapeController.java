package com.spring.pfe.controllers;


import com.spring.pfe.models.*;
import com.spring.pfe.repository.EtapeRepository;
import com.spring.pfe.repository.QuizRepository;
import com.spring.pfe.repository.ThemeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/etape")
public class EtapeController {
    @Autowired
    private EtapeRepository iTape;
    @Autowired
    private ThemeRepository iTheme;

    @Autowired
    QuizRepository iquiz;
    @Autowired
    private EtapeRepository etapeRepository;

    @GetMapping("/")
    public Response<List<Etape>> findAllFormation () {
        System.out.println("affichage");
        return new Response<List<Etape>>("200", "Get all etapes", iTape.findAll());
    }

    @RequestMapping(value="/add/{theme_id}", method= RequestMethod.POST)
    public Response<Etape> addEtape(@RequestBody Etape p,@PathVariable Long theme_id) {
        try {



                Theme t = iTheme.findById(theme_id).orElse(null);
                p.setTheme(t);
            p.setDate(p.getDate());
              //  p.setNameEtapes(n);

                return new Response<Etape>("200", "Creat Etape", iTape.save(p));

        } catch (Exception e) {
            return new Response<Etape>("406", e.getMessage(), null);
        }

    }

    @DeleteMapping("/deletEtape/{id}")
    public Response<Etape> DeletEtape (@PathVariable("id") Long  id){

        iTape.deleteById(id);
        try{
            return new Response<Etape>("200", "Etape deleted", null);
        } catch (Exception e){
            return new Response<Etape> ("406",e.getMessage(),null);
        }
    }

    @GetMapping("/findById/{id}")
    public Response<Etape> findById(@PathVariable Long id){
        try {

            return new Response<Etape>("200", "Get all Etape", iTape.findById(id).orElse(null));
        }catch (Exception e){
            return new Response<Etape>("406", e.getMessage(), null);

        }
    }

    @PutMapping("/updateEtape/{id}")
    public Response<Etape> updateEtape(@PathVariable("id") Long id, @RequestBody Etape e) {
        try {

            Etape oldUser = iTape.findById(id).orElse(null);
            e.setStep_titre(e.getStep_titre() == null ? oldUser.getStep_titre() : e.getStep_titre());
            e.setIdEtape(id);
            return new Response<Etape>("200","Etape updated", iTape.save(e));
        }catch (Exception et){
            return new Response<Etape>("406", et.getMessage(), null);

        }
    }

    @PutMapping("/updateEtapeProgress/{idEtape}")
    public Response<Etape> updateProgress(@PathVariable("idEtape") Long idEtape,  Etape d) {
        try {

            Etape oldUser = iTape.findById(idEtape).orElse(null);
            d.setStep_titre(d.getStep_titre() == null ? oldUser.getStep_titre() : d.getStep_titre());
            d.setDescription(d.getDescription() == null ? oldUser.getDescription() : d.getDescription());
            d.setDate(d.getDate() == null ? oldUser.getDate() : d.getDate());
            d.setNombre_des_heurs(d.getNombre_des_heurs() == null ? oldUser.getNombre_des_heurs() : d.getNombre_des_heurs());
            d.setTheme(d.getTheme() == null ? oldUser.getTheme() : d.getTheme());

            d.setIdEtape(idEtape);
            d.setProgressByEtape(true);


            return new Response<Etape>("200","Video updated", iTape.save(d));
        }catch (Exception e){
            return new Response<Etape>("406", e.getMessage(), null);

        }
    }

    @GetMapping("/chercher/{id}")
    public Response<List<Etape>> getAllEtapeByTheme(@PathVariable("id") Long id) {

        return  new Response<List<Etape>>("200","get formation by theme",iTape.getAllEtapeByTheme(id));

    }

    @GetMapping("/getEtape/{id}/{progressByEtape}")
    public Response<List<Etape>> progesssz( @PathVariable Long id,@PathVariable Boolean progressByEtape) {


        try {
            return new Response<List<Etape>>("200", "progress", iTape.getEtapeeeActive(id,progressByEtape));

        } catch (Exception e) {
            return new Response<List<Etape>>("406", e.getMessage(), null);
        }

    }

    @GetMapping("/findByIdEtape/{id}")
    public Response<Etape> findByIdd(@PathVariable Long id){
        try {

            return new Response<Etape>("200", "Get all Etape", iTape.findById(id).orElse(null));
        }catch (Exception e){
            return new Response<Etape>("406", e.getMessage(), null);

        }
    }


}
