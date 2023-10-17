/**
 * Класс, представляющий музыкальную композицию.
 */
public class Song {
    private String artist;
    private String title;
    private String mediaRequired;

    /**
     * Конструктор класса Song.
     * @param artist Имя исполнителя (группы).
     * @param title Название песни.
     */
    public Song(String artist, String title) {
        this.artist = artist;
        this.title = title;
    }

    /**
     * Метод для получения имени исполнителя.
     * @return Имя исполнителя.
     */
    public String getArtist() {
        return artist;
    }

    /**
     * Метод для получения названия песни.
     * @return Название песни.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Метод для установки типа носителя, необходимого для данной песни.
     * @param mediaRequired Тип необходимого носителя (например, "CD" или "Виниловая пластинка").
     */
    public void setMediaRequired(String mediaRequired) {
        this.mediaRequired = mediaRequired;
    }

    /**
     * Метод для получения типа носителя, необходимого для данной песни.
     * @return Тип необходимого носителя.
     */
    public String getMediaRequired() {
        return mediaRequired;
    }
}