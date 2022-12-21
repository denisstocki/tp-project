package tp.warcaby.klient;

public enum GameType {
    POLISH,
    CLASSIC,
    ENGLISH;

    private final int size = 8;

    public int getSize() {
        return size;
    }
}
