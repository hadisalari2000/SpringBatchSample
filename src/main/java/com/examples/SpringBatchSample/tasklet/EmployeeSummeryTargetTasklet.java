package com.examples.SpringBatchSample.tasklet;

import com.examples.SpringBatchSample.model.dto.EmployeeDTO;
import com.examples.SpringBatchSample.utils.Constants;
import lombok.Synchronized;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class EmployeeSummeryTargetTasklet implements Tasklet {

    private final ExecutionContext executionContext;

    public EmployeeSummeryTargetTasklet(ExecutionContext executionContext) {
        this.executionContext = executionContext;
    }

    @Override
    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {

        try(Stream<String> employees= Files.lines(Paths.get(String.format("output/employee_output_%s.csv",executionContext.get(Constants.FILE_NAME_PART))))){

            List<EmployeeDTO> list=employees
                    .map(x->x.split(","))
                    .map(EmployeeSummeryTargetTasklet::employeeMapper)
                    .collect(Collectors.toList());

            if(!CollectionUtils.isEmpty(list)){
                Map<Integer,Long> employeeGroupByAge= list.stream()
                        .collect(Collectors.groupingBy(EmployeeDTO::getAge,Collectors.counting()));
                System.out.println(employeeGroupByAge);
            }
        }
        return null;
    }

    @Synchronized
    private static EmployeeDTO employeeMapper(String[] record){
        return EmployeeDTO.builder()
                .employeeId(record[0])
                .firstName(record[1])
                .lastName(record[2])
                .email(record[3])
                .age(Integer.parseInt(record[4]))
                .build();
    }
}
