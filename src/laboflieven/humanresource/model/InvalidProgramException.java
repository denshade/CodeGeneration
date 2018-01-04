package laboflieven.humanresource.model;

/**
 * Created by Lieven on 3-1-2018.
 */
public class InvalidProgramException extends Exception
{
    public InvalidProgramException(String reason)
    {
        super(reason);
    }
}
