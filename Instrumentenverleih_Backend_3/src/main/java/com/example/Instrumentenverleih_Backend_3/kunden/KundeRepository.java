package com.example.Instrumentenverleih_Backend_3.kunden;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KundeRepository extends CrudRepository<kunde, Long> {
}
