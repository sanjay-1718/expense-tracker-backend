package in.project.expensetracker.repository;

import in.project.expensetracker.model.Expense;
import in.project.expensetracker.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {

    // 🔒 Core user-based queries
    List<Expense> findByUser(User user);

    Optional<Expense> findByIdAndUser(Long id, User user);

    // 🔍 Filtering with user
    List<Expense> findByUserAndCategoryIgnoreCase(User user, String category);

    List<Expense> findByUserAndDateBetween(
            User user,
            LocalDate startDate,
            LocalDate endDate
    );

    List<Expense> findByUserAndCategoryIgnoreCaseAndDateBetween(
            User user,
            String category,
            LocalDate startDate,
            LocalDate endDate
    );
}

