package laboflieven.humanresource.model;

/**
 * Created by Lieven on 2-1-2018.
 */
public class Guy {
    public Integer valueHesHolding = null;
    public boolean hasValue()
    {
        return valueHesHolding != null;
    }

    public String toString()
    {
        return ""+valueHesHolding;
    }
}
