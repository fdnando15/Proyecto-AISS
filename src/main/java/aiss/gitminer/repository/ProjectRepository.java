package aiss.gitminer.repository;

import aiss.gitminer.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project,Long> {
    Project findByOwnerId(String ownerId);
}
