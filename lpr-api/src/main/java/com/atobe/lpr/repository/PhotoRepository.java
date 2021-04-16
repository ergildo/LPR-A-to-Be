package com.atobe.lpr.repository;

import com.atobe.lpr.domain.Photo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PhotoRepository extends JpaRepository<Photo, Long> {}
