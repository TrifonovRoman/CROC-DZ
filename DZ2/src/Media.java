/**
 * Класс, представляющий носитель музыкальных композиций.
 */
public class Media {
    private String type;

    /**
     * Конструктор класса Media.
     * @param type Тип носителя (например, "Виниловая пластинка" или "CD").
     */
    public Media(String type) {
        this.type = type;
    }

    /**
     * Метод для получения типа носителя.
     * @return Тип носителя.
     */
    public String getType() {
        return type;
    }

    /**
     * Метод, который определяет совместимость носителя с песней.
     * @param song Песня, для которой нужно определить совместимость.
     * @return true, если носитель совместим с песней, иначе false.
     */
    public boolean isCompatibleWith(Song song) {
        // В данном примере считаем, что совместимость зависит только от типа носителя.
        return type.equalsIgnoreCase(song.getMediaRequired());
    }
}