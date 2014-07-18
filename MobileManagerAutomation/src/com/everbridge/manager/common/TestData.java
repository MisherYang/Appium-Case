package com.everbridge.manager.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Random;

import com.everbridge.manager.common.GetConfElements;


	
	/**
	 * TestData class is used to generate test data.First, load a test.properties file as a testdata providor,then parse the
	 * file to Map .We can use get() metohd to get the value we want. Simple example: Map<String, String> testdata = new
	 * TestData("testdata.properties"); testdata.get("key_for_example"); Besides, TestData also provide some method to
	 * generate data, like id, random string.
	 * 
	 * @ClassName: TestData
	 */

	public class TestData {

		private static String testdata_path = GetConfElements.testdata_path;

		public static Map<String, String> parseTestData(String dataFileName) {

			String dataFullName;
			String splitPathName[] = testdata_path.replaceAll("\\\\", "/")
				.split("/");
			String tempPathName = "";
			if (splitPathName.length >= 1) {
				for (int i = 0; i < splitPathName.length; i++) {
					tempPathName = tempPathName + File.separator + splitPathName[i];
				}
			}
			String tempFileName = "";
			String splitFileName[] = dataFileName.replaceAll("\\\\", "/")
				.split("/");
			if (splitFileName.length >= 1) {

				for (int i = 0; i < splitFileName.length; i++) {
					tempFileName = tempFileName + File.separator + splitFileName[i];
				}
			}
			dataFullName = System.getProperty("user.dir") + File.separator
					+ tempPathName + File.separator + tempFileName;
			if (!new File(dataFullName).exists()) {
				throw new RuntimeException("TestData not found!");
			}
			return getPropertiesMap(dataFullName);
		}

		/**
		 * Get testdata froms .properties file, and save testdata as Map<String, String>
		 * 
		 * @param String
		 * @return Map<String, String>
		 */
		public static Map<String, String> getPropertiesMap(String propertiesName) {
			Properties props = getPropertiesFromCommonFilePath(propertiesName);
			Map<String, String> keyInfo = null;
			try {
				keyInfo = new HashMap<String, String>();
				Enumeration<?> en = props.propertyNames();
				while (en.hasMoreElements()) {
					String key = (String) en.nextElement();
					String value = props.getProperty(key);
					keyInfo.put(key, value);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return keyInfo;
		}

		/**
		 * Get the .properties file as testdata file,need a filepath for the .propertie
		 * 
		 * @param String
		 * @return Properties
		 */
		public static Properties getPropertiesFromCommonFilePath(String filePath) {
			if (filePath == null || "".equalsIgnoreCase(filePath)) {
				try {
					throw new Exception("No TestData file given!");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			InputStream input = null;
			Properties properties = new Properties();
			try {
				File propertiesFile = new File(filePath);
				if (!propertiesFile.exists()) {
					return null;
				}
				input = new FileInputStream(propertiesFile);
				properties.load(input);
				// System.out.println("File in"+filePath+"load successful!");
				return properties;
			} catch (IOException e) {
				try {
					throw new Exception(
							"Load"
									+ filePath
									+ "fail，maybe the file not exist or encounter I/O error!");
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			} finally {
				try {
					input.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			return properties;
		}

		/**
		 * Get a unique id of string type,a id is a date string ,i.e. 20131111145618
		 * 
		 * @Method: getUniqId
		 * @return String
		 */
		public static final String getUniId() {
			SimpleDateFormat fomatter = new SimpleDateFormat("yyyyMMddHHmmss");
			String id = fomatter.format(new Date());
			return id;
		}

		/**
		 * Get a unique id of string type,a id is a time string to millilseconds ,i.e.
		 * 
		 * @MethodName: getUIDMillS
		 * @return: String
		 * @throws:
		 */
		public static final String getUIDMillS() {
			SimpleDateFormat fomatter = new SimpleDateFormat("yyMMddHHmmssSSS");
			String id = fomatter.format(new Date())+randomNum(3);
			return id;
		}

		/**
		 * Get a unique id of int type,a id is a date of int type ,i.e. 20131111145618
		 * 
		 * @MethodName: getUniID
		 * @return: Integer
		 */
		public static final Integer getUniNumId() {
			return Integer.parseInt(getUniId());
		}

		/**
		 * Get a random String consist of number and letter, need a parameter length.<br> 
		 * Simple example: 
		 * TestData.randomString(10);
		 * 
		 * @MethodName: getUniID
		 * @return: Integer
		 */
		public static final String randomString(int length) {
			Random randGen = null;
			char[] numbersAndLetters = null;
			if (length < 1) {
				return null;
			}
			if (randGen == null) {
				randGen = new Random();
				numbersAndLetters = ("0123456789abcdefghijklmnopqrstuvwxyz"
						+ "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ")
					.toCharArray();
			}
			char[] randBuffer = new char[length];
			for (int i = 0; i < randBuffer.length; i++) {
				randBuffer[i] = numbersAndLetters[randGen.nextInt(71)];
			}
			return new String(randBuffer);
		}

		/**
		 * Return a number string with the specified length.
		 * 
		 * @MethodName: randomNum
		 * @return: String
		 * @throws:
		 */
		public static final String randomNum(int length) {
			Random randGen = null;
			char[] numbers = null;
			if (length < 1) {
				return null;
			}
			if (randGen == null) {
				randGen = new Random();
				numbers = ("0123456789").toCharArray();
			}
			char[] randBuffer = new char[length];
			for (int i = 0; i < randBuffer.length; i++) {
				randBuffer[i] = numbers[randGen.nextInt(9)];
			}
			return new String(randBuffer);
		}
		
		/**
		 * Return a number string between [begin, end]
		 *
		 * @MethodName: randomNum
		 * @return: String 
		 * @throws: 
		 */
		public static final String randomNum(int begin, int end) {
			int a = (int) (Math.random()*(end-begin+1))+begin;
			return String.valueOf(a);
		}
		
		public static final String randomEmail(int length){
			try {
				Random randGen = null;
				Random middleGen = null;
				Random endGen = null;
				char[] numbersAndLetters = null;
				char[] allLetters = null;
				char[] letters = null;
				if (length<5) {
					return null;
				}
				if (randGen == null) {
					randGen = new Random();
					allLetters = ("0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"+"~!#$%^&*_+")
						.toCharArray();
				}
				int frontLength = ((int) (Math.random()*(length-4)))+1;
				char[] frontBuffer = new char[frontLength];
				for (int i = 0; i < frontBuffer.length; i++) {
					frontBuffer[i] = allLetters[randGen.nextInt(71)];
				}
				
				if (middleGen == null) {
					middleGen = new Random();
					numbersAndLetters = ("0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ")
						.toCharArray();
				}
				int middleLength = ((int) (Math.random()*(length-frontLength-3)))+1;
				char[] middleBuffer = new char[middleLength];
				for (int i = 0; i < middleBuffer.length; i++) {
					middleBuffer[i] = numbersAndLetters[randGen.nextInt(61)];
				}
				
				if (endGen == null) {
					endGen = new Random();
					letters = ("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ")
						.toCharArray();
				}
				int endLength = length-frontLength-middleLength-2;		
				char[] endBuffer = new char[endLength];
				for (int i = 0; i < endBuffer.length; i++) {
					endBuffer[i] = letters[randGen.nextInt(51)];
				}
				return String.valueOf(frontBuffer)+"@"+String.valueOf(middleBuffer)+"."+String.valueOf(endBuffer);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			return null;
		}
	}
	

