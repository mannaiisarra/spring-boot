package com.spring.pfe.controllers;


import com.spring.pfe.models.*;
import com.spring.pfe.repository.CoursRepository;
import com.spring.pfe.repository.EtapeRepository;
import com.spring.pfe.repository.VideoRepository;
import com.spring.pfe.security.services.VideoStreamingService;
import com.spring.pfe.utils.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import reactor.core.publisher.Mono;


import java.util.List;

@RestController
@RequestMapping("/video")
@CrossOrigin("*")
public class VideoController {

    @Autowired
    VideoRepository videoRepository;
    @Autowired
    private StorageService storageService;

    @Autowired
    EtapeRepository itape;

    @Autowired
    VideoStreamingService videoStreamingService;

    @GetMapping(value = "/{titleName}" , produces = "video/mp4")
    public Mono<Resource> getVideo(@PathVariable String titleName) {
        try {
            return videoStreamingService.getVideoStreaming(titleName);
        }
        catch (Exception e){
            System.out.println("erreur");
            return null;

        }
    }

    @RequestMapping(value="/add/{etape_id_etape}", method= RequestMethod.POST)
    public Response<Video> addVideo(Video p, @RequestParam("file") MultipartFile file, @PathVariable("etape_id_etape") Long etape_id_etape) {
        try {
            if(p!=null) {
             /*   Date d=new Date();
                String date=""+d.getDay()+d.getMonth()+d.getYear()+d.getHours()+d.getMinutes()+d.getSeconds();*/
                String fileName =storageService.createNameImage(file);
                storageService.store(file,fileName);
                p.setPhoto(fileName);
                p.setName(p.getName());
                p.setProgress(false);
                Etape e = itape.findById(etape_id_etape).orElse(null);
                p.setEtape(e);
                return new Response<Video>("200", "Creat Video", videoRepository.save(p));
            } else {
                return new Response<Video>("500", "Video not found", null);
            }
        } catch (Exception e) {
            return new Response<Video>("406", e.getMessage(), null);
        }

    }



    @RequestMapping(value="/adddf",method= RequestMethod.POST)
    public Response<Video> addQuizz( @RequestParam("file") MultipartFile file) {
        try {

            Video p = new Video();
            String fileName =storageService.createNameImage(file);
            storageService.store(file,fileName);
            p.setPhoto(fileName);
            p.setName(p.getName());
            return new Response<Video>("200", "Creat quiz", videoRepository.save(p));

        } catch (Exception e) {
            return new Response<Video>("406", e.getMessage(), null);
        }

    }


    @GetMapping("/findVideo/{etape_id_etape}")
    public Response<List<Video>> findCoursByEtape(@PathVariable Long etape_id_etape){
        try {

            return new Response<List<Video>>("200", "Get all video by Etape", videoRepository.getAllVideoByEtape(etape_id_etape));

        }catch (Exception e){
            return new Response<List<Video>>("406", e.getMessage(), null);

        }
    }



    @GetMapping("/findById/{id}")
    public Response<Video> findById(@PathVariable Long id){
        try {

            return new Response<Video>("200", "Get video by id", videoRepository.findById(id).orElse(null));
        }catch (Exception e){
            return new Response<Video>("406", e.getMessage(), null);

        }
    }


    @GetMapping("/All")
    public Response<List<Video>> findAll () {
        return new Response<List<Video>>("200", "Get all video",videoRepository.findAll());
    }
    @DeleteMapping("/delete/{id}")
    public Response<Video> DeleteVideo (@PathVariable("id") Long  id){

        videoRepository.deleteById(id);
        try{
            return new Response<Video>("200", "Theme deleted", null);
        } catch (Exception e){
            return new Response<Video> ("406",e.getMessage(),null);
        }
    }
    @PutMapping("/updatevideo/{id}")
    public Response<Video> updateVideo(@PathVariable("id") Long id, Video d,@RequestParam("file") MultipartFile file) {
        try {

            Video oldCours = videoRepository.findById(id).orElse(null);
            d.setPhoto(d.getPhoto() == null ? oldCours.getPhoto() : d.getPhoto());
            d.setName(d.getName() == null ? oldCours.getName() : d.getName());
            d.setEtape(d.getEtape() == null ? oldCours.getEtape() : d.getEtape());
            d.setId(id);

            String fileName =storageService.createNameImage(file);
            storageService.store(file,fileName);
            d.setPhoto(fileName);
            return new Response<Video>("200","Video updated", videoRepository.save(d));
        }catch (Exception e){
            return new Response<Video>("406", e.getMessage(), null);

        }
    }


    @PutMapping("/updateProgress/{id}")
    public Response<Video> updateProgress(@PathVariable("id") Long id, Video d) {
        try {

            Video oldCours = videoRepository.findById(id).orElse(null);
            d.setPhoto(d.getPhoto() == null ? oldCours.getPhoto() : d.getPhoto());
            d.setName(d.getName() == null ? oldCours.getName() : d.getName());
            d.setEtape(d.getEtape() == null ? oldCours.getEtape() : d.getEtape());
            d.setProgress(true);
            d.setId(id);


            return new Response<Video>("200","Video updated", videoRepository.save(d));
        }catch (Exception e){
            return new Response<Video>("406", e.getMessage(), null);

        }
    }
    @GetMapping("/progress/{idEtape}/{progress}")
        public Response<List<Video>> progess( @PathVariable Long idEtape,@PathVariable Boolean progress) {


        try {
            return new Response<List<Video>>("200", "progress", videoRepository.progress(idEtape,progress));

        } catch (Exception e) {
            return new Response<List<Video>>("406", e.getMessage(), null);
        }

    }

}
