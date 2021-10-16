/*
package com.examples.SpringBatchSample.job;

import com.examples.SpringBatchSample.model.dto.StudentDTO;
import com.examples.SpringBatchSample.model.entity.Student;
import com.examples.SpringBatchSample.model.mapper.StudentFileRowMapper;
import com.examples.SpringBatchSample.processor.StudentProcessor;
import com.examples.SpringBatchSample.tasklet.StudentCleanupTargetTasklet;
import com.examples.SpringBatchSample.utils.Constants;
import com.examples.SpringBatchSample.writer.StudentDBWriter;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.util.Objects;

@Configuration
public class StudentFromCSVToDataBase {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final StudentDBWriter studentDBWriter;
    private final StudentProcessor studentProcessor;
    private final ExecutionContext executionContext;
    private final StudentFileRowMapper studentFileRowMapper;
    private final StudentCleanupTargetTasklet studentCleanupTargetTasklet;

    public StudentFromCSVToDataBase(JobBuilderFactory jobBuilderFactory, StepBuilderFactory stepBuilderFactory, StudentDBWriter studentDBWriter, StudentProcessor studentProcessor, ExecutionContext executionContext, StudentFileRowMapper studentFileRowMapper, StudentCleanupTargetTasklet studentCleanupTargetTasklet) {
        this.jobBuilderFactory = jobBuilderFactory;
        this.stepBuilderFactory = stepBuilderFactory;
        this.studentDBWriter = studentDBWriter;
        this.studentProcessor = studentProcessor;
        this.executionContext = executionContext;
        this.studentFileRowMapper = studentFileRowMapper;
        this.studentCleanupTargetTasklet = studentCleanupTargetTasklet;
    }

    @Bean
    @Qualifier(value="StudentFromCSVToDataBase")
    public Job studentJob(){
        return jobBuilderFactory.get("StudentFromCSVToDataBase")
                .start(cleanStep())
                .next(startStep())
                .build();
    }

    @Bean
    public Step cleanStep() {
        return this.stepBuilderFactory.get("cleanStep")
                .tasklet(studentCleanupTargetTasklet)
                .build();
    }

    @Bean
    public Step startStep(){
        return this.stepBuilderFactory.get("startStep")
                .<StudentDTO, Student>chunk(1)
                .reader(studentReader())
                .processor(studentProcessor)
                .writer(studentDBWriter)
                .build();

    }

    @Bean
    @StepScope
    Resource newInputResource() {
        return new ClassPathResource(
                Objects.requireNonNull(executionContext.get(Constants.FILE_NAME_CONTEXT_KEY)).toString()
        );
    }

    @Bean
    public FlatFileItemReader<StudentDTO> studentReader() {
        FlatFileItemReader<StudentDTO> reader=new FlatFileItemReader<>();
        reader.setResource(newInputResource());
        reader.setLineMapper(new DefaultLineMapper<StudentDTO>() {{
            setLineTokenizer(new DelimitedLineTokenizer() {{
                setNames("firstName", "lastName", "age","mail");
                setDelimiter(",");
            }});
            setFieldSetMapper(studentFileRowMapper);
        }});
        return reader;
    }

    */
/*@Bean
    public JobSkipPolicy skipPolicy(){
        return new JobSkipPolicy();
    }*//*

}
*/
