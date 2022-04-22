package com.easyPicture.easyPicture.repository;

import com.easyPicture.easyPicture.model.Token;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TokenRepository extends CrudRepository<Token, Long> {
    Optional<Token> findByToken(String token);
    Optional<Token> findByUserId(Long userId);

    @Override
    <S extends Token> S save(S entity);
}
