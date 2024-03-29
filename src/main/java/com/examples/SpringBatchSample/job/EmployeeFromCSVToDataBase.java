/*
package com.examples.SpringBatchSample.job;

import com.examples.SpringBatchSample.model.dto.EmployeeDTO;
import com.examples.SpringBatchSample.model.entity.Employee;
import com.examples.SpringBatchSample.processor.EmployeeProcessor;
import com.examples.SpringBatchSample.writer.EmployeeDBWriter;
import com.examples.SpringBatchSample.model.mapper.EmployeeFileRowMapper;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;

import javax.sql.DataSource;

@Configuration
public class EmployeeFromCSVToDataBase {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final EmployeeProcessor employeeProcessor;
    private final DataSource dataSource;
    private final EmployeeDBWriter employeeDBWriter;

    public EmployeeFromCSVToDataBase(JobBuilderFactory jobBuilderFactory, StepBuilderFactory stepBuilderFactory, EmployeeProcessor employeeProcessor, DataSource dataSource, EmployeeDBWriter employeeDBWriter) {
        this.jobBuilderFactory = jobBuilderFactory;
        this.stepBuilderFactory = stepBuilderFactory;
        this.employeeProcessor = employeeProcessor;
        this.dataSource = dataSource;
        this.employeeDBWriter = employeeDBWriter;
    }

    @Qualifier(value = "employeeFromCSVToDataBase")
    @Bean
    public Job employeeJob() throws Exception {
        return this.jobBuilderFactory.get("employeeFromCSVToDataBase")
                .start(startStep())
                .build();
    }

    @Bean
    public Step startStep() throws Exception {
        return this.stepBuilderFactory.get("startStep")
                .<EmployeeDTO, Employee>chunk(1000)
                .reader(employeeReader())
                .processor(employeeProcessor)
                .writer(employeeDBWriter)
                .taskExecutor(taskExecutor())
                .build();
    }

    @Bean
    @StepScope
    Resource inputFileResource(@Value("#{jobParameters[fileName]}") final String fileName) {
        return new ClassPathResource(fileName);
    }

    @Bean
    @StepScope
    public FlatFileItemReader<EmployeeDTO> employeeReader() throws Exception {
        FlatFileItemReader<EmployeeDTO> reader = new FlatFileItemReader<>();
        reader.setResource(inputFileResource(null));
        reader.setLineMapper(new DefaultLineMapper<EmployeeDTO>() {{
            setLineTokenizer(new DelimitedLineTokenizer() {{
                setNames("employeeId", "firstName", "lastName", "email", "age");
                setDelimiter(",");
            }});
            setFieldSetMapper(new EmployeeFileRowMapper());
        }});
        return reader;
    }

    @Bean
    public TaskExecutor taskExecutor() {
        SimpleAsyncTaskExecutor taskExecutor = new SimpleAsyncTaskExecutor();
        taskExecutor.setConcurrencyLimit(1000);
        return taskExecutor;
    }
}
*/
