package com.examples.SpringBatchSample.job;

import com.examples.SpringBatchSample.dto.AccountDTO;
import com.examples.SpringBatchSample.model.entity.Account;
import com.examples.SpringBatchSample.model.entity.AccountTransaction;
import com.examples.SpringBatchSample.model.mapper.AccountDBRowMapper;
import com.examples.SpringBatchSample.model.mapper.AccountFileRowMapper;
import com.examples.SpringBatchSample.processor.AccountProcessor;
import com.examples.SpringBatchSample.processor.AccountTransactionProcessor;
import com.examples.SpringBatchSample.writer.AccountDBWriter;
import com.examples.SpringBatchSample.writer.AccountTransactionDBWriter;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemStreamReader;
import org.springframework.batch.item.database.JdbcCursorItemReader;
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
import java.util.List;


@Configuration
public class AccountFromCSVToDataBase {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final AccountProcessor accountProcessor;
    private final AccountTransactionProcessor accountTransactionProcessor;
    private final AccountDBWriter accountDBWriter;
    private final AccountTransactionDBWriter accountTransactionDBWriter;
    private final DataSource dataSource;

    public AccountFromCSVToDataBase(JobBuilderFactory jobBuilderFactory, StepBuilderFactory stepBuilderFactory, AccountProcessor accountProcessor, AccountTransactionProcessor accountTransactionProcessor, AccountDBWriter accountDBWriter, AccountTransactionDBWriter accountTransactionDBWriter, DataSource dataSource) {
        this.jobBuilderFactory = jobBuilderFactory;
        this.stepBuilderFactory = stepBuilderFactory;
        this.accountProcessor = accountProcessor;
        this.accountTransactionProcessor = accountTransactionProcessor;
        this.accountDBWriter = accountDBWriter;
        this.accountTransactionDBWriter = accountTransactionDBWriter;
        this.dataSource = dataSource;
    }

    @Qualifier(value = "employeeFromCSVToDataBase")
    @Bean
    public Job employeeJob() throws Exception {
        return this.jobBuilderFactory.get("employeeFromCSVToDataBase")
                .start(createAccountStep())
                .next(createAccountTransactionStep())
                .build();
    }

    @Bean
    public Step createAccountStep() throws Exception {
        return this.stepBuilderFactory.get("createAccountStep")
                .<AccountDTO, Account>chunk(500)
                .reader(accountReader())
                .processor(accountProcessor)
                .writer(accountDBWriter)
                .taskExecutor(taskExecutor())
                .build();
    }

    @Bean
    public Step createAccountTransactionStep() throws Exception {
        return this.stepBuilderFactory.get("createAccountTransactionStep")
                .<Account, List<AccountTransaction>>chunk(500)
                .reader(accountDBReader())
                .processor(accountTransactionProcessor)
                .writer(accountTransactionDBWriter)
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
        taskExecutor.setConcurrencyLimit(500);
        return taskExecutor;
    }

    @Bean
    public ItemStreamReader<Account> accountDBReader() {
        JdbcCursorItemReader<Account> reader = new JdbcCursorItemReader<>();
        reader.setDataSource(dataSource);
        reader.setSql("select * from account");
        reader.setRowMapper(new AccountDBRowMapper());
        return reader;
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


