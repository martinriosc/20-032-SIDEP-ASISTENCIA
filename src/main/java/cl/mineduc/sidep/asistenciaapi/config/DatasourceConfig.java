package cl.mineduc.sidep.asistenciaapi.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;

@RequiredArgsConstructor
@Configuration
@MapperScan("cl.mineduc.sidep.asistenciaapi.mappers")
public class DatasourceConfig {


    private final Environment environment;

    @Bean
    @Primary
    public DataSource dataSource() {
        HikariConfig config = new HikariConfig();

        config.setDriverClassName(environment.getProperty("api.sidep.datasource.driver-class-name"));
        config.setJdbcUrl(environment.getProperty("api.sidep.datasource.url"));
        config.setUsername(environment.getProperty("api.sidep.datasource.username"));
        config.setPassword(environment.getProperty("api.sidep.datasource.password"));

        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        config.setMinimumIdle(1);
        config.setMaximumPoolSize(20);

        return new HikariDataSource(config);
    }

    @Primary
    @Bean
    public SqlSessionFactory sqlSessionFactory() throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource());
        Resource[] resources = new PathMatchingResourcePatternResolver().getResources("classpath:sql/*.xml");
        sqlSessionFactoryBean.setMapperLocations(resources);

        return sqlSessionFactoryBean.getObject();

    }

}
