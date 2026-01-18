package pl.wsb.fitnesstracker.training.internal;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import pl.wsb.fitnesstracker.mail.api.EmailDto;
import pl.wsb.fitnesstracker.mail.internal.JavaEmailSender;
import pl.wsb.fitnesstracker.training.api.Training;
import pl.wsb.fitnesstracker.training.api.TrainingProvider;
import pl.wsb.fitnesstracker.user.api.User;
import pl.wsb.fitnesstracker.user.api.UserProvider;

import java.util.List;

@Component
public class TrainingReportJob {
    private final UserProvider userProvider;
    private final TrainingProvider trainingProvider;
    private final JavaEmailSender emailSender;

    public TrainingReportJob(UserProvider userProvider, TrainingProvider trainingProvider, JavaEmailSender emailSender) {
        this.userProvider = userProvider;
        this.trainingProvider = trainingProvider;
        this.emailSender = emailSender;
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
            int sumTrainings = 0;
            int sumDistance = 0;
            for (Training i : trainings){
                sumTrainings += 1;
                sumDistance += i.getDistance();
            }

            String message = "User " + userId + " (" + user.getEmail() + "): "
                    + "trainings="+ sumTrainings+
                    ", distance=" + sumDistance +
                    ", calories=";
            System.out.println(message);
            EmailDto emailDto = new EmailDto(user.getEmail(),
                    "fitnesstracker@cap.wsb.com",
                    "Weekly report",
                    message);
            emailSender.send(emailDto);
        }
        System.out.println("=== END REPORT ===");
    }

}
