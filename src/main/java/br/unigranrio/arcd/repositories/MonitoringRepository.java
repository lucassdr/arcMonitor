package br.unigranrio.arcd.repositories;

import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.unigranrio.arcd.domain.Monitoring;
import br.unigranrio.arcd.domain.MonitoringReport;

@Repository
public interface MonitoringRepository extends JpaRepository<Monitoring, Integer>{
	
	Page<Monitoring> findByregistroDataBetween(Date dataInicio,
			Date dataFim, Pageable pageRequest);
	
	@Transactional(readOnly = true)
	@Query("SELECT new MonitoringReport(sum(m.corrente), "
			+ "sum(m.tensao), "
			+ "SUM (m.corrente) * SUM (m.tensao) * 0.67) "
			+ "FROM Monitoring m "
			+ "where m.registroData between :dataIni and :dataFim")			
	MonitoringReport findCorrenteTensaoCustoBetweenRegistroData(
			@Param("dataIni") Date dataIni,
			@Param("dataFim") Date dataFim);
}
