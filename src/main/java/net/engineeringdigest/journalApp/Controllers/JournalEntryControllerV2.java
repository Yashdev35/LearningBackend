package net.engineeringdigest.journalApp.Controllers;

import net.engineeringdigest.journalApp.Entity.JournalEntry;
import net.engineeringdigest.journalApp.Service.JournalEntryService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping("/journal")
public class JournalEntryControllerV2 {

    @Autowired
    private JournalEntryService journalEntryService;

    public JournalEntryControllerV2(JournalEntryService journalEntryService) {
        this.journalEntryService = journalEntryService;
    }

    @GetMapping
    public ResponseEntity<List<JournalEntry>> getAllJournalEntries() {
        List<JournalEntry> entries = journalEntryService.getAllJournalEntries();
        return ResponseEntity.ok(entries);
    }

    @PostMapping
    public ResponseEntity<JournalEntry> addJournalEntry(@RequestBody JournalEntry myEntry) {
        try {
            myEntry.setDate(LocalDateTime.now());
            myEntry.setTime(String.valueOf(LocalTime.now().toNanoOfDay()));
            journalEntryService.saveJournalEntry(myEntry);
            return new ResponseEntity<>(myEntry, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("id/{myId}")
    public ResponseEntity<JournalEntry> getJournalEntryById(@PathVariable ObjectId myId) {
        return journalEntryService.getJournalEntryById(myId)
                .map(ResponseEntity::ok)
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("id/{myId}")
    public ResponseEntity<Void> deleteJournalEntryById(@PathVariable ObjectId myId) {
        try {
            if (!journalEntryService.getJournalEntryById(myId).isPresent()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            journalEntryService.deleteJournalEntryById(myId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("id/{id}")
    public ResponseEntity<JournalEntry> updateJournalEntry(
            @PathVariable ObjectId id,
            @RequestBody JournalEntry newEntry) {
        try {
            return journalEntryService.getJournalEntryById(id)
                    .map(currentEntry -> {
                        if (newEntry.getTitle() != null && !newEntry.getTitle().isEmpty()) {
                            currentEntry.setTitle(newEntry.getTitle());
                        }
                        if (newEntry.getContent() != null && !newEntry.getContent().isEmpty()) {
                            currentEntry.setContent(newEntry.getContent());
                        }
                        journalEntryService.saveJournalEntry(currentEntry);
                        return new ResponseEntity<>(newEntry, HttpStatus.OK);
                    })
                    .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping
    public ResponseEntity<JournalEntry> updateJournalEntry(@RequestBody JournalEntry newEntry) {
        if (newEntry.getId() == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return updateJournalEntry(newEntry.getId(), newEntry);
    }
}






