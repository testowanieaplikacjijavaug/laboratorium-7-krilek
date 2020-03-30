package laboratorium.krilek;

// Interfejs odpowiedzialny za obsługę serwisu programu
public interface NotesService {
    void add(Note note);
    float averageOf(String name);
    void clear();
}