package ma.enset.hospital;

import ma.enset.hospital.entities.Patient;
import ma.enset.hospital.repository.patientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Date;

@SpringBootApplication
public class HospitalApplication implements CommandLineRunner {
    @Autowired
    private patientRepository patientRepo;

    public static void main(String[] args) {
        SpringApplication.run(HospitalApplication.class, args);
    }

    //@Bean
    @Override
    public void run(String... args) throws Exception {
        patientRepo.save(new Patient(null, "Omar", new Date(), false, 60));
        patientRepo.save(new Patient(null, "Akram", new Date(), true, 90));
    };

@Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}