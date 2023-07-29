package com.swapnilxi.springbatch.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.FlatFileParseException;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.transaction.PlatformTransactionManager;

import com.swapnilxi.springbatch.entity.Movie;

import javax.sql.DataSource;

@Configuration
public class MovieBatchConfig {
    @Bean
    public FlatFileItemReader<Movie> reader() {
        return new FlatFileItemReaderBuilder<Movie>()
                .name("movieItemReader")
                .resource(new ClassPathResource("movie.tsv"))
                .strict(false)
                .linesToSkip(1)
                .delimited()
                .delimiter("\t")
                .names(new String[]{"tconst", "titleType","tprimaryTitle","originalTitle", "isAdult","startYear","endYear","runtimeMinutes","genres"})
                .fieldSetMapper(new BeanWrapperFieldSetMapper<Movie>() {{
                    setTargetType(Movie.class);
                }})
                .build();
    }
    @Bean
    public MovieItemProcessor processor() {
        return new MovieItemProcessor();
    }

    @Bean
    public JdbcBatchItemWriter<Movie> writer(DataSource dataSource) {
        return new JdbcBatchItemWriterBuilder<Movie>()
                .itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>())
                .sql("INSERT INTO movie (tconst,titleType,primaryTitle,originalTitle,isAdult,startYear,endYear,runtimeMinutes,genres) VALUES (:tconst,:titleType,:primaryTitle,:originalTitle,:isAdult,:startYear,:endYear,:runtimeMinutes,:genres)")
                .dataSource(dataSource)
                .build();
    }
    // end::readerwriterprocessor[]

    // tag::jobstep[]
    @Bean
    public Job importUserJob(JobRepository jobRepository,
                             Step step1) {
        return new JobBuilder("importUserJob", jobRepository)
                .incrementer(new RunIdIncrementer())
                //.listener(listener)
                .flow(step1)
                .end()
                .build();
    }

    @Bean
    public Step step1(JobRepository jobRepository,
                      PlatformTransactionManager transactionManager, JdbcBatchItemWriter<Movie> writer) {
        return new StepBuilder("step1", jobRepository)
                .<Movie, Movie> chunk(12, transactionManager)
                .reader(reader())
                .processor(processor())
                .writer(writer)
                .faultTolerant()
                .skipLimit(10)
                .skip(FlatFileParseException.class)
                .build();
    }
}
