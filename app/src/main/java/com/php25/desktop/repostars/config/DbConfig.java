package com.php25.desktop.repostars.config;

import com.php25.common.db.Db;
import com.php25.common.db.DbType;
import com.php25.common.db.cnd.JdbcPair;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;
import org.sqlite.SQLiteDataSource;

import javax.sql.DataSource;

/**
 * @author penghuiping
 * @date 2020/9/23 14:17
 */
public class DbConfig {

    @Bean
    public DataSource dataSource() {
        SQLiteDataSource sqLiteDataSource = new SQLiteDataSource();
        sqLiteDataSource.setDatabaseName("repostars");
        return sqLiteDataSource;
    }

    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
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
        db.scanPackage("com.php25.desktop.repostars.entity");
        return db;
    }
}
