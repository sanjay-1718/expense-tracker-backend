package in.project.expensetracker.controller;

import in.project.expensetracker.model.Expense;
import in.project.expensetracker.service.ExpenseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@CrossOrigin(
		  origins = {
		    "http://localhost:5173",
		    "https://expense-tracker.vercel.app"
		  }
		)
@RestController
@RequestMapping("/api/expenses")
@RequiredArgsConstructor
public class ExpenseController {

    private final ExpenseService service;

    // 🔐 Create expense for logged-in user
    @PostMapping
    public ResponseEntity<Expense> create(@Valid @RequestBody Expense expense) {
        Expense created = service.create(expense);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    // 🔐 Get expenses ONLY for logged-in user (with optional filters)
    @GetMapping
    public ResponseEntity<List<Expense>> getExpenses(
            @RequestParam(required = false) String category,

            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate startDate,

            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate endDate
    ) {
        return ResponseEntity.ok(
                service.filter(category, startDate, endDate)
        );
    }

    // 🔐 Get single expense (only if it belongs to logged-in user)
    @GetMapping("/{id}")
    public ResponseEntity<Expense> byId(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    // 🔐 Update expense (only if owned by logged-in user)
    @PutMapping("/{id}")
    public ResponseEntity<Expense> update(
            @PathVariable Long id,
            @Valid @RequestBody Expense expense
    ) {
        return ResponseEntity.ok(service.update(id, expense));
    }

    // 🔐 Delete expense (only if owned by logged-in user)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
