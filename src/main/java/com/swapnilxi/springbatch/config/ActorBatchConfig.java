package com.swapnilxi.springbatch.config;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import org.springframework.transaction.PlatformTransactionManager;
import com.swapnilxi.springbatch.entity.Actor;



@Configuration
@EnableBatchProcessing
public class ActorBatchConfig {
    @Bean
    public FlatFileItemReader<Actor> reader() {
        return new FlatFileItemReaderBuilder<Actor>()
                .name("ActorItemReader")
                .resource(new ClassPathResource("name.tsv"))
                .delimited()
                .delimiter("\t")
                .names(new String[]{"nconst", "primaryName", "birthYear", "deathYear", "primaryProfession"})
                .fieldSetMapper(new BeanWrapperFieldSetMapper<>() {{
                    setTargetType(Actor.class);
                }})
                .build();
    }

    @Bean
    public ActorItemProcessor processor() {
        return new ActorItemProcessor();
    }

    @Bean
    public JdbcBatchItemWriter<Actor> writer(DataSource dataSource) {
        return new JdbcBatchItemWriterBuilder<Actor>()
                .itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>())
                .sql("INSERT INTO actor (nconst,primaryName,birthYear,deathYear,primaryProfession) VALUES (:nconst, :primaryName, :birthYear, :deathYear, :deathYear, :primaryProfession)")
                .dataSource(dataSource)
                .build();
    }

    @Bean
    public Job importUserJob(JobRepository jobRepository, Step step1) {
        return new JobBuilder("importUserJob", jobRepository)
                .incrementer(new RunIdIncrementer())
                .flow(step1)
                .end()
                .build();
    }

    @Bean
    public Step step1(JobRepository jobRepository,
                      PlatformTransactionManager transactionManager, JdbcBatchItemWriter<Actor> writer) {
        return new StepBuilder("step1", jobRepository)
                .<Actor, Actor> chunk(10, transactionManager)
                .reader(reader())
                .processor(processor())
                .writer(writer)
                .build();
    }

}







