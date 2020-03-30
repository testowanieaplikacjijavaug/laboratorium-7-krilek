package laboratorium.krilek;

import org.assertj.core.data.Offset;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Java6Assertions.assertThatCode;

class NotesServiceImplTest {
    private NotesStorageMock mock;
    private NotesService noteService;

    @BeforeEach
    public void setup()
    {
        mock = new NotesStorageMock();
        noteService = NotesServiceImpl.createWith(mock);
    }
    @Test
    public void createWithNullStorage(){
        assertThatCode(() -> {
            NotesServiceImpl.createWith(null);
        }).doesNotThrowAnyException();
    }
    @Test
    public void clearWithNullStorage()
    {
        assertThatThrownBy(() -> {
            NotesServiceImpl.createWith(null).clear();
        }).isInstanceOf(NullPointerException.class);
    }

    @Test
    public void accessWithNullStorage()
    {
        assertThatThrownBy(() -> {
            NotesServiceImpl.createWith(null).averageOf("TestNote");
        }).isInstanceOf(NullPointerException.class);
    }

    @Test
    public void addWithNullStorage()
    {
        assertThatThrownBy(() -> {
            NotesServiceImpl.createWith(null).add(Note.of("TestNote", 5f));
        }).isInstanceOf(NullPointerException.class);
    }

    @Test
    public void addSingleNote()
    {
        noteService.add(Note.of("TestNote", 4));
        assertThat(mock.addedNotesCalls).hasSize(1);
    }

    @Test
    public void addThreeNotes()
    {
        noteService.add(Note.of("TestNote", 2));
        noteService.add(Note.of("TestNote", 3));
        noteService.add(Note.of("TestNote", 4));
        assertThat(mock.addedNotesCalls).hasSize(3);
    }

    @Test
    public void addWithDifferentName()
    {
        noteService.add(Note.of("TestNote2", 2f));
        noteService.add(Note.of("TestNote3", 3f));
        noteService.add(Note.of("TestNote4", 4f));
        assertThat(mock.addedNotesCalls).hasSize(3);
    }

    @Test
    public void clearAfterAdding()
    {
        noteService.add(Note.of("TestNote2", 2f));
        noteService.add(Note.of("TestNote3", 3f));
        noteService.add(Note.of("TestNote4", 4f));
        assertThat(mock.addedNotesCalls).hasSize(3);
        noteService.clear();
        assertThat(mock.addedNotesCalls).hasSize(3);
        assertThat(mock.cleared).isTrue();
    }

    @Test
    public void clearTwiceWithAddingShouldEmptyAnyway()
    {
        noteService.add(Note.of("TestNote2", 2f));
        noteService.add(Note.of("TestNote3", 3f));
        noteService.add(Note.of("TestNote4", 4f));
        assertThat(mock.addedNotesCalls).hasSize(3);
        noteService.clear();
        assertThat(mock.addedNotesCalls).hasSize(3);
        assertThat(mock.cleared).isTrue();
        noteService.add(Note.of("TestNote5", 5f));
        assertThat(mock.cleared).isFalse();
        noteService.clear();
        assertThat(mock.clearCalls).isEqualTo(2);
        assertThat(mock.cleared).isTrue();
    }

    @Test
    public void testAverageIsReturnedCorrectly()
    {
        List<Note> output = Arrays.asList(
                Note.of("TestNote", 2f),
                Note.of("TestNote", 3f),
                Note.of("TestNote", 4f));
        mock.currentGetAllNotesOfOutput = new ArrayList<>(output);
        assertThat(noteService.averageOf("TestNote")).isCloseTo(3f, Offset.offset(0.00001f));
    }

    @Test
    public void testAverageOnCleared()
    {
        noteService.add(Note.of("TestNote", 2));
        noteService.add(Note.of("TestNote", 3));
        noteService.add(Note.of("TestNote", 4));
        noteService.clear();
        assertThat(noteService.averageOf("TestNote")).isNaN();
    }


    @Test
    public void testAverageOnNonExisting()
    {
        assertThat(noteService.averageOf("TestNote")).isNaN();
    }

    @Test
    public void addNotesCheckIfResultCorrectThenClearItShouldBeNan()
    {
        noteService.add(Note.of("TestNote", 2f));
        noteService.add(Note.of("TestNote", 3f));
        noteService.add(Note.of("NotRelevant", 5f));
        noteService.add(Note.of("NotRelevant", 4f));
        List<Note> output = Arrays.asList(
                Note.of("TestNote", 2f),
                Note.of("TestNote", 3f));
        mock.currentGetAllNotesOfOutput = new ArrayList<>(output);
        assertThat(noteService.averageOf("TestNote")).isEqualTo(2.5f, Offset.offset(0.00001f));
        noteService.clear();
        assertThat(noteService.averageOf("TestNote")).isNaN();
    }




}