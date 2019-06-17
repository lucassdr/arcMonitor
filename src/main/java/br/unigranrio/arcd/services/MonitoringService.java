package br.unigranrio.arcd.services;

import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import br.unigranrio.arcd.domain.Monitoring;
import br.unigranrio.arcd.repositories.MonitoringRepository;
import br.unigranrio.arcd.resources.exceptions.ObjectNotFoundException;


@Service
public class MonitoringService {
	
	@Autowired
	private MonitoringRepository repo;
	
	public Monitoring find(Integer id) {
		
		Monitoring obj = repo.findOne(id);
		
		if(obj == null) {
			throw new ObjectNotFoundException("Objeto com id: "+ id + " não encontrado!");
		}
		
		return obj;
	}
	
	@Transactional
	public Monitoring insert(Monitoring obj) {
		obj.setId(null);;
		obj = repo.save(obj);
		System.out.println(obj);
		
		return obj;
	}
	
	public Page<Monitoring> findPage(Integer page, Integer linesPerPage,
			String orderBy, String direction){

		PageRequest pageRequest = new PageRequest(page, linesPerPage,
				Direction.valueOf(direction), orderBy);

		return repo.findAll(pageRequest);
	}
	
	public Page<Monitoring> search(Date dataIni, Date dataFim, Integer page,
			Integer linesPerPage, String orderBy, String direction) {
		PageRequest pageRequest = new PageRequest(page, linesPerPage,
				Direction.valueOf(direction), orderBy);
		
		return repo.findByregistroDataBetween(dataIni, dataFim, pageRequest);	
	}
}
