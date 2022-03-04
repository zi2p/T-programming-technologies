package com.company.lab1.banks.entities;

import com.company.lab1.banks.entities.methods.TransferLimit;
import com.company.lab1.banks.entities.methods.percentage.MethodPercentageChange;

public interface Banks {
    /* В банке есть Счета и Клиенты.
         Для банков требуется реализовать методы изменений процентов и лимитов нa перевод.
         Также требуется реализовать возможность пользователям подписываться на информацию о таких изменениях -
         банк должен предоставлять интерфейс для подписывания. Например, когда происходит изменение лимита для
         кредитных карт - все пользователи, которые подписались и имеют кредитные карты, должны получить уведомление.*/
    public void setMethodPercentageChange(MethodPercentageChange percentageChange);
    public void setMethodTransferLimit(TransferLimit transferLimit);
}
