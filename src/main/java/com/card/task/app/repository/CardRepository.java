package com.card.task.app.repository;

import com.card.task.app.model.Card;
import com.card.task.app.model.User;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface CardRepository extends JpaRepository<Card,Long> {

    Optional<Card> findCardByName(String name);

    List<Card>findDistinctByUser(User user);

    List<Card> findCardByUser(User user);

    @Query("select c from Card c where c.name like %?1% or c.description  like %?1% or c.color like %?1% or c.status like %?1%  or  c.creationDate like %?1% ")
    Page<Card> searchCard(@Param("keyword")String keyword, Pageable pageable);


    @Query("select c from Card c")
    Page<Card> pageCards(Pageable pageable);

}
