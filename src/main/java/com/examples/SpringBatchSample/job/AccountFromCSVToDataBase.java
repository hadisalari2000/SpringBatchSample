package com.examples.SpringBatchSample.job;

import com.examples.SpringBatchSample.dto.AccountDTO;
import com.examples.SpringBatchSample.model.entity.Account;
import com.examples.SpringBatchSample.model.mapper.AccountFileRowMapper;
import com.examples.SpringBatchSample.processor.AccountProcessor;
import com.examples.SpringBatchSample.writer.AccountDBWriter;
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
public class AccountFromCSVToDataBase {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final AccountProcessor accountProcessor;
    private final DataSource dataSource;
    private final AccountDBWriter accountDBWriter;

    public AccountFromCSVToDataBase(JobBuilderFactory jobBuilderFactory, StepBuilderFactory stepBuilderFactory, AccountProcessor accountProcessor, DataSource dataSource, AccountDBWriter accountDBWriter) {
        this.jobBuilderFactory = jobBuilderFactory;
        this.stepBuilderFactory = stepBuilderFactory;
        this.accountProcessor = accountProcessor;
        this.dataSource = dataSource;
        this.accountDBWriter = accountDBWriter;
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
                .<AccountDTO, Account>chunk(1000)
                .reader(accountReader())
                .processor(accountProcessor)
                .writer(accountDBWriter)
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
    public FlatFileItemReader<AccountDTO> accountReader() throws Exception {
        FlatFileItemReader<AccountDTO> reader = new FlatFileItemReader<>();
        reader.setResource(inputFileResource(null));
        reader.setLineMapper(new DefaultLineMapper<AccountDTO>() {{
            setLineTokenizer(new DelimitedLineTokenizer() {{
                setNames("part1", "part2", "part4");
                setDelimiter(",");
            }});
            setFieldSetMapper(new AccountFileRowMapper());
        }});
        return reader;
    }

    @Bean
    public TaskExecutor taskExecutor() {
        SimpleAsyncTaskExecutor taskExecutor = new SimpleAsyncTaskExecutor();
        taskExecutor.setConcurrencyLimit(1000);
        return taskExecutor;
    }

    /*@Bean
    public JdbcBatchItemWriter<Employee> employeeDBWriterDefault() {
        JdbcBatchItemWriter<Employee> itemWriter = new JdbcBatchItemWriter<Employee>();
        itemWriter.setDataSource(dataSource);
        itemWriter.setSql("insert into employee (employee_id, full_name, email, age,calculate_age1,calculate_age2) " +
                "values (:employeeId, :fullName, :email, :age , :calculateAge1 , :calculateAge2)");
        itemWriter.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<Employee>());
        return itemWriter;
    }
    */
}


