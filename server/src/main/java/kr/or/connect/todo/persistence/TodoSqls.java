package kr.or.connect.todo.persistence;

// 쿼리문 다시 체크하기 
public class TodoSqls {
	static final String DELETE_BY_ID = "DELETE FROM todo WHERE id= :id";

	static final String COUNT_ITEM = "SELECT COUNT(*) FROM todo WHERE completed = 0";
	// BookDao에 todo 테이블을 id로 조회하는 쿼리를 상수로 추가
	static final String SELECT_BY_ID = "SELECT id, todo, completed, date FROM todo where id = :id";
	// static final String DELETE_BY_ID = "DELETE FROM todo WHERE id= :id";
	static final String UPDATE = "UPDATE todo SET\n" + "todo = :todo," + "completed = :completed,"
			+ "date = :date\n"  +"WHERE id = :id";
	static final String SELECT_ACTIVE = "SELECT id, todo, completed, date FROM todo where completed = 0 ORDER BY date DESC";

	static final String SELECT_ALL = "SELECT * FROM todo ORDER BY date DESC";
	
	static final String DELETE_COMPLETED =
			"DELETE FROM todo WHERE completed = 1";
	
	

}