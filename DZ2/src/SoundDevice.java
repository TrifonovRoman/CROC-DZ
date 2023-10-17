/**
 * Класс, представляющий звуковоспроизводящее устройство.
 */
public class SoundDevice {
    private String name;

    /**
     * Конструктор класса SoundDevice.
     * @param name Название звуковоспроизводящего устройства.
     */
    public SoundDevice(String name) {
        this.name = name;
    }

    /**
     * Метод для воспроизведения музыки на устройстве.
     * @param media Носитель музыки.
     * @param song Песня, которую нужно воспроизвести.
     */
    public void playMusic(Media media, Song song) {
        if (media.isCompatibleWith(song)) {
            System.out.println(name + " воспроизводит " + song.getArtist() + " - " + song.getTitle());
        } else {
            System.out.println(name + " не может воспроизвести музыку с " + song.getMediaRequired());
        }
    }
}