package com.talentquery.repository;

import com.talentquery.model.Candidate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface CandidateRepository extends JpaRepository<Candidate, Long> {

    /**
     * Semantic search using the <=> operator from pgvector (Cosine distance)
     * We order by distance ascending.
     */
    @Query(value = "SELECT id, name, email, years_of_experience, bio, CAST(embedding AS text) as embedding FROM candidate c ORDER BY c.embedding <=> cast(:queryVector as vector) LIMIT :limit", nativeQuery = true)
    List<Candidate> findNear(@Param("queryVector") float[] queryVector, @Param("limit") int limit);

    /**
     * Hybrid Search: Filters by experience AND semantic similarity
     */
    @Query(value = "SELECT id, name, email, years_of_experience, bio, CAST(embedding AS text) as embedding FROM candidate c WHERE c.years_of_experience >= :minExp ORDER BY c.embedding <=> cast(:queryVector as vector) LIMIT :limit", nativeQuery = true)
    List<Candidate> findHybrid(@Param("queryVector") float[] queryVector, @Param("minExp") int minExp, @Param("limit") int limit);
}
