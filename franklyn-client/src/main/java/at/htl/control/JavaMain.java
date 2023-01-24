package at.htl.control;

import io.quarkus.logging.Log;
import io.quarkus.runtime.Quarkus;
import io.quarkus.runtime.QuarkusApplication;
import io.quarkus.runtime.annotations.QuarkusMain;
import org.quartz.*;

import javax.inject.Inject;

@QuarkusMain
public class JavaMain implements QuarkusApplication {

    @Inject
    ApiCalls apiCalls;

    @Override
    public int run(String... args) throws Exception {
        Long id = apiCalls.enterPIN();                      // get ExamId
        Long code = apiCalls.enterName(id.toString());      // get ExamineeId
        if(code == -100L){                                  // exit if student declines to enroll again
            return 1;
        }
        apiCalls.getIntervall(id.toString());               // get intervall for Screenshots
        apiCalls.setScheduler();                            // set Scheduler adjusted to the intervall
        apiCalls.sendScreenshots();                         // send screenshots
        Quarkus.waitForExit();
        return 0;
    }
}