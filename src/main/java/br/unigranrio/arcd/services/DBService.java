package br.unigranrio.arcd.services;

import java.text.ParseException;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.unigranrio.arcd.domain.Monitoring;
import br.unigranrio.arcd.repositories.MonitoringRepository;
import br.unigranrio.arcd.resources.utils.Utils;

@Service
public class DBService {
	
	@Autowired
	MonitoringRepository repo;
	
	public void instantiateTestDatabase() throws ParseException{
		
		Monitoring[] m = new Monitoring[50];
		
		for(int i = 0; i < m.length; i++) {
			m[i] = new Monitoring(null, (Math.floor(Math.random() * Math.floor(10))),
					(Math.floor(Math.random() * Math.floor(220))),
					Utils.sqlDateToDate(Utils.generateDate(2000, 2018)));
			
			repo.save(Arrays.asList(m[i]));
		}
	}
}
