package com.inside.insidetask.message;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessagesRepository extends PagingAndSortingRepository<Messages, Long> {
    @Query(value="select text from Messages text")
    Page<Messages> findAll(Pageable pageable);
}
