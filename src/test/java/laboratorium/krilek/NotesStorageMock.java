package laboratorium.krilek;

import com.google.common.collect.Multimap;

import java.util.ArrayList;
import java.util.List;

public class NotesStorageMock implements NotesStorage {
    public ArrayList<Note> addedNotesCalls;
    public ArrayList<Note> currentGetAllNotesOfOutput;
    public int clearCalls;
    public boolean cleared;
    private int nulledNotes;

    public NotesStorageMock() {
        this.addedNotesCalls = new ArrayList<>();
        this.currentGetAllNotesOfOutput = new ArrayList<>();
    }

    @Override
    public void add(Note note) {
        if(note == null) nulledNotes++;
        else{
            addedNotesCalls.add(note);
            cleared = false;
        }
    }

    @Override
    public List<Note> getAllNotesOf(String name) {
        if(cleared) return new ArrayList<Note>();
        else return this.currentGetAllNotesOfOutput;
    }

    @Override
    public void clear() {
        clearCalls++;
        cleared = true;
    }
}
