package laboflieven.instructions.logic;

public interface Formula {
    boolean evaluate();
    Formula parent();
    Formula left();
    Formula right();
    boolean canHaveLeft();
    boolean canHaveRight();
}
