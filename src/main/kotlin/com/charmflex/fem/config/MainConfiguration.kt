package com.charmflex.fem.config

import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.EnableAspectJAutoProxy
import org.springframework.jdbc.core.JdbcTemplate
import javax.sql.DataSource


@Configuration
@EnableAspectJAutoProxy
class MainConfiguration {

    @Bean
    fun firebaseAuth(): FirebaseAuth {
        println("get firebaseAuth")
        return FirebaseAuth.getInstance()
    }

    @Bean
    fun jdbcTemplate(dataSource: DataSource): JdbcTemplate {
        val template = JdbcTemplate(dataSource)
        return template
    }
}