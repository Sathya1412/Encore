package com.dbsample.mysqlcrud.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dbsample.mysqlcrud.entity.Message;

public interface MessageRepository extends JpaRepository<Message,Integer> {
}
