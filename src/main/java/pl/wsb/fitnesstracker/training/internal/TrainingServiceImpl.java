package pl.wsb.fitnesstracker.training.internal;

import org.springframework.stereotype.Service;
import pl.wsb.fitnesstracker.training.api.Training;
import pl.wsb.fitnesstracker.training.api.TrainingProvider;
import pl.wsb.fitnesstracker.training.api.TrainingRepository;
import pl.wsb.fitnesstracker.user.api.User;


import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.TemporalAdjusters;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class TrainingServiceImpl implements TrainingProvider {

    private final TrainingRepository trainingRepository;

    public TrainingServiceImpl(TrainingRepository trainingRepository) {
        this.trainingRepository = trainingRepository;
    }

    @Override
    public Optional<Training> getTraining(final Long trainingId) {
        return trainingRepository.findById(trainingId);
    }

    @Override
    public List<Training> getAllTrainings() {
        return trainingRepository.findAll();
    }

    @Override
    public List<Training> getTrainingsForUser(Long userId) {
        return trainingRepository.findAll().stream()
                .filter(t -> t.getUser().getId().equals(userId))
                .toList();
    }

    @Override
    public List<Training> getTrainingsForUser(Long userId, LocalDate from, LocalDate to) {
        ZoneId zone = ZoneId.of("Europe/Warsaw");

        Date fromDate = Date.from(from.atStartOfDay(zone).toInstant());
        Date toDate = Date.from(to.plusDays(1).atStartOfDay(zone).toInstant());
        return trainingRepository.findByUserIdAndStartTimeBetween(userId, fromDate, toDate);

    }

    @Override
    public List<Training> createWeeklyReport(User user) {
        LocalDate now = LocalDate.now();
        LocalDate startDate = now.minusWeeks(1)
                .with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
        LocalDate endDate = startDate.plusWeeks(1);


        List<Training> trainings = getTrainingsForUser(user.getId(),startDate,endDate);
        return trainings;
    }


}
