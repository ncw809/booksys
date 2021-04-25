package booksys.persistency;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;

import booksys.domain.Table;


@Mapper
public interface TableMapper {
	public List<Table> findList();
	public Table findOneByCode(int number);
	public void save(Table table);
	public void update(Table table);
	public void deleteByOid(int oid);
}
