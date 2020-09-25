package com.karthickshiva.mkuresultscrap;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.*;

public class ResultFetch implements Constants
{
	public static void main(String[] args) throws IOException
	{
		Integer from = Integer.parseInt(Configuration.getProp(FROM));
		Integer to = Integer.parseInt(Configuration.getProp(TO));
		String prefix = Configuration.getProp(PREFIX);
		Map<String, List<Record>> results = new HashMap<>();

		for (int i = from; i <= to; i++)
		{
			String rollNumber = Util.getRollNumber(i, prefix);
			Document doc = Util.getResultDocument(rollNumber);
			Elements tables = doc.select("table");
			if(tables.size() == 4)
			{
				Util.addResult(results, tables, rollNumber);
			}
			else
			{
				System.out.println("Invalid entry for : " + rollNumber);
			}
		}

		if(!results.isEmpty())
		{
			for(String subject : results.keySet())
			{
				Util.saveAsCSV(subject, results.get(subject));
			}

			Util.saveAnalysisCSV(results);
			System.out.println("Completed successfully");
		}
	}
}
