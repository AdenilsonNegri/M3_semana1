package com.Lab365.api_sustentavel.Repository;

import com.Lab365.api_sustentavel.Entity.AcaoSustentavel;
import com.Lab365.api_sustentavel.Enum.CategoriaAcao;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface AcaoSustentavelRepository extends JpaRepository<AcaoSustentavel, Long> {

    //  Método para buscar por categoria
    List<AcaoSustentavel> findByCategoria(CategoriaAcao categoria);
}
