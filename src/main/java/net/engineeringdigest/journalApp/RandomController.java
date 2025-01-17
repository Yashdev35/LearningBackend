package net.engineeringdigest.journalApp;

import net.engineeringdigest.journalApp.Entity.JournalEntry;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RandomController {

    @GetMapping("abc")
    public JournalEntry check(){
        JournalEntry j = new JournalEntry();
        j.setTitle("jai shree ram");
        j.setContent("Miocano wants money!!");
        return j;
    }
}
