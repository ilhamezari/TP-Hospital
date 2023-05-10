package ma.enset.hospital.repository;

import ma.enset.hospital.entities.Patient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface patientRepository extends JpaRepository<Patient,Long> {
//public List<patient> findById(Long id);
    Page<Patient> findByNomContains(String keyword, Pageable pageable);
    @Query("select p from Patient p where p.nom like :x")
    Page<Patient> search(@Param("x") String keyword, Pageable pageable);
}
