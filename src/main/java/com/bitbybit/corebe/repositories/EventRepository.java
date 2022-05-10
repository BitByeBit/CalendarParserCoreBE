package com.bitbybit.corebe.repositories;

import com.bitbybit.corebe.models.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event, Long> {
}
