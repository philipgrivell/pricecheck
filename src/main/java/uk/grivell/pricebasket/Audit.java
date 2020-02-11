package uk.grivell.pricebasket;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class Audit {
    private ArrayList<String> auditEntries = new ArrayList<>();

    public void addEntry(String entry) {
        auditEntries.add(entry);
    }

    public List<String> getEntries() {
        return auditEntries;
    }
}
