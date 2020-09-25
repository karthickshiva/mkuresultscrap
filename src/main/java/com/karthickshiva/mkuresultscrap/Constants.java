package com.karthickshiva.mkuresultscrap;

import java.util.Arrays;
import java.util.List;

public interface Constants
{
	String SEARCH = "search";
	String SUBMIT = "submit";
	String SUBMIT_PARAM_VALUE = "Submit";
	String CONFIG_FILE = "config.properties";
	String FROM = "from";
	String TO = "to";
	String PREFIX = "prefix";
	String SERVER_URL = "server";
	String OUTPUT_FILE = "output_file";
	String SUMMARY = "summary";
	List<String> HEADER = Arrays.asList("Reg.No", "Student Name", "Subject Code", "Internal Mark", "External Mark", "Grade", "Result");
	List<String> SUMMARY_HEADER = Arrays.asList("Sub code", "Appeared Students", "No of students passed", "No of students failures", "% of pass");
}
