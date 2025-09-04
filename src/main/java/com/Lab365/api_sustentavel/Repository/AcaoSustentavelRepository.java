package com.Lab365.api_sustentavel.Repository;

import com.Lab365.api_sustentavel.Entity.AcaoSustentavel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AcaoSustentavelRepository extends JpaRepository<AcaoSustentavel, Long> {
    // Métodos CRUD já disponíveis por herança
    // Ex: save(), findById(), findAll(), deleteById(), etc.
}
