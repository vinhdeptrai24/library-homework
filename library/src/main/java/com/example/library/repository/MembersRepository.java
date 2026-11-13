package com.example.library.repository;

import com.example.library.entity.Members;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MembersRepository extends JpaRepository<Members,Long> {

}
