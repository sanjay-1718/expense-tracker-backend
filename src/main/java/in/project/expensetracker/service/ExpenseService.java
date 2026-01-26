package in.project.expensetracker.service;

import in.project.expensetracker.model.Expense;

import java.time.LocalDate;
import java.util.List;

public interface ExpenseService {

    Expense create(Expense expense);

    List<Expense> findAll();

    Expense findById(Long id);

    Expense update(Long id, Expense expense);

    void delete(Long id);

    List<Expense> filter(String category, LocalDate startDate, LocalDate endDate);
}

