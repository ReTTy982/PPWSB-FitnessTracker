package pl.wsb.fitnesstracker.training.internal;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import pl.wsb.fitnesstracker.training.api.Training;
import pl.wsb.fitnesstracker.training.api.TrainingProvider;
import pl.wsb.fitnesstracker.user.api.User;
import pl.wsb.fitnesstracker.user.api.UserProvider;

import java.util.List;

@Component
public class TrainingReportJob {
    private final UserProvider userProvider;
    private final TrainingProvider trainingProvider;

    public TrainingReportJob(UserProvider userProvider, TrainingProvider trainingProvider) {
        this.userProvider = userProvider;
        this.trainingProvider = trainingProvider;
    }

    @Scheduled(cron = "0 * * * * *", zone = "Europe/Warsaw")
    public void generateReport() {
        System.out.println("Generating report");
        List<User> users = userProvider.findAllUsers();
        System.out.println("=== WEEKLY TRAINING REPORT: ===");

        for (User user : users){
            Long userId = user.getId();
            List<Training> trainings = trainingProvider.createWeeklyReport(user);
            if (trainings.isEmpty()) {
                System.out.println("User " + userId + " - brak danych");
                continue;
            }

            System.out.println("User " + userId + " (" + user.getEmail() + "): "
                    + "trainings="+
                     ", distance=" +
                     ", calories=");


        }
        System.out.println("=== END REPORT ===");
    }

}
