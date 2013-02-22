package org.daisy.pipeline.braille.liblouis;

import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.Map;

public interface Liblouisutdml {

	/**
	 * @param configFiles: array of file names (nullable)
	 * @param semanticFiles: array of file names (nullable)
	 * @param table: fully qualified table URL
	 * @param otherSettings: key/value pairs (nullable)
	 * @param input: the input file
	 * @param output: path to the output file
	 * @param configPath: directory that must contain liblouisutdml.ini & all files listed in configFiles and semanticFiles
	 * @param tempDir: directory where liblouisutdml can store temporary files
	 */
	public void translateFile(
			List<String> configFiles,
			List<String> semanticFiles,
			URL table,
			Map<String,String> otherSettings,
			File input,
			File output,
			File configPath,
			File tempDir);
}