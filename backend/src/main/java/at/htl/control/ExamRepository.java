package at.htl.control;

import at.htl.entity.Exam;
import at.htl.entity.Examinee;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;
import java.time.LocalDate;
import java.util.List;

@ApplicationScoped
public class ExamRepository implements PanacheRepository<Exam> {
    public String createPIN(LocalDate date){
        List<Exam> examqs = this.find("date", LocalDate.now()).list();
        List<Exam> examsWithDate = this.find("date", LocalDate.now()).list();
        boolean pinIsValid = false;
        String pin;
        do{
            pin = String.valueOf((int) (Math.random()*(999-100))+100);
            List<Exam> exams = this.getEntityManager().createNamedQuery("Exam.findExamWithSameDateAndPIN", Exam.class)
                    .setParameter("DATE", LocalDate.now())
                    .setParameter("PIN", pin)
                    .getResultList();
            if(exams == null)
                pinIsValid = true;
        }while (pinIsValid);
        return pin;
    }
    public List<Examinee> getExaminees(Long id) {
        return null;

    }

    public Boolean verifyPIN(Long id, String pin) {
        Exam exam = this.find("Exam.verifyPIN", id).firstResult();
        return pin.equals(exam.pin);

    }

    public Exam getExamByPin(String pin) {
        var query = this.getEntityManager().createQuery(
                "select e from Exam e where e.pin = :PIN",
                Exam.class
        ).setParameter("PIN", pin);
        Exam exam = query.getSingleResult();
        return exam;

    }
}
