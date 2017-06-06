package kr.or.connect.todo.persistence;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import kr.or.connect.todo.domain.TodoItem;


// 코드에서 DataSource에만 의존하는 DAO(Data Access Object)를 추출
@Repository
public class TodoDao {
	private NamedParameterJdbcTemplate jdbc;
	private SimpleJdbcInsert insertAction;
	private RowMapper<TodoItem> rowMapper = BeanPropertyRowMapper.newInstance(TodoItem.class);

	public TodoDao(DataSource dataSource) {
		this.jdbc = new NamedParameterJdbcTemplate(dataSource);
		this.insertAction = new SimpleJdbcInsert(dataSource)
				.withTableName("todo")
				.usingGeneratedKeyColumns("id");
		
		
		
	}
	
	public TodoItem selectById(Integer id) {
		//RowMapper<Book> rowMapper = BeanPropertyRowMapper.newInstance(Book.class);
		Map<String, Object> params = new HashMap<>();
		params.put("id", id);
		return jdbc.queryForObject(TodoSqls.SELECT_BY_ID, params, rowMapper);
	}
	
	public int countTodo() {
		Map<String, Object> params = Collections.emptyMap();
		return jdbc.queryForObject(TodoSqls.COUNT_ITEM, params, Integer.class);
	}

	// 3-1 데이터 추가
	public Integer insert(TodoItem todoitem) {
		
		SqlParameterSource params = new BeanPropertySqlParameterSource(todoitem);
		return insertAction.executeAndReturnKey(params).intValue();
	}

	// 3-13 delete
	public int deleteById(Integer id) {
		Map<String, ?> params = Collections.singletonMap("id", id);
		return jdbc.update(TodoSqls.DELETE_BY_ID, params);
	}

	// 3-14
	public int update(TodoItem todoitem) {
		SqlParameterSource params = new BeanPropertySqlParameterSource(todoitem);
		return jdbc.update(TodoSqls.UPDATE, params);
	}
	
	// 4-4 todo의 모든 데이터를 조회
	public List<TodoItem> selectAll() {
		Map<String, Object> params = Collections.emptyMap();
		return jdbc.query(TodoSqls.SELECT_ALL, params, rowMapper);
	}
	// todo activ만 조회  
	public List<TodoItem> selectActive() {
		Map<String, Object> params = Collections.emptyMap();
		return jdbc.query(TodoSqls.SELECT_ACTIVE, params, rowMapper);
	}
	
	// delete completed all
	public int deleteCompleted() {
		Map<String, ?> params = Collections.emptyMap();
		return jdbc.update(TodoSqls.DELETE_COMPLETED, params);
	}

}
