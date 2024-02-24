package com.karadimas.tyres.repository;

import com.karadimas.tyres.domain.Job;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Job entity.
 */
@Repository
public interface JobRepository extends JpaRepository<Job, Long>, JpaSpecificationExecutor<Job> {
    default Optional<Job> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<Job> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<Job> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select distinct job from Job job left join fetch job.jobCategory",
        countQuery = "select count(distinct job) from Job job"
    )
    Page<Job> findAllWithToOneRelationships(Pageable pageable);

    @Query("select distinct job from Job job left join fetch job.jobCategory")
    List<Job> findAllWithToOneRelationships();

    @Query("select job from Job job left join fetch job.jobCategory where job.id =:id")
    Optional<Job> findOneWithToOneRelationships(@Param("id") Long id);
}
