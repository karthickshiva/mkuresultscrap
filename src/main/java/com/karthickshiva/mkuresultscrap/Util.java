package com.karthickshiva.mkuresultscrap;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Util implements Constants
{
	public static String getRollNumber(Integer number, String prefix)
	{
		return prefix + number;
	}

	public static Document getResultDocument(String rollNumber) throws IOException
	{
		return Jsoup.connect(Configuration.getProp(SERVER_URL))
				.data(SEARCH, rollNumber)
				.data(SUBMIT, SUBMIT_PARAM_VALUE)
				.post();
	}

	public static String getNameFromTable(Element element)
	{
		Elements td = element.select("td");
		return td.get(3).text();
	}

	public static void addResult(Map<String, List<Record>> results, Elements tables, String rollNumber)
	{
		Element metaTable = tables.get(0);
		Element resultTable = tables.get(1);
		String name = getNameFromTable(metaTable);
		Elements tr = resultTable.select("tr");
		for (int i = 1; i < tr.size(); i++)
		{
			Elements td = tr.get(i).select("td");
			String subject = td.get(0).text();
			Record record = new Record();
			record.name = name;
			record.rollNumber = rollNumber;
			record.internalMark = td.get(1).text();
			record.externalMark = td.get(2).text();
			record.grade = td.get(3).text();
			record.result = td.get(4).text();
			addRecord(record, subject, results);
		}
		System.out.println("Processed for : " + name);
	}

	static void addRecord(Record record, String subject, Map<String, List<Record>> results)
	{
		if (!results.containsKey(subject))
		{
			results.put(subject, new ArrayList<>());
		}
		results.get(subject).add(record);
	}

	static File createFile(String subject)
	{
		String directory = Configuration.getProp(OUTPUT_FILE);
		File outputFile = new File(directory + subject + ".csv");
		File parent = outputFile.getParentFile();
		if (!parent.exists() && !parent.mkdirs())
		{
			throw new IllegalStateException("Couldn't create dir: " + parent);
		}
		return outputFile;
	}

	public static void saveAsCSV(String subject, List<Record> records) throws IOException
	{
		File outputFile = createFile(subject);
		BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile));
		CSVPrinter printer = new CSVPrinter(writer, CSVFormat.DEFAULT);
		printer.printRecord(HEADERS);
		for (Record record : records)
		{
			printer.printRecord(
					record.rollNumber,
					record.name,
					subject,
					record.internalMark,
					record.externalMark,
					record.grade,
					record.result
			);
		}
		printer.flush();
		System.out.println("Output file saved successfully : " + outputFile.getAbsolutePath());
	}
}
