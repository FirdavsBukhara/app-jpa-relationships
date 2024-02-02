package uz.pdp.appjparelationships.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import uz.pdp.appjparelationships.entity.Faculty;
import uz.pdp.appjparelationships.entity.Group;
import uz.pdp.appjparelationships.payload.GroupDto;
import uz.pdp.appjparelationships.repository.FacultyRepository;
import uz.pdp.appjparelationships.repository.GroupRepository;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/group")
public class GroupController {

    @Autowired
    GroupRepository groupRepository;
    @Autowired
    FacultyRepository facultyRepository;

    //VAZIRLIK UCHUN
    @GetMapping
    public List<Group> getGroups(){
       return groupRepository.findAll();
    }

    //UNIVERSITET XODIMI UCHUN
    @GetMapping("/byUniversityId/{universityId}")
    public List<Group> getGroups(@PathVariable Integer universityId){
        List<Group> allByFacultyUniversityId = groupRepository.findAllByFaculty_University_Id(universityId);
        return allByFacultyUniversityId;
    }

    @PostMapping
    public String addGroup(@RequestBody GroupDto groupDto){

        Group group=new Group();
        group.setName(groupDto.getName());

        Optional<Faculty> optionalFaculty = facultyRepository.findById(groupDto.getFacultyId());
        if (!optionalFaculty.isPresent())
            return "Such Faculty not found";
        Faculty faculty = optionalFaculty.get();
        group.setFaculty(faculty);
        groupRepository.save(group);
        return "Group Added";
    }
}
