package laboratorium.krilek;

import java.util.List;

// Interfejs obsługujący przechowywanie danych
public interface NotesStorage {
    void add(Note note);
    List<Note> getAllNotesOf(String name);
    void clear();
}