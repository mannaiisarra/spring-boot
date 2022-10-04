package com.spring.pfe.controllers;


import com.spring.pfe.models.*;
import com.spring.pfe.repository.*;
import com.spring.pfe.security.services.QuesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/question")
@CrossOrigin("*")
public class QuestionController {

    @Autowired
    private QuestionRepository iquestion;
    @Autowired
    private QuizRepository iquiz;
    @Autowired
    private QuesService quizservice;

    @Autowired
    private QuizResultResponseRepository iquizResult;

    @Autowired
    FormationRepository formationRepository;

    @Autowired
    UserRepository userRepository;
    @RequestMapping(value="/add/{quiz_id}", method= RequestMethod.POST)
    public Response<Question> addQuestion(Question q,@PathVariable("quiz_id") Long quiz_id) {
        try {

                Quiz u = iquiz.findById(quiz_id).orElse(null);
                q.setQuiz(u);

                return new Response<Question>("200", "Creat question", iquestion.save(q));
            }
         catch (Exception e) {
            return new Response<Question>("406", e.getMessage(), null);
        }

    }

    @RequestMapping(value="/addd/{quiz_id}",method= RequestMethod.POST)
    public Response<Question> addQuizz(@RequestBody Question t ,@PathVariable("quiz_id") Long quiz_id) {
        try {
            Quiz u = iquiz.findById(quiz_id).orElse(null);
            t.setQuiz(u);
            return new Response<Question>("200", "Creat quiz", iquestion.save(t));

        } catch (Exception e) {
            return new Response<Question>("406", e.getMessage(), null);
        }

    }
    @GetMapping("/")
    public Response<List<Question>> findAllQuestion () {
        return new Response<List<Question>>("200", "Get all question", iquestion.findAll());
    }

    @DeleteMapping("/deleteQuestion/{id}")
    public Response<Question> deleteFormation (@PathVariable("id") Long  id){

        iquestion.deleteById(id);
        try{
            return new Response<Question>("200", "question deleted", null);
        } catch (Exception e){
            return new Response<Question> ("406",e.getMessage(),null);
        }
    }

    @GetMapping("/findById/{id}")
    public Response<Question> findById(@PathVariable Long id){
        try {

            return new Response<Question>("200", "Get question by id", iquestion.findById(id).orElse(null));
        }catch (Exception e){
            return new Response<Question>("406", e.getMessage(), null);

        }
    }

    @GetMapping("/findallByid/{id}")
    public Response<List<Question>> findAllByid(@PathVariable Long id){
        try {

            return new Response<List<Question>>("200", "Get Formation by id", iquestion.getAllQuestionByQuiz(id));
        }catch (Exception e){
            return new Response<List<Question>>("406", e.getMessage(), null);

        }
    }


    @PutMapping("/updateQuiz/{id}")
    public Response<Quiz> updateFormation(@PathVariable("id") Long id,@RequestBody Quiz d) {
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
    @PostMapping("/evaluate/{id}/{idQuiz}")
    public Response<QuizResultResponse> evaluate(@PathVariable("id") Long id,@PathVariable("idQuiz") Long idQuiz){

        Quiz quiz = iquiz.findById(idQuiz).orElse(null);
        QuizResultResponse result=new QuizResultResponse();
        User user=new User();
        user = userRepository.getById(id);


        Integer correctAnswers=0;
        double marksObtained = 0;
        Integer attempted=0;



        result.setCorrectAnswers(correctAnswers);
        result.setAttempted(attempted);
        result.setMarksObtained(marksObtained);
        result.setUser(user);
        result.setQuiz(quiz);

        return new Response<QuizResultResponse>("200","Add new marck    ", iquizResult.save(result));

    }


    @PostMapping("/evaluatequiz/{id}")
    public Response<QuizResultResponse> evaluateQuiz(@PathVariable("id") Long id, @RequestBody List<Question> questions){


        QuizResultResponse result=new QuizResultResponse();
        User user=new User();
        user = userRepository.getById(id);
        System.out.println(questions);

        Integer correctAnswers=0;
        double marksObtained = 0;
        Integer attempted=0;
        for(Question theQues:questions) {
            try {


                Question question=this.quizservice.getQuestionById(theQues.getQuesId());
                System.out.println(question);
                //set specific question attended by user in his/her attempted quiz so it can access limited number of time to quiz
                if(theQues.getGivenAnswer().trim().equals(question.getCorrect().trim())) {
                    correctAnswers=correctAnswers+1;
                    attempted=attempted+1;
                    result.setQuiz(theQues.getQuiz());

                }
                else {
                    attempted=attempted+1;
                    result.setQuiz(theQues.getQuiz());
                }
                double marksObtainedPerQuestion=Double.parseDouble(questions.get(0).getQuiz().getMaxMarks())/questions.size();
                marksObtained=correctAnswers*marksObtainedPerQuestion;
                //set a list to questions in users attempted quiz
            } catch (Exception e) {
                e.printStackTrace();
            }

        }



        result.setCorrectAnswers(correctAnswers);
        result.setAttempted(attempted);
        result.setMarksObtained(marksObtained);
        result.setUser(user);


        return new Response<QuizResultResponse>("200","Add new marck    ", iquizResult.save(result));

    }

    @GetMapping("/getAllUser")
    public Response<List<Formation>> findAllFormationArchiver () {
        return new Response<List<Formation>>("200", "Get all Formation Archiver", formationRepository.getAllFormationByArchiver(true));
    }


    @PutMapping("/updateQuestion/{quesId}")
    public Response<Question> updateQuestion(@PathVariable("quesId") Long quesId,@RequestBody Question d) {
        try {

            Question oldCours = iquestion.findById(quesId).orElse(null);
            d.setQuestion(d.getQuestion() == null ? oldCours.getQuestion() : d.getQuestion());
            d.setResponse1(d.getResponse1() == null ? oldCours.getResponse1() : d.getResponse1());
            d.setResponse2(d.getResponse2() == null ? oldCours.getResponse2() : d.getResponse2());
            d.setResponse3(d.getResponse3() == null ? oldCours.getResponse3() : d.getResponse3());
            d.setCorrect(d.getCorrect() == null ? oldCours.getCorrect() : d.getCorrect());
d.setQuiz(d.getQuiz());

            d.setQuesId(quesId);


            return new Response<Question>("200","question updated", iquestion.save(d));
        }catch (Exception e){
            return new Response<Question>("406", e.getMessage(), null);

        }
    }

}
