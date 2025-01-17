package net.engineeringdigest.journalApp.Service;

import net.engineeringdigest.journalApp.Entity.JournalEntry;
import net.engineeringdigest.journalApp.repositroy.JournalEntryRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class JournalEntryService {

    @Autowired
    private JournalEntryRepository journalEntryRepository;//dependency injection, impl is provided by spring

    public void saveJournalEntry(JournalEntry journalEntry){
        journalEntryRepository.save(journalEntry);
    }

    public Optional<JournalEntry> getJournalEntryById(ObjectId id){
        return journalEntryRepository.findById(id);
    }

    public List<JournalEntry> getAllJournalEntries(){
        return journalEntryRepository.findAll();
    }

    public void deleteJournalEntryById(ObjectId id){
        journalEntryRepository.deleteById(id);
    }
}
