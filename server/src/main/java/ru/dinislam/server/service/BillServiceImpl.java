package ru.dinislam.server.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.dinislam.server.exceptions.ClosedBillException;
import ru.dinislam.server.exceptions.ObjectNotFoundException;
import ru.dinislam.server.model.Bill;
import ru.dinislam.server.repository.BillsRepository;

@Service
@RequiredArgsConstructor
public class BillServiceImpl implements BillService {

    private final BillsRepository billsRepository;

    @Override
    @Transactional
    public void fill(Long billId, Long value) {
        final Bill bill = billsRepository.findById(billId).orElseThrow(ObjectNotFoundException::new);
        checkClosure(bill);
        bill.setValue(bill.getValue() + value);
        billsRepository.save(bill);
    }

    @Override
    @Transactional
    public void writeOff(Long billId, Long value) {
        final Bill bill = billsRepository.findById(billId).orElseThrow(ObjectNotFoundException::new);
        checkClosure(bill);
        final long remainder = bill.getValue() - value;
        if (remainder > -1) {
            bill.setValue(remainder);
            billsRepository.save(bill);
        }
    }

    @Override
    @Transactional
    public void transfer(Long sourceId, Long targetId, Long value) {
        writeOff(sourceId, value);
        fill(targetId, value);
    }

    @Override
    @Transactional
    public void create(Long billId) {
        billsRepository.save(Bill.builder()
                .id(billId)
                .build());
    }

    @Override
    @Transactional
    public void close(Long billId) {
        final Bill bill = billsRepository.findById(billId).orElseThrow(ObjectNotFoundException::new);
        bill.setClosed(true);
        billsRepository.save(bill);
    }

    private void checkClosure(Bill bill) {
        if (bill.getClosed()) {
            throw new ClosedBillException();
        }
    }
}
