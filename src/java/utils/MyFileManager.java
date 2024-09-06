/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utils;

import java.io.File;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author vidur
 */
public class MyFileManager {

    static void createFolder(String folderPath) {
        File folder = new File(folderPath);
        // Check if the folder already exists
        if (!folder.exists()) {
            // Create the folder
            boolean created = folder.mkdir();  // For multiple levels use mkdirs()

            if (created) {
                System.out.println("Folder created successfully at " + folderPath);
            } else {
                System.out.println("Failed to create folder at " + folderPath);
            }
        } else {
            System.out.println("Folder already exists at " + folderPath);
        }

    }

   public static void createShopFolder(String name, HttpServletRequest request) {
        // Define the folder path (relative to the server or absolute path)
        String shopFolder = request.getServletContext().getRealPath("/") + "shops";
        createFolder(shopFolder);
        shopFolder += "/"+name;
        createFolder(shopFolder);
        
    }
}
