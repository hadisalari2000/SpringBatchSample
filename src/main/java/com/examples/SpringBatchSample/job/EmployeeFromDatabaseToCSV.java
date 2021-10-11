/*

package com.examples.SpringBatchSample.job;

import com.examples.SpringBatchSample.dto.EmployeeDTO;
import com.examples.SpringBatchSample.model.mapper.EmployeeDBRowMapper;
import com.examples.SpringBatchSample.model.entity.Employee;
import com.examples.SpringBatchSample.processor.EmployeeProcessor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemStreamReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

import javax.sql.DataSource;
import java.util.Date;

@Configuration
public class EmployeeFromDatabaseToCSV {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final EmployeeProcessor employeeProcessor;
    private final DataSource dataSource;

    public EmployeeFromDatabaseToCSV(JobBuilderFactory jobBuilderFactory, StepBuilderFactory stepBuilderFactory, EmployeeProcessor employeeProcessor, DataSource dataSource) {
        this.jobBuilderFactory = jobBuilderFactory;
        this.stepBuilderFactory = stepBuilderFactory;
        this.employeeProcessor = employeeProcessor;
        this.dataSource = dataSource;
    }

    private Resource outputResource = new FileSystemResource(String.format("output/employee_output_%s.csv", new Date().getTime()));

    @Qualifier(value = "employeeFromDataBaseToCSV")
    @Bean
    public Job employeeJob() throws Exception {
        return this.jobBuilderFactory.get("employeeFromDataBaseToCSV")
                .start(startStep())
                .build();
    }

    @Bean
    public Step startStep() throws Exception {
        return this.stepBuilderFactory.get("startStep")
                .<Employee, EmployeeDTO>chunk(1000)
                .reader(employeeDBReader())
                .writer(employeeFileWriter())
                .build();
    }

    @Bean
    public ItemStreamReader<Employee> employeeDBReader() {
        JdbcCursorItemReader<Employee> reader = new JdbcCursorItemReader<>();
        reader.setDataSource(dataSource);
        reader.setSql("select * from employee");
        reader.setRowMapper(new EmployeeDBRowMapper());
        return reader;
    }

    @Bean
    public ItemWriter<EmployeeDTO> employeeFileWriter() {
        FlatFileItemWriter writer = new FlatFileItemWriter();
        writer.setResource(outputResource);
        writer.setLineAggregator(new DelimitedLineAggregator<EmployeeDTO>() {
            {
                setFieldExtractor(new BeanWrapperFieldExtractor<EmployeeDTO>() {
                    {
                        setNames(new String[]{"employeeId", "fullName", "email", "age", "calculateAge1", "calculateAge2"});
                    }
                });
                setDelimiter(" ||| ");
            }
        });
        writer.setShouldDeleteIfExists(true);
        return writer;
    }
}
*/
