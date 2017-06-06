package kr.or.connect.todo.persistence;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.sql.Timestamp;
import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import kr.or.connect.todo.domain.TodoItem;


@RunWith(SpringRunner.class)
@SpringBootTest // 이를 통해 Spring Boot 통해 설정한 ApplicationContext에서 BookDao를 참조할 수 있다.
@Transactional // 해당 클래스가 트랜잭션 안에서 실행되는 것을 표시하는 것이다
public class TodoDaoTest {

	@Autowired // bookdao참조한다는 어노테이션
	private TodoDao dao;
	@Test
	public void shouldInsertAndSelect() {
		Date date = new Date();
		Timestamp sqlDate = new Timestamp(new java.util.Date().getTime());
		System.out.println(" insert " + sqlDate);
		// given
		TodoItem todo = new TodoItem("TASK1수행", 0 , new Date(date.getTime()) );

		// when
		Integer id = dao.insert(todo);

		// then
		TodoItem selected = dao.selectById(id);
		System.out.println(selected);

		// assertThat(selected.getTitle(), is("Java 웹개발"));
	}
	
	
}
