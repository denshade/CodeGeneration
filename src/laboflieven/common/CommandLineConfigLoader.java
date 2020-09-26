package laboflieven.common;

public class CommandLineConfigLoader
{
    public Configuration loadFromCommandLine(String[] args)
    {
        Configuration conf = new Configuration();
        for (String arg : args) {
            String[] keyValPair = arg.split("=");
            conf.setByKey(Configuration.ConfigurationKey.valueOf(keyValPair[0]), keyValPair[1]);
        }
        return conf;
    }
}
