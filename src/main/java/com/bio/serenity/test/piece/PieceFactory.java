package com.bio.serenity.test.piece;

@FunctionalInterface
public interface PieceFactory {
   public abstract Piece getPiece(int r1, int c1, int r2, int c2);
}
