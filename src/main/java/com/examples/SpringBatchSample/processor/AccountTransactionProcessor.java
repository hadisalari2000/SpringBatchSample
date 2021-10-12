package com.examples.SpringBatchSample.processor;

import com.examples.SpringBatchSample.handler.exception.NegativeBalanceException;
import com.examples.SpringBatchSample.model.entity.Account;
import com.examples.SpringBatchSample.model.entity.AccountTransaction;
import com.examples.SpringBatchSample.model.enums.TransactionType;
import lombok.Synchronized;
import org.hibernate.type.descriptor.java.LocalDateTimeJavaDescriptor;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

@Component
public class AccountTransactionProcessor implements ItemProcessor<Account, List<AccountTransaction>> {

    @Override
    @Synchronized
    public List<AccountTransaction> process(Account account) throws Exception {
        List<AccountTransaction> list=new ArrayList<>();
        AccountTransaction accountTransaction;
        for(int i=0;i<=20;i++){
            TransactionType transactionType=getRandomBoolean()? TransactionType.DEPOSIT:TransactionType.WITHDRAWAL;
            BigDecimal amount=BigDecimal.valueOf(getRandomNumber(0,990000000));
            BigDecimal lastBalance=i==0? BigDecimal.valueOf(0) :list.get(i-1).getBalance();
            BigDecimal balance=transactionType.equals(TransactionType.DEPOSIT)
                    ?lastBalance.add(amount):lastBalance.subtract(amount);

            if(balance.compareTo(BigDecimal.valueOf(0))<0){
                throw  NegativeBalanceException.getInstance(Account.class, "TransactionCode",(account.getId()+"-"+i));
            }

            accountTransaction=AccountTransaction.builder()
                    .accountId(account.getId())
                    .amount(amount)
                    .balance(balance)
                    .transactionType(transactionType)
                    .transactionDate((new Date()).getTime())
                    .build();
            list.add(accountTransaction);
        }
        return list;
    }

    public int getRandomNumber(int min, int max) {
        Random random = new Random();
        return random.ints(min, max)
                .findFirst()
                .getAsInt();
    }

    public boolean getRandomBoolean() {
        return new Random().nextBoolean();
    }
}
