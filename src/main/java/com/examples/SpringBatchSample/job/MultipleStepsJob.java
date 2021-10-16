package com.examples.SpringBatchSample.job;

import com.examples.SpringBatchSample.model.dto.EmployeeDTO;
import com.examples.SpringBatchSample.model.mapper.EmployeeDBRowMapper;
import com.examples.SpringBatchSample.model.mapper.EmployeeFileRowMapper;
import com.examples.SpringBatchSample.model.entity.Employee;
import com.examples.SpringBatchSample.processor.EmployeeDTOProcessor;
import com.examples.SpringBatchSample.processor.EmployeeProcessorV2;
import com.examples.SpringBatchSample.tasklet.AccountCleanupTasklet;
import com.examples.SpringBatchSample.tasklet.AccountTransactionCleanupTasklet;
import com.examples.SpringBatchSample.tasklet.EmployeeCleanupTasklet;
import com.examples.SpringBatchSample.tasklet.EmployeeSummeryTargetTasklet;
import com.examples.SpringBatchSample.utils.Constants;
import com.examples.SpringBatchSample.writer.EmailSenderWriter;
import com.examples.SpringBatchSample.writer.EmployeeDBWriter;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemStreamReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.support.CompositeItemWriter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Configuration
public class MultipleStepsJob {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final EmployeeProcessorV2 employeeProcessorV2;
    private final EmployeeDTOProcessor employeeDTOProcessor;
    private final DataSource dataSource;
    private final EmployeeDBWriter employeeDBWriter;
    private final EmailSenderWriter emailSenderWriter;
    private final ExecutionContext executionContext;

    private final AccountCleanupTasklet accountCleanupTasklet;
    private final EmployeeCleanupTasklet employeeCleanupTasklet;
    private final AccountTransactionCleanupTasklet accountTransactionCleanupTasklet;
    private final EmployeeSummeryTargetTasklet employeeSummeryTargetTasklet;

    public MultipleStepsJob(JobBuilderFactory jobBuilderFactory, StepBuilderFactory stepBuilderFactory, EmployeeProcessorV2 employeeProcessorV2, EmployeeDTOProcessor employeeDTOProcessor, DataSource dataSource, EmployeeDBWriter employeeDBWriter, EmailSenderWriter emailSenderWriter, ExecutionContext executionContext, AccountCleanupTasklet accountCleanupTasklet, EmployeeCleanupTasklet employeeCleanupTasklet, AccountTransactionCleanupTasklet accountTransactionCleanupTasklet, EmployeeSummeryTargetTasklet employeeSummeryTargetTasklet) {
        this.jobBuilderFactory = jobBuilderFactory;
        this.stepBuilderFactory = stepBuilderFactory;
        this.employeeProcessorV2 = employeeProcessorV2;
        this.employeeDTOProcessor = employeeDTOProcessor;
        this.dataSource = dataSource;
        this.employeeDBWriter = employeeDBWriter;
        this.emailSenderWriter = emailSenderWriter;
        this.executionContext = executionContext;
        this.accountCleanupTasklet = accountCleanupTasklet;
        this.employeeCleanupTasklet = employeeCleanupTasklet;
        this.accountTransactionCleanupTasklet = accountTransactionCleanupTasklet;
        this.employeeSummeryTargetTasklet = employeeSummeryTargetTasklet;
    }


    @Bean
    @StepScope
    Resource getOutputResource(){
       return new FileSystemResource(String.format("output/employee_output_%s.csv",
                executionContext.get(Constants.FILE_NAME_PART)));
    }

    @Bean
    @StepScope
    Resource inputResource(@Value("#{jobParameters[fileName]}") final String fileName) {
        return new ClassPathResource(fileName);
    }

    /********************** Read context Variable by Execution Context *******************************

    @Bean
        @StepScope
        Resource newInputResource() {
            return new ClassPathResource(
                    Objects.requireNonNull(executionContext.get(Constants.FILE_NAME_CONTEXT_KEY)).toString()
            );
        }

    ************************************************************************************************/


    @Qualifier(value = "multipleStepJob")
    @Bean
    public Job multipleStepJob() throws Exception{
        return this.jobBuilderFactory.get("multipleStepJob")
                .start(accountTransactionCleanup())
                .next(accountCleanup())
                .next(employeeCleanup())
                .next(inputDataStep())
                .next(outputDataStep())
                .next(employeeGroupByTasklet())
                .build();
    }

    @Bean
    public Step accountTransactionCleanup() {
        return this.stepBuilderFactory.get("accountTransactionCleanup")
                .tasklet(accountTransactionCleanupTasklet)
                .build();
    }

    @Bean
    public Step accountCleanup() {
        return this.stepBuilderFactory.get("accountCleanup")
                .tasklet(accountCleanupTasklet)
                .build();
    }

    @Bean
    public Step employeeCleanup() {
        return this.stepBuilderFactory.get("employeeCleanup")
                .tasklet(employeeCleanupTasklet)
                .build();
    }

    @Bean
    public Step employeeGroupByTasklet() {
        return this.stepBuilderFactory.get("employeeGroupByTasklet")
                .tasklet(employeeSummeryTargetTasklet)
                .build();
    }

    @Bean
    public Step inputDataStep() {
        return this.stepBuilderFactory.get("inputDataStep")
                .<EmployeeDTO, Employee>chunk(1000)
                .reader(employeeFileReader())
                .processor(employeeProcessorV2)
                .writer(employeeDBWriter)
                //.faultTolerant().skipPolicy(skipPolicy())
                .taskExecutor(taskExecutor())
                .build();
    }

    @Bean
    public Step outputDataStep() {
        CompositeItemWriter<EmployeeDTO> compositeItemWriter = new CompositeItemWriter<>();
        List<ItemWriter<? super EmployeeDTO>> delegates = new ArrayList<>();
        delegates.add(emailSenderWriter);
        delegates.add(employeeFileWriter());
        compositeItemWriter.setDelegates(delegates);

        return this.stepBuilderFactory.get("outputDataStep")
                .<Employee, EmployeeDTO>chunk(1000)
                .reader(employeeDBReader())
                .processor(employeeDTOProcessor)
                .writer(compositeItemWriter)
                //.faultTolerant().skipPolicy(skipPolicy())
                .build();
    }

    @Bean
    public FlatFileItemReader<EmployeeDTO> employeeFileReader() {
        FlatFileItemReader<EmployeeDTO> reader = new FlatFileItemReader<>();
        reader.setResource(inputResource(null));
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
        writer.setResource(getOutputResource());
        writer.setLineAggregator(new DelimitedLineAggregator<EmployeeDTO>() {
            {
                setFieldExtractor(new BeanWrapperFieldExtractor<EmployeeDTO>() {
                    {
                        setNames(new String[]{"employeeId", "firstName", "lastName", "email", "age"});
                    }
                });
                setDelimiter(",");
            }
        });
        writer.setShouldDeleteIfExists(true);
        return writer;
    }

    @Bean
    public TaskExecutor taskExecutor() {
        SimpleAsyncTaskExecutor executor=new SimpleAsyncTaskExecutor();
        executor.setConcurrencyLimit(1000);
        return executor;
    }

@Bean
    public JobSkipPolicy skipPolicy(){
        return new JobSkipPolicy();
    }

}
