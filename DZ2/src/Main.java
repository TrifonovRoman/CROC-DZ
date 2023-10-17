/**
 * Этот класс демонстрирует работу музыкальной системы, используя созданные классы SoundDevice, Media и Song.
 */
public class Main {
    /**
     * Главный метод программы, который инициализирует объекты и демонстрирует их взаимодействие.
     * @param args Массив аргументов командной строки (не используется).
     */
    public static void main(String[] args) {
        // Создаем носители музыки
        Media vinyl = new Media("Виниловая пластинка");
        Media cd = new Media("CD");

        // Создаем песни
        Song song1 = new Song("The Beatles", "Yesterday");
        Song song2 = new Song("Linkin Park", "In the End");
        Song song3 = new Song("Eminem", "Lose Yourself");
        Song song4 = new Song("Frank Sinatra", "Fly Me To The Moon");

        // Устанавливаем необходимые носители для песен
        song1.setMediaRequired("Виниловая пластинка");
        song2.setMediaRequired("CD");
        song3.setMediaRequired("CD");
        song4.setMediaRequired("Виниловая пластинка");

        // Создаем звуковоспроизводящие устройства
        SoundDevice turntable = new SoundDevice("Виниловая вертушка");
        SoundDevice cdPlayer = new SoundDevice("CD-плеер");

        // Воспроизводим песни на устройствах
        turntable.playMusic(vinyl, song1);
        cdPlayer.playMusic(cd, song2);
        turntable.playMusic(vinyl, song3);
        cdPlayer.playMusic(cd, song4);
    }
}
