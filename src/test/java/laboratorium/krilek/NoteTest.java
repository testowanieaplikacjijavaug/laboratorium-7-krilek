/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package laboratorium.krilek;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class NoteTest {
    @Test
    void testGetName(){
        assertThat(Note.of("TestName", 3).getName()).isEqualTo("TestName");
    }

    @Test
    void testGetNote(){
        assertThat(Note.of("NotRelevant", 4).getNote()).isEqualTo(4);
    }

    @Test
    void testInvalidNameNullString(){
        assertThatThrownBy(() -> Note.of(null, 2))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Imię ucznia nie może być null");
    }
    @Test
    void testInvalidNameEmptyString(){
        assertThatThrownBy(() -> Note.of("", 2))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Imię ucznia nie może być puste");
    }

    @Test
    void testInvalidNoteBelowOne() {
        assertThatThrownBy(() -> Note.of("NotRelevant", 0))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Niewłaściwa ocena");
    }

    @Test
    void testInvalidNoteAbove6() {
        assertThatThrownBy(() -> Note.of("NotRelevant", 7))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Niewłaściwa ocena");
    }
}