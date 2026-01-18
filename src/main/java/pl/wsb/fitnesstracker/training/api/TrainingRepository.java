package pl.wsb.fitnesstracker.training.api;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Date;
import java.util.List;
public interface TrainingRepository extends JpaRepository<Training, Long> {
    List<Training> findByUserIdAndStartTimeBetween(Long userId, Date from, Date to);
}
