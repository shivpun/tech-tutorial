package com.tech.zerodha.kite.jpa.repository;

import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;

import com.tech.zerodha.kite.jpa.model.KiteLoginHistory;

@Repository
public interface KiteLoginHistoryRepository extends R2dbcRepository<KiteLoginHistory, String> {
}
