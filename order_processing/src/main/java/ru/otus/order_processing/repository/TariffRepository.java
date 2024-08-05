package ru.otus.order_processing.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.order_processing.model.Tariff;

import java.util.List;
import java.util.Optional;

public interface TariffRepository extends JpaRepository<Tariff, Long> {

    List<Tariff> findByArchivedFalse();

    Optional<Tariff> findByNameAndArchived(String name, boolean archived);
}