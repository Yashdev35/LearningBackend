package net.engineeringdigest.journalApp.Entity;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * this is pjo class plain old java object, later declaring it as a document for ORM
 * if no collection provided in @Document, it will take the class name as collection name
 */
@Document(collection = "journal_entries")
@Data
public class JournalEntry {
    @Id//not a necessity but a good practice to have an id
    private ObjectId id;//changed id type from string to ObjectId which is id given by mongodb

    private String title;
    private String content;
    private LocalDateTime date;
    private String time;
}
