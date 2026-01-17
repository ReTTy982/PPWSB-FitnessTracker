package pl.wsb.fitnesstracker.training.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/v1/trainings")
public class TrainingController {

    private final TrainingProvider trainingProvider;

    public TrainingController(TrainingProvider trainingProvider) {
        this.trainingProvider = trainingProvider;
    }

    @GetMapping
    public List<Training> getAllTrainings() {
        return trainingProvider.getAllTrainings();
    }

    @GetMapping("/{userId}")
    public List<Training> getTrainingsForUser(@PathVariable Long userId) {
        return trainingProvider.getTrainingsForUser(userId);
    }
}