package de.eurowings.newsletter.repositories;

import de.eurowings.newsletter.models.Newsletter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Ashok Pelluru
 */
@Repository
public interface NewsletterRepository extends JpaRepository<Newsletter, Long> {

}
