package com.swiatek.benford;

import org.springframework.data.jpa.repository.JpaRepository;

public interface GraphRepository extends JpaRepository<Graph, Long>
{
}