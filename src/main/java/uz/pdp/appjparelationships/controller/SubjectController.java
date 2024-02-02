package uz.pdp.appjparelationships.controller;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import uz.pdp.appjparelationships.entity.Subject;
import uz.pdp.appjparelationships.repository.SubjectRepository;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/subject")
public class SubjectController {


    @Autowired
    SubjectRepository subjectRepository;

    @RequestMapping(value = "/subject",method = RequestMethod.POST)
    public String addSubject(@RequestBody Subject subject){
        boolean existsByName = subjectRepository.existsByName(subject.getName());
        if (existsByName)
            return "This subject already exsist";

        subjectRepository.save(subject);
        return "Subject added";
    }
//    @RequestMapping(value = "/subject",method = RequestMethod.GET)
    @GetMapping
    public List<Subject> getSubjects(){
        List<Subject> subjects = subjectRepository.findAll();
        return subjects;
    }
    @RequestMapping(value = "/subject/{id}",method = RequestMethod.PUT)
    public String updateSubject(@PathVariable Integer id,@RequestBody Subject subject){
        Optional<Subject> optionalSubject = subjectRepository.findById(id);
        if (optionalSubject.isPresent()){
            Subject currentSubject = optionalSubject.get();
            currentSubject.setName(subject.getName());
            subjectRepository.save(currentSubject);
            return "Subject updated";
        }
        return "Subject does not exsist";
    }
    @RequestMapping(value = "/subject/{id}",method = RequestMethod.DELETE)
    public String deleteSubject(@PathVariable Integer id){
        subjectRepository.deleteById(id);
        return "Subject deleted";
    }
    @RequestMapping(value = "/subject/{id}",method = RequestMethod.GET)
    public Subject getSubject(@PathVariable Integer id){
        Optional<Subject> optionalSubject = subjectRepository.findById(id);
        if (optionalSubject.isPresent()){
            Subject subject = optionalSubject.get();
            return subject;
        }
        return new Subject();
    }
}
