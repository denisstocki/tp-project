package tp.warcaby.serwer;

/**
 * Enum holding info whose turn it is now
 * */
public enum TurnState {
    WHITE{
        @Override
        String getEnemyCode() {
            return "B";
        }

        @Override
        String getOwnCode() {
            return "W";
        }
    },
    BLACK{
        @Override
        String getEnemyCode() {
            return "W";
        }

        @Override
        String getOwnCode() {
            return "B";
        }
    };
    abstract String getEnemyCode();

    abstract String getOwnCode();
}
