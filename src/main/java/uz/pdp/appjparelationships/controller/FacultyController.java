package uz.pdp.appjparelationships.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import uz.pdp.appjparelationships.entity.Faculty;
import uz.pdp.appjparelationships.entity.University;
import uz.pdp.appjparelationships.payload.FacultyDto;
import uz.pdp.appjparelationships.repository.FacultyRepository;
import uz.pdp.appjparelationships.repository.UnversityRepository;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/faculty")
public class FacultyController {

    @Autowired
    FacultyRepository facultyRepository;

    @Autowired
    UnversityRepository unversityRepository;

    //VAZIRLIK UCHUN
    @GetMapping
    public List<Faculty> getFaculties() {
        return facultyRepository.findAll();
    }

    @PostMapping
    public String addFaculty(@RequestBody FacultyDto facultyDto) {
        boolean exists = facultyRepository.existsByNameAndUniversityId(facultyDto.getName(), facultyDto.getUniversityId());
        if (exists)
            return "This University such faculty exsist";

        Optional<University> optionalUniversity = unversityRepository.findById(facultyDto.getUniversityId());
        if (optionalUniversity.isPresent()) {
            Faculty faculty = new Faculty();
            faculty.setName(facultyDto.getName());
            University university = optionalUniversity.get();
            faculty.setUniversity(university);
            facultyRepository.save(faculty);
            return "Faculty added";
        }
        return "University not found";
    }

    //UNIVERSITET XODIMI UCHUN
    @GetMapping("/byUniversityId/{universityId}")
    public List<Faculty> getFacultiesByUniversityId(@PathVariable Integer universityId) {
        List<Faculty> allByUniversityId = facultyRepository.findAllByUniversityId(universityId);
        return allByUniversityId;
    }
    @DeleteMapping("/{id}")
    public String deleteById(@PathVariable Integer id){
        try {
            facultyRepository.deleteById(id);
            return "Faculty deleted";
        }catch (Exception e){
            return "Error deleting";
        }
    }
    @PutMapping("/{id}")
    public String editFaculty(@PathVariable Integer id,@RequestBody FacultyDto facultyDto){
        Optional<Faculty> optionalFaculty = facultyRepository.findById(id);
        if (optionalFaculty.isPresent()){
            Faculty faculty = optionalFaculty.get();
            faculty.setName(facultyDto.getName());
            Optional<University> optionalUniversity = unversityRepository.findById(facultyDto.getUniversityId());
            if (!optionalFaculty.isPresent()){
                return "University not found";
            }
            faculty.setUniversity(optionalUniversity.get());
            facultyRepository.save(faculty);
            return "Faculty updated";
        }
        return "Faculty not found";
    }
}
