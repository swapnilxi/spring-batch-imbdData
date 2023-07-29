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

import com.swapnilxi.springbatch.entity.Relation;

import javax.sql.DataSource;

@Configuration
public class RelationBatchConfig {
    @Bean
    public FlatFileItemReader<Relation> reader() {
        return new FlatFileItemReaderBuilder<Relation>()
                .name("relationItemReader")
                .resource(new ClassPathResource("relation.tsv"))
                .strict(false)
                .delimited()
                .delimiter("\t")
                .names(new String[]{"tconst","ordering","nconst","category","job",	"characters"})
                .fieldSetMapper(new BeanWrapperFieldSetMapper<Relation>() {{
                    setTargetType(Relation.class);
                }})
                .build();
    }
    @Bean
    public RelationItemProcessor processor() {
        return new RelationItemProcessor();
    }

    @Bean
    public JdbcBatchItemWriter<Relation> writer(DataSource dataSource) {
        return new JdbcBatchItemWriterBuilder<Relation>()
                .itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>())
                .sql("INSERT INTO relation (tconst,nconst) VALUES (:tconst,:nconst)")
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
                      PlatformTransactionManager transactionManager, JdbcBatchItemWriter<Relation> writer) {
        return new StepBuilder("step1", jobRepository)
                .<Relation, Relation> chunk(12, transactionManager)
                .reader(reader())
                .processor(processor())
                .writer(writer)
                .faultTolerant()
                .skipLimit(10)
                .skip(FlatFileParseException.class)
                .build();
    }
}
