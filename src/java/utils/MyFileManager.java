/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;

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
        shopFolder += "/" + name;
        createFolder(shopFolder);

    }

    public static void saveProductImage(int shopID, int pId, List<Part> fileParts, HttpServletRequest request) throws IOException {

        String applicationPath = request.getServletContext().getRealPath("");
        System.out.println(applicationPath);
        String folderPath = applicationPath + "\\shops\\" + shopID + "\\" + pId;
        createFolder(folderPath);

        int count = 1;
        for (Part part : fileParts) {
            String filePath = folderPath + "\\" + count + ".png";

            File newFile = new File(filePath);
            File parentDir = newFile.getParentFile();
            if (!parentDir.exists()) {
                parentDir.mkdirs();  // Create parent directories if they don't exist
            }
            System.out.println(newFile.toPath());
            InputStream inputStream = part.getInputStream();
            Files.copy(
                    inputStream,
                    newFile.toPath(),
                    StandardCopyOption.REPLACE_EXISTING
            );
            count++;
        }

    }

    public static List<String> getProductImagesRealPath(int shopId, int pid, HttpServletRequest request) throws IOException {
        List<String> paths = new ArrayList<>();

        // Get the application path (where the images are stored)
        String applicationPath = request.getServletContext().getRealPath("");
        String folderPath = applicationPath + "\\shops\\" + shopId + "\\" + pid;

        // Create a File object for the folder path
        File folder = new File(folderPath);

        // Check if the folder exists
        if (folder.exists() && folder.isDirectory()) {
            // List all files in the directory
            File[] files = folder.listFiles();

            // Loop through all files and add the file paths to the list
            if (files != null) {
                for (File file : files) {
                    // Ensure it's a file and not a directory
                    if (file.isFile()) {
                        // Add the absolute file path to the list
                        paths.add(file.getAbsolutePath());
                    }
                }
            }
        }

        return paths;
    }

    public static String getProductImageRealPath(String imageID, HttpServletRequest request) throws IOException {
        // Get the application path (where the images are stored)
        String applicationPath = request.getServletContext().getRealPath("");
        String folderPath = applicationPath + "\\shops" +imageID;
        return folderPath;
    }

}
