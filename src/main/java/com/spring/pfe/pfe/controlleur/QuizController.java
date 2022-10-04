package com.spring.pfe.controllers;


import com.spring.pfe.models.*;
import com.spring.pfe.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/quiz")
@CrossOrigin("*")
public class QuizController {

    @Autowired
    private EtapeRepository iTape;
    @Autowired
    private FormationRepository iformation;
    @Autowired
    QuizRepository iquiz;

    @Autowired
    private QuizResultResponseRepository quizResultResponseRepository;
    @RequestMapping(value="/add/{etape_id_etape}/{formation}",method= RequestMethod.POST)
    public Response<Quiz> addQuiz(@RequestBody Quiz t ,@PathVariable("etape_id_etape") Long etape_id_etape,@PathVariable("formation") Long formation ) {
        try {
            Etape u = iTape.findById(etape_id_etape).orElse(null);
            Formation FR = iformation.findById(formation).orElse(null);
            t.setEtape(u);
            t.setFormation(FR);
            return new Response<Quiz>("200", "Creat quiz", iquiz.save(t));

        } catch (Exception e) {
            return new Response<Quiz>("406", e.getMessage(), null);
        }

    }


    @RequestMapping(value="/addd",method= RequestMethod.POST)
    public Response<Quiz> addQuizz(@RequestBody Quiz t ) {
        try {

            return new Response<Quiz>("200", "Creat quiz", iquiz.save(t));

        } catch (Exception e) {
            return new Response<Quiz>("406", e.getMessage(), null);
        }

    }
    @GetMapping("/getQuizbyclick/{id}/{idUser}")
    public Response<List<QuizResultResponse>>  GetQuizById(@PathVariable("id") Long id,@PathVariable("idUser") Long idUser) {
        try {
            List<QuizResultResponse> response = quizResultResponseRepository.getAllUserByQuiz(id,idUser);
            return new Response<List<QuizResultResponse>>("200", "Get quiz By ID", response);

        } catch (Exception e) {
            return new Response<List<QuizResultResponse>>("406", e.getMessage(), null);
        }

    }

    @GetMapping("/getQuiz/{id}")
    public Response<Quiz> GetQuizById(@PathVariable("id") Long id) {
        try {
            return new Response<Quiz>("200", "Get quiz By ID", iquiz.findById(id).orElse(null));

        } catch (Exception e) {
            return new Response<Quiz>("406", e.getMessage(), null);
        }

    }

    @GetMapping("/")
    public Response<List<Quiz>> findAllQuiz (){
        // return /*iCategory.findAll();*/

        try {

            return new Response<List<Quiz>>("200", "Get all quiz", iquiz.findAll());
        }catch (Exception e){
            return new Response<List<Quiz>>("406", e.getMessage(), null);

        }
    }



    @DeleteMapping("/deleteQuiz/{id}")
    public Response<Quiz> deleteQuiz (@PathVariable("id") Long  id){

        iquiz.deleteById(id);
        try{
            return new Response<Quiz>("200", "Quiz deleted", null);
        } catch (Exception e){
            return new Response<Quiz> ("406",e.getMessage(),null);
        }
    }


    @PutMapping("/updateQuiz/{id}")
    public Response<Quiz> updateQuiz(@PathVariable("id") Long id,@RequestBody Quiz d) {
        try {

            Quiz oldCours = iquiz.findById(id).orElse(null);
            d.setQuestions(d.getQuestions() == null ? oldCours.getQuestions() : d.getQuestions());
            d.setDescription(d.getDescription() == null ? oldCours.getDescription() : d.getDescription());
            d.setTitle(d.getTitle() == null ? oldCours.getTitle() : d.getTitle());
            d.setEtape(d.getEtape() == null ? oldCours.getEtape() : d.getEtape());
            d.setMaxMarks(d.getMaxMarks() == null ? oldCours.getMaxMarks() : d.getMaxMarks());
            d.setNumberOfQuestions(d.getNumberOfQuestions() == null ? oldCours.getNumberOfQuestions() : d.getNumberOfQuestions());

            d.setActive(d.isActive() );

            d.setId(id);


            return new Response<Quiz>("200","Formation updated", iquiz.save(d));
        }catch (Exception e){
            return new Response<Quiz>("406", e.getMessage(), null);

        }
    }

    @GetMapping("/ResultQuiz/{id}")
    public Response<List<QuizResultResponse>> ResultQuiz(@PathVariable("id") Long id) {
        try {



            return new Response<List<QuizResultResponse>>("200","Video updated", quizResultResponseRepository.getResultQuiz(id));
        }catch (Exception e){
            return new Response<List<QuizResultResponse>>("406", e.getMessage(), null);

        }
    }
    @GetMapping("/getAllEtapeByQuiz/{id}")
    public Response<Quiz> getAllEtapeByQuiz( @PathVariable Long id) {


        try {
            return new Response<Quiz>("200", "quiz", iquiz.getAllEtapeByQuiz(id));

        } catch (Exception e) {
            return new Response<Quiz>("406", e.getMessage(), null);
        }

    }
    @GetMapping("/getAllEtapeByFormation/{id}")
    public Response<List<Quiz>> getAllEtapeByFotmation( @PathVariable Long id) {


        try {
            return new Response<List<Quiz>>("200", "quiz", iquiz.getAllEtapeByFormation(id));

        } catch (Exception e) {
            return new Response<List<Quiz>> ("406", e.getMessage(), null);
        }

    }


}
