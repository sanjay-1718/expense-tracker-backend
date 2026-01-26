package in.project.expensetracker.service;

import in.project.expensetracker.exception.AccessDeniedException;
import in.project.expensetracker.exception.ResourceNotFoundException;
import in.project.expensetracker.model.Expense;
import in.project.expensetracker.model.User;
import in.project.expensetracker.repository.ExpenseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ExpenseServiceImpl implements ExpenseService {

    private final ExpenseRepository expenseRepository;
    private final UserService userService;

    @Override
    public Expense create(Expense expense) {
        User user = userService.getLoggedInUser();
        expense.setUser(user);
        return expenseRepository.save(expense);
    }

    @Override
    public List<Expense> findAll() {
        User user = userService.getLoggedInUser();
        return expenseRepository.findByUser(user);
    }

    @Override
    public Expense findById(Long id) {
        User user = userService.getLoggedInUser();
        return expenseRepository.findByIdAndUser(id, user)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Expense not found")
                );
    }

    @Override
    public Expense update(Long id, Expense updatedExpense) {
        User user = userService.getLoggedInUser();

        Expense existing = expenseRepository.findByIdAndUser(id, user)
                .orElseThrow(() ->
                        new AccessDeniedException("You are not allowed to update this expense")
                );

        existing.setTitle(updatedExpense.getTitle());
        existing.setAmount(updatedExpense.getAmount());
        existing.setCategory(updatedExpense.getCategory());
        existing.setDate(updatedExpense.getDate());

        return expenseRepository.save(existing);
    }

    @Override
    public void delete(Long id) {
        User user = userService.getLoggedInUser();

        Expense expense = expenseRepository.findByIdAndUser(id, user)
                .orElseThrow(() ->
                        new AccessDeniedException("You are not allowed to delete this expense")
                );

        expenseRepository.delete(expense);
    }

    @Override
    public List<Expense> filter(String category, LocalDate startDate, LocalDate endDate) {
        User user = userService.getLoggedInUser();

        if (category != null && startDate != null && endDate != null) {
            return expenseRepository
                    .findByUserAndCategoryIgnoreCaseAndDateBetween(
                            user, category, startDate, endDate
                    );
        }

        if (category != null) {
            return expenseRepository.findByUserAndCategoryIgnoreCase(user, category);
        }

        if (startDate != null && endDate != null) {
            return expenseRepository.findByUserAndDateBetween(
                    user, startDate, endDate
            );
        }

        return expenseRepository.findByUser(user);
    }
}
