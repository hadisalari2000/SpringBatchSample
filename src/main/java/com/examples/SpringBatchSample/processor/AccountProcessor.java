package com.examples.SpringBatchSample.processor;

import com.examples.SpringBatchSample.model.dto.AccountDTO;
import com.examples.SpringBatchSample.model.entity.Account;
import com.examples.SpringBatchSample.model.entity.Person;
import com.examples.SpringBatchSample.repo.PersonRepo;
import com.examples.SpringBatchSample.utils.Constants;
import lombok.Synchronized;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
public class AccountProcessor implements ItemProcessor<AccountDTO, Account> {

    private final PersonRepo personRepo;
    private final ExecutionContext executionContext;

    public AccountProcessor(PersonRepo personRepo, ExecutionContext executionContext) {
        this.personRepo = personRepo;
        this.executionContext = executionContext;
    }

    @Override
    @Synchronized
    public Account process(AccountDTO dto) {

        long rowCounter = executionContext.getLong(Constants.COUNTER);
        long personId = getPersonId(rowCounter);
        Person person = personRepo.findPersonById(personId);

        if (person == null || person.getAccounts().size() > 3) {
            executionContext.put(Constants.COUNTER, rowCounter + 1);
            return null;
        } else {
            Account account = Account.builder()
                    .personId(personId)
                    .accountNumber(dto.getPart1() + "-" + dto.getPart2() + "-"
                            + (person.getId() + 10000000 )+ "-" + dto.getPart4())
                    .build();
            //System.out.println(rowCounter + "- Create PersonAccount for [" + personId + "] - [" + account.getAccountNumber() + "]");
            executionContext.put(Constants.COUNTER, rowCounter + 1);
            return account;
        }
    }

    private long getPersonId(Long rowCounter) {
        long counter = rowCounter > 4000
                ? rowCounter - 4000
                : rowCounter;
        return counter == 1 ? counter : counter / 2;
    }
}
