package pl.wsb.fitnesstracker.training.api;

import pl.wsb.fitnesstracker.user.api.User;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface TrainingProvider {

    /**
     * Retrieves a training based on their ID.
     * If the user with given ID is not found, then {@link Optional#empty()} will be returned.
     *
     * @param trainingId id of the training to be searched
     * @return An {@link Optional} containing the located Training, or {@link Optional#empty()} if not found
     */
    Optional<Training> getTraining(Long trainingId);
    List<Training> getAllTrainings();
    List<Training> getTrainingsForUser(Long userId);
    List<Training> getTrainingsForUser(Long userId, LocalDate from, LocalDate to);
    List<Training> createWeeklyReport(User user);



    }
