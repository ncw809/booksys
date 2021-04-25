package booksys.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import booksys.domain.Table;
import booksys.persistency.TableMapper;

@RestController
public class BooksysController {
	@Autowired
	private TableMapper tableMapper;
	
	@RequestMapping(value="/test", method=RequestMethod.GET)
	//@ResponseStatus(value=HttpStatus.OK)
	public String isTest() {
		return "TEST";
	}
	
	@RequestMapping(value="/table", method=RequestMethod.GET)
	@ResponseStatus(value=HttpStatus.OK)
	public String TableInfo() {
		int a=1;
		Table table=tableMapper.findOneByCode(a);
		return table.toString();
	}
}
