package com.karthickshiva.mkuresultscrap;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Configuration implements Constants
{
	private static Properties properties;

	static
	{
		try
		{
			properties = new Properties();
			InputStream in = Configuration.class.getClassLoader().getResourceAsStream(CONFIG_FILE);
			properties.load(in);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	public static String getProp(String key)
	{
		return properties.getProperty(key);
	}
}
