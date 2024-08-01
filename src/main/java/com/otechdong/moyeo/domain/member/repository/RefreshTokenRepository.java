package com.otechdong.moyeo.domain.member.repository;

import com.otechdong.moyeo.domain.member.entity.RefreshToken;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends CrudRepository<RefreshToken, String> {
    Optional<RefreshToken> findById(String id);

    Optional<RefreshToken> findByAccessToken(String accessToken);
}
