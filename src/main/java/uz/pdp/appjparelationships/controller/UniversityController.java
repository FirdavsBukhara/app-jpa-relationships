package uz.pdp.appjparelationships.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import uz.pdp.appjparelationships.entity.Address;
import uz.pdp.appjparelationships.entity.University;
import uz.pdp.appjparelationships.payload.UniversityDto;
import uz.pdp.appjparelationships.repository.AddressRepository;
import uz.pdp.appjparelationships.repository.UnversityRepository;

import java.util.List;
import java.util.Optional;

@RestController
public class UniversityController {

    @Autowired
    UnversityRepository unversityRepository;

    @Autowired
    AddressRepository addressRepository;


    @RequestMapping(value = "/university", method = RequestMethod.GET)
    public List<University> getUniversities() {
        List<University> universities = unversityRepository.findAll();
        return universities;
    }

    @RequestMapping(value = "/university", method = RequestMethod.POST)
    public String addUniversity(@RequestBody UniversityDto universityDto) {
        Address address = new Address();
        address.setCity(universityDto.getCity());
        address.setDistrict(universityDto.getDistrict());
        address.setStreet(universityDto.getStreet());
        //YASAB OLGAN ADDRESSIMIZNI DB GA SAQLADIK
        Address savedAddress = addressRepository.save(address);
        //YANGI UNIVERSITET YASAB OLDIK
        University university = new University();
        university.setName(universityDto.getName());
        university.setAddress(savedAddress);
        unversityRepository.save(university);
        return "University added";
    }

    @RequestMapping(value = "/university/{id}", method = RequestMethod.DELETE)
    public String deleteUniversity(@PathVariable Integer id) {
        unversityRepository.deleteById(id);
        return "University deleted";
    }

    @RequestMapping(value = "/university/{id}", method = RequestMethod.PUT)
    public String updateUniversity(@PathVariable Integer id, @RequestBody UniversityDto universityDto) {
        Optional<University> optionalUniversity = unversityRepository.findById(id);
        if (optionalUniversity.isPresent()) {
            University university = optionalUniversity.get();


            Address address=new Address();
            address.setCity(universityDto.getCity());
            address.setDistrict(universityDto.getDistrict());
            address.setStreet(universityDto.getStreet());
            Address saved = addressRepository.save(address);

            university.setName(universityDto.getName());
            university.setAddress(saved);
            unversityRepository.save(university);
            return "University updated";
        }
        return "University not found";
    }
}
