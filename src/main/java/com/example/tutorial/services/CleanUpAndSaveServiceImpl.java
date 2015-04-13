package com.example.tutorial.services;

import java.io.File;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Scanner;

import org.apache.tapestry5.ioc.annotations.Inject;

import com.example.tutorial.dao.PostDAO;
import com.example.tutorial.dao.SettingsDAO;

public class CleanUpAndSaveServiceImpl implements CleanUpAndSaveService {
	@Inject
	private PostDAO postDAO;
	@Inject
	private SettingsDAO settingsDAO;
	
	public void execute() {
		optimize();
		try {
			File f = new File(settingsDAO.getSettings().backupURL+"/save.sql");
			PrintWriter pw = new PrintWriter(new FileWriter(f));
			Scanner s = new Scanner(save());
			while (s.hasNextLine()) {
				pw.println(s.nextLine());
			}
			pw.flush();
			pw.close();
			s.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void optimize() {
		postDAO.optimize();
	}

	public InputStream save() {
		String dbName = "clasified_production";
        String dbUser = "root";
        String dbPass = "root123";
        try {
            String executeCmd = "";
            executeCmd = "/usr/bin/mysqldump -u " + dbUser + " -p" + dbPass + " " + dbName;

            Process runtimeProcess = Runtime.getRuntime().exec(executeCmd);
            int processComplete = runtimeProcess.waitFor();

            if (processComplete == 0) {
                System.out.println("Backup taken successfully");
            } else {
                System.out.println("Could not take mysql backup");
            }
            return runtimeProcess.getInputStream();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
		return null;
	}
}
