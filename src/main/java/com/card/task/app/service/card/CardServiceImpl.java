package com.card.task.app.service.card;


import com.card.task.app.model.Card;
import com.card.task.app.model.User;
import com.card.task.app.repository.CardRepository;
import com.card.task.app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CardServiceImpl implements CardService {

    @Autowired
    CardRepository cardRepository;

    @Autowired
    UserRepository userRepository;

    @Override
    public Optional<Card> findById(Long id) {
        return cardRepository.findById(id);
    }

    @Override
    public Card createCard(Card card, String username) {
        Optional<User> user = userRepository.findByUsername(username);
            card.setUser(user.get());
        return cardRepository.save(card);
    }
    @Override
    public Card updateCard(Card card, String username) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new BadCredentialsException("User not found"));
        card.setUser(user);
        return cardRepository.save(card);
    }

    @Override
    public List<Card> findAllByUser(String username) {
        Optional<User> user = userRepository.findByUsername(username);
        return cardRepository.findDistinctByUser(user.get());
    }

    @Override
    public Page<Card> pageCards(Pageable pageable) {
        return cardRepository.pageCards(pageable);
    }

    @Override
    public Page<Card> searchCard(String keyword, Pageable pageable) {
        return  cardRepository.searchCard(keyword, pageable);
    }

    @Override
    public List<Card> findAll() {
        return cardRepository.findAll();
    }

    @Override
    public List<Card> findCardByUser(String username) {
        Optional<User> userOpt = userRepository.findByUsername(username);
        return cardRepository.findCardByUser(userOpt.get());
    }

    @Override
    public void deleteCardById(Long id) {
        cardRepository.deleteById(id);
    }

    @Override
    public boolean existsById(Long id) {
        return cardRepository.existsById(id);
    }
}
