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
}

//        String savePath = request.getServletContext().getRealPath("") + "//shops/" + shopID + "/" + pId;
//        System.out.println("PATH ====================== " + savePath);
//        // Validate file count (1 to 3)
//        if (fileParts.size() < 1 || fileParts.size() > 3) {
////            resp.getWriter().write("You must upload between 1 to 3 images.");
//            return;
//        }
//
//        int imageCount = 1;
//        for (Part part : fileParts) {
//            // Validate file type (only png and jpg)
//            String fileName = part.getSubmittedFileName();
//            if (!fileName.endsWith(".png") && !fileName.endsWith(".jpg") && !fileName.endsWith(".jpeg")) {
////                resp.getWriter().write("Only PNG and JPG images are allowed.");
//                return;
//            }
//
//            // Save the file as 1.png, 2.png, 3.png, etc.
//            String filePath = savePath + "/" + imageCount + ".png";
//            System.out.println("FILEPATH " + filePath);
//            InputStream inputStream = part.getInputStream();
//            File newFile = new File(filePath);
//            Files.copy(
//                    inputStream,
//                    newFile.toPath(),
//                    StandardCopyOption.REPLACE_EXISTING
//            );
//            imageCount++;
//        }
