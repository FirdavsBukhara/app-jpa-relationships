package uz.pdp.appjparelationships.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.appjparelationships.entity.Faculty;
import uz.pdp.appjparelationships.entity.Group;

import java.util.List;

@Repository
public interface GroupRepository extends JpaRepository<Group,Integer> {

    List<Group> findAllByFaculty_University_Id(Integer faculty_university_id);
}
