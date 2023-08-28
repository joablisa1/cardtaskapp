package com.card.task.app.service.card;


import com.card.task.app.model.Card;
import com.card.task.app.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CardService {

    Optional<Card> findById(Long id);

    Card createCard(Card card, String username);

    Card updateCard(Card card, String username);

    Page<Card> pageCards(Pageable pageable);

    Page<Card> searchCard(String keyword, Pageable pageable);

    List<Card> findAll();

    List<Card>findCardByUser(String username);

    void deleteCardById(Long id);

    boolean existsById(Long id);
}
