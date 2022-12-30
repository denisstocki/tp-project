package tp.warcaby.klient.board;

//CLASS RESPONSIBLE FOR CONTROLLING STATE OF BOARD AND BOARDVIEW

//public class BoardController implements EventHandler<ActionEvent> {
//    private final ClassicBoard currentClassicBoard;
//    private BoardView currentView;
//    private Pair<Integer, Integer> activePawn;
//    private int size = 8;
//
//    private Stage stage;
//
//    public BoardController(Stage stage) {
////        this.stage = stage;
//////        currentClassicBoard = initBoard();
//        activePawn = new Pair<>(-1, -1);
//        currentView = null;
//    }

//    public void setSize(int n) { currentClassicBoard.setSize(n); }

//    public int getSize() { return size; }
//
//    public void setPawn(int x, int y, PawnLook type, PawnColor color) {
//        currentClassicBoard.addPawn(x, y, type, color);
//    }
//
//    public void removePawn(int x, int y) {
//        currentClassicBoard.removePawn(x, y);
//    }
//
//    public void updatePawnPosition(int oldX, int oldY, int newX, int newY)
//    {
//        if(currentClassicBoard.getPawn(oldX, oldY).get_color() == PawnColor.BLACK)
//        {
//            currentClassicBoard.addPawn(newX, newY, PawnLook.NORMAL, PawnColor.BLACK);
//        }
//        else
//        {
//            currentClassicBoard.addPawn(newX, newY, PawnLook.NORMAL, PawnColor.WHITE);
//        }
//        currentClassicBoard.removePawn(oldX, oldY);
//    }
//
//    public void promoteToQueen(int x, int y) {
//        currentClassicBoard.promoteToQueen(x, y);
//    }
//
//    public void showView() {
//        Platform.setImplicitExit(false);
//        Platform.runLater(() -> {
//            currentView = new BoardView(currentClassicBoard.getPieces(),this);
//        });
//    }
//
//    @Override
//    public void handle(ActionEvent actionEvent) {
//
//        Object source = actionEvent.getSource();
//        Button[][] buttons = currentView.getButtons();
//
//        for(int i = 0; i < currentClassicBoard.getSize(); i++) {
//            for(int j = 0; j < currentClassicBoard.getSize(); j++) {
//                if(source.equals(buttons[i][j]))
//                {
//                    if(activePawn.getKey() == i && activePawn.getValue() == j) {
//                        System.out.println("You clicked active pawn");
//                       continue;
//                    }
//                    else if (currentClassicBoard.getPawn(i, j) != null && activePawn.getKey() == -1) {
//                        System.out.println("Setting up first active pawn");
//                        activePawn = new Pair<>(i, j);
//                    }
//                    else if (currentClassicBoard.getPawn(i, j) == null) {
//                        System.out.println("Trying to move active pawn there");
//                        updatePawnPosition(activePawn.getKey(), activePawn.getValue(), i, j);
//                        activePawn = new Pair<>(i, j);
//                    } else {
//                        System.out.println("changing active pawn");
//                        activePawn = new Pair<>(i, j);
//                    }
//
//                    currentView = new BoardView(currentClassicBoard.getPieces(), this);
//                    return;
//                }
//            }
//        }
//    }
//
//    public ClassicBoard initBoard() {
//        ClassicBoard initialClassicBoard = new ClassicBoard(size);
//        for(int row = 0; row < size; row++) {
//            for(int col = 0; col < size; col++) {
//                if ((col + row) % 2 == 0) {
//                    if (col <= 2) {
//                        initialClassicBoard.addPawn(row, col, PawnLook.NORMAL, PawnColor.WHITE);
//                    } else if (col >= 5) {
//                        initialClassicBoard.addPawn(row, col, PawnLook.NORMAL, PawnColor.BLACK);
//
//                    }
//                }
//            }
//        }
//        return initialClassicBoard;
//    }
//
//    private void showStage() {
//        stage.setScene(new Scene(currentView));
//        stage.show();
//        stage.setResizable(false);
//    }
//}