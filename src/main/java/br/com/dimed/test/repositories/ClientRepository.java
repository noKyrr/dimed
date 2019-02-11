package br.com.dimed.test.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.dimed.test.domain.ClientModel;

public interface ClientRepository extends JpaRepository<ClientModel, Long> {

}
