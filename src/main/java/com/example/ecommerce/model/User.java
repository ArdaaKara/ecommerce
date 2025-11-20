package com.example.ecommerce.model;

import java.time.LocalDateTime;

import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String role = "USER";

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;


    public User() {}
    
    public User(String name, String email, String password, LocalDateTime createdAt) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = "USER";
        this.createdAt = createdAt;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

   public LocalDateTime getCreatedAt() { return createdAt; }
public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

  

}
/* @Controller
public class BookController {

    private final BookService bookService;
    private final ReviewService reviewService; 

    public BookController(BookService bookService, ReviewService reviewService) {
        this.bookService = bookService;
        this.reviewService = reviewService;
    }

    @GetMapping("/books/{id}")
    public String bookDetail(@PathVariable Integer id, Model model, Principal principal) {
        Book book = bookService.findById(id);
        if (book == null) {
            return "error/404";
        }

        List<Review> reviews = reviewService.findByBookId(id);
        double avgRating = reviewService.getAverageRatingByBookId(id);

        model.addAttribute("book", book);
        model.addAttribute("reviews", reviews);
        model.addAttribute("avgRating", String.format("%.1f", avgRating));

        if (principal != null) {
            User currentUser = reviewService.getCurrentUser(principal);
            boolean alreadyReviewed = reviewService.hasUserReviewedBook(id, currentUser.getId());
            model.addAttribute("currentUser", currentUser);
            model.addAttribute("alreadyReviewed", alreadyReviewed);
        }

        return "books";
    }

    @PostMapping("/books/{bookId}/review")
    public String addReview(
            @PathVariable Integer bookId,
            @RequestParam Byte rating,
            @RequestParam(required = false, defaultValue = "") String comment,
            Principal principal,
            RedirectAttributes redirectAttributes) {

        if (principal == null) {
            return "redirect:/login";
        }

        try {
            reviewService.saveReview(bookId, principal.getName(), rating, comment);
            redirectAttributes.addFlashAttribute("success", "Yorumun eklendi, teşekkürler!");
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }

        return "redirect:/books/" + bookId;
    }
} */