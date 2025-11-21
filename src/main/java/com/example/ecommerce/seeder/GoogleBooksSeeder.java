package com.example.ecommerce.seeder;

import com.example.ecommerce.model.Book;
import com.example.ecommerce.repository.BookRepository;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@Component
public class GoogleBooksSeeder {

    private final RestTemplate restTemplate = new RestTemplate();
    private final Random random = new Random();
    
    private final List<String> queries = Arrays.asList(
            "1984", "Hayvan Çiftliği", "Fahrenheit 451", "Cesur Yeni Dünya", "Bilinmeyen Bir Kadının Mektubu",
            "Suç ve Ceza", "Karamazov Kardeşler", "Anna Karenina", "Savaş ve Barış", "Don Kişot",
            "Küçük Prens", "Simyacı", "Nutuk", "Kürk Mantolu Madonna", "Saatleri Ayarlama Enstitüsü",
            "Yeraltından Notlar", "Beyaz Diş", "Martin Eden", "Sefiller", "Notre Dame'ın Kamburu",
            "Çalıkuşu", "Kuyucaklı Yusuf", "Huzur", "Aşk-ı Memnu", "Mai ve Siyah", "Araba Sevdası",
            "İnce Memed", "Yılanların Öcü", "Eylül", "Sergüzeşt", "Zengin Baba Yoksul Baba", 
            "Atomik Alışkanlıklar", "Sapiens", "Homo Deus", "Körlük", "Denemeler Montaigne"
    );

    @Bean
    public CommandLineRunner seedDatabase(BookRepository bookRepository) {
        return args -> {
            if (bookRepository.count() >= 50) {
                System.out.println("DB zaten dolu (" + bookRepository.count() + " kitap). Seeder çalışmadı.");
                return;
            }

            System.out.println("Open Library kitap çekimi başlatılıyor");

            int basarili = 0;
            
            
            System.out.println("Liste Sorguları:");
            for (int i = 0; i < queries.size(); i++) {
                String query = queries.get(i);
                
                if (basarili >= 50) break;

                Book book = processQuery(query, bookRepository); 
                if (book != null) {
                    basarili++;
                    System.out.println(" [" + basarili + "/" + queries.size() + "] Eklendi: " + book.getTitle());
                } else {
                    System.out.println(" [" + (i + 1) + "/" + queries.size() + "] Başarısız: " + query);
                }
                
                TimeUnit.MILLISECONDS.sleep(1500); 
            }

            
            if (basarili < 50) {
                System.out.println("Rastgele: ");
                int maxDeneme = 1500; 
                int denemeSayisi = 0;
                List<String> suffix = Arrays.asList(" roman", " hikaye", " kitap");
                
                while (basarili < 50 && denemeSayisi < maxDeneme) {
                    String randomQueryBase = queries.get(random.nextInt(queries.size()));
                    String randomQuery = randomQueryBase + suffix.get(random.nextInt(suffix.size()));
                    
                    Book book = processQuery(randomQuery, bookRepository);
                    if (book != null) {
                        basarili++;
                        System.out.println(" Eklendi: " + book.getTitle() + " (" + basarili + ")");
                    } 
                    
                    denemeSayisi++; 
                    TimeUnit.MILLISECONDS.sleep(1500);
                }
            }
            
            System.out.println("\n BİTTİ ");
            System.out.println("SONUÇ: " + basarili + " kitap eklendi");
        };
    }

    private Book processQuery(String query, BookRepository bookRepository) {
        try {
            Book book = fetchBook(query);
            
            if (book != null && !existsByTitle(book.getTitle(), bookRepository)) {
                bookRepository.save(book);
                return book;
            }
        } catch (Exception e) {
            System.err.println("Hata (" + query + "): " + e.getMessage());
        }
        return null;
    }

    private boolean existsByTitle(String title, BookRepository repo) {
        return repo.findAll().stream().anyMatch(b -> b.getTitle().equalsIgnoreCase(title));
    }

    private Book fetchBook(String query) throws Exception {
        String url = "https://openlibrary.org/search.json?q=" + query.replace(" ", "+") + "&limit=1";

        JsonNode root = restTemplate.getForObject(url, JsonNode.class);

        if (root == null || root.path("numFound").asInt(0) == 0) {
            return null;
        }
        
        JsonNode bookData = root.path("docs").get(0);

        String title = bookData.path("title").asText("");
        if (title.isEmpty()) return null; 

        Book book = new Book();
        book.setTitle(title);

        var authors = bookData.path("author_name");
        if (authors.isArray() && authors.size() > 0) {
            book.setAuthor(authors.get(0).asText("Bilinmeyen Yazar"));
        } else {
            return null; 
        }
        
        String coverId = bookData.path("cover_i").asText("");
        String thumbnail = "";
        if (!coverId.isEmpty()) {
            thumbnail = "https://covers.openlibrary.org/b/id/" + coverId + "-L.jpg"; 
        }

        if (thumbnail.isEmpty()) {
            return null;
        }
        
        book.setPicture(thumbnail.replace("http://", "https://"));

        double[] Fiyatlar = { 79.90, 89.90, 94.90, 99.90, 104.90, 109.90, 119.90,
                129.90, 139.90, 149.90, 159.90, 169.90, 179.90, 199.90 };
        book.setPrice(Fiyatlar[random.nextInt(Fiyatlar.length)]);
        
        book.setCategoryId((long) (1 + random.nextInt(5))); 
        book.setStock(5 + random.nextInt(50));

        return book;
    }
}