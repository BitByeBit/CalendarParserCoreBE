package com.bitbybit.corebe.DAO;

import com.bitbybit.corebe.models.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventDAO extends JpaRepository<Event, Long> {
}
