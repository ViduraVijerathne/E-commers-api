/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controllers.pub;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import utils.MyFileManager;

/**
 *
 * @author vidur
 */
@WebServlet(name = "ProductImages", urlPatterns = {"/product/images"})
public class ProductImages extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");

        JsonObject jsonObj = new Gson().fromJson(req.getReader(), JsonObject.class);
        List<String> ids = new ArrayList<>();

        String pid = jsonObj.get("pid").toString();
        String shopID = jsonObj.get("shopid").toString();
        try {
            List<String> list = MyFileManager.getProductImagesRealPath(Integer.parseInt(shopID), Integer.parseInt(pid), req);
            for (String path : list) {
                String[] part = path.split("shops");
                ids.add(part[1]);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        String json = new Gson().toJson(ids);
        resp.getWriter().write(json);

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Get the image ID from the request
        String imageid = req.getParameter("imageid");

        // Get the real path of the image file
        String path = MyFileManager.getProductImageRealPath(imageid, req);
        File newFile = new File(path);

        // Check if the file exists
        if (newFile.exists()) {
            // Set the content type based on the file type (e.g., "image/png")
            String mimeType = Files.probeContentType(newFile.toPath());
            if (mimeType == null) {
                // Default to binary stream if unable to detect
                mimeType = "application/octet-stream";
            }
            resp.setContentType(mimeType);

            // Write the image to the output stream
            try (OutputStream outputStream = resp.getOutputStream()) {
                Files.copy(newFile.toPath(), outputStream);
                outputStream.flush();
            }
        } else {
            // If the image file is not found, send a 404 response
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Image not found");
        }

    }

}
