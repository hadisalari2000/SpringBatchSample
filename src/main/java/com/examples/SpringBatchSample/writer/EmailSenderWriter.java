package com.examples.SpringBatchSample.writer;

import com.examples.SpringBatchSample.model.dto.EmployeeDTO;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class EmailSenderWriter  implements ItemWriter<EmployeeDTO> {

    @Override
    public void write(List<? extends EmployeeDTO> list) {
        list.forEach(x->System.out.printf("Email send successfully to %s %s%n",x.getFirstName() ,x.getLastName()));
    }
}
