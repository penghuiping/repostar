package com.php25.desktop.repostars.config;

import com.php25.common.core.exception.Exceptions;
import com.php25.common.core.mess.SnowflakeIdWorker;
import com.php25.common.db.Db;
import com.php25.common.db.DbType;
import com.php25.common.db.cnd.JdbcPair;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;
import org.sqlite.SQLiteDataSource;

import javax.sql.DataSource;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.charset.StandardCharsets;

/**
 * @author penghuiping
 * @date 2020/9/23 14:17
 */
@Slf4j
@Configuration
public class DbConfig {

    @Bean
    public DataSource dataSource(
            @Value("${spring.datasource.url}") String url
    ) {
        SQLiteDataSource sqLiteDataSource = new SQLiteDataSource();
        //sqLiteDataSource.setDatabaseName("repostars");
        sqLiteDataSource.setUrl(url);
        return sqLiteDataSource;
    }

    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        try (var readChannel = Channels.newChannel(new ClassPathResource("initsql/repostars_schema.sql").getInputStream())) {
            var jdbcTemplate = new JdbcTemplate(dataSource);
            ByteBuffer byteBuffer = ByteBuffer.allocate(512);
            StringBuilder sb = new StringBuilder();
            for (; ; ) {
                byteBuffer.clear();
                int len = readChannel.read(byteBuffer);
                if (len <= 0) {
                    break;
                }
                byteBuffer.flip();
                byte[] tmp = new byte[len];
                byteBuffer.get(tmp, byteBuffer.position(), len);
                sb.append(new String(tmp, StandardCharsets.UTF_8));
            }
            log.info("initScript:{}", sb.toString());

            for (String sql : sb.toString().split(";")) {
                jdbcTemplate.execute(sql);
            }
            return jdbcTemplate;
        } catch (Exception e) {
            throw Exceptions.throwIllegalStateException("初始化数据库出错", e);
        }
    }

    @Bean
    public TransactionTemplate transactionTemplate(PlatformTransactionManager transactionManager) {
        return new TransactionTemplate(transactionManager);
    }

    @Bean
    Db db(JdbcTemplate jdbcTemplate, TransactionTemplate transactionTemplate) {
        Db db = new Db(DbType.MYSQL);
        JdbcPair jdbcPair = new JdbcPair(jdbcTemplate, transactionTemplate);
        db.setJdbcPair(jdbcPair);
        db.scanPackage("com.php25.desktop.repostars.respository.entity");
        return db;
    }

    @Bean
    SnowflakeIdWorker snowflakeIdWorker() {
        return new SnowflakeIdWorker(1, 1);
    }
}
