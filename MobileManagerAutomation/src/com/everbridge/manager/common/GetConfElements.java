package com.everbridge.manager.common;


public class GetConfElements {
	
	private static XMLParse confxml = new XMLParse();

	//Get screenshot configuration, toggle and  path
	public static final String screenshot_toggle = confxml.getConfElementText("screenshot_toggle");
	public static final String screenshot_path = confxml.getConfElementText("screenshot_path");
	public static final String screenshot_linkpath = confxml.getConfElementText("screenshot_linkpath");
	// Test data
	public static final String testdata_path = confxml.getConfElementText("testdata_path");
	public static final String datafile_name = confxml.getConfElementText("datafile_name");
	// Test link
	public static final String testlink_toggle = confxml.getConfElementText("testlink_toggle");
	public static final String testlink_apiurl = confxml.getConfElementText("testlink_apiurl");
	public static final String TESTLINK_THREADS = confxml.getConfElementText("testlink_threads");
	
}