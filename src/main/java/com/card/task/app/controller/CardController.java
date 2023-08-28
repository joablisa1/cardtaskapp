package com.card.task.app.controller;


import com.card.task.app.model.CardStatus;
import com.card.task.app.model.Card;
import com.card.task.app.model.RoleEnum;
import com.card.task.app.payload.CardRequest;
import com.card.task.app.payload.UpdateCardRequest;
import com.card.task.app.service.card.CardService;
import com.card.task.app.service.role.RoleService;
import com.card.task.app.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.*;

@RestController
@RequestMapping("/api/v1/cards")
public class CardController {

    @Autowired
    CardService cardService;
    @Autowired
    UserService userService;

    @Autowired
    RoleService roleService;

    @GetMapping("/access_all_content")
    public ResponseEntity<List<Card>> findAllCardListResponse() {
        return new ResponseEntity<>(cardService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/access_card_by_username")
    public ResponseEntity<List<Card>> findAllCardResponse(Authentication authentication) {
        String username = authentication.getName();
        List<Card> cardList = null;
        if(userService.existsByUsername(username) == roleService.existsByName(RoleEnum.ROLE_USER)) {
            cardList = cardService.findCardByUser(username);
        }
        return new ResponseEntity<>(cardList, HttpStatus.OK);
    }

    @PostMapping("/save")
    public ResponseEntity<?> createCardsResponse(@RequestBody CardRequest cardRequest, Authentication authentication) {

        Optional<Card> cardOptional = cardService.findById(cardRequest.getId());
        if (cardOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("card already exists.");
        }
        String username = authentication.getName();
        Card card = new Card();
        card.setName(cardRequest.getCardName());
        card.setColor(cardRequest.getColor());
        card.setStatus(String.valueOf(CardStatus.TODO));
        card.setDescription(cardRequest.getDescription());
        card.setCreationDate(LocalDate.now());
        cardService.createCard(card, username);
        return ResponseEntity.ok("successfully created");
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateCardResponseEntity(@PathVariable("id") Long id, Authentication authentication, @RequestBody UpdateCardRequest cardRequest) {
        Optional<Card> cardOptional = cardService.findById(id);

        if (!cardOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("card already exists.");
        }

        String username = authentication.getName();
        Card card = cardOptional.get();
        card.setName(cardRequest.getCardName());
        card.setColor(cardRequest.getColor());
        if (cardRequest.getStatus() != null) {
            CardStatus newStatus = validateAndSetStatus(cardRequest.getStatus());
            card.setStatus(String.valueOf(newStatus));
        }
        card.setDescription(cardRequest.getDescription());
        card.setCreationDate(LocalDate.now());
        cardService.createCard(card, username);
        return ResponseEntity.ok("updated successfully ");
    }

    private CardStatus validateAndSetStatus(String status) {
        switch (status) {
            case "To Do":
                return CardStatus.TODO;
            case "In Progress":
                return CardStatus.IN_PROGRESS;
            case "Done":
                return CardStatus.DONE;
            default:
                throw new IllegalArgumentException("Invalid status provided");
        }
    }

    // Use postman for testing this search api  on swagger UI it's not working
    @RequestMapping(value = "/search", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<Map<String, Object>> searchResponseEntity(
            @RequestParam(required = false) String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        try {
            List<Card> content;
            Pageable paging = PageRequest.of(page, size, Sort.by("creationDate").descending());
            Page<Card> pageCards;

            if (keyword == null || keyword.isBlank()) {
                pageCards = cardService.pageCards(paging);
            } else {
                pageCards = cardService.searchCard(keyword, paging);
            }

            content = pageCards.getContent();
            Map<String, Object> map = new HashMap<>();
            map.put("content", content);
            map.put("currentPage", pageCards.getNumber());
            map.put("pageSize",pageCards.getSize());
            map.put("totalItems", pageCards.getTotalElements());
            map.put("totalPages", pageCards.getTotalPages());

            return new ResponseEntity<>(map, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/find_card_by_id/{id}")
    public ResponseEntity<?> findByIdResponse(@PathVariable("id") Long id) {
        Optional<Card> existingOptional = cardService.findById(id);
        if (existingOptional.isPresent()) {
            return new ResponseEntity<>(existingOptional.get(), HttpStatus.OK);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Card  not found..");
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteCardById(@PathVariable("id") Long id) {
        if (!cardService.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        cardService.deleteCardById(id);
        return ResponseEntity.ok("deleted successfully");
    }

}
