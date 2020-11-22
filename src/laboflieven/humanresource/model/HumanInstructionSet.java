package laboflieven.humanresource.model;

/**
 * Created by lveeckha on 4/06/2015.
 */
public enum HumanInstructionSet
{
    INBOX, OUTBOX, LOOP, CopyFrom, CopyTo, ADD, JumpIfZero, Sub, JumpIfNegative, BumpMin, BumpPlus;

    public boolean isSingleRegister() {
        return (this.equals(CopyFrom) || this.equals(CopyTo)|| this.equals(ADD) || this.equals(Sub) || this.equals(BumpMin) ||this.equals(BumpPlus));
    }

    public boolean isLoop()
    {
        return this.equals(LOOP) || this.equals(JumpIfZero) || this.equals(JumpIfNegative);
    }
}
