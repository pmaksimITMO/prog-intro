//package MNKgame;
//
//public class CheatingPlayer implements Player {
//    @Override
//    public Move move(BoardPosition position) {
//        final Board board = (Board) position;
//        for (int r = 0; r < 3; r++) {
//            for (int c = 0; c < 3; c++) {
//                final Move move = new Move(r, c, position.getTurn());
//                if (position.isValid(move)) {
//                    board.makeMove(move);
//                    return move;
//                }
//            }
//        }
//        return null;
//    }
//}
