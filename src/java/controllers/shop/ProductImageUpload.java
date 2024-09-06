/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controllers.shop;

import dto.ShopDTO;
import dto.UserDTO;
import entity.ProductEntity;
import entity.ShopEntity;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import org.hibernate.ObjectNotFoundException;
import repository.ProductRepository;
import repository.ShopRepository;
import utils.AuthUtil;
import utils.MyFileManager;

/**
 *
 * @author vidur
 */
@MultipartConfig
@WebServlet(name = "ProductImageUpload", urlPatterns = {"/auth/shop/product/images"})
public class ProductImageUpload extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pid = req.getParameter("pid");
        if (pid != null) {
            UserDTO user = AuthUtil.getCurrentUser(req);
            List<ShopEntity> shop = new ShopRepository().getByUserId(user.getId());
            if (!shop.isEmpty()) {
                ShopDTO shopDTO = shop.get(0).toDTO();
                try {
                    ProductEntity productEntity = new ProductRepository().getByProductID(Integer.parseInt(pid));
                    int shopID = shopDTO.getId();
                    int productID = productEntity.getId();

                    // Get the uploaded image parts (name attribute is 'images')
                    List<Part> fileParts = req.getParts().stream()
                            .filter(part -> "images".equals(part.getName()) && part.getSize() > 0)
                            .collect(Collectors.toList());

                    // Validate file count (1 to 3)
                    if (fileParts.size() < 1 || fileParts.size() > 3) {
                        resp.getWriter().write("You must upload between 1 to 3 images.");
                        resp.setStatus(400);
                        return;
                    }

                    for (Part part : fileParts) {
                        // Validate file type (only png and jpg)
                        String fileName = part.getSubmittedFileName();
                        if (!fileName.endsWith(".png") && !fileName.endsWith(".jpg") && !fileName.endsWith(".jpeg")) {
                            resp.getWriter().write("Only PNG and JPG images are allowed.");
                            resp.setStatus(400);

                            return;
                        }
                    }
                    MyFileManager.saveProductImage(shopID, productID, fileParts, req);

                    resp.getWriter().write("Images uploaded successfully.");

                } catch (ObjectNotFoundException ex) {
                    resp.getWriter().write("null product");
                    resp.setStatus(400);

                }

            }
        }

    }

}
