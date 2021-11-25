package org.springframework.samples.petclinic.owner;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import javax.sql.DataSource;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DbTest {

	@Autowired
	DataSource dataSource;

	@Test
	@DisplayName("DB 연결 테스트")
	public void connection() throws SQLException {
		Connection connection = dataSource.getConnection();


		DatabaseMetaData metaData = connection.getMetaData();

		System.out.println(metaData.getConnection());
		System.out.println(metaData.getURL());
		System.out.println(metaData.getDriverName());
		System.out.println(metaData.getUserName());
	}

}
