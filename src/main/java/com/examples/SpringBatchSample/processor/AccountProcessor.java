package com.examples.SpringBatchSample.processor;

import com.examples.SpringBatchSample.dto.AccountDTO;
import com.examples.SpringBatchSample.dto.EmployeeDTO;
import com.examples.SpringBatchSample.model.entity.Account;
import com.examples.SpringBatchSample.model.entity.Employee;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
public class AccountProcessor implements ItemProcessor<AccountDTO, Account> {

    @Override
    public Account process(AccountDTO dto) {
        if(!ageIsValid(dto))
            return null;

        String calculateAge1=calculateAge1(dto.getAge());
        String calculateAge2=calculateAge2(calculateAge1,dto);
        Employee employee =Employee.builder()
                .employeeId(dto.getEmployeeId())
                .fullName(dto.getFirstName() + " - " + dto.getLastName())
                .email(dto.getEmail())
                .age(dto.getAge())
                .calculateAge1(calculateAge1)
                .calculateAge2(calculateAge2)
                .build();
        System.out.println("inside processor " + employee.toString());
        return employee;
    }

    private Boolean ageIsValid(EmployeeDTO employeeDTO){
        return employeeDTO.getAge() > 0;
    }

    private String calculateAge1(Integer age) {
        if(age/10<=1)
            return (age*1000)+"-"+age+"-0"+(age%10);
        else if(age/10<=2)
            return (age*2000)+"-"+age+"-0"+(age%10);
        else if(age/10<=3)
            return (age*3000)+"-"+age+"-0"+(age%10);
        else if(age/10<=4)
            return (age*4000)+"-"+age+"-0"+(age%10);
        else if(age/10<=5)
            return (age*5000)+"-"+age+"-0"+(age%10);
        else if(age/10<=6)
            return (age*6000)+"-"+age+"-0"+(age%10);
        else if(age/10<=7)
            return (age*7000)+"-"+age+"-0"+(age%10);
        else if(age/10<=8)
            return (age*8000)+"-"+age+"-0"+(age%10);
        else if(age/10<=9)
            return (age*9000)+"-"+age+"-0"+(age%10);
        else
            return (age*10000)+"-"+age+"-0"+(age%10);
    }

    private String calculateAge2(String calculateAge1, EmployeeDTO employeeDTO) {
        int cal1=Integer.parseInt(calculateAge1.substring(0,4))/100;
        if(cal1/7<2) {
            if(employeeDTO.getFirstName().toLowerCase().startsWith("s"))
                return "### first name is =>" +employeeDTO.getFirstName() + " -- " + employeeDTO.getAge() + " ###";
            else
                return "*** last name is =>"+employeeDTO.getLastName() + " -- " + employeeDTO.getAge() + " ***";
        }else{
            if(employeeDTO.getFirstName().startsWith("s"))
                return "&&& first name is =>" +employeeDTO.getFirstName() + " -- " + employeeDTO.getAge() + " &&&";
            else
                return "@@@ last name is =>"+employeeDTO.getLastName() + " -- " + employeeDTO.getAge() + " @@@";
        }
    }
}
