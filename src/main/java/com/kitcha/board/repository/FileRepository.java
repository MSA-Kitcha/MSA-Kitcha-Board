package com.kitcha.board.repository;

import com.kitcha.board.entity.Board;
import com.kitcha.board.entity.File;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileRepository extends CrudRepository<File, Long> {
}
