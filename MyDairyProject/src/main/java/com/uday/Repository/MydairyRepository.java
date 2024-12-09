package com.uday.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.uday.binding.Mydairy;

public interface MydairyRepository extends JpaRepository<  Mydairy, Long > {

}
